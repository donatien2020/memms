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

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

import org.chai.memms.IntegrationTests;
import org.chai.memms.reports.dashboard.IndicatorCategory
import org.chai.memms.reports.dashboard.Indicator
import org.chai.memms.reports.dashboard.DashboardInitializer
/**
 * @author Antoine Nzeyi, Donatien Masengesho, Pivot Access Ltd
 *
 */
class IndicatorControllerSpec extends IntegrationTests{

    def indicatorController

	def "create Indicator with valid fields"(){
		setup:
		indicatorController = new IndicatorController();
                 def category=new IndicatorCategory(code:DashboardInitializer.MANAGEMENT_EQUIPMENT+"TESTSTRUCTUREController",names_en:"MANAGEMENT EQUIPMENT",redToYellowThreshold:60,yellowToGreenThreshold:80).save()
        
		when:
                indicatorController.params.category=category
		indicatorController.params.code = "DEGREE_STD_EQUIPMENTESTSTRUCTURE"
		indicatorController.params.names_en = "DEGREE_STD_EQUIPMENTESTSTRUCTURE"
		indicatorController.params.descriptions_en = "DEGREE_STD_EQUIPMENTESTSTRUCTURE"   
                indicatorController.params.names_fr = "DEGREE_STD_EQUIPMENTESTSTRUCTURE"
		indicatorController.params.descriptions_fr = "DEGREE_STD_EQUIPMENTESTSTRUCTURE"   
                indicatorController.params.formula_en ="(max (no. of equipments of type 1 from one manufacturer)+max(no. of equipments of type 2 from one manufacturer)+....) / (Total no. of equipments at the facility except equipments with status = For disposal or Disposed))"
                indicatorController.params.formula_fr ="(max (no. of equipments of type 1 from one manufacturer)+max(no. of equipments of type 2 from one manufacturer)+....) / (Total no. of equipments at the facility except equipments with status = For disposal or Disposed))"
                indicatorController.params.unit ="%"
                indicatorController.params.redToYellowThreshold =30
                indicatorController.params.yellowToGreenThreshold =60
                indicatorController.params.queryScript =DashboardInitializer.DEGREE_STANDARDIZATION_SIMPLE_SLD10
                indicatorController.params. sqlQuery =true
                indicatorController.params. active =true
                indicatorController.params.groupName_en ="Equipment Type"
                indicatorController.params.groupName_fr ="Equipment Type"
                indicatorController.params.groupQueryScript =DashboardInitializer.DEGREE_STANDARDIZATION_GROUP_SLD10
                indicatorController.params.historicalPeriod=Indicator.HistoricalPeriod.YEARLY
                indicatorController.params.historyItems=5
		indicatorController.save()

		then:
		Indicator.count() == 1;
		Indicator.findByCode("DEGREE_STD_EQUIPMENTESTSTRUCTURE").code.equals("DEGREE_STD_EQUIPMENTESTSTRUCTURE")
		
	}

    
    
    
    def "can't create Indicator with invalid fields"(){
		setup:
		indicatorController = new IndicatorController();
                 def category=new IndicatorCategory(code:DashboardInitializer.MANAGEMENT_EQUIPMENT+"TESTSTRUCTUREController",names_en:"MANAGEMENT EQUIPMENT",redToYellowThreshold:60,yellowToGreenThreshold:80).save()
        
		when:
                indicatorController.params.category=category
		indicatorController.params.code = "DEGREE_STD_EQUIPMENTESTSTRUCTURE"
		indicatorController.params.names_en = "DEGREE_STD_EQUIPMENTESTSTRUCTURE"
		indicatorController.params.descriptions_en = "DEGREE_STD_EQUIPMENTESTSTRUCTURE"   
                indicatorController.params.names_fr = "DEGREE_STD_EQUIPMENTESTSTRUCTURE"
		indicatorController.params.descriptions_fr = "DEGREE_STD_EQUIPMENTESTSTRUCTURE"   
                indicatorController.params.formula_en ="(max (no. of equipments of type 1 from one manufacturer)+max(no. of equipments of type 2 from one manufacturer)+....) / (Total no. of equipments at the facility except equipments with status = For disposal or Disposed))"
                indicatorController.params.formula_fr ="(max (no. of equipments of type 1 from one manufacturer)+max(no. of equipments of type 2 from one manufacturer)+....) / (Total no. of equipments at the facility except equipments with status = For disposal or Disposed))"
                indicatorController.params.unit ="%"
                indicatorController.params.redToYellowThreshold =30
                indicatorController.params.yellowToGreenThreshold =60
                indicatorController.params.queryScript =DashboardInitializer.DEGREE_STANDARDIZATION_SIMPLE_SLD10
                indicatorController.params. sqlQuery =true
                indicatorController.params. active =true
//                indicatorController.params.groupName_en ="Equipment Type"
//                indicatorController.params.groupName_fr ="Equipment Type"
                indicatorController.params.groupQueryScript =DashboardInitializer.DEGREE_STANDARDIZATION_GROUP_SLD10
                indicatorController.params.historicalPeriod=3 // invilid period
                indicatorController.params.historyItems=5
		indicatorController.save()

		then:
		Indicator.count() == 0;
		
		
	}
	def "list indicators"(){

		setup:
		indicatorController = new IndicatorController();
                createDashboardTestData()

		when: "none ajax"
		indicatorController.list()
		then:
		Indicator.count() == 1;
		indicatorController.modelAndView.model.entities.size() == 1
		
		when: "with ajax"
		indicatorController.request.content = '{"sort":"code"}'.getBytes()
		indicatorController.request.makeAjaxRequest()
		indicatorController.list()
		then:
		Indicator.count() == 1;
		indicatorController.response.json.results[0].contains(Indicator.findByCode("DEGREE_STD_EQUIPMENTESTSTRUCTURE").names)
	}

	def "search indicator"(){
		setup:
		indicatorController = new IndicatorController();
		createDashboardTestData()
		when:
		indicatorController.params.q = "DEGREE_STD_EQUIPMENTESTSTRUCTURE"
		indicatorController.request.makeAjaxRequest()
		indicatorController.search()
		then:
		Indicator.count() == 1;
		indicatorController.response.json.results[0].contains(Indicator.findByCode("DEGREE_STD_EQUIPMENTESTSTRUCTURE").names)
		

	}
	
	
}
