<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<%-- �����m�߱ĥ� EL ���g�k���� --%>

<%
    MemberService memberSvc = new MemberService();
    List<MemberVO> list = memberSvc.getAll();
    pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>�Ҧ����u��� - listAllMember.jsp</title>

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
	width: 800px;
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

<h4>�����m�߱ĥ� EL ���g�k����:</h4>
<table id="table-1">
	<tr><td>
		 <h3>�Ҧ����u��� - listAllMember.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">�^����</a></h4>
	</td></tr>
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
		<th>�ק�</th>
		<th>�R��</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="memberVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr>
			<td>${memberVO.memberId}</td>
			<td>${memberVO.memberEmail}</td>
			<td>${memberVO.memberName}</td>
			<td>${memberVO.memberPassword}</td>
			<td>${memberVO.memberBirthday}</td>
			<c:if test="${memberVO.memberGender==0}">
				<td>�k</td>
			</c:if>
			<c:if test="${memberVO.memberGender==1}">
				<td>�k</td>
			</c:if>
			<c:if test="${memberVO.memberGender==2}">
				<td>��L</td>
			</c:if>
			<td>${memberVO.memberPhone}</td> 
			<td>${memberVO.memberAddress}</td> 			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/member.do" style="margin-bottom: 0px;">
			     <input type="submit" value="�ק�">
			     <input type="hidden" name="memberId"  value="${memberVO.memberId}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/member.do" style="margin-bottom: 0px;">
			     <input type="submit" value="�R��">
			     <input type="hidden" name="memberId"  value="${memberVO.memberId}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>