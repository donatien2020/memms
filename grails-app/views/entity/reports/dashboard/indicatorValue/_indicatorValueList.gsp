<table class="items">
	<thead>
		<tr>
			<th/>
			<g:sortableColumn property="code"  title="${message(code: 'indicator.value.code.label')}" params="[q:q]" />
			<g:sortableColumn property="computedValue"  title="${message(code: 'indicator.value.computed.value.label')}" params="[q:q]" />
			<th><g:message code="indicator.value.indicator.label" /></th>
		    <g:sortableColumn property="generatedAt"  title="${message(code: 'indicator.value.generated.at.label')}" params="[q:q]" />
			<th><g:message code="indicator.value.data.location.report.label" /></th>
		</tr>
	</thead>
	<tbody>
	<g:each in="${entities}" status="i" var="indicatorValue">
		
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td>
					<ul class="horizontal">
						<li>
							<shiro:hasPermission permission="indicatorValue:edit">
								<a href="${createLinkWithTargetURI(controller:'indicatorValue', action:'edit', params:[id: indicatorValue.id])}"  class="edit-button">
									<g:message code="default.link.edit.label" />
								</a>
							</shiro:hasPermission>
						</li>
						<li>
							<shiro:hasPermission permission="indicatorValue:delete">
								<a href="${createLinkWithTargetURI(controller:'indicatorValue', action:'delete', params:[id: indicatorValue.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message')}');" class="delete-button">
									<g:message code="default.link.delete.label" />
								</a>
							</shiro:hasPermission>
						</li>
					</ul>
				</td>
				<td>${indicatorValue.code}</td>
				<td>${indicatorValue.computedValue}</td>
				<td>${indicatorValue.indicator?.names}</td>
				<td>${indicatorValue.generatedAt}</td>
				<td>${indicatorValue.dataLocationReport.code}</td>
				
			</tr>
		</g:each>
</tbody>
</table>
<g:render template="/templates/pagination" />
