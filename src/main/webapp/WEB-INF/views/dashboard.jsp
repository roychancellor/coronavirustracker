<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="chart" uri="http://java.sun.com/jsp/jstl/core" %>
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
	addLoadEvent(makeChart("chartContainer1", "Chart 1 Title", "Chart 1 X-axis", "Chart 1 Y-axis", 1));
	addLoadEvent(makeChart("chartContainer2", "Chart 2 Title", "Chart 2 X-axis", "Chart 2 Y-axis", 2));
	addLoadEvent(makeChart("chartContainer3", "Chart 3 Title", "Chart 3 X-axis", "Chart 3 Y-axis", 3));
	addLoadEvent(makeChart("chartContainer4", "Chart 4 Title", "Chart 4 X-axis", "Chart 4 Y-axis", 4));
	addLoadEvent(makeChart("chartContainer5", "Chart 5 Title", "Chart 5 X-axis", "Chart 5 Y-axis", 5));
	addLoadEvent(makeChart("chartContainer6", "Chart 6 Title", "Chart 6 X-axis", "Chart 6 Y-axis", 6));
		
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

		function makeChart(chartContainerString, chartTitle, axisXTitle, axisYTitle, i) {
			var dps = [[], []];
			var chart = new CanvasJS.Chart(chartContainerString, {
				animationEnabled: true,
				exportEnabled: true,
				theme: "light2", //light1, dark1, dark2
				title: {
					text: chartTitle
				},
				axisY: {
					title: axisYTitle
				},
				axisX: {
					title: axisXTitle
				},
				data: [{
					type: "scatter",
					dataPoints: dps[0]
				},
				{
					type: "scatter",
					dataPoints: dps[1]
				}]
			});
			 
			var xValue;
			var yValue;
			 
			<chart:forEach items="${dashboardDataSetsList}" var="dataset">
				<chart:forEach items="${dataset}" var="dataPoints" varStatus="loop">	
					<chart:forEach items="${dataPoints}" var="dataPoint">
						xValue = parseFloat("${dataPoint.x}");
						yValue = parseFloat("${dataPoint.y}");
						dps[parseInt("${loop.index}")].push({
							x : xValue,
							y : yValue,
						});		
					</chart:forEach>	
				</chart:forEach> 
			</chart:forEach>
			 
			chart.render();
		}
	</script>
</body>
</html>