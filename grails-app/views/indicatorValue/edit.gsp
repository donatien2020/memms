<%@ page import="org.chai.memms.report.IndicatorValue" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'indicatorValue.label', default: 'IndicatorValue')}" />
		<title><g:message code="indicatorValue.edit" /></title>
	</head>
	<body>
	
		
		<div id="edit-indicatorValue" class="content scaffold-edit" role="main">
			<span class="don_titles"><g:message code="indicatorValue.edit" /></span>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${indicatorValueInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${indicatorValueInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form method="post" class="simple-list">
				<g:hiddenField name="id" value="${indicatorValueInstance?.id}" />
				<g:hiddenField name="version" value="${indicatorValueInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
