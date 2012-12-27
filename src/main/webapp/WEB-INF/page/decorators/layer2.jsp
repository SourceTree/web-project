<%--
	Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
--%><%@ include file="/WEB-INF/page/common/taglibs.jsp"%>
<page:applyDecorator name="layer1Default">
	<decorator:usePage id="decoratorPage" />

<html>
<head>
<decorator:head />
<title><decorator:title /></title>
</head>
<body <decorator:getProperty property="body.onload" writeEntireProperty="true"/> <decorator:getProperty property="body.onunload" writeEntireProperty="true"/>>
	<decorator:body />
</body>
</html>
</page:applyDecorator>