<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="region" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="format" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<spring:url value="/resources/css/style.css" var="mainCss" />
	<spring:url value="/resources/images/header.jpg" var="headerImg" />
	<spring:url value="/resources/images/footer.jpg" var="footerImg" />
	<meta charset="UTF-8">
	<title>Corona Home</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div class="container">
		<%@ include file="common/header-common.jspf" %>
	</div>
	<div class="container">
		<section>
			<region:form modelAttribute="region" action="${pageContext.request.contextPath}/dashboard" method="POST">
			<!-- <div class="container"> -->
			<h4 style="color:#232f98;">Click a button to see the region dashboard (reference countries at bottom)</h4>
			<!-- </div> -->
		    <div class="row">
		      <div class="col-md-3"></div>
		      <div class="col-md-6"><button class="btn btn-success btn-lg btn-block" name="region" value=${USA} type="submit">United States as a Whole Country</button></div>
		      <div class="col-md-3"></div>
		      <div class="col-md-4"></div>
		      <div class="col-md-4"><button class="btn btn-secondary btn-md btn-block" name="region" value=${USA_NO_NY} type="submit">United States without New York State</button></div>
		      <div class="col-md-4"></div>
		    </div>
		    <h4 style="color:#232f98;">States</h4>
		    <div class="row">
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${AL} type="submit">Alabama</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${AK} type="submit">Alaska</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${AZ} type="submit">Arizona</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${AR} type="submit">Arkansas</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${CA} type="submit">California</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${CO} type="submit">Colorado</button></div>
		    </div>
		    <div class="row">
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${CT} type="submit">Connecticut</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${DE} type="submit">Delaware</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${DC} type="submit">District of Columbia</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${FL} type="submit">Florida</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${GA} type="submit">Georgia</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${HI} type="submit">Hawaii</button></div>
		    </div>
		    <div class="row">
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${ID} type="submit">Idaho</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${IL} type="submit">Illinois</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${IN} type="submit">Indiana</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${IA} type="submit">Iowa</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${KS} type="submit">Kansas</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${KY} type="submit">Kentucky</button></div>
		    </div>
		    <div class="row">
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${LA} type="submit">Louisiana</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${ME} type="submit">Maine</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${MD} type="submit">Maryland</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${MA} type="submit">Massachusetts</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${MI} type="submit">Michigan</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${MN} type="submit">Minnesota</button></div>
		    </div>
		    <div class="row">
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${MS} type="submit">Mississippi</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${MO} type="submit">Missouri</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${MT} type="submit">Montana</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${NE} type="submit">Nebraska</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${NV} type="submit">Nevada</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${NH} type="submit">New Hampshire</button></div>
		    </div>
		    <div class="row">
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${NJ} type="submit">New Jersey</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${NM} type="submit">New Mexico</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${NY} type="submit">New York</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${NC} type="submit">North Carolina</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${ND} type="submit">North Dakota</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${OH} type="submit">Ohio</button></div>
		    </div>
		    <div class="row">
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${OK} type="submit">Oklahoma</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${OR} type="submit">Oregon</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${PA} type="submit">Pennsylvania</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${RI} type="submit">Rhode Island</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${SC} type="submit">South Carolina</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${SD} type="submit">South Dakota</button></div>
		    </div>
		    <div class="row">
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${TN} type="submit">Tennessee</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${TX} type="submit">Texas</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${UT} type="submit">Utah</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${VT} type="submit">Vermont</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${VA} type="submit">Virginia</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${WA} type="submit">Washington</button></div>
		    </div>
		    <div class="row">
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${WV} type="submit">West Virginia</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${WI} type="submit">Wisconsin</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${WY} type="submit">Wyoming</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${PRI} type="submit">Puerto Rico</button></div>
		    </div>
		    <h4 style="color:#232f98;">Reference Countries</h4>
		    <div class="row">
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${AUS} type="submit">Australia</button></div>
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${CAN} type="submit">Canada</button></div>
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${CHN} type="submit">China</button></div>
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${FRA} type="submit">France</button></div>
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${DEU} type="submit">Germany</button></div>
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${GBR} type="submit">Great Britain</button></div>
		    </div>
		    <div class="row">
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${HKG} type="submit">Hong Kong</button></div>
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${ITA} type="submit">Italy</button></div>
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${JPN} type="submit">Japan</button></div>
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${MEX} type="submit">Mexico</button></div>
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${SGP} type="submit">Singapore</button></div>
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${KOR} type="submit">South Korea</button></div>
		    </div>
			</region:form>
		</section>
	</div>
	<div>
		<%@ include file="common/footer-common.jspf" %>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>