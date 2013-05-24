<table class="items">
	<thead>
		<tr>
			<th/>
			<g:sortableColumn property="code"  title="${message(code: 'intermediate.variable.code.label')}" params="[q:q]" />
			<g:sortableColumn property="names"  title="${message(code: 'intermediate.variable.name.label')}" params="[q:q]" />
			<g:sortableColumn property="executableScript"  title="${message(code: 'intermediate.variable.executable.script.label')}" params="[q:q]" />
			
		</tr>
	</thead>
	<tbody>
	<g:each in="${entities}" status="i" var="intermediateVariable">
		
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td>
					<ul class="horizontal">
						<li>
							<shiro:hasPermission permission="intermediateVariable:edit">
								<a href="${createLinkWithTargetURI(controller:'intermediateVariable', action:'edit', params:[id: intermediateVariable.id])}"  class="edit-button">
									<g:message code="default.link.edit.label" />
								</a>
							</shiro:hasPermission>
						</li>
						<li>
							<shiro:hasPermission permission="intermediateVariable:delete">
								<a href="${createLinkWithTargetURI(controller:'intermediateVariable', action:'delete', params:[id: intermediateVariable.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message')}');" class="delete-button">
									<g:message code="default.link.delete.label" />
								</a>
							</shiro:hasPermission>
						</li>
					</ul>
				</td>
				<td>${intermediateVariable.code}</td>
				<td>${intermediateVariable.names}</td>
				<td>${intermediateVariable.executableScript}</td>
				
			</tr>
		</g:each>
</tbody>

</table>
<g:render template="/templates/pagination" />
