package fopt6.sandbox.servletContexListener;

import fopt6.sandbox.util.Counter;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class Listener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        Counter counter = new Counter();
        sce.getServletContext().setAttribute("counter_2", counter);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Context destroyed." + sce);
    }


}
