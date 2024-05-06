<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="ko">
	
<head>
	<meta charset="UTF-8">
	
	<!-- 참조 : http://getbootstrap.com/css/   참조 -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	
	<!-- 캘린더 -->
	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script src="./jquery-ui-1.12.1/datepicker-ko.js"></script>

	
	<!-- Bootstrap Dropdown Hover CSS -->
   <link href="/css/animate.min.css" rel="stylesheet">
   <link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
   
    <!-- Bootstrap Dropdown Hover JS -->
   <script src="/javascript/bootstrap-dropdownhover.min.js"></script>
	
	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
 		body {
            padding-top : 50px;
        }
     </style>
    
     <!--  ///////////////////////// JavaScript ////////////////////////// -->
	<script type="text/javascript">
		
	function fncUpdatePurchase(){

		var receiverName = $("input[name = 'receiverName']").val();
		var receiverPhone = $("input[name = 'receiverPhone']").val();
		var divyAddr = $("input[name = 'divyAddr']").val();
		

		if(receiverName == null || receiverName.length<1){
			alert("구매자이름은 반드시 입력하여야 합니다.");
			return;
		}
		if(receiverPhone == null || receiverPhone.length<1){
			alert("구매자연락처는 반드시 입력하여야 합니다.");
			return;
		}
		if(divyAddr == null || divyAddr.length<1){
			alert("구매자주소는 반드시 입력하셔야 합니다.");
			return;
		}
	
	$("form").attr("method","POST").attr("action","/purchase/updatePurchase").submit();

	}

	

	$(document).ready(function() {
		
		//$.datepicker.setDefaults($.datepicker.regional['ko']);
		
	    $( "input[name='divyDate']" ).datepicker({
	      showOtherMonths: true,
	      selectOtherMonths: true,
	      dateFormat : 'yy-mm-dd'
	   		 }); 
	    
	  	});
	
	
	

	$(function(){
		$("button").on("click",function(){
			self.location = "/purchase/listPurchase";
		});
		
		
	});
		
		
	</script>
	
</head>

<body>

	<!-- ToolBar Start /////////////////////////////////////-->
	<jsp:include page="/layout/toolbar.jsp" />
   	<!-- ToolBar End /////////////////////////////////////-->
	
	<!--  화면구성 div Start /////////////////////////////////////-->
	<div class="container">
	<form>
		<div class="page-header">
	       <h3 class=" text-info">수정완료</h3>
	       <input type="hidden" id="tranNo" name="tranNo" value="${purchase.tranNo}" />
	       <!--  <h5 class="text-muted">내 정보를 <strong class="text-danger">최신정보로 관리</strong>해 주세요.</h5>-->
	    </div>
	
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>구매자아이디</strong></div>
			<div class="col-xs-8 col-md-4">${purchase.buyer.userId}
			<input type="hidden" id="buyerId" name="buyerId" value="${purchase.buyer.userId}" />
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>구매방법</strong></div>
			<div class="col-xs-8 col-md-4">
			<input type="hidden" id="paymentOption" name="paymentOption" value="${purchase.paymentOption}" />
			<c:choose>
			<c:when test = "${purchase.paymentOption} eq '1'">
			 현금구매
			 </c:when>
			<c:otherwise>
			신용구매
			</c:otherwise>
			</c:choose>
	
				 
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>구매자이름</strong></div>
			<div class="col-xs-8 col-md-4">${purchase.receiverName}
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>구매자연락처</strong></div>
			<div class="col-xs-8 col-md-4">${purchase.receiverPhone}</div>
			
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>구매자주소</strong></div>
			<div class="col-xs-8 col-md-4">${purchase.divyAddr}
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>구매요청사항</strong></div>
			<div class="col-xs-8 col-md-4">${purchase.divyRequest}</div>
			
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>배송희망일자</strong></div>
			<div class="col-xs-8 col-md-4">${purchase.divyDate}</div>
			
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-md-6 text-right ">
	  			<button type="button" class="btn btn-primary">
	  		 	수정완료
	  			</button>
	  		</div>
			
	  		</form>
		</div>
		
		<br/>
		
 	</div>
 	<!--  화면구성 div Start /////////////////////////////////////-->

</body>

</html>