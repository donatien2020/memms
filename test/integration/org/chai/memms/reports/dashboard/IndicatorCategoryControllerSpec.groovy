/**
 * Copyright (c) 2012, Clinton Health Access Initiative.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.chai.memms.reports.dashboard

import org.chai.memms.IntegrationTests;
import org.chai.memms.reports.dashboard.IndicatorCategory
import org.chai.memms.reports.dashboard.DashboardInitializer
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.*
/**
 * @author Antoine Nzeyi, Donatien Masengesho, Pivot Access Ltd
 *
 */
class IndicatorCategoryControllerSpec extends IntegrationTests{

   
    def indicatorCategoryController

	def "create IndicatorCategory with valid fields"(){
		setup:
		indicatorCategoryController = new IndicatorCategoryController()
		when:
		indicatorCategoryController.params.code =DashboardInitializer.MANAGEMENT_EQUIPMENT
		indicatorCategoryController.params.names_en="MANAGEMENT EQUIPMENT"
                indicatorCategoryController.params.names_fr="MANAGEMENT EQUIPMENT" 
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
		indicatorCategoryController.params.names_en="MANAGEMENT EQUIPMENT"
                indicatorCategoryController.params.names_fr="MANAGEMENT EQUIPMENT" 
		indicatorCategoryController.params.redToYellowThreshold=60  
                indicatorCategoryController.params.yellowToGreenThreshold=80
		indicatorCategoryController.save()
		then:
		IndicatorCategory.count() == 0;
		
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
