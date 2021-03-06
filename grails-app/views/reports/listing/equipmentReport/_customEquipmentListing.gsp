<%@ page import="org.chai.memms.util.Utils.ReportSubType" %>
<%@ page import="org.chai.memms.util.Utils" %>
<table class="items spaced">
	<thead>
		<tr>
			<g:if test="${reportTypeOptions.contains('location')}">
				<g:sortableColumn property="dataLocation"  title="${message(code: 'location.label')}" params="[q:q]" />
			</g:if>
			<g:if test="${reportTypeOptions.contains('code')}">
				<g:sortableColumn property="code"  title="${message(code: 'equipment.code.label')}" params="[q:q]" />
			</g:if>
			<g:if test="${reportTypeOptions.contains('serialNumber')}">
				<g:sortableColumn property="serialNumber"  title="${message(code: 'equipment.serial.number.label')}" params="[q:q]" />
			</g:if>
			<g:if test="${reportTypeOptions.contains('equipmentType')}">
				<g:sortableColumn property="type"  title="${message(code: 'equipment.type.label')}" params="[q:q]" />
			</g:if>
			<g:if test="${reportTypeOptions.contains('model')}">
				<g:sortableColumn property="model"  title="${message(code: 'equipment.model.label')}" params="[q:q]" />
			</g:if>

			%{-- <g:if test="${reportSubType == ReportSubType.STATUSCHANGES && reportTypeOptions.contains('statusChanges')}">
				// TODO SL AFTER RELEASE
				<g:sortableColumn property="statusChanges"  title="Status Changes" params="[q:q]" />
			</g:if> --}%

			<g:if test="${reportTypeOptions.contains('currentStatus')}">
				<g:sortableColumn property="currentStatus"  title="${message(code: 'equipment.status.label')}" params="[q:q]" />
			</g:if>
			<g:if test="${reportTypeOptions.contains('obsolete')}">
				<g:sortableColumn property="obsolete"  title="${message(code: 'equipment.obsolete.label')}" params="[q:q]" />
			</g:if>

			<g:if test="${reportTypeOptions.contains('manufacturer')}">
				<g:sortableColumn property="manufacturer"  title="${message(code: 'provider.type.manufacturer')}" params="[q:q]" />
			</g:if>
			<g:if test="${reportTypeOptions.contains('supplier')}">
				<g:sortableColumn property="supplier"  title="${message(code: 'provider.type.supplier')}" params="[q:q]" />
			</g:if>
			<g:if test="${reportTypeOptions.contains('purchaser')}">
				<g:sortableColumn property="purchaser"  title="${message(code: 'equipment.purchaser.label')}" params="[q:q]" />
			</g:if>
			<g:if test="${reportTypeOptions.contains('acquisitionDate')}">
				<g:sortableColumn property="purchaseDate"  title="${message(code: 'equipment.purchase.date.label')}" params="[q:q]" />
			</g:if>
			<g:if test="${reportTypeOptions.contains('cost')}">
				<g:sortableColumn property="purchaseCost"  title="${message(code: 'equipment.purchase.cost.label')}" params="[q:q]" />
			</g:if>
			<g:if test="${reportTypeOptions.contains('warrantyProvider')}">
				<g:sortableColumn property="warranty"  title="${message(code: 'listing.report.equipment.warranty.provider.label')}" params="[q:q]" />
			</g:if>
			<g:if test="${reportTypeOptions.contains('warrantyPeriodRemaining')}">
				%{-- TODO AR --}%
				<th><g:message code="listing.report.equipment.warranty.period.remaining.label"/></th>
			</g:if>
			<g:if test="${reportTypeOptions.contains('currentValue')}">
			 	<th><g:message code="equipment.current.value.label"/></th>
			</g:if>
		</tr>
	</thead>
	<tbody>
		<g:each in="${entities}" status="i" var="equipment">
			<tr>
				<g:if test="${reportTypeOptions.contains('location')}">
					<td>${equipment.dataLocation.names}</td>
				</g:if>
				<g:if test="${reportTypeOptions.contains('code')}">
					<td>${equipment.code}</td>
				</g:if>
				<g:if test="${reportTypeOptions.contains('serialNumber')}">
					<td>${equipment.serialNumber}</td>
				</g:if>
				<g:if test="${reportTypeOptions.contains('equipmentType')}">
					<td>${equipment.type.names}</td>
				</g:if>
				<g:if test="${reportTypeOptions.contains('model')}">
					<td>${equipment.model}</td>
				</g:if>

				%{-- <g:if test="${reportSubType == ReportSubType.STATUSCHANGES && reportTypeOptions.contains('statusChanges')}">
					// TODO SL AFTER RELEASE
					<g:set var="statusChangesEnum" value="${equipment.getTimeBasedStatusChange(customEquipmentParams?.statusChanges)}"/>
					<td>${message(code: statusChangesEnum?.messageCode+'.'+statusChangesEnum?.name)}</td>
				</g:if> --}%

				<g:if test="${reportTypeOptions.contains('currentStatus')}">
					<td>${message(code: equipment.currentStatus?.messageCode+'.'+equipment.currentStatus?.name)}</td>
				</g:if>
				<g:if test="${reportTypeOptions.contains('obsolete')}">
					<td>
						<g:if name="obsolete" id="${equipment.id}" test="${(!equipment.obsolete==true)}">&radic;</g:if>
						<g:else>&nbsp;</g:else>
					</td>
				</g:if>

				<g:if test="${reportTypeOptions.contains('manufacturer')}">
					<td>${equipment.manufacturer?.contact?.contactName}</td>
				</g:if>
				<g:if test="${reportTypeOptions.contains('supplier')}">
					<td>${equipment.supplier?.contact?.contactName}</td>
				</g:if>
				<g:if test="${reportTypeOptions.contains('purchaser')}">
					<td>${message(code: equipment.purchaser?.messageCode+'.'+equipment.purchaser?.name)}</td>
				</g:if>
				<g:if test="${reportTypeOptions.contains('acquisitionDate')}">
					<td>${Utils.formatDate(equipment.purchaseDate)}</td>
				</g:if>
				<g:if test="${reportTypeOptions.contains('cost')}">
					<td>${equipment.purchaseCost}</td>
				</g:if>
				<g:if test="${reportTypeOptions.contains('warrantyProvider')}">
					<td>${equipment.warranty?.contact?.contactName}</td>
				</g:if>
				<g:if test="${reportTypeOptions.contains('warrantyPeriodRemaining')}">
					%{-- TODO AR calculation AFTER RELEASE --}%
					<td>${equipment.warrantyPeriod?.numberOfMonths}</td>
				</g:if>
				<g:if test="${reportTypeOptions.contains('currentValue')}">
					%{-- TODO AR calculation AFTER RELEASE --}%
					<td><g:formatNumber number="${equipment.currentValueOfThisEquipment}" type="number" format="###.##"/></td>
				</g:if>
			</tr>
		</g:each>

	</tbody>	
</table>
<g:render template="/templates/pagination" />