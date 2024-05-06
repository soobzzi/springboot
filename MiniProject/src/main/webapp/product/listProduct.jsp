<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<!--  ///////////////////////// JSTL  ////////////////////////// -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>

<html lang="ko">

<head>
<meta charset="UTF-8">

<!-- 참조 : http://getbootstrap.com/css/   참조 -->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


<!-- Bootstrap Dropdown Hover CSS -->
<link href="/css/animate.min.css" rel="stylesheet">
<link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
<!-- Bootstrap Dropdown Hover JS -->
<script src="/javascript/bootstrap-dropdownhover.min.js"></script>
<link href="/css/images.css" rel="stylesheet">


<!-- jQuery UI toolTip 사용 CSS-->
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<!-- jQuery UI toolTip 사용 JS-->
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<!--  ///////////////////////// CSS ////////////////////////// -->
<style>
body {
	padding-top: 50px;
}
</style>

<!--  ///////////////////////// JavaScript ////////////////////////// -->
<script type="text/javascript">


 
	$( function() {
		
		$("a[href='#' ]:contains('구매')").on("click" , function() {
			var prodNo = $(this).closest(".thumbnail").find("input[name='prodNo']").val();
			self.location = "/purchase/addPurchase?prodNo="+prodNo
		});
	});
		
	$( function() {
	    $("a[href='#']:contains('수정')").on("click" , function() {
	    	
	    	
	        var prodNo = $(this).closest(".thumbnail").find("input[name='prodNo']").val();
	        self.location = "/product/getProduct?menu=${param.menu}&prodNo="+prodNo;
	        
	        
	    });
	});
	
	$( function() {
	    $(".detail").on("click" , function() {
	    	
	    	var prodNo = $(this).closest(".thumbnail").find("input[name='prodNo']").val();
	        self.location = "/product/getProduct?menu=search&prodNo="+prodNo;
	        
	        
	    });
	});
	
	
	function fncGetList(currentPage) {
		$("#currentPage").val(currentPage)

		$("form").attr("method", "POST").attr("action",
				"/product/listProduct?menu=${param.menu}").submit();
	}
	
	//=============    검색 / page 두가지 경우 모두  Event  처리 =============	
	function fncGetList(currentPage) {
		$("#currentPage").val(currentPage)
		$("form").attr("method" , "POST").attr("action" , "/product/listProduct").submit();
	}
	
	
	//============= "검색"  Event  처리 =============	
	 $(function() {
		 
		 $( "button.btn.btn-default" ).on("click" , function() {
			fncGetList(1);
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

		<div class="page-header text-info">
			<h3>
				<c:if test="${param.menu=='manage'}">
						상품관리 
						</c:if>


				<c:if test="${param.menu=='search'}">
						상품목록조회
						</c:if>
			</h3>
		</div>

		<!-- table 위쪽 검색 Start /////////////////////////////////////-->
		<div class="row">

			<div class="col-md-6 text-left">
				<p class="text-primary">전체 ${resultPage.totalCount } 건수, 현재
					${resultPage.currentPage} 페이지</p>
			</div>

			<div class="col-md-6 text-right">
				<form class="form-inline" name="detailForm">

					<div class="form-group">
						<select name="searchCondition" class="ct_input_g"
							style="width: 80px">

							<option value="0"
								${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품번호</option>
							<option value="1"
								${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>상품명</option>
							<option value="2"
								${ ! empty search.searchCondition && search.searchCondition==2 ? "selected" : "" }>상품가격</option>


						</select>
						<div class="form-group">
							<label class="sr-only" for="searchKeyword">검색어</label> 
							<input
								type="text" class="form-control" id="searchKeyword"
								name="searchKeyword" placeholder="검색어" 
								value="${! empty search.searchKeyword ? search.searchKeyword : '' }">
						</div>
						<button type="button" class="btn btn-default" >검색</button>
						<input type="hidden" id="currentPage" name="currentPage"  />
						
				</form>
			</div>

		</div>
		<!-- table 위쪽 검색 Start /////////////////////////////////////-->


		<!--  table Start ////a/////////////////////////////////-->
		
		
		<div class="row">
		<c:forEach var = "product" items = "${map.list}">
		  <div class="col-sm-6 col-md-4">
		    <div class="thumbnail">
		      <img class ="thumimage" src= "../images/uploadFiles/${fn:split(product.fileName,'/')[0]}" width="300" height="300">
		      <div class="caption">
		        <h3>${product.prodName}</h3>
		        <p>${product.price}</p>
		        <p>
			        <c:choose>
		    			<c:when test="${product.proTranCode eq '판매완료' and not empty param.menu and param.menu eq 'manage'}">
		       				 <a href="/purchase/updateTranCodeByProd?prodNo=${product.prodNo}">배송하기</a>
		   				</c:when>
		    			<c:when test="${product.proTranCode eq '판매중' and not empty param.menu}">
						판매중
						</c:when>
						<c:otherwise>
						재고없음
						</c:otherwise>
					</c:choose>
				</p>
		        <p><input type="hidden"  name="prodNo" value = "${product.prodNo}"  /></p>
		        <p>
			        <a href="#" class="btn btn-primary" role="button">
			        <c:if test="${sessionScope.user.role == 'user'}">구매
			        </c:if>
			        <c:if test="${sessionScope.user.role == 'admin'}">수정
			        </c:if>
			        </a> 
			        <a href="#" class="btn btn-default detail" role="button" ">상세정보</a>
		        </p>
		      </div>
		    </div>
		  

		 </div>
		 </c:forEach>
				

		<!--  table End /////////////////////////////////////-->

	</div> 
	<!--  화면구성 div End /////////////////////////////////////-->


	<!-- PageNavigation Start... -->
	<jsp:include page="../common/pageNavigator_new.jsp" />
	<!-- PageNavigation End... -->

</body>

</html>