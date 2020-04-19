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
				<td class="col-md-2"><div id="chartContainer21" style="height: 450px; width: 100%">
				<h3 style="color:#522398;">April 16, 2020</h3>
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.1em;">You can now get data for all 50 states + D.C., 
				plus a few reference countries! It just dawned on me that it's Friday night as I'm updating this site. I need to get a life.
				Wine is calling me. So, while you enjoy
				endless hours of entertainment by clicking all the buttons, take a look at some of the reference countries and compare their daily rates of
				new cases and deaths to ours. I am still interested in Italy, as they are about 9 days ahead of us in terms of reaching 100 cases. That would
				put us roughly in their current position by April 26. I have been thinking all along that April 30 is when we will be at a low enough level
				of new cases that we can open things up a bit and get the economy moving again. Yes, there will continue to be new cases, but the rate will be,
				I think, below the threshold where our hospitals can handle the new intakes. This is just my opinion and, obviously, that doesn't matter much.
				Also, keep in mind that all of this is highly region-dependent. Compare the highly-populated, dense states (e.g. the eastern seaboard) with
				the larger, less-populated western states and there is quite a difference. Remember to pay attention to per capita numbers, not just the total
				cases. The table at the top of each dashboard provides those numbers. Finally, if you have a strong opinion about the state of the economy,
				when we should open up, or whatever, write to your elected officials. Exercise your right to let your representatives know what you think!
				</p>
				</div>
				</td>
				<td class="col-md-2"><div id="chartContainer21" style="height: 450px; width: 100%">
				<h3 style="color:#522398;">April 14, 2020</h3>
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.1em;">This is the first deployment of the new dashboard site!
				It's been several days in the making and will evolve over time. What is the data saying? The United States as a whole continues to trend in
				in the right direction. We have unquestionably gone past the inflection point and the number of new cases is increasing at a decreasing rate.
				This is a fancy way of saying the new cases are slowing down. The same is true of deaths-categorized-as-COVID-19-deaths. I write it this way
				because currently there is some question about the way deaths are being recorded. Regardless, they too are slowing down.
				There is still a long way to go, we are all tired of being cooped up, the economy is really floundering, so the trillion dollars
				question is "when do we return to normal?" I have to think <strong><u>based on the data</u></strong> that many regions of the U.S. could
				return to normal now. Other regions, mostly the dense, urban areas need more time. Thankfully, not my decision. Pray for our leaders
				who have to make the tough decisions. As you can imagine, they are in a no-win situation. That's all for now. Peace to you all!</p>
				</div>
				</td>
			</tr>
			<tr>
				<td class="col-md-2">
				<div id="chartContainer12" style="height: 325px; width: 100%">
				<h3 style="color:#522398;">Past commentary</h3>
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.1em;">You can find past commentary 
				<a href="https://coronawithdata.wordpress.com/blog-2/">here</a>. All future commentary will be posted on this site.</p>
				</div>
				</td>
				<td class="col-md-2">
				<div id="chartContainer22" style="height: 325px; width: 100%">
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
				<p style="font-size:0.55em; color:white; padding:2px; margin:2px; line-height:1.1em;">There's actually no good reason.
				I'm not an expert in epidemiology. I'm not a medical professional. I don't have "a very particular set of skills,
				skills I have acquired over a very long career." I'm just a guy with a wide <i>variety</i> of skills and experience who is
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
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>