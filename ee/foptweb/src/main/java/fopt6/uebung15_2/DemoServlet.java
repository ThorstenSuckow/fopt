package fopt6.uebung15_2;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.util.Date;

/**
 * init() gets called once when the first request is made to the servlet after webapp has been (re)deployed
 */
//@WebServlet("/uebung15_2/demo-servlet")
/**
 * init() gets called once when webapp has been (re)deployed
 */
@WebServlet(urlPatterns = "/uebung15_2/demo-servlet", loadOnStartup = 1)
public class DemoServlet extends HttpServlet {

    public void init() {
        System.out.println("init() uebung15_2/demo-servlet at " + (new Date()));
    }

    public void destroy() {
        /**
         * Called when the webapp is being undeployed.
         */
        System.out.println("destroy() uebung15_2/demo-servlet at " + (new Date()));
    }

}
