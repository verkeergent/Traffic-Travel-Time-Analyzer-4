<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/shared/head.jsp">
            <jsp:param name="title" value="Traject ${detail.naam}" />
        </jsp:include>
    </head>
    <body>
        <h1>Traject ${detail.naam}</h1>
        <c:choose>
            <c:when test="${not empty detail}">
                Id: ${detail.id}
                <br/>
                Naam: ${detail.naam}
                <br/>
                Afstand ${detail.afstand} meter
            </c:when>
            <c:otherwise>
               Traject niet gevonden
            </c:otherwise>
        </c:choose>
    </body>
</html>
