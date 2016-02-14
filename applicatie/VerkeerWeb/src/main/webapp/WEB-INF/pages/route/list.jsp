<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/shared/head.jsp">
            <jsp:param name="title" value="Traject overzicht" />
        </jsp:include>
    </head>
    <body>
        <div class="container">
            <h1>Traject Overzicht</h1>

            <c:if test="${not empty routes}">

                <table class="table">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Naam</th>
                            <th>Afstand</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="route" items="${routes}">
                            <tr>
                                <td>
                                    ${route.id}
                                </td>
                                <td>
                                    <a href="detail.htm?id=${route.id}">
                                        ${route.name}
                                    </a>
                                </td>
                                <td>
                                    ${route.distance} m
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </body>
</html>
