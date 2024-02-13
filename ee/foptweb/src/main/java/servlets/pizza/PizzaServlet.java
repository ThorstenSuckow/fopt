package servlets.pizza;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;

@WebServlet(urlPatterns="/zusatzaufgaben/pizza-servlet")
public class PizzaServlet extends HttpServlet {

    private final static int MAX_AGE = 10;

    private int bestellungId;
    record Bestellung(String kundenname, String kundennummer, String adresse, String pizzaliste, long letzteBestellung){

    }

    private HashMap<String, Bestellung> bestellungen;

    public void init() {
        bestellungId = 0;
        bestellungen = new HashMap<>();
    }

    public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html");

        try {
            Writer p = response.getWriter();

            String form = getForm(request);

            p.write(form);

        } catch (IOException e) {
            response.setStatus(500);
        }

    }



    public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html");

        Cookie pizzaCookie = getPizzaCookie(request);
        if (pizzaCookie == null) {
            pizzaCookie = createPizzaCookie();
        }

        Bestellung bestellung = new Bestellung(
            request.getParameter("kundenname"),
            request.getParameter("kundennummer"),
            request.getParameter("adresse"),
            request.getParameter("pizzaliste"),
            System.currentTimeMillis()
        );

        bestellungen.put(pizzaCookie.getValue(), bestellung);
        response.addCookie(pizzaCookie);

        try {
            response.getWriter().print("Vielen Dank für ihre Bestellung!");
        } catch (IOException e) {
            response.setStatus(500);
        }

    }

    private String getForm(HttpServletRequest request) {

        Cookie pizzaCookie = getPizzaCookie(request);
        Bestellung bestellung = getBestellungFromCookie(pizzaCookie);

        String kundenname = "";
        String kundennummer = "";
        String adresse = "";
        String pizzaliste = "";

        if (bestellung != null) {
            kundenname = bestellung.kundenname();
            kundennummer = bestellung.kundennummer();
            adresse = bestellung.adresse();
            pizzaliste = bestellung.pizzaliste();
        }


        return "<form method=\"POST\">" +
                "<input type=\"text\" value=\"" + kundenname + "\" placeholder=\"kundenname\" name=\"kundenname\" /><br />"+
                "<input type=\"text\" value=\"" + kundennummer + "\" placeholder=\"kundennummer\"  name=\"kundennummer\" /><br />"+
                "<textarea value=\"" + adresse + "\" name=\"adresse\" ></textarea><br />" +
                "<select name=\"pizzaliste\">" +
                "<option " + (pizzaliste.equals("Vegetaria") ? "selected" : "") + " value=\"Vegetaria\">Vegetaria</option>" +
                "<option " + (pizzaliste.equals("Diavolo") ? "selected" : "") + " value=\"Diavolo\">Diavolo</option>" +
                "<option " + (pizzaliste.equals("Quattro Stagioni") ? "selected" : "") + " value=\"Quattro Stagioni\">Quattro Stagioni</option>" +
                "</select><br />" +
                "<input type=\"submit\" />" +
                "</form>";
    }

    private Bestellung getBestellungFromCookie(Cookie pizzaCookie) {

       if (pizzaCookie != null) {
            String key = pizzaCookie.getValue();
            return bestellungen.get(key);
        }

        return null;
    }


    private Cookie getPizzaCookie(HttpServletRequest request) {
        Cookie pizzaCookie = null;
        if (request.getCookies() != null) {
            for (Cookie cookie: request.getCookies()) {
                if (cookie.getName().equals("pizzaCookie")) {
                    pizzaCookie = cookie;
                    break;
                }
            }
        }


        if (pizzaCookie != null) {
            String key = pizzaCookie.getValue();
            Bestellung bestellung = bestellungen.get(key);
            if (bestellung == null) {
                pizzaCookie = null;
            } else {
                long letzteBestellung = bestellung.letzteBestellung();
                if (System.currentTimeMillis() - letzteBestellung > MAX_AGE) {
                    pizzaCookie = null;
                    bestellungen.remove(key);
                }
            }
        }

        return pizzaCookie;
    }

    private Cookie createPizzaCookie() {
        Cookie pizzaCookie = new Cookie("pizzaCookie", "bestellung" + (bestellungId++));
        pizzaCookie.setMaxAge(MAX_AGE);

        return pizzaCookie;
    }

}
