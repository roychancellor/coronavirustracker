<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="chart" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" href="webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
	<spring:url value="/resources/css/style.css" var="mainCss" />
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Data Dashboard</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<chart:set var = "type" scope = "session" value = "${regionType}" />
	<div class="container">
	    <div class="row row-no-gutters">
	      <div class="col-md-6"><p style="color:#10A5F5;font-size:2em;">Dashboard Region</p></div>
	      <div class="col-md-4"><p style="color:#10A5F5;font-size:2em;">Population</p></div>
	      <div class="col-md-2"><a class="btn btn-md btn-success" href="${pageContext.request.contextPath}/chart-info">Chart Info</a></div>
	    </div>
	    <div class="row row-no-gutters">
	      <div class="col-md-6"><p><strong style="color:#FFFFFF;font-size:2em;">${fullregion}</strong></p></div>
	      <div class="col-md-4">
	      	<p><strong style="color:#FFFFFF;font-size:2em;">
	      		<fmt:formatNumber type = "number" pattern = "#,###" value = "${population}" /></strong>
				<chart:if test = "${type == 'state'}">
		      		<strong style="color:#FFFFFF;font-size:1.25em;">
		      		(<fmt:formatNumber type = "number" pattern = "#.##" value = "${regionpop_uspop}" /> % of U.S.)
		      		</strong>
				</chart:if>
	      	</p>
	      </div>
	      <div class="col-md-2"><a class="btn btn-md btn-warning" href="${pageContext.request.contextPath}/corona">Return Home</a></div>
	    </div>
		<div>
		<table class="table table-bordered">
			<tr style="line-height:12px;">
				<td style="text-align:right;color:#0000FF;">
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
				<td style="text-align:right;color:#8600FF;">
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
				<td style="text-align:right;color:#FFFF00;">
					By Region Pop.:
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
			<chart:if test = "${(type == 'us' or type == 'state')}">
			<tr style="line-height:12px;">
				<td style="text-align:right;color:#009C0C;">
					By Testing:
				</td>
				<td style="color:#FFFFFF;">
					Cases: <fmt:formatNumber type = "number" pattern = "#.#" value = "${dashstats.proportionOfPositiveTests}" />%
					of <fmt:formatNumber type = "number" pattern = "#,###" value = "${dashstats.totalTestsConducted}" /> total tests
				</td>
				<td style="color:#FFFFFF;">
					Deaths: <fmt:formatNumber type = "number" pattern = "#.#" value = "${dashstats.proportionOfDeathsFromPositives}" />% of positives
					(<fmt:formatNumber type = "number" pattern = "#.###" value = "${dashstats.proportionOfDeathsFromTested}" />% of tested)
				</td>
			</tr>
			</chart:if>
			<chart:if test = "${type == 'state'}">
			<tr style="line-height:12px;">
				<td style="text-align:right;color:#FF0000;">
					By U.S. Totals:
				</td>
				<td style="color:#FFFFFF;">
					Cases: <fmt:formatNumber type = "number" pattern = "#.##" value = "${casesregion_totaluscases}" />%
					of <fmt:formatNumber type = "number" pattern = "#,###" value = "${totaluscases}" /> U.S. cases
				</td>
				<td style="color:#FFFFFF;">
					Deaths: <fmt:formatNumber type = "number" pattern = "#.##" value = "${deathsregion_totalusdeaths}" />%
					of <fmt:formatNumber type = "number" pattern = "#,###" value = "${totalusdeaths}" /> U.S. deaths
				</td>
			</tr>
			</chart:if>			
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
	<script src="webjars/jquery/3.1.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
 	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
	<script src="https://canvasjs.com/assets/script/jquery.canvasjs.min.js"></script>
	
	<!-- MAKE ALL THE CHARTS LAST -->
	<script type="text/javascript">
		const NUM_CHARTS = 8;
		const CASES_TIME_HISTORY_INDEX = 0;
		const DEATHS_TIME_HISTORY_INDEX = 4;
		
		//Constants that define the row-column of the chart position on the dashboard
		const CHANGE_IN_CASES_VS_TOTAL_CASES = "22";
		const CHANGE_IN_DEATHS_VS_TOTAL_DEATHS = "42";
		const TIME_SERIES_CASES = "11";
		const TIME_SERIES_DEATHS = "31";
		const TIME_SERIES_RATE_OF_CASES = "12";
		const TIME_SERIES_ACCEL_OF_CASES = "21";
		const TIME_SERIES_RATE_OF_DEATHS = "32";
		const TIME_SERIES_ACCEL_OF_DEATHS = "41";
	 		
		//Maps the index of the chart data to the row-column constant
		//for referencing the chartContainer div tags above
		var mapIndexToContainerRowCol = new Map();
		mapIndexToContainerRowCol.set(0, TIME_SERIES_CASES);
		mapIndexToContainerRowCol.set(1, TIME_SERIES_RATE_OF_CASES);
		mapIndexToContainerRowCol.set(2, TIME_SERIES_ACCEL_OF_CASES);
		mapIndexToContainerRowCol.set(3, CHANGE_IN_CASES_VS_TOTAL_CASES);
		mapIndexToContainerRowCol.set(4, TIME_SERIES_DEATHS);
		mapIndexToContainerRowCol.set(5, TIME_SERIES_RATE_OF_DEATHS);
		mapIndexToContainerRowCol.set(6, TIME_SERIES_ACCEL_OF_DEATHS);
		mapIndexToContainerRowCol.set(7, CHANGE_IN_DEATHS_VS_TOTAL_DEATHS);
		
		//MAIN ACTIONS
		var containers = [];
 		var configObjects = [];
		var chartArray = [];
		
		makeChartDataFromJavaLists();
		makeChartConfigs(); 		
 		for(var i = 0; i < NUM_CHARTS; i++) {
 			if(i == CASES_TIME_HISTORY_INDEX || i == DEATHS_TIME_HISTORY_INDEX) {
 				addLoadEvent(makeChartCasesOrDeathsByTime(containers[i], configObjects[i], chartArray[i]));
 			} else {
 				addLoadEvent(makeChart(containers[i], configObjects[i], chartArray[i]));
 			}
 		}
 		//END MAIN ACTIONS
 		
 		//Function that makes a chart configuration object for each chart
		function makeChartConfigs() {
	 		<chart:forEach items = "${allDashboardCharts}" var = "config" varStatus = "loop">
 				var c = parseInt("${loop.index}");
	 			containers[c] = "chartContainer" + mapIndexToContainerRowCol.get(c);
	 			
	 			var axis2TitleValue = "";
	 			var pointColorStr = "blue";
	 			var lineColorStr = "red";
	 			if(c == CASES_TIME_HISTORY_INDEX) {
	 				axis2TitleValue = "Daily Cases";
	 			} else if(c == DEATHS_TIME_HISTORY_INDEX) {
	 				axis2TitleValue = "Daily Deaths";
	 			}
	 			
	 			if(c >= DEATHS_TIME_HISTORY_INDEX) {
	 				pointColorStr = "purple";
	 				lineColorStr = "green";
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
					axis2Title: axis2TitleValue,
					colorPoints: pointColorStr,
					colorLine: lineColorStr
	 			};
	 		</chart:forEach>
		}
 		
 		//Function that makes every chart except the time series of total and daily cases/deaths
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
			   	toolTip: {
			   		shared: true,
		            contentFormatter: function(e) {
		            	return contentFormatterFunction(e, chartContainerString);
		            }
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
					showInLegend: true,
					color: config.colorPoints,
					indexLabelPlacement: "outside",
					indexLabelOrientation: "vertical",
					indexLabelFontColor: "white",
					indexLabelFontSize: 0
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
					showInLegend: true,
					lineColor: config.colorLine,
					indexLabelPlacement: "outside",
					indexLabelOrientation: "vertical",
					indexLabelFontColor: "white",
					indexLabelFontSize: 0
				}]
			});
			chart.render();
		}
		
 		//Function that makes the time series charts for cases and deaths
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
			   	toolTip: {
			   		shared: true,
		            contentFormatter: function(e) {
		            	return contentFormatterFunction(e, chartContainerString);
		            }
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
						showInLegend: true,
						color: config.colorPoints,
						indexLabelPlacement: "outside",
						indexLabelOrientation: "vertical",
						indexLabelFontColor: "white",
						indexLabelFontSize: 0
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
						showInLegend: true,
						lineColor: config.colorLine,
						indexLabelPlacement: "outside",
						indexLabelOrientation: "vertical",
						indexLabelFontColor: "white",
						indexLabelFontSize: 0
					}
				]
			});
			chart.render();
		}
		
        //The tool tip content formatter functions for each chart type
		function contentFormatterFunction(e, chartContainerString) {
        	var content = "Date: " + e.entries[0].dataPoint.indexLabel + "</br>";
        	var positionRowCol = chartContainerString.substring(chartContainerString.length - 2, chartContainerString.length);
        	
        	if(positionRowCol == CHANGE_IN_CASES_VS_TOTAL_CASES) {
        		content += "Total Cases: "
        			+ "<strong>"
        			+ CanvasJS.formatNumber(e.entries[0].dataPoint.x, "#,###")
     				+ "</strong>"
     				+ "</br>"
     				+ "Daily Change in Cases: "
     				+ "<strong>"
     				+ CanvasJS.formatNumber(e.entries[0].dataPoint.y, "#,###")
     				+ "</strong>";
     		} else if(positionRowCol == CHANGE_IN_DEATHS_VS_TOTAL_DEATHS) {
        		content += "Total Deaths: "
        			+ "<strong>"
        			+ CanvasJS.formatNumber(e.entries[0].dataPoint.x, "#,###")
	 				+ "</strong>"
	 				+ "</br>"
	 				+ "Daily Change in Deaths: "
	 				+ "<strong>"
	 				+ CanvasJS.formatNumber(e.entries[0].dataPoint.y, "#,###")
	 				+ "</strong>";
 			} else {      	
	        	for(var i = 0; i < e.entries.length; i++) {
	        		if(positionRowCol == TIME_SERIES_CASES || positionRowCol == TIME_SERIES_DEATHS) {
		        		content += e.entries[i].dataSeries.name + ": "
		        			+ "<strong>"
		        			+ CanvasJS.formatNumber(e.entries[i].dataPoint.y, "#,###")
		        			+ "</strong>";
		        		content += "<br/>";
	        		} else if(positionRowCol == TIME_SERIES_RATE_OF_CASES
	        				|| positionRowCol == TIME_SERIES_ACCEL_OF_CASES
	        				|| positionRowCol == TIME_SERIES_RATE_OF_DEATHS
	        				|| positionRowCol == TIME_SERIES_ACCEL_OF_DEATHS) {
		        		content += e.entries[i].dataSeries.name + ": "
		        			+ "<strong>"
	        				+ CanvasJS.formatNumber(e.entries[i].dataPoint.y, "#.##") + "%"
	        				+ "</strong>";
		        		content += "<br/>";
	        		}
	        	}
     		}
           	return content;
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

		//Function that constructs the chart data objects for each chart in the dashboard
		function makeChartDataFromJavaLists() {
			var xValue;
			var yValue;
			var loopIndex;
			var dateString;
			<chart:forEach items="${allDashboardCharts}" var="dataset" varStatus="c">
				var dataPointsArr = [[], []];
				<chart:forEach items="${dataset.chartLists.chartLists}" var="dataPoints" varStatus="loop">	
					<chart:forEach items="${dataPoints}" var="dataPoint">
						xValue = parseFloat("${dataPoint.x}");
						yValue = parseFloat("${dataPoint.y}");
						loopIndex = parseInt("${loop.index}");
						if(loopIndex == 0) {
							dateString = "${dataPoint.dateChecked}";
							dataPointsArr[loopIndex].push({
								x: xValue,
								y: yValue,
								indexLabel: dateString
							});
						} else {
							dataPointsArr[loopIndex].push({
								x: xValue,
								y: yValue
							});
						}
					</chart:forEach>
				</chart:forEach>
				chartArray[parseInt("${c.index}")] = dataPointsArr;
			</chart:forEach>	
			/*logChartDataToConsole(chartArray);*/
		}
		
		//Helper function to log the data to the console for debugging purposes
		function logChartDataToConsole(chartArray) {
			for(var c = 0; c < chartArray.length; c++) {
				console.log("dataset[" + c + "]");
				for(var i = 0; i < chartArray[c].length; i++) {
					for(var j = 0; j < chartArray[c][i].length; j++) {
						console.log("data: " + chartArray[c][i][j]);
					}
				}
			}
		}
	</script>
</body>
</html>