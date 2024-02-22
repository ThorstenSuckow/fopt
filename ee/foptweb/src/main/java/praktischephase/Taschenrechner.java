package praktischephase;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns="/praktischephase/taschenrechner")
public class Taschenrechner extends HttpServlet {

    private record ClientResultWithCookies (String result, Cookie[] cookies){

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html");

        boolean useCookies = false;

        try {
            PrintWriter writer = response.getWriter();

            ClientResultWithCookies result = getResult(request, response, useCookies);

            if (useCookies) {
                writer.print(getSimpleForm(request, result.cookies()));
            } else {
                writer.print(getButtonedForm(request, result.cookies()));
            }

            writer.print(result.result());

            writer.flush();
        } catch (IOException e) {
            response.setStatus(500);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }


    private ClientResultWithCookies getResult(HttpServletRequest request, HttpServletResponse response, boolean useCookies) {

        String result = "";

        Cookie[] cookies;

        if (useCookies) {
            cookies = request.getCookies();
        } else {
            List<Cookie> tmpList = new ArrayList<>();
            HttpSession session = request.getSession();
            Enumeration<String> names = session.getAttributeNames();
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                tmpList.add(new Cookie(name, (String)session.getAttribute(name)));
            }
            cookies = tmpList.toArray(Cookie[]::new);
        }


        String operator = request.getParameter("operator");
        if (request.getParameter("op1") != null && request.getParameter("op2") != null) {

            try {
                double x = Double.parseDouble(request.getParameter("op1"));
                double y = Double.parseDouble(request.getParameter("op2"));

                if ((operator.equals("divide") || operator.equals("/")) && y == 0) {

                    result ="Division durch 0.";

                } else {

                    switch (operator) {
                        case "divide":
                        case "/":
                            result = String.valueOf(x / y);
                            break;
                        case "add":
                        case "+":
                            result = String.valueOf(x + y);
                            break;
                        case "subtract":
                        case "-":
                            result = String.valueOf(x - y);
                            break;
                        case "multiply":
                        case "*":
                            result = String.valueOf(x * y);
                            break;
                    }

                    cookies = new Cookie[]{
                            new Cookie("op1", "" + x),
                            new Cookie("op2", "" + y),
                            new Cookie("operator", operator)
                    };

                    if (useCookies) {
                        response.addCookie(new Cookie("op1", "" + x));
                        response.addCookie(new Cookie("op2", "" + y));
                        response.addCookie(new Cookie("operator", operator));
                    } else {
                        HttpSession session = request.getSession();
                        session.setAttribute("op1", "" + x);
                        session.setAttribute("op2", ""+ y);
                        session.setAttribute("operator", operator);
                    }

                }

            } catch (NumberFormatException e) {
                result = "Bitte gültige Werte eingeben!";
            }

        }

        return new ClientResultWithCookies("<div>" + result + "</div>", cookies);

    }


    private String getSimpleForm(HttpServletRequest request, Cookie[] cookies) {

        String op1 = "";
        String op2 = "";
        String operator = "add";

        if (cookies != null) {

            for (int i = 0; i < cookies.length; i++) {
                switch (cookies[i].getName()) {
                    case "op1":
                        op1 = cookies[i].getValue();
                        break;
                    case "op2":
                        op2 = cookies[i].getValue();
                        break;
                    case "operator":
                        operator = cookies[i].getValue();
                        break;

                }
            }
            
        }
        
        
        return "<form method=\"GET\">" +
                "<div>1. Operand <input type=\"text\" value=\"" + op1 + "\" name=\"op1\"/></div>" +
                "<div>2. Operand <input type=\"text\" value=\" " + op2 + "\" name=\"op2\"/></div>" +
                "<div><input type=\"radio\" name=\"operator\" " + (operator.equals("add") ? "checked" : "") + " value=\"add\" /> + </div>" +
                "<div><input type=\"radio\" name=\"operator\" " + (operator.equals("subtract") ? "checked" : "")  + "  value=\"subtract\" /> - </div>" +
                "<div><input type=\"radio\" name=\"operator\" " + (operator.equals("multiply") ? "checked" : "")  + " value=\"multiply\" /> * </div>" +
                "<div><input type=\"radio\" name=\"operator\" " + (operator.equals("divide") ? "checked" : "") + " value=\"divide\" /> / </div>" +
                "<div><input type=\"submit\" value=\"Berechnung durchführen!\" /></div>" +
                "</form>";

    }

    private String getButtonedForm(HttpServletRequest request, Cookie[] cookies) {

        String op1 = "";
        String op2 = "";
        String operator = "add";

        if (cookies != null) {

            for (int i = 0; i < cookies.length; i++) {
                switch (cookies[i].getName()) {
                    case "op1":
                        op1 = cookies[i].getValue();
                        break;
                    case "op2":
                        op2 = cookies[i].getValue();
                        break;
                    case "operator":
                        operator = cookies[i].getValue();
                        break;

                }
            }

        }

        return "<form method=\"POST\">" +
                "<div>1. Operand <input type=\"text\" value=\"" + op1 + "\" name=\"op1\"/></div>" +
                "<div>2. Operand <input type=\"text\" value=\"" + op2 +  "\" name=\"op2\"/></div>" +
                "<div " + (operator.equals("+") ? "style=\"background:green\"" : "") + "><input type=\"submit\" name=\"operator\" value=\"+\" /></div>" +
                "<div " + (operator.equals("-") ? "style=\"background:green\"" : "") + " ><input type=\"submit\" name=\"operator\" value=\"-\" /></div>" +
                "<div " + (operator.equals("*") ? "style=\"background:green\"" : "") + "><input type=\"submit\" name=\"operator\" value=\"*\" /></div>" +
                "<div " + (operator.equals("/") ? "style=\"background:green\"" : "") +" ><input type=\"submit\" name=\"operator\" value=\"/\" /></div>" +
                "</form>";

    }


}
