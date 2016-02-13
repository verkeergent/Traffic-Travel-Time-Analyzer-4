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
        <h1>Traject Overzicht</h1>

        <c:if test="${not empty trajecten}">

            <table class="table">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Naam</th>
                        <th>Afstand</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="traject" items="${trajecten}">
                        <tr>
                            <td>
                                ${traject.id}
                            </td>
                            <td>
                                <a href="detail.htm?id=${traject.id}">
                                    ${traject.naam}
                                </a>
                            </td>
                            <td>
                                ${traject.afstand} m
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

    </body>
</html>
