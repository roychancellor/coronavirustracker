<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="chart1" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="chart2" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="chart3" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="chart4" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="money" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<spring:url value="/resources/css/style.css" var="mainCss" />
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Data Dashboard</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div class="container">
		<h2>Dashboard for <strong style="color:#522398">${region}</strong></h2>
		<div class="dashboard">
		<table class="table table-dark">
			<tr>
				<td><div id="chartContainer1" style="height: 250px; width: 100%"></div></td>
				<td><div id="chartContainer2" style="height: 250px; width: 100%"></div></td>
			</tr>
			<tr>
				<td><div id="chartContainer3" style="height: 250px; width: 100%"></div></td>
				<td><div id="chartContainer4" style="height: 250px; width: 100%"></div></td>
			</tr>
			<tr>
				<td><div id="chartContainer5" style="height: 250px; width: 100%"></div></td>
				<td><div id="chartContainer6" style="height: 250px; width: 100%"></div></td>
			</tr>
		</table>
		</div>
		<p><a style="font-size:1.5em" class="btn btn-success"
				href="${pageContext.request.contextPath}/chart-info">Chart Info</a>
		<!-- </p> -->
		<!-- <p> --><a style="font-size:1.5em" class="btn btn-warning"
				href="${pageContext.request.contextPath}/corona">Return Home</a>
		</p>
	</div>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
 	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
	<script src="https://canvasjs.com/assets/script/jquery.canvasjs.min.js"></script>
	
	<!-- MAKE ALL THE CHARTS LAST -->
	<script type="text/javascript">
		addLoadEvent(makeChart("chartContainer1"));
		addLoadEvent(makeChart("chartContainer2"));
		addLoadEvent(makeChart("chartContainer3"));
		addLoadEvent(makeChart("chartContainer4"));
		addLoadEvent(makeChart("chartContainer5"));
		addLoadEvent(makeChart("chartContainer6"));
		
	<!-- From: https://www.htmlgoodies.com/beyond/javascript/article.php/3724571/using-multiple-javascript-onload-functions.htm -->	
		function addLoadEvent(func) {
		  var oldonload = window.onload;
		  if (typeof window.onload != 'function') {
		    window.onload = func;
		  } else {
		    window.onload = function() {
		      if (oldonload) {
		        oldonload();
		      }
		      func();
		    }
		  }
		}

		function makeChart(chartContainerString) {
			var dps = [[]];
			var chart = new CanvasJS.Chart(chartContainerString, {
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
			 
			<chart1:forEach items="${dataPointsList}" var="dataPoints" varStatus="loop">	
				<chart1:forEach items="${dataPoints}" var="dataPoint">
					xValue = parseFloat("${dataPoint.x}");
					yValue = parseFloat("${dataPoint.y}");
					dps[parseInt("${loop.index}")].push({
						x : xValue,
						y : yValue,
					});		
				</chart1:forEach>	
			</chart1:forEach> 
			 
			chart.render();
		}
	</script>
</body>
</html>