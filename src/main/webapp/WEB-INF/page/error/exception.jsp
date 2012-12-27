<%--
	Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
--%><%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Server Error</title>
</head>
<body>
	<div class="accordion">
		Something's Wrong!

		<div align="left">
			It looks as though we've broken something on our system. Please try again later.
			<br />
			<br />
		</div>
	</div>
	<%-- 
	<c:out value="${exception.message}" />
	<br />
	<c:forEach items="${exception.stackTrace}" var="element">
		<c:out value="${element}" />
	</c:forEach>--%>
</body>
</html>