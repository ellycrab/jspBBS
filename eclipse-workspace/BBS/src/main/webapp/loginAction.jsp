<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO"%>
<%@ page import="java.io.PrintWriter"%>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="user" class="user.User" scope="page"/>

<!-- login.jsp의 userID를 받아서 처리함 -->
<jsp:setProperty name="user" property="userID"/>
<jsp:setProperty name="user" property="userPassword"/>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/custom.css">
<title>JSP 게시판 웹사이트</title>
</head>
<body>
 	<%
 	
 		//로그인이 된 회원은 다시 로그인으로 들어갈 수 없게끔 설정해줘야함.
 		String userID = null;
 		if(session.getAttribute("userID")!=null){
 			userID = (String)session.getAttribute("userID");
 		}
 		if(userID != null){
 			PrintWriter script = response.getWriter();
 			script.println("<script>");
 			script.println("alert(이미 로그인 되어있습니다.)");
 			script.println("location.href='main.jsp'");
 			script.println("</script>");
 		}
 		
 		
 		
 		UserDAO userDAO = new UserDAO();
 	//  UserDAO에있는 login 함수를 실행한다.
 	//  아이디와 비밀번호가 loginAction페이지로 넘어오면 그 결과(-2~1값)가 result에 담긴다.
 		int result = userDAO.login(user.getUserID(), user.getUserPassword());
 		if(result == 1){
 			//로그인에 성공했을때, session을 걸어줌으로써.
 			//고객한명한명을 세션을 부여함으로써 구분할 수 있다.
 			session.setAttribute("userID", user.getUserID());
 			PrintWriter script = response.getWriter();
 			script.println("<script>");
 			script.println("location.href='main.jsp'");
 			script.println("</script>");
 		}
 		else if(result == 0){
 			PrintWriter script = response.getWriter();
 			script.println("<script>");
 			script.println("alert('비밀번호가 틀립니다.')");
 			script.println("history.back()");
 			script.println("</script>");
 		}
 		else if(result == -1){
 			PrintWriter script = response.getWriter();
 			script.println("<script>");
 			script.println("alert('아이디가 존재하지 않습니다.')");
 			script.println("history.back()");
 			script.println("</script>");
 		}
 		else if(result == -2){
 			PrintWriter script = response.getWriter();
 			script.println("<script>");
 			script.println("alert('데이터베이스 오류가 발생했습니다.')");
 			script.println("history.back()");
 			script.println("</script>");
 		}
 	%>
</body>
</html>