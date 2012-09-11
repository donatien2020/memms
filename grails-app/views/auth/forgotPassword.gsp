<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="layout" content="main" />
		<title><g:message code="forgot.password.title"/></title>
	</head>
	<body>
		<h2 class="login-heading"><g:message code="forgot.password.header.label"/></h2>
		<div class="form-box register">
			<g:form action="retrievePassword" >    
				<table class="listing">
					<tbody>
						<tr>
							<td>
								<label><g:message code="forgot.password.email.label"/></label>
								<input type="text" name="email" class="idle-field" value="${retrievePassword?.email}" />
								<div class="error-list"><g:renderErrors bean="${retrievePassword}" field="email" /></div>
							</td>
						</tr>
						<tr><td>
							<div><button type="submit">${message(code:'forgot.password.retrieve.label')}</button></div>
							<div><g:message code="forgot.password.info.text"/></div>
						</td></tr>
					</tbody>
				</table>
			</g:form>
		</div>
	</body>
</html>
