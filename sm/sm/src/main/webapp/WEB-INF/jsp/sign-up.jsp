<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<body>
<form method="post">
 <h1>${message}</h1>
 Full Name<input type="text" name="fullname"> <br>
 Email    <input type="text" name="username"><br>
 Password <input type="Password" name="password"><br>
  <input type="submit" value="Submit" class="btn btn-success"><br>
 </form>
 	<%@ include file="common/footer.jspf"%>
