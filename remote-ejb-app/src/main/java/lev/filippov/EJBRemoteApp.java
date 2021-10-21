package lev.filippov;

import org.jboss.ejb.client.EJBClient;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.Properties;

public class EJBRemoteApp {

    public static void main(String[] args) throws IOException, NamingException {
        Context context = createInitialContext();

        RemoteProductService prodService = (RemoteProductService) context
                .lookup("ejb:/first-jsf-app/ProductServiceImpl!lev.filippov.RemoteProductService");
        prodService.getAllRemote()
                .forEach(p -> System.out.printf("%d\t%s\t%s\n", p.getId(), p.getName(), p.getPrice().toString()));
    }

    public static Context createInitialContext() throws IOException, NamingException {
        final Properties env = new Properties();
        env.load(EJBClient.class
                .getClassLoader()
                .getResourceAsStream("wildfly-jndi.properties"));
        return new InitialContext(env);
    }

}
