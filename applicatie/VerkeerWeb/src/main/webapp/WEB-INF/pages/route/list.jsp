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
            <h1>Traject Overzicht</h1>

            <c:if test="${not empty routes}">

                <table class="table">
                    <thead>
                        <tr>
                            <th><center>Id</center></th>
                            <th><center>Naam</center></th>
                            <th><center>Afstand</center></th>
                            <th><center>Provider</center></th> <!--temp-->
                            <th><center>Normale Reisduur</center></center></th>
                            <th><center>Huidige Reisduur</center></th>
                            <th><center>Vertraging</center></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="route" items="${routes}">
                            <tr>
                                <td>
                                    <center>${route.id}</center>
                                </td>
                                <td>
                                    <a href="detail?id=${route.id}">
                                        <center>${route.name}</center>
                                    </a>
                                </td>
                                <td>
                                    <center>${route.distance} m</center>
                                </td>
                                <td>
                                <center><img class="resize" src="../images/tomtom.png"/></center> <!-- route.provider -->
                                </td>
                                <td>
                                <center>T.B.D.</center><!-- route.normalereisduur -->
                                </td>
                                <td>
                                <center>T.B.D.</center><!-- route.huidigereisduur -->
                                </td>
                                <td>
                                <center>T.B.D.</center><!-- route.vertraging -->
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
        <div class="container">
            <h1>Traject Overzicht v2</h1>

            <c:if test="${not empty routes}">

                <table class="table">
                    <thead>
                        <tr>
                            <th><center>Id</center></th>
                            <th><center>Naam</center></th>
                            <th><center>Afstand</center></th>
                            <th><center>Normale Reistijd</center></th>
                            <th><center>Gem. Delay</center></th>
                            <th colspan="2"><center><img class="resize" src="../images/tomtom.png"/></center></th> <!--temp-->
                            <th colspan="2"><center><img class="resize" src="../images/waze.png"/></center></th> <!--temp-->
                            <th colspan="2"><center><img class="resize" src="../images/gmaps.png"/></center></th> <!--temp-->
                            <th colspan="2"><center><img class="resize" src="../images/here.png"/></center></th> <!--temp-->
                            <th colspan="2"><center><img class="resize" src="../images/coyote.jpg"/></center></th> <!--temp-->
                          <!--  <th><center>Normale Reisduur</center></center></th>
                            <th><center>Huidige Reisduur</center></th>
                            <th><center>Vertraging</center></th>-->
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                                <td>
                                    <center></center>
                                </td>
                                <td>
                                    
                                </td>
                                <td>
                                    <center>m</center>
                                </td>
                                <td>
                                <td>
                                <center>%</center><!-- Gem. delay -->
                                </td>
                                <td>
                                <center>HR</center> <!-- tomtom -->
                                </td>
                                <td>
                                <center>V</center> <!-- tomtom -->
                                </td>
                                <td>
                                <center>HR</center> <!-- waze -->
                                </td>
                                <td>
                                <center>V</center> <!-- waze -->
                                </td>
                                <td>
                                <center>HR</center> <!-- gmaps -->
                                </td>
                                <td>
                                <center>V</center> <!-- gmaps -->
                                </td>
                                <td>
                                <center>HR</center> <!-- here -->
                                </td>
                                <td>
                                <center>V</center> <!-- here -->
                                </td>
                                <td>
                                <center>HR</center> <!-- coyote -->
                                </td>
                                <td>
                                <center>V</center> <!-- coyote -->
                                </td>
                                <td>

                            
                            </tr>
                        <c:forEach var="route" items="${routes}">
                            <tr>
                                <td>
                                    <center>${route.id}</center>
                                </td>
                                <td>
                                    <a href="detail?id=${route.id}">
                                        <center>${route.name}</center>
                                    </a>
                                </td>
                                <td>
                                    <center>${route.distance} m</center>
                                </td>
                                <td>
                                <center>T.B.D.</center> <!-- tomtom -->
                                </td>
                                <td>
                                <center>T.B.D.</center> <!-- tomtom -->
                                </td>
                                <td>
                                <center>T.B.D.</center> <!-- tomtom -->
                                </td>
                                <td>
                                <center>T.B.D.</center> <!-- tomtom -->
                                </td>
                                <td>
                                <center>T.B.D.</center> <!-- tomtom -->
                                </td>
                                <td>
                                <center>T.B.D.</center><!-- waze -->
                                </td>
                                <td>
                                <center>T.B.D.</center> <!-- tomtom -->
                                </td>
                                <td>
                                <center>T.B.D.</center> <!-- tomtom -->
                                </td>
                                <td>
                                <center>T.B.D.</center> <!-- tomtom -->
                                </td>
                                <td>
                                <center>T.B.D.</center><!-- gmaps -->
                                </td>
                                <td>
                                <center>T.B.D.</center> <!-- tomtom -->
                                </td>
                                <td>
                                <center>T.B.D.</center> <!-- tomtom -->
                                </td>

                               

                            
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </body>
</html>
