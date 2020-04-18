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
	<title>About</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div class="container">
		<div>
			<h2 style="display:inline-block"><strong style="color:#522398">About this site</strong></h2>
			<a class="btn btn-success" onClick="history.back()">Return to Home Page</a>
		</div>
		<div class="dashboard">
		<table class="table table-dark">
			<tr>
				<td class="col-md-2">
				<div id="chartContainer11" style="height: 140px; width: 100%">
				<h3 style="color:#522398;">Why another Coronavirus site?</h3>
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.1em;">There are certainly far more flashy sites
				out there that are more visually appealing and dynamic.
				This <a href="https://public.tableau.com/profile/jonas.nart#!/vizhome/COVID19_15844962693420/COVID19-TrendTracker">Tableau page</a>
				is one such example. This site has a different purpose. Here, I am attempting to look at the data
				a little differently than the other sites to help satisfy my curiosity and inform my own
				opinions about when it is safe to "go back into the water", as it were.</p>
				</div>
				</td>
				<td class="col-md-2">
				<div id="chartContainer12" style="height: 140px; width: 100%">
				<h3 style="color:#522398;">What are the data sources?</h3>
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.1em;">All United States data comes from
				<a href="https://covidtracking.com">The COVID Tracking Project</a> which offers some really sweet APIs for easy access of the data by a computer. 
				These people seem to be gathering data in a serious way, attempting to be as accurate as possible. Data for any other countries comes from
				<a href="https://opendata.ecdc.europa.eu/covid19/casedistribution/json/">European Center for Disease Control</a> which offers
				a similar type of API, but with the whole world lumped into one file. Makes it slower to download, but I'm caching it, so you won't notice.</p>
				</div>
				</td>
			</tr>
			<tr>
				<td class="col-md-2"><div id="chartContainer21" style="height: 120px; width: 100%">
				<h3 style="color:#522398;">The regions are less limited!</h3>
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.1em;">I finally got around to adding all 50 states, the 
				District of Columbia, and a host of other reference countries, so the selection is not as limited as when initially released. Click away!</p>
				</div>
				</td>
				<td class="col-md-2"><div id="chartContainer22" style="height: 120px; width: 100%">
				<h3 style="color:#522398;">What are all these charts?</h3>
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.1em;">Click on the Chart Info button when viewing a
				dashboard page for an explanation of each chart. If it seems too technical, well, consider that I am a mechanical engineer by training,
				who taught mathematics, and who works as a software engineer......get over it.  :-)</p>
				</div>
				</td>
			</tr>
			<tr>
				<td class="col-md-2"><div id="chartContainer31" style="height: 200px; width: 100%">
				<h3 style="color:#522398;">Who is the site creator?</h3>
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.1em;">Roy Chancellor is a guy who likes math and likes
				to program. I recently began a career as a software engineer after working as a mechanical engineer and math teacher. Like all of you,
				I am currently homebound for work and want to let the data inform me when I *should* be able to return to normal. Very grateful for my job,
				having said job means I work on the site part-time in the evenings.</p>
				</div>
				</td>
				<td class="col-md-2"><div id="chartContainer32" style="height: 200px; width: 100%">
				<h3 style="color:#522398;">Your site isn't completely terrible, how did you build it?</h3>
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.1em;">The site is a 
				<a href="https://www.java.com/en/">Java</a> application that uses <a href="https://en.wikipedia.org/wiki/JavaServer_Pages">JSP</a>
				and <a href="https://getbootstrap.com">Bootstrap</a> to
				render the front-end pages that you see. Sorry, no React, Angular, etc. Underneath, it uses Java with
				<a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/web.htmlSpring">Spring MVC</a> for all
				external API calls, internal logic, and data manipulation/analysis. This is a stateless application, in that it stores
				no persistent data (e.g. in a database), rather it uses <a href="https://spring.io">Spring</a> caching to make the site a bit snappier.
				The charts are from <a href="https://www.chartjs.org">Chart.js</a> which uses
				<a href="https://en.wikipedia.org/wiki/JavaScript">JavaScript</a> to configure and render. The site resides on
				<a href="https://aws.amazon.com">AWS</a> in an <a href="https://aws.amazon.com/elasticbeanstalk/">Elastic Beanstalk</a> instance
				running a <a href="https://en.wikipedia.org/wiki/Linux">Linux</a> virtual machine with a <a href="http://tomcat.apache.org">Tomcat</a> server.</p>
				</div>
				</td>
			</tr>
			<tr>
				<td class="col-md-2"><div id="chartContainer22" style="height: 200px; width: 100%">
				<h3 style="color:#522398;">How can I write to law makers?</h3>
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.1em;">I'm not about to tell you <strong><u>what</u></strong>
				to write to your elected officials, but I <strong><u>will</u></strong> tell you <strong><u>to</u></strong> write to them. Here are some links:</p>
				<ul style="font-size:0.75em;">
					<li><a href="https://www.whitehouse.gov/contact/" target="_blank">President Trump</a></li>
					<li><a href="https://www.mcsally.senate.gov/contact_martha" target="_blank">Senator McSally (Arizona)</a></li>
					<li><a href="https://www.sinema.senate.gov/contact-kyrsten" target="_blank">Senator Sinema (Arizona)</a></li>
					<li><a href="https://www.senate.gov/general/contact_information/senators_cfm.cfm" target="_blank">Contact your U.S. Senator</a></li>
					<li><a href="https://www.house.gov/representatives/find-your-representative" target="_blank">Contact your U.S. Representative</a></li>
					<li><a href="https://azgovernor.gov/engage/form/contact-governor-ducey" target="_blank">Governor Ducey (Arizona)</a></li>
					<li><a href="https://www.azleg.gov/findmylegislator/" target="_blank">Contact your Arizona Senator</a></li>
					<li><a href="https://www.azleg.gov/findmylegislator/" target="_blank">Contact your Arizona Representatives</a></li>
					<li><a href="https://www.phoenix.gov/mayor/contact-mayor-gallego" target="_blank">Mayor Gallego (Phoenix)</a></li>
				</ul>
				</div>
			</tr>
		</table>
		</div>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>