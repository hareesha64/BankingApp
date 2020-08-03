<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>TRANSCATION LIST</title>
</head>
<body>
<h2 style="color:Tomato">The Last Five Transcation you made are</h2>
<c:forEach items="${transferlist}" var="transfer">
<div style="color:darkgreen;font-size:20px">The amount you transfered is ${transfer}</div>
</c:forEach>
<p style="color:darkgreen;font-size:20px">The Available Balance after transcation is ${Availablebalance}</p>
</body>
</html>