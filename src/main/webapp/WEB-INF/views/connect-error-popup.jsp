<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="region" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="format" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
	<spring:url value="/resources/css/style.css" var="mainCss" />
	<spring:url value="/resources/images/header.jpg" var="headerImg" />
	<spring:url value="/resources/images/footer.jpg" var="footerImg" />
	<meta charset="UTF-8">
	<meta http-equiv="refresh" content="8; url=${pageContext.request.contextPath}/corona">
	<title>Error!</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div class="container">	
		<section>
		<div class="alert alert-danger" role="alert">
  			<h2 class="alert-heading">Error!</h2>
  			<h3>Unable to make dashboard for your region. Error connecting with data source.</h3>
  			<hr>
  			<h4 class="mb=0">Redirecting to the home page...</h4>
		</div>
		</section>
	</div>
	<script src="webjars/jquery/3.1.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
  	<script src="/resources/webstatic/alertfade.js"></script>
</body>
</html>