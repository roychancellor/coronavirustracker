<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<link href="webjars/bootstrap/3.3.7-1/css/bootstrap.min.css" rel="stylesheet">
	<spring:url value="/resources/css/style.css" var="mainCss" />
	<spring:url value="/resources/images/header.jpg" var="headerImg" />
	<spring:url value="/resources/images/footer.jpg" var="footerImg" />
	<meta charset="UTF-8">
	<title>Math</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div class="container">
		<div>
			<h2 style="display:inline-block"><strong style="color:#522398">The math behind the charts</strong></h2>
			<a class="btn btn-success" onClick="history.back()">MATH!!! GET ME OUTTA HERE!!!</a>
		</div>
		<div class="dashboard">
		<table class="table table-dark">
			<tr>
				<td class="col-md-2">
				<div id="chartContainer11" style="height: 200px; width: 100%">
				<h3 style="color:#522398;">Coming soon...</h3>
				<p style="font-size:0.5em; color:white; padding:2px; margin:2px; line-height:1.0em;">I'll get you the math you so eagerly desire.
				Just gotta get the site deployed first!</p>
				</div>
				</td>
			</tr>
		</table>
		</div>
	</div>
	<script src="webjars/jquery/3.1.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
</body>

</html>