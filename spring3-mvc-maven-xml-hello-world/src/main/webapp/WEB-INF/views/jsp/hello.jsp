<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Maven + Spring MVC</title>
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script src="http://code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">

<script>
	$("#testForm").submit(function(event) {
		alert('hehe');
		$("#mySubmitButton").prop("disabled", true).addClass("disabled");
	});

	$(function() {
		$('#toDate').datepicker();
	});

	$(function() {
		$('#fromDate').datepicker();
	});
</script>
</head>
<body>
	<form:form id="testForm" modelAttribute="testForm" action="testAction">
		Name: <form:input path="name" />
		From date: <form:input path="fromDate" id="fromDate" />
		<form:errors path="fromDate" class="control-label" />
		To date: <form:input path="toDate" id="toDate" />
		<input id="mySubmitButton" type="submit" value="HELLO">
	</form:form>
	<h2>Spring MVC and List Example</h2>

	<c:if test="${not empty lists}">

		<ul>
			<c:forEach var="listValue" items="${lists}">
				<li>${listValue}</li>
			</c:forEach>
		</ul>

	</c:if>

	<a href="/spring3/1">1</a>
	<a href="/spring3/2">2</a>
	<a href="/spring3/3">3</a>
	<div>
		<a href="/spring3/phoneList">Phone List</a>
	</div>

	<%-- // load our paging tag, pass pagedListHolder and the link --%>
	<tg:paging pagedListHolder="${pagedListHolder}"
		pagedLink="${pagedLink}" />

	<%-- <nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Spring 3 MVC Project</a>
			</div>
		</div>
	</nav>

	<div class="jumbotron">
		<div class="container">
			<h1>${title}</h1>
			<p>
				<c:if test="${not empty name}">
			Hello ${name}
		</c:if>

				<c:if test="${empty name}">
			Welcome Welcome!
		</c:if>
			</p>
			<p>
				<a class="btn btn-primary btn-lg" href="#" role="button">Learn
					more</a>
			</p>
		</div>
	</div>

	<div class="container">

		<div class="row">
			<div class="col-md-4">
				<h2>Heading</h2>
				<p>ABC</p>
				<p>
					<a class="btn btn-default" href="#" role="button">View details</a>
				</p>
			</div>
			<div class="col-md-4">
				<h2>Heading</h2>
				<p>ABC</p>
				<p>
					<a class="btn btn-default" href="#" role="button">View details</a>
				</p>
			</div>
			<div class="col-md-4">
				<h2>Heading</h2>
				<p>ABC</p>
				<p>
					<a class="btn btn-default" href="#" role="button">View details</a>
				</p>
			</div>
		</div>


		<hr>
		<footer>
			<p>&copy; Mkyong.com 2015</p>
		</footer>
	</div>

	<spring:url value="/resources/core/css/hello.js" var="coreJs" />
	<spring:url value="/resources/core/css/bootstrap.min.js"
		var="bootstrapJs" />

	<script src="${coreJs}"></script>
	<script src="${bootstrapJs}"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script> --%>

</body>
</html>