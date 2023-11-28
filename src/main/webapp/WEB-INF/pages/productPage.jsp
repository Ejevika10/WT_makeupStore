<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="makeup" scope="request" type="MakeupStore.model.entities.makeup.Makeup"/>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<tags:master pageTitle="Makeup Details">

    <c:choose>
        <c:when test="${not empty inputErrors}">
            <div class="container">
                <div class="panel panel-danger">
                    <div class="panel-heading"><fmt:message key="error_title" /></div>
                    <div class="panel-body">
                        <fmt:message key="error_updating_cart" />
                        <br>
                        ${inputErrors.get(makeup.id)}
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <c:if test="${not empty successMessage}">
                <div class="container">
                    <div class="panel panel-success">
                        <div class="panel-heading"><fmt:message key="success_title" /></div>
                        <div class="panel-body">${successMessage}</div>
                    </div>
                </div>
            </c:if>
        </c:otherwise>
    </c:choose>
    <div class="panel"></div>
    <div class="container">
        <div style="display: flex; flex-direction:row;justify-content: space-between">
                <div>
                    <img class="rounded" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${makeup.imageUrl}">
                </div>
                <div>
                    <p class="text-justify">${makeup.description}</p>
                </div>
                <div class="float-right">
                    <p class="text">Brand: ${makeup.brand}</p>
                    <p class="text">Name: ${makeup.name}</p>
                    <p class="text">Price: $${makeup.price}</p>
                    <p class="text">Weight: ${makeup.weightGr} g</p>
                    <c:choose>
                        <c:when test="${not empty sessionScope.login}">
                            <form action="/" method="post">
                                <input type="hidden" name="command" value="cart_add">
                                <input type="hidden" name="page_type" value="product_details">
                        </c:when>
                        <c:otherwise>
                            <form action="/" method="get">
                                <input type="hidden" name="command" value="authorisation">
                        </c:otherwise>
                    </c:choose>
                        <input type="hidden" name="id" value="${makeup.id}">
                        <input type="number" name="quantity" id="quantity${makeup.id}" min="1" required>
                        <button class="btn btn-lg btn-outline-light text-dark border-dark float-right" type="submit"><fmt:message key="button_add" /></button>
                    </form>
                </div>
        </div>
</tags:master>