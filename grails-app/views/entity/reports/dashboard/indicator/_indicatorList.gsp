<table class="items">
	<thead>
		<tr>
			<th/>
			<g:sortableColumn property="code"  title="${message(code: 'indicator.code.label')}" params="[q:q]" />
			
			<g:sortableColumn property="names"  title="${message(code: 'indicator.names.label')}" params="[q:q]" />
			<g:sortableColumn property="formula"  title="${message(code: 'indicato.formula.label')}" params="[q:q]" />
			<g:sortableColumn property="type"  title="${message(code: 'indicatort.indicator.type.label')}" params="[q:q]" />
			<th>  <g:message code="indicator.category.label"/></th>
		</tr>
	</thead>
	<tbody>
	<g:each in="${entities}" status="i" var="indicator">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td>
					<ul class="horizontal">
						<li>
							<a href="${createLinkWithTargetURI(controller:'indicator', action:'edit', params:[id: indicator.id])}"  class="edit-button">
								<g:message code="default.link.edit.label" />
							</a>
						</li>
						<li>
							<a href="${createLinkWithTargetURI(controller:'indicator', action:'delete', params:[id: indicator.id])}" onclick="return confirm('\${message(code: 'default.link.delete.confirm.message')}');" class="delete-button">
								<g:message code="default.link.delete.label" />
							</a>
						</li>
					</ul>
				</td>
				<td><g:stripHtml field="${indicator.code}" chars="10"/></td>
				<td><g:stripHtml field="${indicator.names}" chars="25"/></td>
				<td><g:stripHtml field="${indicator.formula}" chars="35"/></td>
				<td>${indicator.type}</td>
				<td>${indicator?.indicatorCategory?.code}</td>
			</tr>
		</g:each>
	</tbody>	
</table>
<g:render template="/templates/pagination" />
