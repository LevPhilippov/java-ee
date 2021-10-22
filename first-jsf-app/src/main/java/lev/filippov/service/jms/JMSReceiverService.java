package lev.filippov.service.jms;



import lev.filippov.ProductRemoteDto;
import lev.filippov.models.dto.ProductDto;
import lev.filippov.service.intefaces.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/exported/jms/queue/productQueue")
        }
)
public class JMSReceiverService implements MessageListener {

    @EJB
    ProductService productService;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public void onMessage(Message message) {
        logger.info("New JMS message");
        if(message instanceof ObjectMessage) {
            ObjectMessage om = (ObjectMessage) message;
            try {
                ProductRemoteDto remoteDto = (ProductRemoteDto) om.getObject();
                ProductDto dto = new ProductDto(remoteDto.getId(), remoteDto.getName(), remoteDto.getPrice(), null, null, null, null);
                productService.save(dto);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
