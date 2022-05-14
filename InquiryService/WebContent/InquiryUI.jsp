<%@ page import="com.Inquiry"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Inquiry</title>
<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<script src="Components/jquery-3.4.1.min.js"></script> 
<script src="Components/validationinquiry.js"></script> 
</head>
<body>
<div class="container"> 
	<div class="row">  
		<div class="col-6"> 
			<h1>Inquiry Management</h1>
				<form id="formInquiry" name="formInquiry" method="post" action="InquiryUI.jsp">  
					Account No:  
 	 				<input id="iAccNo" name="iAccNo" type="text"  class="form-control form-control-sm">
					<br>Customer Name:   
  					<input id="iCName" name=iCName type="text" class="form-control form-control-sm">   
  					<br>Date:   
  					<input id="iDate" name="iDate" type="date"  class="form-control form-control-sm">
  					<br>Reason:   
  					<input id="iReason" name="iReason" type="text"  class="form-control form-control-sm">
					<br>  
					<input id="btnSave" name="btnSave" type="button" value="SAVE" class="btn btn-primary">  
					<input type="hidden" id="hidInquiryIDSave" name="hidInquiryIDSave" value=""> 
				</form>
				
				<div id="alertSuccess" class="alert alert-success"> </div>
				
			   <div id="alertError" class="alert alert-danger"></div>
				
			   <br>
				<div id="divInquiryGrid">
					<%
						Inquiry inquiryObj = new Inquiry();
						out.print(inquiryObj.readInquiry());
					%>
				</div>
				
				 
			</div>
		</div>
</div>
 
</body>
</html>