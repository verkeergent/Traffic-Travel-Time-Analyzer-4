<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/shared/head.jsp">
            <jsp:param name="title" value="Contact" />
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
                        <li><a href="../route/list">Traject Overzicht <span class="sr-only">(current)</span></a></li>
                        <li><a href="../route/map">Traject Map</a></li>
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
                                <li><a href="providers">Providers</a></li>
                                <li><a href="tos">TOS</a></li>
                                <li><a href="about">About</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="contact">Contact</a></li>
                            </ul>
                        </li>
                    </ul>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>
        <div class="container">


            <h1>Providers:</h1>
            <ul>
                <li>Google maps</li>
                <li>Waze</li>
                <li>Here</li>
                <li>Coyote</li>
                <li>TomTom</li>
            </ul>
            
        </div>
    </body>
</html>
