<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="format" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="money" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <!DOCTYPE html>
<html>
<head>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<spring:url value="/resources/css/style.css" var="mainCss" />
	<spring:url value="/resources/images/header.jpg" var="headerImg" />
	<spring:url value="/resources/images/footer.jpg" var="footerImg" />
	<meta charset="UTF-8">
	<title>Data Dashboard</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div class="container">
		<!-- <%@ include file="common/header-common.jspf" %> -->
		<section>
			<h1>Dashboard for <strong style="color:#522398">${region}</strong></h1>
			<h2 style="text-align:left"><strong>Cases as a Function of Time</strong></h2>
			<div class="dashboard">
			<table class="table table-striped">
				<tr><th>C(t)</th><th>C'(t)</th><th>C"(t)</th></tr>
				<tr>
					<td>Charts will go here</td>
				</tr>
			</table>
			</div>
			<p><a style="font-size:1.5em" class="btn btn-success"
					href="${pageContext.request.contextPath}/chart-info">Chart Info</a>
			</p>
			<p><a style="font-size:1.5em" class="btn btn-warning"
					href="${pageContext.request.contextPath}/corona">Return Home</a>
			</p>
		</section>
		<!-- <%@ include file="common/footer-common.jspf" %> -->
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>