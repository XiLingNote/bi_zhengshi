<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
* {
	margin: 0;
	padding: 0;
}

body {
	height: 100%;
	overflow: hidden;
	background: url("<%=basePath %>/img/bg.jpg");
	width: 100%;
}

.zhu {
	width: 10%;
	margin: 0 auto;
	padding-top: 20%;
	text-align: center;
}

.zhu input {
	width: 100%;
	background: #FFFFFF;
	height: 30px;
	text-align: center;
}

.deng {
	width: 5%;
	margin: 0 auto;
}

.deng input {
	width: 100%;
	background: #FFFFFF;
	margin-top: 5%;
	height: 30px
}
</style>
<body>
	<form action=" <%=basePath%>pcFlowStructure/test.do" method="post">
		<div class="deng">
			<input type="submit" value="test" />
		</div>
	</form>
	<form action=" <%=basePath%>pcFlowStructure/test2.do" method="post">
		<div class="deng">
			<input type="submit" value="testThread" />
		</div>
	</form>
	<form action=" <%=basePath%>/page/login.do" method="post">
		<div class="zhu">
			<input id="username" name="username" value="test" /> <br /><br /> <input
				type="password" name="password" value="1234" />
		</div>
		<div class="deng">
			<input type="submit" value="login" />
		</div>
	</form>

</body>
</html>