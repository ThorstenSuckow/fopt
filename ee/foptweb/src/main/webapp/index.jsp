<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %></h1>
<br/>
<h2>Sandbox</h2>
<a href="sandbox/hello-servlet">Hello Servlet</a> | <a href="sandbox/demo-servlet">Demo Servlet</a> <br />
| <a href="sandbox/form-servlet">Form Servlet</a> | <a href="sandbox/servlet405">405 Servlet (will produce 405!)</a> <br/>
| <a href="sandbox/sleep-servlet">Sleep Servlet</a> | <a href="sandbox/counter-servlet">Counter Servlet</a> <br />
| <a href="sandbox/context-servlet/increment">Servlet Context increment()</a> | <a href="sandbox/context-servlet/reset">Servlet Context reset()</a><br />
| <a href="sandbox/contextlistener-servlet/increment">Servlet Context Listener increment()</a> | <a href="sandbox/contextlistener-servlet/reset">Servlet Context Listener reset()</a><br />
|  <a href="sandbox/threadpolling-servlet">Threadpolling Servlet</a>
<h2>Uebungen</h2>
<ul>
  <li><a href="uebung15_1/demo-servlet?meta=1">Uebung 15.1 (meta-tag)</a> </li>
  <li><a href="uebung15_1/demo-servlet">Uebung 15.1 (header)</a></li>
  <li><a href="uebung15_2/demo-servlet">Uebung 15.2 (responds w/ 405)</a> </li>
  <li><a href="uebung15_3/demo-servlet">Uebung 15.3</a> </li>
  <li><a href="uebung16_2/demo-servlet">Uebung 16.2</a> </li>
</ul>
<h2>Aufgaben</h2>
<ul>
  <li><a href="aufgabe6/exam-servlet">Aufgabe 6 ExamServlet</a> </li>
  <li><a href="aufgabe6/results-servlet">Aufgabe 6 ResultsServlet</a> </li>
</ul>
<h2>Zusatzaufgaben</h2>
<ul>
  <li><a href="zusatzaufgaben/pizza-servlet">zusatzaufgabe Pizza Bestelldienst</a></li>
</ul>
<h2>Praktische Phase</h2>
<ul>
  <li><a href="praktischephase/taschenrechner">Taschenrechner</a></li>
  <li><a href="praktischephase/gaestebuch">Gästebuch</a></li>
  <li><a href="praktischephase/wiki">Wiki</a></li>
</ul>
</body>
</html>