<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>My Shop</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
  <script src="/js/jquery-3.7.1.min.js"></script>
  <link href="/css/main.css" rel="stylesheet" type="text/css">
  <style>
  .fakeimg {
    height: 200px;
    background: #aaa;
  }
  </style>
</head>
<body>

<div class="p-5 bg-primary text-white text-center">
  <h1>Myshop</h1>
</div>

<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
  <div class="container-fluid">
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link active" href="/product/list">상품</a>
      </li>
      <li class="nav-item">
        <a class="nav-link active" href="/cart/list">장바구니</a>
      </li>
    </ul>
  </div>
</nav>

	<div class="container text-center">
	
	<!-- 본문시작 -->
    <div class="row">
    <div class="col-sm-4">
    	<form name="loginfrm" id="loginfrm" method="post" action="loginProc" onsubmit="return loginCheck()">
					<table class="table" style="width: 30%">
						<tr>
							<td class="active"><input type="text" name="id" id="id"
								placeholder="아이디" maxlength="10" required></td>
							<td rowspan="2">
								<!-- type=image는 기본속성이 submit --> <input type="image"
								src="../images/bt_login.gif">
							</td>
						</tr>
						<tr>
							<td class="active"><input type="password" name="passwd"
								id="passwd" placeholder="비밀번호" maxlength="10" required>
							</td>
						</tr>
						<tr>
							<td colspan="2"><label><input type="checkbox"
									value="SAVE" name="c_id"> ID저장</label> &nbsp;&nbsp;&nbsp; <a
								href="agreement.jsp">회원가입</a> &nbsp;&nbsp;&nbsp; <a
								href="findID.jsp">아이디/비밀번호찾기</a></td>
						</tr>
					</table>
				</form>
     </div><!-- colend -->
  </div><!-- row end -->
  
  <!--본문 끝-->
</div><!--container end -->

<div class="mt-5 p-4 bg-dark text-white text-center">
  <p>Footer</p>
</div>

</body>
</html>
