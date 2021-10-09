<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="en">

<%--<%@ include file="head.jsp"%>--%>

<jsp:include page="head.jsp">
    <jsp:param name="title" value="Product Form"/>
</jsp:include>

<body>
<%@include file="navbar.jsp"%>

<div class="container">
    <div class="row py-2">
        <div class="col-12">
            <c:url value="/products" var="productSubmitUrl"/>
            <form action="${productSubmitUrl}" method="post" >
                <input value="${requestScope.product.id}" type="hidden" id="id" name="id">
                <div class="form-group">
                    <label>Name</label>
                    <input value="${requestScope.product.name}" type="text" class="form-control" id="name" name="name" placeholder="Enter name">
                </div>
                <div class="form-group">
                    <label>Price</label>
                    <input value="${requestScope.product.price}" type="number" class="form-control" id="price" name="price" placeholder="Enter price" >
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </div>
</div>

<%@include file="scripts.jsp"%>

</body>

</html>