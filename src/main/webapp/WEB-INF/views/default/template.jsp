<!DOCTYPE html>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@page session="true"%>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>${breadcrumbP} :: ${breadcrumbCh}</title>
	<link rel="stylesheet" href="<c:url value='/webjars/bootstrap/3.3.2-1/css/bootstrap.min.css'/>">
	<link rel="stylesheet" href="<c:url value='/resources/css/fullcalendar.min.css'/>">
	<link rel="stylesheet" href="<c:url value='/resources/css/head.css'/>">
	<script src="<c:url value='/resources/js/moment.min.js'/>"></script>
	<script src="<c:url value='/webjars/jquery/2.1.3/jquery.min.js'/>"></script>
	  <script src="<c:url value='/webjars/bootstrap/3.3.2-1/js/bootstrap.min.js'/>"></script>
	<script src="<c:url value='/resources/js/fullcalendar.min.js'/>"></script>
    <c:if test="${css != null}"><link rel="stylesheet" href="<c:url value='/resources/css/${css}'/>"></c:if>
</head>
<body>
    <tiles:insertAttribute name="header" />
    <div class="container-fluid">
        <c:if test="${not empty formError}">
           <div class="alert alert-danger alert-dismissible" role="alert">
              <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                 <span aria-hidden="true">&times;</span>
              </button>
              ${formError}
           </div>
        </c:if>
        <c:if test="${not empty formMessage}">
           <div class="alert alert-success alert-dismissible" role="alert">
              <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                 <span aria-hidden="true">&times;</span>
              </button>
              ${formMessage}
           </div>
        </c:if> 
    	<tiles:insertAttribute name="body" />
    </div>
    <tiles:insertAttribute name="footer" />
    <c:if test="${js != null}"><script src="<c:url value='/resources/js/${js}'/>"></script></c:if>
</body>
</html>