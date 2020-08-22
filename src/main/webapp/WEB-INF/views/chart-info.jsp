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
	<title>Chart Information</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div class="container">
		<div>
			<h2 style="display:inline-block"><strong style="color:#522398">Chart Information</strong></h2>
			<a class="btn btn-success" onClick="history.back()">Return to Dashboard</a>
		</div>
		<div class="dashboard">
		<table class="table table-dark">
			<tr>
				<td class="col-md-2">
				<div id="dashboardContainer" style="height: 280px; width: 100%">
				<img style="max-height:100%; max-width:100%;" src="resources/images/chartinfo/dashboard.png">
				</div>
				</td>
				<td class="col-md-2">
				<div id="dashboardExplanation" style="height: 400px; width: 100%">
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.07em;">The dashboard shows relevant statistics for
				the current day. Some of the statistics are self-describing, but others need some explanation.</p>
				<ul style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.07em;">
					<li><strong style="color:#00FF00;">All-Time Totals (Today):</strong> The all-time cumulative number of confirmed positive cases or deaths 
					(increase in cases or deaths from the previous day)</li>
					<li><strong style="color:#00FF00;">Rate of Change:</strong> The % increase in number of cases or deaths, computed as 
					(today - yesterday) / yesterday * 100
					(change: the % change in the rate computed as (rate today - rate yesterday) / rate yesterday * 100)</li>
					<li><strong style="color:#FFFFFF;">Per Capita Rate L7 (L10):</strong> A moving ratio of confirmed positive cases or deaths over the 
					last 7 days (last 10 days) to a standardized number of people (100,000 currently). 
					<strong>This is an estimate of the current per capita rate of positive cases in the region.</strong></li>
					<li><strong style="color:#FFFFFF;">Test Positivity Rate (L7):</strong> Total positive cases for last 7 days divided by total tests over last 7 days * 100.
					Not computed for deaths because there is not currently a way to correlate specific positive tests with specific deaths.</li>
					<li><strong style="color:#FF8000;">Test Positivity Rate (All-Time):</strong> Total all-time positive cases or deaths divided by total tests * 100</li>
					<li><strong style="color:#FF8000;">Positivity Rate (per Capita):</strong> Total all-time cases or deaths divided by the population * 100 
					(i.e. total / population * 100,000)</li>
					<li><strong style="color:#FF8000;">Positivity Rate (per U.S. Totals):</strong> Total all-time region cases (or deaths) divided by total U.S. cases (or deaths) * 100 
					(i.e. total region cases / total U.S. cases * 100)
				</ul>
				</div>
				</td>
			</tr>
			<tr>
				<td class="col-md-2">
				<div id="chartContainer11" style="height: 380px; width: 100%"><img src="resources/images/chartinfo/Chart-11.png">
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.07em;">This chart shows a 4-day moving average of the daily
				new cases as well as the all-time total cases to date. The moving average smoothes out some of the daily noise and allows seeing the trend more cleanly.
				Remember that total cases tells only part of the story - the size of the population matters also. The dashboard table gives per-capita
				statistics for reference.</p>
				</div>
				</td>
				<td class="col-md-2">
				<div id="chartContainer12" style="height: 380px; width: 100%"><img src="resources/images/chartinfo/Chart-12.png">
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.07em;">This chart shows the rate of change of cases in
				units of % per day. For example, if the current number of cases is 100 today and 4 new cases are added, the rate of change is 4% per day.
				This is similar to a car's speed-o-meter: How fast the car is moving in miles per hour. The lower the rate of change,
				the fewer number of new cases are being added each day. Note that as long as the rate is positive, the number of cases is still increasing,
				but as the rate goes lower, the increase is less and less.</p>
				</div>
				</td>
			</tr>
			<tr>
				<td class="col-md-2"><div id="chartContainer21" style="height: 430px; width: 100%"><img src="resources/images/chartinfo/Chart-21.png">
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.07em;">This chart shows a 10-day moving sum of cases. 
				This means that each data point is the sum of the last 10 days. Each day, that day's cases are added and the cases from 10 days prior 
				is subtracted. Assuming the life span of a positive case is about 10 days, these data point approximate the total number of positive cases 
				that currently exist in the region right now. The chart also gives the sum divided by the region population times one million to normalize 
				the data.</p>
				</div>
				</td>
				<td class="col-md-2"><div id="chartContainer22" style="height: 430px; width: 100%"><img src="resources/images/chartinfo/Chart-22.png">
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.07em;">This chart shows the daily change in cases versus
				the total cases and is important for detecting the change from "increasing at an increasing rate" to "increasing at a decreasing rate",
				mathematically called the inflection point. Keeping with the car analogy, this is the point at which you hit the brakes after pressing the 
				gas pedal to accelerate to a constant speed. The straight line is a reference line that shows how change in cases
				would grow if purely exponential. Deviation downward from the straight line is good, meaning the change in cases per cases is less than 
				exponential, implying inflection has occurred. The scale is logarithmic so the early cases still show well.</p>
				</div>
				</td>
			</tr>
			<tr>
				<td class="col-md-2"><div id="chartContainer31" style="height: 300px; width: 100%"><img src="resources/images/chartinfo/Chart-31.png">
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.07em;">This chart is exactly the same as the time history
				chart for cases, but for deaths. Note that deaths lags cases by a few days, but the time scale here is the same as cases.</p>
				</div>
				</td>
				<td class="col-md-2"><div id="chartContainer32" style="height: 300px; width: 100%"><img src="resources/images/chartinfo/Chart-32.png">
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.07em;">This chart is exactly the same as
				the rate of change of cases (speed-o-meter), but for deaths.  Note that deaths lags cases by a few days,
				but the time scale here is the same as cases.</p>
				</div>
				</td>
			</tr>
			<tr>
				<td class="col-md-2"><div id="chartContainer41" style="height: 300px; width: 100%"><img src="resources/images/chartinfo/Chart-41.png">
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.07em;">This chart is exactly the same as the
				acceleration chart for cases, but for deaths. Note that deaths lags cases by a few days, but the time scale here is the same as cases.</p>
				</div>
				</td>
				<td class="col-md-2"><div id="chartContainer42" style="height: 300px; width: 100%"><img src="resources/images/chartinfo/Chart-42.png">
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.07em;">This chart is exactly the same as
				the chart for detecting inflection in cases, but for deaths.</p>
				</div>
				</td>
			</tr>
			<tr>
				<td class="col-md-2"><div id="chartContainer51" style="height: 320px; width: 100%"><img src="resources/images/chartinfo/Chart-51.png">
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.07em;">This chart is exactly the same as the
				cases time history, except for tests conducted. The application computes this as the sum of positive and negative cases.
				The time scale is the same as cases and deaths.</p>
				</div>
				</td>
				<td class="col-md-2"><div id="chartContainer52" style="height: 320px; width: 100%"><img src="resources/images/chartinfo/Chart-52.png">
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.07em;">This chart shows the ratio of positive
				test results to total tests conducted (i.e. positives / total * 100%) over time.</p>
				</div>
				</td>
			</tr>
			<tr>
				<td class="col-md-2"><div id="chartContainer61" style="height: 350px; width: 100%"><img src="resources/images/chartinfo/Chart-61.png">
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.07em;">This chart is exactly the same as the
				cases time history, except for current hospitalizations. Note that not all states report current hospitalizations.
				The time scale is the same as cases, deaths, and tests.</p>
				</div>
				</td>
				<td class="col-md-2"><div id="chartContainer62" style="height: 350px; width: 100%"><img src="resources/images/chartinfo/Chart-62.png">
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.07em;">This chart is exactly the same as the
				cases time history, except for cumulative hospitalizations. Note that not all states report cumulative hospitalizations. Also note
				that the daily new hospitalizations will not always be the same as the daily new as calculated by the current hospitalizations data.
				The time scale is the same as cases, deaths, and tests.</p>
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