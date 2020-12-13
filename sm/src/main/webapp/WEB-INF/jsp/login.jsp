<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<div class="container">  
<form action="login" method="post">
 



	<h1>Spring Boot Security- Change default login page</h1>

		
		<table>
			<tr style="color: red;">
				<td></td>
				<td>${SPRING_SECURITY_LAST_EXCEPTION.message}</td>
			</tr>
			<tr>
				<td>User name:</td>
				<td><input type="text" name="username"></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" name="password"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Login"></td>
			</tr>
		</table>
		
		<a type="button" href="/sign-up"> New User, Click here for Sign up</a>
	
       
        
	</form>
	 </div>
 	<%@ include file="common/footer.jspf"%>
