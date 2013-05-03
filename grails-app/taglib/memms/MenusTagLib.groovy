package memms

class MenusTagLib {
	def reportMenus = {
	
		  out << """${link(action:"list", controller:"indicatorType"){message(code:"report_menu_indicators")}}"""+" || "
		  out << """${link(action:"list", controller:"indicatorValue"){message(code:"report_menu_indicator_values")}}"""+" || "
		  out << """${link(action:"list", controller:"categoryType"){message(code:"report_menu_category_type")}}"""+" || "
		  out << """${link(action:"list", controller:"facilityReport"){message(code:"report_menu_Facility_report")}}"""+" || "
		  out << """${link(action:"list", controller:"intermediateValues"){message(code:"report_menu_intermediate_values")}}"""+" || "
		  out << """${link(action:"list", controller:"indicatorColorCriteriaPerFacilityType"){message(code:"report_menu_Color_criteria")}}"""+" || "
    
	  }
}
