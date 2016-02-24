<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
            <a class="navbar-brand" href="<c:url value="/" />">Home</a>
        </div>
        
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="<c:url value="/route/list" />">Traject Overzicht <span class="sr-only">(current)</span></a></li>
                <li><a href="<c:url value="/route/map" />">Traject Map</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown active dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true">Info <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="<c:url value="/info/providers" />">Providers</a></li>
                        <li><a href="<c:url value="/info/tos" />">TOS</a></li>
                        <li><a href="<c:url value="/info/about" />">About</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="<c:url value="/info/contact" />">Contact</a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>