<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Team Roster</title>

	<style>
		tr:first-child{
			font-weight: bold;
			background-color: #C6C9C4;
		}
	</style>

</head>


<body>
	<h2>List of Players</h2>	
	<table>
		<tr>
			<td>NAME</td><td>Joining Date</td><td>Salary</td><td>SSN</td><td></td>
		</tr>
		<c:forEach items="${players}" var="player">
			<tr>
			<td>${player.name}</td>
			<td>${player.joiningDate}</td>
			<td>${player.salary}</td>
			<td><a href="<c:url value='/edit-${player.ssn}-player' />">${player.ssn}</a></td>
			<td><a href="<c:url value='/delete-${player.ssn}-player' />">delete</a></td>
			</tr>
		</c:forEach>
	</table>
	<br/>
	<a href="<c:url value='/new' />">Add New Player</a>
</body>
</html>