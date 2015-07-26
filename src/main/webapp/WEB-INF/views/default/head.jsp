<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@page session="true"%>

<div class="container-fluid">
  <div class="row">
    <div class="col-xs-4 col-sm-3 col-md-2 col-lg-2 sidebar">
     <c:set var="_URL" value="${requestScope['javax.servlet.forward.request_uri']}"/>
     <c:url var="HOME_URL" value="/"/>
      <div class="sidebar-header text-center">
        <a class="sidebar-brand" href="${HOME_URL}">Transubic</a>
      </div>
      
      
     
      <ul class="nav nav-sidebar">
        <li class="li-header">Main Menu</li>
        <li class="${_URL==HOME_URL?'active':''}"><a href="${HOME_URL}"><span class="glyphicon glyphicon-home"></span>&nbsp;&nbsp; Home</a></li>
        
      </ul>
    </div>
    <div class="col-xs-8 col-sm-9 col-md-10 col-lg-10 col-xs-offset-4 col-sm-offset-3 col-md-offset-2 col-lg-offset-2 main">
      <nav class="navbar navbar-default navbar-static-top">
        <div class="container-fluid">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><strong>${breadcrumbP}</strong><c:if test="${breadcrumbP != breadcrumbCh}">/${breadcrumbCh}</c:if></a>
          </div>
          <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
              <li>
              	<c:url var="logoutUrl" value="/logout" />
				<form action="${logoutUrl}" method="post">
				  <input type="submit" class="btn btn-link" value="Log out" />
				  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</form>
              </li>
            </ul>
          </div>
        </div>
      </nav>

