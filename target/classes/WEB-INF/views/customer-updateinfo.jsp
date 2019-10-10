<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="updatecust" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="format" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<spring:url value="/resources/css/style.css" var="mainCss" />
	<spring:url value="/resources/images/header.jpg" var="headerImg" />
	<spring:url value="/resources/images/footer.jpg" var="footerImg" />
	<meta charset="UTF-8">
	<title>Settings</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div class="container">
		<%@ include file="common/header-common.jspf" %>
		<section>
			<h1>Customer Settings</h1>
			<h3>Make a selection below:</h3>
			<p class="error">${errorMessage}</p>
			<updatecust:form modelAttribute="customer" action="${pageContext.request.contextPath}/updatecustomer" method="POST">
				<updatecust:hidden path="custId"/> <!--  auto-generated by the database -->
				<div class="form-row">
					<h4><strong>Name</strong></h4>
					<div class="form-group col-md-6">
						<label class="col-lg-4 col-form-label">First Name:<br>${firstname}</label>
					</div>
					<div class="form-group col-md-6">
						<label class="col-lg-4 col-form-label">Last Name:<br>${lastname}</label>
					</div>
				</div>
				<div class="form-row">
					<h4><strong>Update Password</strong></h4>
					<div class="form-group col-md-6">
					<updatecust:label path="password">NEW Password:</updatecust:label><br />
					<updatecust:input type="password" class="form-control" placeholder="Enter NEW password (min 8 characters)" value="" path="password" />
					<updatecust:errors path="password" cssClass="error" />
					</div>
					<div class="form-group col-md-6">
					<updatecust:label path="passCompare">Re-enter NEW Password:</updatecust:label><br />
					<updatecust:input type="password" class="form-control" placeholder="Re-enter NEW password" value ="" path="passCompare" />
					<updatecust:errors path="passCompare" cssClass="error" />
					</div>
				</div>
				<div class="form-row">
					<div class="form-group col-md-8">
					<a class="error">${passmatcherror}</a>
					</div>
					<div class="form-group col-md-4">
					</div>
				</div>
				<div class="form-row">
					<h4><strong>Update E-mail</strong></h4>
					<div class="form-group col-md-6">
					<label>CURRENT E-mail Address:<br>${curEmail}</label><br />
					</div>
					<div class="form-group col-md-6">
					<updatecust:label path="emailAddress">NEW E-mail Address:</updatecust:label><br />
					<updatecust:input type="email" class="form-control" placeholder="Enter NEW e-mail address" path="emailAddress" />
					<updatecust:errors path="emailAddress" cssClass="error" />
					</div>
				</div>
				<div class="form-row">
					<h4><strong>Update Phone Number</strong></h4>
					<div class="form-group col-sm-6">
					<label>CURRENT Phone Number:<br>${curPhone}</label><br />
					</div>
					<div class="form-group col-sm-6">
					<updatecust:label path="phoneNumber">NEW Phone Number (10 digits):</updatecust:label><br />
					<updatecust:input type="tel" class="form-control"
						placeholder="Enter NEW phone number (1234567890)" path="phoneNumber" />
 					<updatecust:errors path="phoneNumber" cssClass="error" />
					</div>
				</div>
				<button class="btn btn-success" type="submit">Submit Information</button>
				<a class="btn btn-primary" href="${pageContext.request.contextPath}/login">Cancel and Return to Login</a>
			</updatecust:form>
		</section>
		<%@ include file="common/footer-common.jspf" %>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>