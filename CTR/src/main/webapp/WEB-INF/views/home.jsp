
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<title>Home</title>
<style>
.parent {
	float: left;
	margin: 1px;
}

.clear:after {
	display:block;
	content:"";
	clear:both;
} 
</style>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#Progress_Loading').hide();
	});

	function testing() {
		$('#Progress_Loading').show();
		$.ajax({
			url : "testing",
			type : "POST",
			cache : false,
			data : $("#form1").serialize(),
			async : true,
			success : function(data) {
				$('#testingBtn').attr("disabled", "disabled");
				$('#testingBtn').val('동기화 완료');
				$('#Progress_Loading').hide();
			},
			error : function(e) {
				console.log("err : " + e);
			}
		});
	}
</script>

</head>

<body>
	<h1>CTR 예측</h1>

	<P>데이터 베이스를 활용하여 CTR을 예측합니다.</P>
	<P>
		동기화버튼을 한번만 눌러주시고 <strong>완료가 뜰떄까지 기다려주세요.</strong>
	</P>

	<P>
		<a href="/chart"> 차트 테스트 </a>
	</P>
	<div class="clear">

		<div class="parent">
			<button type="submit" id="update_Btn"
				onclick="location.href = '/update'">CSV 업로드</button>
		</div>

		<div class="parent">
			<c:choose>
				<c:when test="${ableToRunThread == false}">
					<input id="testingBtn" type="button" onclick="testing()"
						value="동기화 완료" disabled="disabled" />
				</c:when>
				<c:otherwise>
					<input id="testingBtn" type="button" onclick="testing()"
						value="동기화" />
				</c:otherwise>
			</c:choose>
		</div>

		<div class="parent" id="Progress_Loading">
			<!-- 로딩바 -->
			<img src="/resources/Loding/Progress_Loading.gif" alt="로딩중" />
		</div>
	</div>
	<div>
		<div id="Parse_Area"></div>
		<br/>
		<table>
			<thead>
				<tr>
					<th>파일 이름</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="list">
					<tr>
						<td>${list.name}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>