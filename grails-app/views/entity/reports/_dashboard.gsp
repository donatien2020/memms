<div class="entity-list">
  <!-- List Top Header Template goes here -->
  <div class="heading1-bar">
    <h1>Reports Dashboard</h1>

  </div>
  <!-- End of template -->

  <!-- Filter template if any goes here -->
  <g:if test="${filterTemplate!=null}">
    <g:render template="/entity/${filterTemplate}" />
  </g:if>
  <!-- End of template -->

  <!-- List Template goes here -->
  <div id ="list-grid" class="v_tabs">
	<!-- <div class="spinner-container">
      <img src="${resource(dir:'images',file:'list-spinner.gif')}" class="ajax-big-spinner"/>
    </div> -->
    <ul id='top_tabs' class="v_tabs_nav left">
      <li><a class="active" id="#corrective">Corrective Maintenance</a></li>
      <li><a id="#preventive">Preventive Maintenance</a></li>
      <li><a id="#equipment">Management Medical Equipment</a></li>
      <li><a id="#spare_parts">Management Of Spare Parts</a></li>
      <li><a id="#monitoring">Monitoring Of MEMMS Use</a></li>
    </ul>
    <div class="v_tabs_content right">
    	<a id="showhide" class="right" href="#">Show / Hide filters</a>
      <ul class="v_tabs_filters">
        <li><input type="checkbox" /><label>option 1</label></li>
        <li><input type="checkbox" /><label>option 2</label></li>
        <li><input type="checkbox" /><label>option 3</label></li>
      </ul>

      <div class="filters main">
        <script src="/memms/static/js/form_init.js" type="text/javascript" ></script>
        <script src="/memms/static/js/dashboard/foldable.js" type="text/javascript" ></script>
        <script src="/memms/static/js/dashboard/list_tabs.js" type="text/javascript" ></script>
        <!-- Load & initialize Tipsy -->
        <script src="/memms/static/js/jquery/tipsy/src/javascripts/jquery.tipsy.js" type="text/javascript" ></script>
        <script src="/memms/static/js/tipsy_init.js" type="text/javascript" ></script>

        <form class="filters-box" method="get" action="/memms/equipmentView/filter" style="display: none;">
          <ul class="filters-list">

            <li>
              <div class="row ">
                <label for="equipmentType.id">Equipment Type :</label>
                <select name="obsolete">
                  <option value="">Please select</option>
                  <option value="true">obsolete</option>
                  <option value="false">not obsolete</option>
                </select>
              </div>
            </li>

            <li>
              <div class="row ">
                <label for="manufacturer.id">Manufacturer :</label>
                <select name="obsolete">
                  <option value="">Please select</option>
                  <option value="true">obsolete</option>
                  <option value="false">not obsolete</option>
                </select>
              </div>
            </li>

            <li>
              <div class="row ">
                <label for="supplier.id">Supplier :</label>
                <select name="obsolete">
                  <option value="">Please select</option>
                  <option value="true">obsolete</option>
                  <option value="false">not obsolete</option>
                </select>
              </div>
            </li>

            <li>
              <div class="row ">
                <label for="serviceProvider.id">Service Provider :</label>
                <select name="obsolete">
                  <option value="">Please select</option>
                  <option value="true">obsolete</option>
                  <option value="false">not obsolete</option>
                </select>
              </div>
            </li>

            <li>
              <label>Obsolete</label>
              <select name="obsolete">
                <option value="">Please select</option>
                <option value="true">obsolete</option>
                <option value="false">not obsolete</option>
              </select>
            </li>

            <li>
              <div class="row ">
                <label for="status">Status :</label>
                <select name="status">
                  <option value="NONE">Please select</option>
                  <option value="OPERATIONAL">Operational</option>
                  <option value="PARTIALLYOPERATIONAL">Partially Operational</option>
                  <option value="INSTOCK">In Stock</option>
                  <option value="UNDERMAINTENANCE">Under Maintenance</option>
                  <option value="FORDISPOSAL">For Disposal</option>
                  <option value="DISPOSED">Disposed</option>
                </select>
                <div class="error-list"></div>
              </div>
            </li>

            <li>
              <div class="row ">
                <label for="purchaser">Purchaser :</label>
                <select name="purchaser">
                  <option value="NONE">Please select</option>
                  <option value="BYMOH">Ministry of Health</option>
                  <option value="BYFACILITY">Facility</option>
                  <option value="BYDONOR">Donor</option>
                </select>
                <div class="error-list"></div>
              </div>
            </li>

            <li>
              <div class="row ">
                <label for="donor">Donor :</label>
                <select name="donor">
                  <option value="NONE">Please select</option>
                  <option value="MOHPARTNER">MoH Partner</option>
                  <option value="OTHERNGO">Other NGO</option>
                  <option value="INDIVIDUAL">Individual</option>
                  <option value="OTHERS">Others</option>
                </select>
                <div class="error-list"></div>
              </div>
            </li>

          </ul>
          <input type="hidden" value="13" name="dataLocation.id">
          <button type="submit">Filter</button>
          <a class="clear-form" href="#">Clear</a>
        </form>
      </div>

      <div id="corrective">
        <ul class="v_tabs_list">
          <li class="v_tabs_row">
            <p>
                  <a class="v_tabs_name v_tabs_fold_toggle">
                    <span class="v_tabs_switch"><img src="${resource(dir:'images',file:'arrow.png')}"/></span>
                    Lorem ipsum dolor sit amet
                  </a>
                  <span class="tooltip v_tabs_formula " original-title="Enter text here!">Something</span>
                  <span class="v_tabs_value">53%</span>
            </p>
            <div class="v_tabs_fold_container">
              <ul class="v_tabs_nested_nav">
                      <li><a id='historic_trend' class='active' href="#">Historic Trend</a></li>
                      <li><a id='comparison' href="#">Comparison To Other Facilities</a></li>
                      <li><a id='geo_trend' href="#">Geographic Trend</a></li>
                      <li><a id='info_facility' href="#">Information By Facility</a></li>
              </ul>
              <div id="historic_trend" class='toggled_tab'>
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="comparison">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="geo_trend">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="info_facility">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
            </div>
          </li>

          <li class="v_tabs_row">
            <p>
                  <a class="v_tabs_name v_tabs_fold_toggle">
                    <span class="v_tabs_switch"><img src="${resource(dir:'images',file:'arrow.png')}"/></span>
                    Lorem ipsum dolor sit amet 1
                  </a>
                  <span class="tooltip v_tabs_formula " original-title="Enter text here!">Something</span>
                  <span class="v_tabs_value">53%</span>
            </p>
            <div class="v_tabs_fold_container">
              <ul class="v_tabs_nested_nav">
                      <li><a id='historic_trend' class='active' href="#">Historic Trend</a></li>
                      <li><a id='comparison' href="#">Comparison To Other Facilities</a></li>
                      <li><a id='geo_trend' href="#">Geographic Trend</a></li>
                      <li><a id='info_facility' href="#">Information By Facility</a></li>
              </ul>
              <div id="historic_trend" class='toggled_tab'>
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="comparison">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="geo_trend">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="info_facility">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
            </div>
          </li>

          <li class="v_tabs_row">
            <p>
              <a class="v_tabs_name v_tabs_fold_toggle">
                <span class="v_tabs_switch"><img src="${resource(dir:'images',file:'arrow.png')}"/></span>
                Lorem ipsum dolor sit amet
              </a>
              <span class="tooltip v_tabs_formula " original-title="Enter text here!">Something</span>
              <span class="v_tabs_value">53%</span>
            </p>
            <div class="v_tabs_fold_container">
              <ul class="v_tabs_nested_nav">
                      <li><a id='historic_trend' class='active' href="#">Historic Trend</a></li>
                      <li><a id='comparison' href="#">Comparison To Other Facilities</a></li>
                      <li><a id='geo_trend' href="#">Geographic Trend</a></li>
                      <li><a id='info_facility' href="#">Information By Facility</a></li>
              </ul>
              <div id="historic_trend" class='toggled_tab'>
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="comparison">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="geo_trend">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="info_facility">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
            </div>
          </li>
        </ul>
      </div> <!-- end of Corrective Maintenance -->


      <div id="preventive">
        <ul class="v_tabs_list">
          <li class="v_tabs_row">
            <p>
              <a class="v_tabs_name v_tabs_fold_toggle">
                <span class="v_tabs_switch"><img src="${resource(dir:'images',file:'arrow.png')}"/></span>
                Lorem ipsum dolor sit amet
              </a>
              <span class="tooltip v_tabs_formula " original-title="Enter text here!">Something</span>
              <span class="v_tabs_value">53%</span>
            </p>
            <div class="v_tabs_fold_container">
              <ul class="v_tabs_nested_nav">
                      <li><a id='historic_trend' class='active' href="#">Historic Trend</a></li>
                      <li><a id='comparison' href="#">Comparison To Other Facilities</a></li>
                      <li><a id='geo_trend' href="#">Geographic Trend</a></li>
                      <li><a id='info_facility' href="#">Information By Facility</a></li>
              </ul>
              <div id="historic_trend" class='toggled_tab'>
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="comparison">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="geo_trend">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="info_facility">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
            </div>
          </li>

          <li class="v_tabs_row">
            <p>
              <a class="v_tabs_name v_tabs_fold_toggle">
                <span class="v_tabs_switch"><img src="${resource(dir:'images',file:'arrow.png')}"/></span>
                Lorem ipsum dolor sit amet
              </a>
              <span class="tooltip v_tabs_formula " original-title="Enter text here!">Something</span>
              <span class="v_tabs_value">53%</span>
            </p>
            <div class="v_tabs_fold_container">
              <ul class="v_tabs_nested_nav">
                      <li><a id='historic_trend' class='active' href="#">Historic Trend</a></li>
                      <li><a id='comparison' href="#">Comparison To Other Facilities</a></li>
                      <li><a id='geo_trend' href="#">Geographic Trend</a></li>
                      <li><a id='info_facility' href="#">Information By Facility</a></li>
              </ul>
              <div id="historic_trend" class='toggled_tab'>
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="comparison">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="geo_trend">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="info_facility">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
            </div>
          </li>
        </ul>
      </div> <!-- end of Preventive Maintenance -->


      <div id="equipment">
        <ul class="v_tabs_list">
          <li class="v_tabs_row">
            <p>
              <a class="v_tabs_name v_tabs_fold_toggle">
                <span class="v_tabs_switch"><img src="${resource(dir:'images',file:'arrow.png')}"/></span>
                Lorem ipsum dolor sit amet
              </a>
              <span class="tooltip v_tabs_formula " original-title="Enter text here!">Something</span>
              <span class="v_tabs_value">53%</span>
            </p>
            <div class="v_tabs_fold_container">
              <ul class="v_tabs_nested_nav">
                      <li><a id='historic_trend' class='active' href="#">Historic Trend</a></li>
                      <li><a id='comparison' href="#">Comparison To Other Facilities</a></li>
                      <li><a id='geo_trend' href="#">Geographic Trend</a></li>
                      <li><a id='info_facility' href="#">Information By Facility</a></li>
              </ul>
              <div id="historic_trend" class='toggled_tab'>
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="comparison">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="geo_trend">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="info_facility">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
            </div>
          </li>

          <li class="v_tabs_row">
            <p>
              <a class="v_tabs_name v_tabs_fold_toggle">
                <span class="v_tabs_switch"><img src="${resource(dir:'images',file:'arrow.png')}"/></span>
                Lorem ipsum dolor sit amet
              </a>
              <span class="tooltip v_tabs_formula " original-title="Enter text here!">Something</span>
              <span class="v_tabs_value">53%</span>
            </p>

            <div class="v_tabs_fold_container">
              <ul class="v_tabs_nested_nav">
                      <li><a id='historic_trend' class='active' href="#">Historic Trend</a></li>
                      <li><a id='comparison' href="#">Comparison To Other Facilities</a></li>
                      <li><a id='geo_trend' href="#">Geographic Trend</a></li>
                      <li><a id='info_facility' href="#">Information By Facility</a></li>
              </ul>
              <div id="historic_trend" class='toggled_tab'>
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="comparison">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="geo_trend">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="info_facility">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
            </div>
          </li>
          <li class="v_tabs_row">
            <p>
              <a class="v_tabs_name v_tabs_fold_toggle">
                <span class="v_tabs_switch"><img src="${resource(dir:'images',file:'arrow.png')}"/></span>
                Lorem ipsum dolor sit amet
              </a>
              <span class="tooltip v_tabs_formula " original-title="Enter text here!">Something</span>
              <span class="v_tabs_value">53%</span>
            </p>
            <div class="v_tabs_fold_container">
              <ul class="v_tabs_nested_nav">
                      <li><a id='historic_trend' class='active' href="#">Historic Trend</a></li>
                      <li><a id='comparison' href="#">Comparison To Other Facilities</a></li>
                      <li><a id='geo_trend' href="#">Geographic Trend</a></li>
                      <li><a id='info_facility' href="#">Information By Facility</a></li>
              </ul>
              <div id="historic_trend" class='toggled_tab'>
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="comparison">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="geo_trend">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="info_facility">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
            </div>
          </li>
        </ul>
      </div> <!-- end of Management Medical Equipment -->


      <div id="spare_parts">
        <ul class="v_tabs_list">
          <li class="v_tabs_row">
            <p>
              <a class="v_tabs_name v_tabs_fold_toggle">
                <span class="v_tabs_switch"><img src="${resource(dir:'images',file:'arrow.png')}"/></span>
                Lorem ipsum dolor sit amet
              </a>
              <span class="tooltip v_tabs_formula " original-title="Enter text here!">Something</span>
              <span class="v_tabs_value">53%</span>
            </p>

            <div class="v_tabs_fold_container">
              <ul class="v_tabs_nested_nav">
                      <li><a id='historic_trend' class='active' href="#">Historic Trend</a></li>
                      <li><a id='comparison' href="#">Comparison To Other Facilities</a></li>
                      <li><a id='geo_trend' href="#">Geographic Trend</a></li>
                      <li><a id='info_facility' href="#">Information By Facility</a></li>
              </ul>
              <div id="historic_trend" class='toggled_tab'>
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="comparison">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="geo_trend">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="info_facility">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
            </div>
          </li>

          <li class="v_tabs_row">
            <p>
              <a class="v_tabs_name v_tabs_fold_toggle">
                <span class="v_tabs_switch"><img src="${resource(dir:'images',file:'arrow.png')}"/></span>
                Lorem ipsum dolor sit amet
              </a>
              <span class="tooltip v_tabs_formula " original-title="Enter text here!">Something</span>
              <span class="v_tabs_value">53%</span>
            </p>

            <div class="v_tabs_fold_container">
              <ul class="v_tabs_nested_nav">
                      <li><a id='historic_trend' class='active' href="#">Historic Trend</a></li>
                      <li><a id='comparison' href="#">Comparison To Other Facilities</a></li>
                      <li><a id='geo_trend' href="#">Geographic Trend</a></li>
                      <li><a id='info_facility' href="#">Information By Facility</a></li>
              </ul>
              <div id="historic_trend" class='toggled_tab'>
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="comparison">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="geo_trend">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="info_facility">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
            </div>
          </li>
        </ul>
      </div> <!-- end of Spare parts -->


      <div id="monitoring">
        <ul class="v_tabs_list">
          <li class="v_tabs_row">
            <p>
              <a class="v_tabs_name v_tabs_fold_toggle">
                <span class="v_tabs_switch"><img src="${resource(dir:'images',file:'arrow.png')}"/></span>
                Lorem ipsum dolor sit amet
              </a>
              <span class="tooltip v_tabs_formula " original-title="Enter text here!">Something</span>
              <span class="v_tabs_value">53%</span>
            </p>

            <div class="v_tabs_fold_container">
              <ul class="v_tabs_nested_nav">
                      <li><a id='historic_trend' class='active' href="#">Historic Trend</a></li>
                      <li><a id='comparison' href="#">Comparison To Other Facilities</a></li>
                      <li><a id='geo_trend' href="#">Geographic Trend</a></li>
                      <li><a id='info_facility' href="#">Information By Facility</a></li>
              </ul>
              <div id="historic_trend" class='toggled_tab'>
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="comparison">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="geo_trend">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="info_facility">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
            </div>
          </li>

          <li class="v_tabs_row">
            <p>
              <a class="v_tabs_name v_tabs_fold_toggle">
                <span class="v_tabs_switch"><img src="${resource(dir:'images',file:'arrow.png')}"/></span>
                Lorem ipsum dolor sit amet
              </a>
              <span class="tooltip v_tabs_formula " original-title="Enter text here!">Something</span>
              <span class="v_tabs_value">53%</span>
            </p>

            <div class="v_tabs_fold_container">
              <ul class="v_tabs_nested_nav">
                      <li><a id='historic_trend' class='active' href="#">Historic Trend</a></li>
                      <li><a id='comparison' href="#">Comparison To Other Facilities</a></li>
                      <li><a id='geo_trend' href="#">Geographic Trend</a></li>
                      <li><a id='info_facility' href="#">Information By Facility</a></li>
              </ul>
              <div id="historic_trend" class='toggled_tab'>
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="comparison">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="geo_trend">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="info_facility">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
            </div>
          </li>

          <li class="v_tabs_row">
            <p>
              <a class="v_tabs_name v_tabs_fold_toggle">
                <span class="v_tabs_switch"><img src="${resource(dir:'images',file:'arrow.png')}"/></span>
                Lorem ipsum dolor sit amet
              </a>
              <span class="tooltip v_tabs_formula " original-title="Enter text here!">Something</span>
              <span class="v_tabs_value">53%</span>
            </p>

            <div class="v_tabs_fold_container">
              <ul class="v_tabs_nested_nav">
                      <li><a id='historic_trend' class='active' href="#">Historic Trend</a></li>
                      <li><a id='comparison' href="#">Comparison To Other Facilities</a></li>
                      <li><a id='geo_trend' href="#">Geographic Trend</a></li>
                      <li><a id='info_facility' href="#">Information By Facility</a></li>
              </ul>
              <div id="historic_trend" class='toggled_tab'>
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="comparison">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="geo_trend">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
              <div id="info_facility">
                <g:render template="/entity/reports/nested_tabs" />
              </div>
            </div>
          </li>
        </ul>
      </div> <!-- end of Monitoring MEMMS Use -->
    </div>
  </div>
  <!-- End of template -->
</div>
