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
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="dropdown active navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="../">Home</a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li><a href="list">Traject Overzicht <span class="sr-only">(current)</span></a></li>
                        <li><a href="map">Traject Map</a></li>
                    </ul>
                    <form class="navbar-form navbar-left" role="search">
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="Traject">
                        </div>
                        <button type="submit" class="btn btn-default">Filter</button>
                    </form>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a href="#" class="dropdown active dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true">Info <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="../info/providers">Providers</a></li>
                                <li><a href="../info/tos">TOS</a></li>
                                <li><a href="../info/about">About</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="../info/contact">Contact</a></li>
                            </ul>
                        </li>
                    </ul>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>
        <div class="container">
            <h1>Traject map</h1>



            <div class="row">
                <div class="col-md-8">
                    <div class="map" id="map" style="height:800px">

                    </div>        
                </div>   
                <div class="col-md-4">
                    <c:if test="${not empty routes}">

                        <table class="table">
                            <thead>
                                <tr>
                                    <th><input name="routecheckAll" type="checkbox" class="routecheckAll" checked="checked"></input>Naam</th>
                                    <th>Afstand</th>
                                    <th>Vertraging</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="route" items="${routes}">
                                    <tr data-id="${route.id}">

                                        <td>
                                            <input name="check${route.id}" type="checkbox" class="routecheck" checked="checked" data-id="${route.id}"/>

                                            <a class="routename" href="#" data-id="${route.id}">${route.name}</a>
                                        </td>
                                        <td>
                                            ${route.distance} m
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
