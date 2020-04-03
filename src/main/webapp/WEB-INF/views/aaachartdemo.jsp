<%@ page language="java" contentType="text/html; charset=charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="format" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="money" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  -->
<html>
<head>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<spring:url value="/resources/css/style.css" var="mainCss" />
	<spring:url value="/resources/images/header.jpg" var="headerImg" />
	<spring:url value="/resources/images/footer.jpg" var="footerImg" />
	<meta http-equiv="Content-Type" content="text/html; charset=charset=ISO-8859-1">
	<title>Data Dashboard</title>
	<link rel="stylesheet" href="${mainCss}" />
	<script type="text/javascript">
		window.onload = function() {
		 
		var dps = [[]];
		var chart = new CanvasJS.Chart("chartContainer", {
			animationEnabled: true,
			exportEnabled: true,
			theme: "light2",
			title: {
				text: "Ultrasonic Calibration"
			},
			axisY: {
				title: "Ultrasonic Response"
			},
			axisX: {
				title: "Metal Distance"
			},
			data: [{
				type: "scatter",
				dataPoints: dps[0]
			}]
		});
		 
		var xValue;
		var yValue;
		 
		<c:forEach items="${dataPointsList}" var="dataPoints" varStatus="loop">	
			<c:forEach items="${dataPoints}" var="dataPoint">
				xValue = parseFloat("${dataPoint.x}");
				yValue = parseFloat("${dataPoint.y}");
				dps[parseInt("${loop.index}")].push({
					x : xValue,
					y : yValue,
				});		
			</c:forEach>	
		</c:forEach> 
		 
		chart.render();
		 
		}
	</script>
</head>

<body>
	<div class="container">
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
	<div id="chartContainer" style="height: 370px; width: 100%;"></div>
	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>