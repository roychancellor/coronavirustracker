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
	<title>Commentary</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div class="container">
		<div>
			<h2 style="display:inline-block"><strong style="color:#522398">Commentary about the data</strong></h2>
			<a class="btn btn-success" onClick="history.back()">Return to Home Page</a>
		</div>
		<div class="dashboard">
		<table class="table table-dark">
			<tr>
				<td class="col-md-2">
				<div id="chartContainer12" style="height: 50px; width: 100%">
				<h3 style="color:#522398;">Current Commentary</h3>
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.1em;">All my commentary is now located at my  
				<a href="https://coronawithdata.wordpress.com" target="_blank">Wordpress site</a>.
				</p>
				</div>
				</td>
				<td class="col-md-2">
				<div id="chartContainer22" style="height: 50px; width: 100%">
				<h3 style="color:#522398;">Where to write your elected officials?</h3>
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
				</td>
			</tr>
			<tr>
				<td class="col-md-2">
				<div id="chartContainer11" style="height: 240px; width: 100%">
				<h3 style="color:#522398;">Why should you listen to me?</h3>
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.1em;">Actually, there's no good reason.
				I'm not an epidemiologist. I'm not a medical professional. I don't have
				<a href="https://www.youtube.com/watch?v=KgmO32IdwuE" target="_blank">"a very particular set of skills,
				skills I have acquired over a very long career."</a> I'm just a guy with a <i>wide variety</i> of skills and experience who is
				interested in helping the data tell a story. Is the data I'm using reliable? As reliable as it can be. Trying to collect and
				organize data at the scale of a whole state, country, or world is going to be complicated and fraught with variation.
				That's the nature of data. But, by looking at it the right way, we can see things more clearly and in a way that matters
				to making decisions about the pandemic. I'll largely keep my personal opinions to myself, but feel free to reach out
				on Facebook or LinkedIn if you want to discuss privately.</p>
				</div>
				</td>
				<td class="col-md-2">
				<div id="chartContainer12" style="height: 240px; width: 100%">
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