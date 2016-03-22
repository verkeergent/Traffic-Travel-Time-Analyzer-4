<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/shared/head.jsp">
            <jsp:param name="title" value="Traject ${detail.name}"/>
        </jsp:include>
        <jsp:include page="/WEB-INF/shared/maprequirements.jsp"/>
    </head>
    <body>
        <jsp:include page="/WEB-INF/shared/navigation.jsp"/>
        <div class="container">
            <input type="hidden" id="routeId" value="${detail.id}"/>
            <div class="row">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">${detail.name}</h3>
                    </div>
                    <div class="panel-body">
                        <div class="col-md-4">
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>Provider</th>
                                        <th>Travel time</th>
                                        <th>Delay</th>
                                    </tr>
                                </thead>
                                <tbody id="summary-table-body">
                                    <c:forEach var="data" items="${detail.summaries}">
                                        <tr>
                                            <td>${data.provider}</td>
                                            <td><span class="time" data-time=${data.travelTime}></span></td>
                                            <td><span class="label-delay time" data-time=${data.delay}></span></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <dl>
                                <dt>Standard travel time</dt>
                                <dd><span class="humanize" data-time=${detail.defaultTravelTime}></span></dd>
                                <dt>Distance</dt>
                                <dd>${detail.distance} meter</dd>
                                <dt>From</dt>
                                <dd>${detail.fromAddress}</dd>
                                <dt>To</dt>
                                <dd>${detail.toAddress}</dd>
                            </dl>
                            <a href="${pageContext.servletContext.contextPath}/route/edit/${detail.id}">Edit Route</a>
                        </div>
                        <div class="col-md-8">
                            <div id="map" style="height:500px">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Filter options</h3>
                    </div>
                    <div class="panel-body">
                        <div class="col-md-6">
                            <label for="datetimepicker-begin-input">Start Date</label>
                            <div class="form-group">
                                <div class='input-group date' id='datetimepicker-begin'>
                                    <input type='text' class="form-control" id="datetimepicker-begin-input"/>
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label for="datetimepicker-end-input">End Date</label>
                            <div class="form-group">
                                <div class='input-group date' id='datetimepicker-end'>
                                    <input type='text' class="form-control" id="datetimepicker-end-input"/>
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel-footer">
                        <button id="update-btn" type="button" class="btn btn-primary">Update</button>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">History</h3>
                    </div>
                    <div class="panel-body">
                            <div id="container" style="height: 400px"></div>
                        <button id="toggle-btn" type="button" class="btn btn-primary" style="margin: 10px auto">Toggle</button>
                    </div>
                </div>
            </div>

           <div class="row">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title ">Traffic jams</h3>
                    </div>
                    <div class="panel-body">
                        <table id="tblJams" class="table">
                            <thead>
                                <tr>
                                    <th>From</th>
                                    <th>To</th>
                                    <th>Duration</th>
                                    <th>Avg delay</th>
                                    <th>Peak delay</th>
                                    <th>Possible causes</th>
                                </tr>
                            </thead>
                            <tbody id="tblJamsBody">
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <script src="<c:url value="/static/scripts/route/map.js" />"></script>
        <script>
        MapManagement.intializeRouteMap("map", "<c:url value="/route/mapdata?id=${detail.id}" />");
        </script>
        <script src="<c:url value="/static/scripts/libs/highcharts.js" />"></script>
        <script src="<c:url value="/static/scripts/libs/exporting.js" />"></script>
        <script src="<c:url value="/static/scripts/libs/export-csv.js" />"></script>
        <script src="<c:url value="/static/scripts/route/detail/detail.js" />"></script>
    </body>
</html>
