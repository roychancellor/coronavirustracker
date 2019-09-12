<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="addcustomer" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="format" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<spring:url value="/resources/css/style.css" var="mainCss" />
	<spring:url value="/resources/images/header.jpg" var="headerImg" />
	<spring:url value="/resources/images/footer.jpg" var="footerImg" />
	<meta charset="UTF-8">
	<title>New Customer</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div class="container">
		<%@ include file="common/header-common.jspf" %>
		<section>
			<h1>Become a Customer!</h1>
			<h3>Fill out the fields below:</h3>
			<p class="error">${errorMessage}</p>
			<addcustomer:form modelAttribute="customer" method="POST">
				<addcustomer:hidden path="custId"/> <!--  auto-generated by the database -->
				<div class="form-row">
					<h4><strong>Name</strong></h4>
					<div class="form-group col-md-6">
					<addcustomer:label path="firstName">First Name: </addcustomer:label>
					<addcustomer:input type="text" class="form-control" placeholder="Enter first name (min 1 character)" path="firstName" />
					<addcustomer:errors path="firstName" cssClass="error" />
					</div>
					<div class="form-group col-md-6">
					<addcustomer:label path="lastName">Last Name:</addcustomer:label>
					<addcustomer:input type="text" class="form-control" placeholder="Enter last name (min 1 character)" path="lastName" />
					<addcustomer:errors path="lastName" cssClass="error" />
					</div>
				</div>
				<div class="form-row">
					<h4><strong>Login Credentials</strong></h4>
					<div class="form-group col-md-4">
					<addcustomer:label path="username">Username:</addcustomer:label><br />
					<addcustomer:input type="text" class="form-control" placeholder="Enter username (min 6 characters)" path="username" />
					<addcustomer:errors path="username" cssClass="error" />
					</div>
					<div class="form-group col-md-4">
					<addcustomer:label path="password">Password:</addcustomer:label><br />
					<addcustomer:input type="password" class="form-control" placeholder="Enter password (min 8 characters)" value="${password}" path="password" />
					<addcustomer:errors path="password" cssClass="error" />
					</div>
					<div class="form-group col-md-4">
					<addcustomer:label path="passCompare">Re-enter Password:</addcustomer:label><br />
					<addcustomer:input type="password" class="form-control" placeholder="Re-enter password (min 8 characters)" value="${passCompare}" path="passCompare" />
					<addcustomer:errors path="passCompare" cssClass="error" />
					</div>
				</div>
				<div class="form-row">
					<div class="form-group col-md-4">
					</div>
					<div class="form-group col-md-8">
					<a class="error">${passmatcherror}</a>
					</div>
				</div>
				<div class="form-row">
					<h4><strong>Contact Information</strong></h4>
					<div class="form-group col-md-6">
					<addcustomer:label path="emailAddress">E-mail Address:</addcustomer:label><br />
					<addcustomer:input type="email" class="form-control" placeholder="email@example.com" path="emailAddress" />
					<addcustomer:errors path="emailAddress" cssClass="error" />
					</div>
					<div class="form-group col-sm-6">
					<addcustomer:label path="phoneNumber">Phone Number (10 digits):</addcustomer:label><br />
					<addcustomer:input type="tel" class="form-control"
						placeholder="Enter phone number (1234567890)" path="phoneNumber" />

 					<addcustomer:errors path="phoneNumber" cssClass="error" />
					</div>
				</div>
				<button class="btn btn-success" type="submit">Submit Information</button>
				<a class="btn btn-primary" href="/login">Cancel and Return to Login</a>
			</addcustomer:form>
		</section>
		<%@ include file="common/footer-common.jspf" %>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>