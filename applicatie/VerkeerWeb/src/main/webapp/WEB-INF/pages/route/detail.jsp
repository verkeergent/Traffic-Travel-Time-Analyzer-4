<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/shared/head.jsp">
            <jsp:param name="title" value="Traject ${detail.name}" />
        </jsp:include>
    </head>
    <body>
        <div class="container">
            <h1>Traject ${detail.name}</h1>

            <div class="row">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="col-md-4">
                            <div class="input-group">
                                <input class="datePicker form-control" type="text" />
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="input-group">
                                <input class="datePicker form-control" type="text" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <c:choose>
                            <c:when test="${not empty detail}">
                                Id: ${detail.id}
                                <br/>
                                Naam: ${detail.name}
                                <br/>
                                Afstand ${detail.distance} meter
                            </c:when>
                            <c:otherwise>
                                Traject niet gevonden
                            </c:otherwise>
                        </c:choose>

                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Timestamp</th>
                                    <th>Tijd (seconden)</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="data" items="${detail.data}">
                                    <tr>
                                        <td>
                                            ${data.id}
                                        </td>
                                        <td>
                                            ${data.timestamp}
                                        </td>
                                        <td>
                                            ${data.travelTime}
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <script>
                $(".datePicker").datetimepicker();
            </script>
    </body>
</html>
