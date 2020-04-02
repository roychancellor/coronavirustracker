<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="region" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="format" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<spring:url value="/resources/css/style.css" var="mainCss" />
	<spring:url value="/resources/images/header.jpg" var="headerImg" />
	<spring:url value="/resources/images/footer.jpg" var="footerImg" />
	<meta charset="UTF-8">
	<title>Settings</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div class="container">
		<%@ include file="common/header-common-login.jspf" %>
		<section>
			<h1>Coronavirus Dashboard Creator</h1>
			<region:form modelAttribute="region" action="${pageContext.request.contextPath}/dashboard" method="POST">
				<table class="table table-hover">
				  <thead>
				    <tr>
				      <th scope="col">Region</th>
				      <th scope="col">Description</th>
				    </tr>
				  </thead>
				  <tbody>
				    <tr class="info">
				      <td><input class="btn btn-info btn-lg" type="submit" value="us">United States</td>
				      <td>Click this button to make a dashboard for the entire United States.</td>
				    </tr>
				    <tr class="danger">
				      <td><input class="btn btn-danger btn-lg" type="submit" value="us_no_ny">US without NY</td>
				      <td>Click this button to make a dashboard for the United States, excluding New York.</td>
				    </tr>
				    <tr class="danger">
				      <td><input class="btn btn-danger btn-lg" type="submit" value="ny">New York</td>
				      <td>Click this button to make a dashboard for the state of New York.</td>
				    </tr>
				    <tr class="danger">
				      <td><input class="btn btn-danger btn-lg" type="submit" value="az">Arizona</td>
				      <td>Click this button to make a dashboard for the state of Arizona.</td>
				    </tr>
				    <tr class="warning">
				      <td><input class="btn btn-warning btn-lg" type="submit" value="italy">Italy</td>
				      <td>Click this button to make a dashboard for the country of Italy.</td>
				    </tr>
				  </tbody>
				</table>
			</region:form>
		</section>
		<%@ include file="common/footer-common.jspf" %>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>