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
            <h1>Verkeer 4</h1>
            
            <a href="<c:url value="/route/list" />">Traject overzicht</a>
            <a href="<c:url value="/route/map" />">Traject map</a>
        </div>
    </body>
</html>
