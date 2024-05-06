<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<!--  ///////////////////////// JSTL  ////////////////////////// -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>

<html lang="ko">

<head>
<meta charset="UTF-8">

<!-- 참조 : http://getbootstrap.com/css/   참조 -->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<link href="/css/animate.min.css" rel="stylesheet">
<link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">

<script src="/javascript/bootstrap-dropdownhover.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<style>
body {
	padding-top: 50px;
}
</style>

<script type="text/javascript">

$(document).ready(function(){
	$(".ct_list_pop td:nth-child(6)").css("color", "red");
	$(".ct_list_pop td:nth-child(6)").on("click",function(){
		self.location = "/purchase/updatePurchaseView?tranNo="
				+$(this).closest("tr").find("input[name='tranNo']").val();
				
	});
});



 
 
	

</script>

</head>

<body>

<jsp:include page="/layout/toolbar.jsp" />

<div class="container">

		<div class="page-header text-info">
			<h3>
				구매목록
			</h3>
		</div>
		
		<div class="row">

			<div class="col-md-12 text-right">
				<p class="text-primary">전체 ${resultPage.totalCount } 건수, 현재
					${resultPage.currentPage} 페이지</p>
			</div>
		</div>
		
		<table class="table table-hover table-striped">

			<thead>
				<tr>
					<th align="center">No</th>
					<th align="left">회원ID</th>
					<th align="left">회원명</th>
					<th align="left">전화번호</th>
					<th align="left">배송현황</th>
					<th align="left">배송정보수정</th>
					<th align="left">정보수정</th>
				</tr>
			</thead>
			
			<tbody>

				<c:set var="i" value="0" />
				<%--초기화 --%>
				<c:forEach var="purchase" items="${map.list}">
					<%-- 프로덕트에서 리스트가져와서 리스트만큼 돌리기 --%>
					<c:set var="i" value="${ i+1 }" />
					<tr class="ct_list_pop">
						<td align="center">${ i }</td>
						<td align="left">${purchase.buyer.userId}</td>
						<td align="left">${purchase.receiverName}</td>
						<td align="left">${purchase.receiverPhone}</td>
						<td align="left">${purchase.tranCode}상태 입니다.</td>
						<td align="left">
							<c:if test = "${purchase.tranCode eq '현재 배송중' }">
								<a href="/purchase/updateTranCode?tranNo=${purchase.tranNo}&tranCode=2">물건도착</a>
							</c:if>
						</td>
						
						<td align="left">
						<input type="hidden" name="tranNo" value="${purchase.tranNo}"> 
						수정하기
						</td>
					</tr>

					<!--  <tr>
						<td id="${product.prodNo}" ></td>
					</tr>-->
				</c:forEach>

			</tbody>

		</table>
		
		</div>


	<jsp:include page="../common/pageNavigator_new.jsp" />
	

</body>

</html>
		
		
