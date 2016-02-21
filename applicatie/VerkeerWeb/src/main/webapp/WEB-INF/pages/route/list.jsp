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
        <jsp:include page="/WEB-INF/shared/navigation.jsp" />
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
