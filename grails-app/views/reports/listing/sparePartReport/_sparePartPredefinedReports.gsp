<div class="v-tabs-dynav-wrap">
  <a class='v-tabs-dynav-scroll-right' href='#' id='js-scroll-right'></a>
  <div class="v-tabs-dynav" id='js-slider-wrapper'>
    <ul>
    	<g:each in="${savedReports}" var="savedReport" status="i">
        <li>
          <a href="${createLinkWithTargetURI(controller: 'listing', action:'savedCustomizedListing', params: [savedReportId:savedReport.id, reportType:savedReport.reportType])}"
            class="tooltip" title="${savedReport.reportName}">
            ${savedReport.reportName}
          </a>
          <span class='delete-node' data-saved-report-id="${savedReport.id}">X</span>
        </li>
      </g:each>
	    <li>
			<a href="${createLinkWithTargetURI(controller: 'listing', action:'generalSparePartsListing')}"
				class="tooltip" title="${message(code:'default.all.spare.parts.label')}">
				<g:message code="default.all.spare.parts.label"/>
			</a>
			</li>
			<li>
				<a href="${createLinkWithTargetURI(controller: 'listing', action:'pendingOrderSparePartsListing')}"
					class="tooltip" title="${message(code:'default.pending.order.spare.parts.label')}">
					<g:message code="default.pending.order.spare.parts.label"/>
				</a>
			</li>
    </ul>
  </div>
  <a class='v-tabs-dynav-scroll-left' href='#' id='js-scroll-left'></a>
</div>
<r:script>
$(document).ready(function(){
  $(".delete-node").click(function(e){
      var baseUrl = "${createLink(controller: 'listing', action:'deleteCustomizedListing')}";
      var reportType = "${reportType}";
      removeElement(e, this, baseUrl, reportType);
  });
});
</r:script>