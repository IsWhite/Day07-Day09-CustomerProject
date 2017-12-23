<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>客户列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
<h3 align="center">客户列表</h3>
<table border="1" width="70%" align="center">
	<tr>
		<th>客户姓名</th>
		<th>性别</th>
		<th>生日</th>
		<th>手机</th>
		<th>邮箱</th>
		<th>描述</th>
		<th>操作</th>
	</tr>

	<c:forEach var="customer" items="${pageBean.beanList}">
		<tr>
			<td>${customer.cname}</td>
			<td>${customer.gender}</td>
			<td>${customer.birthday}</td>
			<td>${customer.cellphone}</td>
			<td>${customer.email}</td>
			<td>${customer.description}</td>

			<td>
				<a href="<c:url value='/edit.jsp'/>">编辑</a>
				<a href="<c:url value='/customer?method=del&&cid=${customer.cid}'/>">删除</a>
			</td>
		</tr>

	</c:forEach>



</table>

<%--以下是分页查询的前端代码--%>
<center>
	第${pageBean.pc}页/共${pageBean.tp}页
	<%--给出分页相关的链接--%>
	<%--高级搜索之前:/customer?method=findAll&pc=1--%>
	<a href="${pageBean.url}&pc=1">首页</a>
	<c:if test="${pageBean.pc > 1}">
		<%--高级搜索之前:/cst?method=findAll&pc=${pageBean.pc-1}--%>
		<a href="${pageBean.url}&pc=${pageBean.pc - 1}">上一页</a>
	</c:if>

	<%--jstl标签,相当于if..else if..else if..else..--%>
	<c:choose>
		<%--如果页面总数小于十--%>
		<c:when test="${pageBean.tp<10}">
			<%--向page域中添加两个变量--%>
			<c:set var="begin" value="1"/>
			<%--则页码列表的最大值为页面总数--%>
			<c:set var="end" value="${pageBean.tp}"/>
		</c:when>
		<%--如果总页数大于十时,通过公式计算出begin和end--%>
		<c:otherwise>
			<c:set var="begin" value="${pageBean.pc-5}"/>
			<c:set var="end" value="${pageBean.pc+4}"/>
			<%--头溢出--%>
			<c:if test="${begin < 1}">
				<c:set var="begin" value="1"/>
				<c:set var="end" value="10"/>
			</c:if>
			<%--尾溢出--%>
			<c:if test="${end > pageBean.tp}">
				<c:set var="begin" value="${pageBean.tp - 9}"/>
				<c:set var="end" value="${pageBean.tp}"/>
			</c:if>
		</c:otherwise>
	</c:choose>

	<%--循环显示页码列表--%>
	<c:forEach var="i" begin="${begin}" end="${end}">
		<c:choose>
			<c:when test="${pageBean.pc == i}">
				[${i}]
			</c:when>
			<c:otherwise>
				<%--高级搜索之前:<c:url value="/cst?method=findAll&pc=${i}"/>--%>
				<a href="${pageBean.url}&pc=${i}">[${i}]</a>
			</c:otherwise>
		</c:choose>

	</c:forEach>


	<c:if test="${pageBean.pc < pageBean.tp}">

		<%--高级搜索之前:/cst?method=findAll&pc=${pageBean.pc+1}--%>
		<a href="${pageBean.url}&pc=${pageBean.pc + 1}">下一页</a>
	</c:if>
	<%--高级搜索之前:/cst?method=findAll&pc=${pageBean.tp}--%>
	<a href="${pageBean.url}&pc=${pageBean.tp}">尾页</a>

</center>
  </body>
</html>
