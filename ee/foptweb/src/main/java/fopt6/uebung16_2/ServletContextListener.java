package fopt6.uebung16_2;

import fopt6.sandbox.util.Counter;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.annotation.WebListener;


@WebListener
public class ServletContextListener implements jakarta.servlet.ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        sce.getServletContext().setAttribute("uebung16_2_counter", new Counter());

    }
}
