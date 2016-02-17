<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/shared/head.jsp">
            <jsp:param name="title" value="Traject overzicht" />
        </jsp:include>
        <jsp:include page="/WEB-INF/shared/maprequirements.jsp" />
        <script src="<c:url value="/static/scripts/route/map.js" />"></script>            
    </head>
    <body>
        <div class="container">
            <h1>Traject map</h1>

            <div class="map" id="map" style="height:500px">

            </div>

            <c:if test="${not empty routes}">

                <table class="table">
                    <thead>
                        <tr>
                            <th>Naam</th>
                            <th>Afstand</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="route" items="${routes}">
                            <tr data-id="${route.id}">

                                <td>
                                    <input name="check${route.id}" type="checkbox" class="routecheck" data-id="${route.id}"/>
                                    <label for="check${route.id}">
                                        ${route.name}
                                    </label> 
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
        
        <script>
            $(document).ready(function() {
                MapManagement.intializeMap("map", "<c:url value="/route/mapdata" />");
            });
        </script>
    </body>
</html>
