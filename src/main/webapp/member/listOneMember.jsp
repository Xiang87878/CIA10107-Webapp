<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="com.member.model.*"%>
<%-- �����Ƚm�߱ĥ� Script ���g�k���� --%>

<%
//EmpVO empVO = (EmpVO) request.getAttribute("empVO"); //EmpServlet.java(Concroller), �s�Jreq��empVO����
%>

<html>
<head>
<title>�|����� - listOneMember.jsp</title>

<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}
</style>

<style>
table {
	width: 600px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 5px;
	text-align: center;
}
</style>

</head>
<body bgcolor='white'>

	<h4>�����Ƚm�߱ĥ� Script ���g�k����:</h4>
	<table id="table-1">
		<tr>
			<td>
				<h3>���u��� - listOneMember.jsp</h3>
				<h4>
					<a href="select_page.jsp"><img src="images/back1.gif"
						width="100" height="32" border="0">�^����</a>
				</h4>
			</td>
		</tr>
	</table>

	<table>
		<tr>
			<th>�|���s��</th>
			<th>�|���H�c</th>
			<th>�|���m�W</th>
			<th>�|���K�X</th>
			<th>�|���ͤ�</th>
			<th>�|���ʧO</th>
			<th>�|���q��</th>
			<th>�|���a�}</th>
		</tr>
		<tr>
			<td>${memberVO.memberId}</td>
			<td>${memberVO.memberEmail}</td>
			<td>${memberVO.memberName}</td>
			<td>${memberVO.memberPassword}</td>
			<td>${memberVO.memberBirthday}</td>
			<td>${memberVO.memberGender}</td>
			<td>${memberVO.memberPhone}</td>
			<td>${memberVO.memberAddress}</td>
		</tr>
	</table>

</body>
</html>