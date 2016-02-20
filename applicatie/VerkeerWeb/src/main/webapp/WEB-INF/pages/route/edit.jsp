<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/shared/head.jsp">
            <jsp:param name="title" value="Route wijzigen" />
        </jsp:include>
        <jsp:include page="/WEB-INF/shared/maprequirements.jsp" />
        <script src="<c:url value="/static/scripts/route/map.js" />"></script>

    </head>
    <body>
        <div class="container">
            <h1>Traject wijzigen</h1>

            <div class="row">
                <div class="panel panel-default">
                    <form:form method="POST" modelAttribute="obj" commandName="routeEdit">
                        <div class="panel-body">
                            <div class="form-group">
                                <label for="txtNaam">Naam</label>
                                <form:input class="form-control" path="name" />
                                
                                <form:errors path="name"  cssClass="error" />
                
                            </div>
                        </div>

                        <input type="submit" class="btn btn-sm"/>
                    </form:form>
                </div>
            </div>
        </div>

    </body>
</html>
