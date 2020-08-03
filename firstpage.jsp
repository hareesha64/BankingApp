<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">

<title>Online Banking</title>
</head>
<body>
<div align="center" style="color:white;background-color:green"><br><h3><i>WELCOME TO ONLINE BANKING</i></h3><br></div><br><br>
<div align="center">
<form method="post" action="/OnlineBanking">
<label style="font-size:15px">Enter Amount to transfer:</label>
<input type="number" name="amount"/><br><br>
<font style="color:red">${errmsg}</font>
<font style="color:red">${errormsg}</font>
<button name="transfer" class="btn btn-success">TRANSFER</button>
</form>
</div>
</body>
</html>