<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="chart" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
	    <div class="row row-no-gutters">
	      <div class="col-md-7"><p style="color:#522398;font-size:2em;">Dashboard Region</p></div>
	      <div class="col-md-3"><p style="color:#522398;font-size:2em;">Population</p></div>
	      <div class="col-md-2"><a class="btn btn-md btn-success" href="${pageContext.request.contextPath}/chart-info">Chart Info</a></div>
	    </div>
	    <div class="row row-no-gutters">
	      <div class="col-md-7"><p><strong style="color:#FFFFFF;font-size:2em;">${fullregion}</strong></p></div>
	      <div class="col-md-3"><p><strong style="color:#FFFFFF;font-size:2em;"><fmt:formatNumber type = "number" pattern = "#,###" value = "${population}" /></strong></p></div>
	      <div class="col-md-2"><a class="btn btn-md btn-warning" href="${pageContext.request.contextPath}/corona">Return Home</a></div>
	    </div>
		<div>
		<table class="table table-bordered">
			<tr style="line-height:12px;">
				<td style="color:#522398;">
					Cases:
				</td>
				<td style="color:#FFFFFF;">
					Total: <fmt:formatNumber type = "number" pattern = "#,###" value = "${dashstats.casesTotal}" />
					(+<fmt:formatNumber type = "number" pattern = "#,###" value = "${dashstats.casesToday}" /> today)
				</td>
				<td style="color:#FFFFFF;">
					Rate: <fmt:formatNumber type = "number" pattern = "#.##" value = "${dashstats.rateOfCasesToday}" />% per day
					(change: <fmt:formatNumber type = "number" pattern = "#.#" value = "${dashstats.accelOfCasesToday}" />%)
				</td>
			</tr>
			<tr style="line-height:12px;">
				<td style="color:#522398;">
					Deaths:
				</td>
				<td style="color:#FFFFFF;">
					Total: <fmt:formatNumber type = "number" pattern = "#,###" value = "${dashstats.deathsTotal}" />
					(+<fmt:formatNumber type = "number" pattern = "#,###" value = "${dashstats.deathsToday}" /> today)
				</td>
				<td style="color:#FFFFFF;">
					Rate: <fmt:formatNumber type = "number" pattern = "#.##" value = "${dashstats.rateOfDeathsToday}" />% per day
					(change: <fmt:formatNumber type = "number" pattern = "#.#" value = "${dashstats.accelOfDeathsToday}" />%)
				</td>
			</tr>
			<tr style="line-height:12px;">
				<td style="color:#522398;">
					Per Capita:
				</td>
				<td style="color:#FFFFFF;">
					Cases: <fmt:formatNumber type = "number" pattern = "#.####" value = "${casespercent}" />%
					(<fmt:formatNumber type = "number" pattern = "#,###" value = "${casespermillion}" /> per million)
				</td>
				<td style="color:#FFFFFF;">
					Deaths: <fmt:formatNumber type = "number" pattern = "#.#####" value = "${deathspercent}" />%
					(<fmt:formatNumber type = "number" pattern = "#,###" value = "${deathspermillion}" /> per million)
				</td>
			</tr>
		</table>
		</div>
		<div class="dashboardCharts">
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
			<tr>
				<td><div id="chartContainer41" style="height: 250px; width: 100%"></div></td>
				<td><div id="chartContainer42" style="height: 250px; width: 100%"></div></td>
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
		
		var mapIndexToContainerRowCol = new Map();
		mapIndexToContainerRowCol.set("0", "11");
		mapIndexToContainerRowCol.set("1", "12");
		mapIndexToContainerRowCol.set("2", "21");
		mapIndexToContainerRowCol.set("3", "22");
		mapIndexToContainerRowCol.set("4", "31");
		mapIndexToContainerRowCol.set("5", "32");
		mapIndexToContainerRowCol.set("6", "41");
		mapIndexToContainerRowCol.set("7", "42");
		
		const numCharts = 8;
		const CASES_TIME_HISTORY = 0;
		const DEATHS_TIME_HISTORY = 4;
 		
		//ACTIONS
		makeChartDataFromJavaLists();
		makeChartConfigs(); 		
 		for(var i = 0; i < numCharts; i++) {
 			if(i == CASES_TIME_HISTORY || i == DEATHS_TIME_HISTORY) {
 				addLoadEvent(makeChartCasesOrDeathsByTime(containers[i], configObjects[i], chartArray[i]));
 			} else {
 				addLoadEvent(makeChart(containers[i], configObjects[i], chartArray[i]));
 			}
 		}
		addLoadEvent(makeChartCasesOrDeathsByTime(containers[4], configObjects[4], chartArray[4]));
 		for(var i = numCharts / 2 + 1; i < numCharts; i++) {
 			addLoadEvent(makeChart(containers[i], configObjects[i], chartArray[i]));
 		}
 		//END ACTIONS
 		
		function makeChartConfigs() {
	 		<chart:forEach items = "${allDashboardCharts}" var = "config" varStatus = "loop">
 				var c = parseInt("${loop.index}");
	 			containers[c] = "chartContainer" + mapIndexToContainerRowCol.get(c);
	 			
	 			var axis2Title = "";
	 			if(c == CASES_TIME_HISTORY) {
	 				axis2Title = "Total and Daily Cases in " + "${fullRegion}";
	 			} else if(c == DEATHS_TIME_HISTORY) {
	 				axis2Title = "Total and Daily Deaths in " + "${fullRegion}";
	 			}
	 			
 				configObjects[c] = {
	 				chartTitle: "${config.chartConfig.chartTitle}",
	 				xAxisTitle: "${config.chartConfig.xAxisTitle}",
	 				yAxisTitle: "${config.chartConfig.yAxisTitle}",
	 				xAxisLogarithmic: "${config.chartConfig.xAxisLogarithmic}" == "true",
					yAxisLogarithmic: "${config.chartConfig.yAxisLogarithmic}" == "true",
					xGridDashType: "${config.chartConfig.xGridDashType}",
					xMinimum: parseInt("${config.chartConfig.xAxisMin}"),
					xMaximum: parseInt("${config.chartConfig.xAxisMax}"),
					yMinimum: parseInt("${config.chartConfig.yAxisMin}"),
					yMaximum: parseInt("${config.chartConfig.yAxisMax}"),
					yInterval: parseInt("${config.chartConfig.yAxisInterval}"),
					legendVAlign: "${config.chartConfig.legendVerticalAlign}",
					legendHAlign: "${config.chartConfig.legendHorizonalAlign}",
					data1Name: "${config.chartConfig.dataSeries1Name}",
					data2Name: "${config.chartConfig.dataSeries2Name}",
					axis2Title: axis2Title
	 			};
	 		</chart:forEach>
		}
 		
		function makeChart(chartContainerString, config, dataPointsArr) {
 			var chart = new CanvasJS.Chart(chartContainerString, {
				animationEnabled: true,
				exportEnabled: true,
				theme: 'light2', //light1, dark1, dark2
				legend: {
					verticalAlign: config.legendVAlign, //top, bottom
					horizontalAlign: config.legendHAlign, //left, right
					dockInsidePlotArea: true
				},
				title: {
					text: config.chartTitle,
					fontSize: 14
				},
				axisY: {
					title: config.yAxisTitle,
					logarithmic: config.yAxisLogarithmic,
					minimum: config.yMinimum,
					maximum: config.yMaximum,
					interval: config.yInterval
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
					name: config.data1Name,
					pointRadius: 1,
					pointHoverRadius: 5,
					fill: true,
					tension: 0,
					showLine: false,
					dataPoints: dataPointsArr[0],
					showInLegend: true
				},
				{
					type: "line",
					name: config.data2Name,
					markerSize: 0,
					pointHoverRadius: 5,
					fill: false,
					tension: 0.4,
					showLine: true,
					dataPoints: dataPointsArr[1],
					showInLegend: true
				}]
			});
			chart.render();
		}
		
		function makeChartCasesOrDeathsByTime(chartContainerString, config, dataPointsArr) {
 			var chart = new CanvasJS.Chart(chartContainerString, {
				animationEnabled: true,
				exportEnabled: true,
				theme: 'light2', //light1, dark1, dark2
				legend: {
					verticalAlign: config.legendVAlign, //top, bottom
					horizontalAlign: config.legendHAlign, //left, right
					dockInsidePlotArea: true
				},
				title: {
					text: config.chartTitle,
					fontSize: 14
				},
				axisY: {
					title: config.yAxisTitle,
					logarithmic: config.yAxisLogarithmic,
					minimum: config.yMinimum,
					maximum: config.yMaximum,
					interval: config.yInterval
				},
				axisY2: {
					title: config.axis2Title
				},
				axisX: {
					title: config.xAxisTitle,
					logarithmic: config.xAxisLogarithmic,
					minimum: config.xMinimum,
					maximum: config.xMaximum,
					gridDashType: config.xGridDashType
				},
				data: [
					{
						type: "scatter",
						name: config.data1Name,
						pointRadius: 1,
						pointHoverRadius: 5,
						fill: true,
						tension: 0,
						showLine: false,
						dataPoints: dataPointsArr[0],
						showInLegend: true
					},
					{
						axisYType: "secondary",
						name: config.data2Name,
						type: "line",
						markerSize: 0,
						pointHoverRadius: 5,
						fill: false,
						tension: 0.4,
						showLine: true,
						dataPoints: dataPointsArr[1],
						showInLegend: true
					}
				]
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
				<chart:forEach items="${dataset.chartLists.chartLists}" var="dataPoints" varStatus="loop">	
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