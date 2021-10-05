package lev.filippov.listeners;

import lev.filippov.persistance.PersistanceBean;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

//TODO: здесь в servletcontext внедряется persistance-bean
@WebListener
public class PersistentInjectionListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        sce.getServletContext().setAttribute("persistanceBean", PersistanceBean.getInstance());
    }
}
