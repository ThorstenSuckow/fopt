package fopt6.sandbox;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

class ClientThread extends Thread {

    public void run() {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {

        }
    }

}

@WebServlet("/sandbox/threadpolling-servlet")
public class ThreadPollingServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        ClientThread clientThread = null;

        synchronized (session) {

            if (session.getAttribute("start") == null) {
                session.setAttribute("start", "1");
                clientThread = new ClientThread();
                clientThread.start();
                session.setAttribute("clientThread", clientThread);

            }
        }

        response.setHeader("Refresh", "1");

        PrintWriter pw = response.getWriter();

        pw.println("<html><head></head><body><div>");

        synchronized (session) {
            if (session.getAttribute("clientThread") != null) {
                clientThread = (ClientThread) session.getAttribute("clientThread");

                if (clientThread.isAlive()) {

                    /*
                    //This block will cause the browser to hang since join waits for the thread to end.
                    try {
                        clientThread.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    */
                    pw.println("clientThread still alive");
                } else {
                    try {
                        clientThread.join();
                    } catch (InterruptedException ignored) {
                    }
                    pw.println("clientThread ended");
                }
            }
        }

        pw.println("</div></body></html>");

    }

}
