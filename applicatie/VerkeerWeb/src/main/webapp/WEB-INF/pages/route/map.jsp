<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/shared/head.jsp">
            <jsp:param name="title" value="Map view" />
        </jsp:include>
        <jsp:include page="/WEB-INF/shared/maprequirements.jsp" />
        <script src="<c:url value="/static/scripts/route/map.js" />"></script>            
        <link rel="stylesheet" href="<c:url value="/static/styles/trajectmapcss.css" />"/>
    </head>
    <body>
        <jsp:include page="/WEB-INF/shared/navigation.jsp" />
        <div class="container-fluid">
            <div class="row">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">Map View</h3>
                    </div>
                    <div class="panel-body">


                        <div class="col-md-8">
                            <div class="map" id="map" style="height:800px">

                            </div>        
                        </div>   
                        <div class="col-md-4">
                            <table class="table sortable">
                                <thead>
                                    <tr>
                                        <th><input name="routecheckAll" type="checkbox" class="routecheckAll" checked="checked"></input>Name</th>
                                        <th>Distance</th>
                                        <th>CTT</th>
                                        <th>Delay</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="routeSummaryEntry" items="${overview.summaries}">
                                        <tr data-id="${routeSummaryEntry.route.id}">

                                            <td>
                                                <input name="check${routeSummaryEntry.route.id}" type="checkbox" class="routecheck" checked="checked" data-id="${routeSummaryEntry.route.id}"/>

                                                <a class="routename" href="#" data-id="${routeSummaryEntry.route.id}">${routeSummaryEntry.route.name}</a>
                                            </td>
                                            <td>
                                                ${routeSummaryEntry.route.distance} m
                                            </td>
                                            <td>
                                                <span class="label label-info time" data-time="${routeSummaryEntry.averageCurrentTravelTime}">
                                                    ${routeSummaryEntry.averageCurrentTravelTime}
                                                </span>  
                                            </td>
                                            <td>
                                                <span class="label time label-delay" data-time="${routeSummaryEntry.delay}">
                                                    ${routeSummaryEntry.delay}
                                                </span>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>

                        </div>
                    </div>
                </div>                
            </div>

            <script>
                $(document).ready(function () {
                    var map = MapManagement.intializeRouteMap("map", "<c:url value="/route/mapdata" />");

                    $(document).on("change", ".routecheck", function () {
                        var id = parseInt($(this).attr("data-id"));
                        map.setRouteVisibility(id, $(this).prop("checked"));
                    });

                    $(document).on("click", ".routename", function () {
                        var id = parseInt($(this).attr("data-id"));
                        map.centerMapOnRoute(id);
                    });

                    $(document).on("change", ".routecheckAll", function () {
                        $(".routecheck").prop("checked", $(this).prop("checked"));
                        $(".routecheck").change();
                    });
                });
            </script>
    </body>
</html>
