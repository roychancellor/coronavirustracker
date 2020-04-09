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
			<h2 style="display:inline-block">Dashboard for <strong style="color:#522398">${region}</strong></h2>
			<a style="font-size:1em" class="btn btn-success" href="${pageContext.request.contextPath}/chart-info">Chart Info</a>
			<a style="font-size:1em" class="btn btn-warning" href="${pageContext.request.contextPath}/corona">Return Home</a>
		</div>
		<div class="dashboard">
		<table class="table table-dark">
			<tr>
				<td><div id="chartContainer0" style="height: 250px; width: 100%"></div></td>
				<td><div id="chartContainer1" style="height: 250px; width: 100%"></div></td>
			</tr>
			<tr>
				<td><div id="chartContainer2" style="height: 250px; width: 100%"></div></td>
				<td><div id="chartContainer3" style="height: 250px; width: 100%"></div></td>
			</tr>
			<tr>
				<td><div id="chartContainer4" style="height: 250px; width: 100%"></div></td>
				<td><div id="chartContainer5" style="height: 250px; width: 100%"></div></td>
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
		var chartArray = [];
		
 		makeChartDataFromJavaLists();
 		
 		addLoadEvent(makeChart("chartContainer0", "${chart1.chartTitle}", "${chart1.xAxisTitle}", "${chart1.yAxisTitle}", chartArray[0]));
 		addLoadEvent(makeChart("chartContainer1", "${chart2.chartTitle}", "${chart2.xAxisTitle}", "${chart2.yAxisTitle}", chartArray[1]));
		addLoadEvent(makeChart("chartContainer2", "${chart3.chartTitle}", "${chart3.xAxisTitle}", "${chart3.yAxisTitle}", chartArray[2]));
		addLoadEvent(makeChart("chartContainer3", "${chart4.chartTitle}", "${chart4.xAxisTitle}", "${chart4.yAxisTitle}", chartArray[3]));
		addLoadEvent(makeChart("chartContainer4", "${chart5.chartTitle}", "${chart5.xAxisTitle}", "${chart5.yAxisTitle}", chartArray[4]));
		addLoadEvent(makeChart("chartContainer5", "${chart6.chartTitle}", "${chart6.xAxisTitle}", "${chart6.yAxisTitle}", chartArray[5]));
		
	<!-- From h t t p s www.htmlgoodies.com/beyond/javascript/article.php/3724571/using-multiple-javascript-onload-functions.htm -->	
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
			<chart:forEach items="${allDashboardData}" var="dataset" varStatus="c">
				var dataPointsArr = [[], []];
				<chart:forEach items="${dataset}" var="dataPoints" varStatus="loop">	
					<chart:forEach items="${dataPoints}" var="dataPoint">
						xValue = parseFloat("${dataPoint.x}");
						yValue = parseFloat("${dataPoint.y}");
						dataPointsArr[parseInt("${loop.index}")].push({
							x : xValue,
							y : yValue,
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
		
		function makeChart(chartContainerString, chartTitle, axisXTitle, axisYTitle, dataPointsArr) {
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
					dataPoints: dataPointsArr[0]
				},
				{
					type: "scatter",
					dataPoints: dataPointsArr[1]
				}]
			});
			 
			chart.render();
		}
	</script>
</body>
</html>