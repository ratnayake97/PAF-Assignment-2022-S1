$(document).ready(function() 
{  
		$("#alertSuccess").hide();  
	    $("#alertError").hide(); 
}); 
 
 
// SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 
 
	// Form validation-------------------  
	var status = validateInquiryForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 
 
	// If valid------------------------  
	var type = ($("#hidInquiryIDSave").val() == "") ? "POST" : "PUT"; 

	$.ajax( 
	{  
			url : "InquiryService",   
			type : type,  
			data : $("#formInquiry").serialize(),  
			dataType : "text",  
			complete : function(response, status)  
			{   
				onInquirySaveComplete(response.responseText, status);  
			} 
	}); 
}); 


function onInquirySaveComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			$("#alertSuccess").text("Successfully saved.");    
			$("#alertSuccess").show(); 

			$("#divInquiryGrid").html(resultSet.data);   
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		} 

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while saving.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while saving..");   
		$("#alertError").show();  
	} 

	$("#hidInquiryIDSave").val("");  
	$("#formInquiry")[0].reset(); 
} 

 
// UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
{     
	$("#hidInquiryIDSave").val($(this).closest("tr").find('#hidInquiryIDUpdate').val());     
	$("#iAccNo").val($(this).closest("tr").find('td:eq(0)').text());     
	$("#iCName").val($(this).closest("tr").find('td:eq(1)').text()); 
	$("#iDate").val($(this).closest("tr").find('td:eq(2)').text());
	$("#iReason").val($(this).closest("tr").find('td:eq(3)').text());     
}); 




//REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) 
{  
	$.ajax(  
	{   
		url : "InquiryService",   
		type : "DELETE",   
		data : "inqID=" + $(this).data("inquiryid"),   
		dataType : "text",   
		complete : function(response, status)   
		{    
			onInquiryDeleteComplete(response.responseText, status);   
		}  
	}); 
}); 

function onInquiryDeleteComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			
			$("#alertSuccess").text("Successfully deleted.");    
			$("#alertSuccess").show(); 
		
			$("#divInquiryGrid").html(resultSet.data); 
			
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		}
		

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while deleting.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while deleting..");   
		$("#alertError").show();  
	}
}
 
// CLIENT-MODEL========================================================================= 
function validateInquiryForm() 
{  
	// ACCOUNT NO-----------------------
	 var tmpAcc = $("#iAccNo").val().trim();
	 if (!$.isNumeric(tmpAcc)) 
		{
		return "Insert Account No.";
		} 
	
	// NAME---------------------------  
	if ($("#iCName").val().trim() == "")  
	{   
		return "Insert Name.";  
	} 
	
	// Date-------------------------------
	
	if ($("#iDate").val().trim() == "")  
	{   
		return "Insert Date.";  
	} 
	
	// REASON-------------------------------
	if ($("#iReason").val().trim() == "")  
	{   
		return "Insert Reason.";  
	} 

	return true; 
}