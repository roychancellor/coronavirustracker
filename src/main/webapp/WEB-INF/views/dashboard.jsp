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
		<div>
			<h2 style="display:inline-block">Dashboard for <strong style="color:#522398">${fullregion}</strong></h2>
			<a style="font-size:1em" class="btn btn-success" href="${pageContext.request.contextPath}/chart-info">Chart Info</a>
			<a style="font-size:1em" class="btn btn-warning" href="${pageContext.request.contextPath}/corona">Return Home</a>
		</div>
		<div class="dashboard">
		<table class="table table-dark">
			<tr>
				<td><div id="chartContainer11" style="height: 250px; width: 100%"></div></td>
				<td><div id="chartContainer12" style="height: 250px; width: 100%"></div></td>
			</tr>
			<tr>
				<td><div id="chartContainer21" style="height: 250px; width: 100%"></div></td>
				<td><div id="chartContainer22" style="height: 250px; width: 100%"></div></td>
			</tr>
			<tr>
				<td><div id="chartContainer31" style="height: 250px; width: 100%"></div></td>
				<td><div id="chartContainer32" style="height: 250px; width: 100%"></div></td>
			</tr>
		</table>
		</div>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
 	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
	<script src="https://canvasjs.com/assets/script/jquery.canvasjs.min.js"></script>
	
	<!-- MAKE ALL THE CHARTS LAST -->
	<script type="text/javascript">
		var containers = [];
 		var configObjects = [];
		var chartArray = [];
		
		var mapArr = new Map();
		mapArr.set("0", "11");
		mapArr.set("1", "12");
		mapArr.set("2", "21");
		mapArr.set("3", "22");
		mapArr.set("4", "31");
		mapArr.set("5", "32");
 		
		//ACTIONS
		makeChartDataFromJavaLists();
		makeChartConfigs(); 		
 		for(var i = 0; i < 6; i++) {
 			addLoadEvent(makeChart(containers[i], configObjects[i], chartArray[i]));
 		}
 		//END ACTIONS
 		
		function makeChartConfigs() {
	 		<chart:forEach items = "${allDashboardCharts}" var = "config" varStatus = "loop">
 				containers[parseInt("${loop.index}")] = "chartContainer" + mapArr.get("${loop.index}");
	 			
 				configObjects[parseInt("${loop.index}")] = {
	 				chartTitle: "${config.chartConfig.chartTitle}",
	 				xAxisTitle: "${config.chartConfig.xAxisTitle}",
	 				yAxisTitle: "${config.chartConfig.yAxisTitle}",
	 				xAxisLogarithmic: "${config.chartConfig.xAxisLogarithmic}" == "true",
					yAxisLogarithmic: "${config.chartConfig.yAxisLogarithmic}" == "true",
					xGridDashType: "${config.chartConfig.xGridDashType}",
					xMinimum: parseInt("${config.chartConfig.xAxisMin}"),
					xMaximum: parseInt("${config.chartConfig.xAxisMax}"),
					yMinimum: parseInt("${config.chartConfig.yAxisMin}"),
					yMaximum: parseInt("${config.chartConfig.yAxisMax}")
	 			};
	 		</chart:forEach>
		}
 		
		function makeChart(chartContainerString, config, dataPointsArr) {
 			var chart = new CanvasJS.Chart(chartContainerString, {
				animationEnabled: true,
				exportEnabled: true,
				theme: 'light2', //light1, dark1, dark2
				title: {
					text: config.chartTitle,
					fontSize: 14
				},
				axisY: {
					title: config.yAxisTitle,
					logarithmic: config.yAxisLogarithmic,
					minimum: config.yMinimum,
					maximum: config.yMaximum
				},
				axisX: {
					title: config.xAxisTitle,
					logarithmic: config.xAxisLogarithmic,
					minimum: config.xMinimum,
					maximum: config.xMaximum,
					gridDashType: config.xGridDashType
				},
				data: [{
					type: "scatter",
					pointRadius: 1,
					pointHoverRadius: 5,
					fill: true,
					tension: 0,
					showLine: false,
					dataPoints: dataPointsArr[0]
				},
				{
					type: "line",
					markerSize: 0,
					pointHoverRadius: 5,
					fill: false,
					tension: 0.4,
					showLine: true,
					dataPoints: dataPointsArr[1]
				}]
			});
			chart.render();
		}
		
	//<!-- From h t t p s www.htmlgoodies.com/beyond/javascript/article.php/3724571/using-multiple-javascript-onload-functions.htm -->	
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

		function makeChartDataFromJavaLists() {
			var xValue;
			var yValue;
			<chart:forEach items="${allDashboardCharts}" var="dataset" varStatus="c">
				var dataPointsArr = [[], []];
				<chart:forEach items="${dataset.chartLists}" var="dataPoints" varStatus="loop">	
					<chart:forEach items="${dataPoints}" var="dataPoint">
						xValue = parseFloat("${dataPoint.x}");
						yValue = parseFloat("${dataPoint.y}");
						
						dataPointsArr[parseInt("${loop.index}")].push({
							x: xValue,
							y: yValue,
						});
					</chart:forEach>
				</chart:forEach>
				chartArray[parseInt("${c.index}")] = dataPointsArr;
			</chart:forEach>	
			/* logChartDataToConsole(chartArray); */
		}
		
		function logChartDataToConsole(chartArray) {
			for(var c = 0; c < chartArray.length; c++) {
				console.log("dataset[" + c + "]");
				for(var i = 0; i < dataPointsArr.length; i++) {
					for(var j = 0; j < dataPointsArr[i].length; j++) {
						console.log(dataPointsArr[i][j]);
					}
				}
			}
		}
	</script>
</body>
</html>