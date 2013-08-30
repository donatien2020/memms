package org.chai.memms.reports.dashboard

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*
import static org.junit.Assert.*
import org.chai.memms.IntegrationTests;
import org.chai.memms.reports.dashboard.IndicatorCategory
import org.chai.memms.reports.dashboard.DashboardInitializer
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.*

class IndicatorCategoryControllerSpec extends IntegrationTests{

   
    def indicatorCategoryController

	def "create IndicatorCategory with valid fields"(){
		setup:
		indicatorCategoryController = new IndicatorCategoryController()
		when:
		indicatorCategoryController.params.code =DashboardInitializer.MANAGEMENT_EQUIPMENT
		indicatorCategoryController.params.name_en="MANAGEMENT EQUIPMENT"
                indicatorCategoryController.params.name_fr="MANAGEMENT EQUIPMENT" 
		indicatorCategoryController.params.redToYellowThreshold=60  
                indicatorCategoryController.params.yellowToGreenThreshold=80
		indicatorCategoryController.save()
		then:
		IndicatorCategory.count() == 1;
		IndicatorCategory.findByCode(DashboardInitializer.MANAGEMENT_EQUIPMENT).code.equals(DashboardInitializer.MANAGEMENT_EQUIPMENT)
		
	}
        
    
    
    
    def "can not create IndicatorCategory with valid fields"(){
		setup:
		indicatorCategoryController = new IndicatorCategoryController()
		when:
		indicatorCategoryController.params.code=""
		indicatorCategoryController.params.name_en="MANAGEMENT EQUIPMENT"
                indicatorCategoryController.params.name_fr="MANAGEMENT EQUIPMENT" 
		indicatorCategoryController.params.redToYellowThreshold=60  
                indicatorCategoryController.params.yellowToGreenThreshold=80
		indicatorCategoryController.save()
		then:
		IndicatorCategory.count() == 0;
		IndicatorCategory.findByCode(DashboardInitializer.MANAGEMENT_EQUIPMENT).code.equals(DashboardInitializer.MANAGEMENT_EQUIPMENT)
		
	}
  
	def "list IndicatorCategories"(){
		setup:
		indicatorCategoryController = new IndicatorCategoryController();
                createDashboardTestData()

		when:
		indicatorCategoryController.list()
		then:
		IndicatorCategory.count() == 1;
		indicatorCategoryController.modelAndView.model.entities.size() == 1
		
	}

	
	
	
}
