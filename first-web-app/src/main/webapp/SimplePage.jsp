<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date" %>
<%@ page import="lev.filippov.persistance.ProductRepositoryImpl" %>
<%@ page import="lev.filippov.models.Product" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<%--<%@include file="header.jsp"%>--%>
<jsp:include page="header.jsp">
   <jsp:param name="title" value="My Simple Page"/>
</jsp:include>
    <body>
        <%!
            private PersistanceBean ps;

            @Override
            public void jspInit() {
                ps = (PersistanceBean) getServletContext().getAttribute("persistanceBean");
            }
        %>
        <%session.setAttribute("Hello", "Привет");%>
        <% out.println(new Date()); %>
        <% for(int i=0;i<5;i++) { %>
        <p>Number: <%= i %></p>
        <%}%>
        <% for (Product p : ps.getAll()) { %>
        <div><%=p.getId()%>&nbsp<%=p.getName()%>&nbsp<%=p.getPrice()%></div>
        <%}%>
        <p>Обращение к request <%= request.getRequestURI()%></p>
        <p>Обращение к session <%= session.getAttribute("Hello")%></p>
        <p>Обращение к application <%= application.getContextPath()%></p>
        <p>Обращение к out <% out.println(session.getId());%></p>
    </body>
</html>