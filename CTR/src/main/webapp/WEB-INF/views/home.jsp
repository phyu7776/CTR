<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<head>
<title>Home</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
	function testing() {
		$.ajax({
			url : "testing",
			type : "POST",
			cache : false,
			data : $("#form1").serialize(),
			async : false,
			success : function(data) {
				$('#testingBtn').attr("disabled", "disabled");
				$('#testingBtn').val('동기화 완료');
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
	<P>동기화버튼을 한번 눌러주시고 완료가 뜰떄까지 기다려주세요.</P>

	<P>
		<a href="/chart"> 차트 테스트 </a>
	</P>
	<button type="submit" id="update_Btn"
		onclick="location.href = '/update'">CSV 업로드</button>

	<c:choose>
		<c:when test="${ableToRunThread == false}">
			<input id="testingBtn" type="button" onclick="testing()"
				value="동기화 완료" disabled="disabled" />
		</c:when>
		<c:otherwise>
			<input id="testingBtn" type="button" onclick="testing()" value="동기화" />
		</c:otherwise>
	</c:choose>
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

</body>
</html>
