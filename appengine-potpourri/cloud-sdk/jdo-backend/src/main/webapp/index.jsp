<%--Don't code JSPs like I do--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.cloud.tools.examples.servers.jdo.Garbage" %>
<%@ page import="com.google.cloud.tools.examples.servers.jdo.Persistence" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="javax.jdo.Query" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Garbage List</title>
</head>
<body>
<h1>Garbage List</h1>
<ul>
    <%
        PersistenceManager persistenceManager = Persistence.PMF.getPersistenceManager();
        Query garbageQuery = persistenceManager.newQuery(Garbage.class);
        garbageQuery.setOrdering("contents asc");
        try {
            List<Garbage> allGarbage = (List<Garbage>) garbageQuery.execute();
            for (Garbage garbage : allGarbage) {
    %>
    <li><%= garbage.getContents() %>
    </li>
    <%
            }
        } finally {
            garbageQuery.closeAll();
            persistenceManager.close();
        }
    %>
</ul>
<a href="/addGarbage">Add Garbage</a>
<c:out value="${tokenJstlTag}"/>
</body>
</html>
