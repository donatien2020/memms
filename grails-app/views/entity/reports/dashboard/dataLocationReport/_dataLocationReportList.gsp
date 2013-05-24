<table class="items">
	<thead>
		<tr>
			<th/>
			<g:sortableColumn property="code"  title="${message(code: 'spare.part.code.label')}" params="[q:q]" />
			<th> <g:message code="data.location.report.facility.label"/></th>
			<th> <g:message code="data.location.report.facility.type.label"/></th>
			<th> <g:message code="data.location.report.generated.at.label"/></th>
			
		</tr>
	</thead>
	<tbody>
	<g:each in="${entities}" status="i" var="dataLocationReport">
		
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td>
					<ul class="horizontal">
						<li>
							<shiro:hasPermission permission="dataLocationReport:edit">
								<a href="${createLinkWithTargetURI(controller:'dataLocationReport', action:'edit', params:[id: dataLocationReport.id])}"  class="edit-button">
									<g:message code="default.link.edit.label" />
								</a>
							</shiro:hasPermission>
						</li>
						<li>
							<shiro:hasPermission permission="dataLocationReport:delete">
								<a href="${createLinkWithTargetURI(controller:'dataLocationReport', action:'delete', params:[id: dataLocationReport.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message')}');" class="delete-button">
									<g:message code="default.link.delete.label" />
								</a>
							</shiro:hasPermission>
						</li>
					</ul>
				</td>
				<td>${dataLocationReport.code}</td>
				<td>${dataLocationReport.dataLocation?.names}</td>
				<td>${dataLocationReport.dataLocationType?.names}</td>
				<td>${dataLocationReport.generatedAt}</td>
				
			</tr>
		</g:each>
</tbody>
</table>
<g:render template="/templates/pagination" />
