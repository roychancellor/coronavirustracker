<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
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
				<div id="chartContainer11" style="height: 350px; width: 100%"><img src="resources/images/chartinfo/Chart-11.png">
				<p style="font-size:0.5em; color:white; padding:2px; margin:2px; line-height:1.0em;">This chart shows the total (cumulative) number of cases as reported by the data source versus the
				number of days since cases became greater than zero. This chart has, perhaps, the least amount of valuable information, as it does not
				reference the size of the population; nevertheless, its shape is able to give an overall picture of the rate of change of cases over time.</p>
				</div>
				</td>
				<td class="col-md-2">
				<div id="chartContainer12" style="height: 350px; width: 100%"><img src="resources/images/chartinfo/Chart-12.png">
				<p style="font-size:0.5em; color:white; padding:2px; margin:2px; line-height:1.0em;">This chart shows the rate of change of cases in
				units of % per day. For example, if the current number of cases is 100 today and 4 new cases are added, the rate of change is 4% per day.
				This is similar to a car's speed-o-meter: How fast the car is moving in miles per hour. The lower the rate of change,
				the fewer number of new cases are being added each day. Note that as long as the rate is positive, the number of cases is still increasing,
				but as the rate goes lower, the increase is less and less.</p>
				</div>
				</td>
			</tr>
			<tr>
				<td class="col-md-2"><div id="chartContainer21" style="height: 400px; width: 100%"><img src="resources/images/chartinfo/Chart-21.png">
				<p style="font-size:0.5em; color:white; padding:2px; margin:2px; line-height:1.0em;">This chart shows the acceleration of cases, or the 
				rate of change of the rate of change of cases in units of % per day. Keeping with the car analogy, this is a measure of how hard you
				are pressing the gas pedal (positive acceleration) or pressing the brake (negative acceleration). For coronavirus, we want negative
				acceleration, implying that while cases are still increasing, the rate of change is decreasing (braking --> slowing down).</p>
				</div>
				</td>
				<td class="col-md-2"><div id="chartContainer22" style="height: 400px; width: 100%"><img src="resources/images/chartinfo/Chart-22.png">
				<p style="font-size:0.5em; color:white; padding:2px; margin:2px; line-height:1.0em;">This chart shows the daily change in cases versus
				the total cases and is important for detecting the change from "increasing at an increasing rate" to "increasing at a decreasing rate",
				mathematically called the inflection point. Keeping with the car analogy, this is the point at which you hit the brakes after pressing the 
				gas pedal to accelerate to a constant speed. The straight line is a reference line that shows how change in cases
				would grow if purely exponential. Deviation downward from the straight line is good, meaning the change in cases per cases is less than 
				exponential, implying inflection has occurred. The scale is logarithmic so the early cases still show well.</p>
				</div>
				</td>
			</tr>
			<tr>
				<td class="col-md-2"><div id="chartContainer31" style="height: 350px; width: 100%"><img src="resources/images/chartinfo/Chart-31.png">
				<p style="font-size:0.5em; color:white; padding:2px; margin:2px; line-height:1.0em;">This chart is exactly the same as the previous
				chart, except for deaths. Note that deaths lags cases by a few days, but the time scale here is the same as cases.</p>
				</div>
				</td>
				<td class="col-md-2"><div id="chartContainer32" style="height: 350px; width: 100%"><img src="resources/images/chartinfo/Chart-32.png">
				<p style="font-size:0.5em; color:white; padding:2px; margin:2px; line-height:1.0em;">This chart is exactly the same as
				the rate of change of cases (speed-o-meter), except for deaths.  Note that deaths lags cases by a few days,
				but the time scale here is the same as cases.</p>
				</div>
				</td>
			</tr>
		</table>
		</div>
	</div>
<%-- 	<div class="container">
		<%@ include file="common/header-common.jspf" %>
		<section>
			<h1>Chart Information</h1>
			<h3>Explain the charts here...</h3>
			<p>
			<p><a class="btn btn-success" onClick="history.back()">Return to Dashboard</a></p>
			<!-- href="${pageContext.request.contextPath}/dashboard" -->
		</section>
		<%@ include file="common/footer-common.jspf" %>
	</div> --%>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>