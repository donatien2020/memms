
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
import org.chai.memms.IntegrationTests
import org.chai.memms.reports.dashboard.Indicator
import org.chai.location.CalculationLocation
import org.chai.location.DataLocation
import org.chai.location.Location
import java.util.Map
/**
 * @author Antoine Nzeyi, Donatien Masengesho, Pivot Access Ltd
 *
 */
class IndicatorComputationServiceSpec  extends IntegrationTests{
    def indicatorComputationService
    
    //simple data computation terst
    
        def "compute SQL Qury Script"() {
		setup:
                setupLocationTree()
                setupSystemUser()
                setupEquipment()
                createDashboardTestData()
		when:
		   def result=indicatorComputationService.executeSQL("select sum(tmp2.max_eq)/tmp3.denominator as final_result from (select max(tmp1.counter) as max_eq from (select type_id, manufacturer_id, count(id) as counter FROM memms_equipment where current_status not in ('FORDISPOSAL','DISPOSED') and type_id is not null and manufacturer_id is not null and data_location_id is not null group by type_id, manufacturer_id) tmp1 group by tmp1.type_id) tmp2, (select sum(temp22.counter3) as denominator from (select e.type_id as typeid,count(e.id) as counter3 from memms_equipment e,memms_equipment_type et where e.type_id=et.id and current_status not in ('FORDISPOSAL','DISPOSED') and data_location_id is not null group by e.type_id,e.manufacturer_id) temp22) tmp3")
		then:
                assert result!=null
                assert result==1
	}
   
    def "compute Script"(){
     
                setup:
                setupLocationTree()
                setupEquipment()
                createDashboardTestData()
                when:
		 def result=indicatorComputationService.computeScript("select sum(tmp2.max_eq)/tmp3.denominator as final_result from (select max(tmp1.counter) as max_eq from (select type_id, manufacturer_id, count(id) as counter FROM memms_equipment where current_status not in ('FORDISPOSAL','DISPOSED') and type_id is not null and manufacturer_id is not null and data_location_id is not null group by type_id, manufacturer_id) tmp1 group by tmp1.type_id) tmp2, (select sum(temp22.counter3) as denominator from (select e.type_id as typeid,count(e.id) as counter3 from memms_equipment e,memms_equipment_type et where e.type_id=et.id and current_status not in ('FORDISPOSAL','DISPOSED') and data_location_id is not null group by e.type_id,e.manufacturer_id) temp22) tmp3",true)
		then:
                 assert result==1
          
   }
    
    def "compute Script With DataLocation Condition"(){
       
                 setup:
                 setupLocationTree()
                 setupEquipment()
                 createDashboardTestData()
                
		 when:
		  def result=indicatorComputationService.computeScriptWithDataLocationCondition("select sum(tmp2.max_eq)/tmp3.denominator as final_result from (select max(tmp1.counter) as max_eq from (select type_id, manufacturer_id, count(id) as counter FROM memms_equipment where current_status not in ('FORDISPOSAL','DISPOSED') and type_id is not null and manufacturer_id is not null and data_location_id is not null group by type_id, manufacturer_id) tmp1 group by tmp1.type_id) tmp2, (select sum(temp22.counter3) as denominator from (select e.type_id as typeid,count(e.id) as counter3 from memms_equipment e,memms_equipment_type et where e.type_id=et.id and current_status not in ('FORDISPOSAL','DISPOSED') and data_location_id is not null group by e.type_id,e.manufacturer_id) temp22) tmp3",true,"is not null")
		 then:
             
                 assert result==1
        
    }
    
    
    
   def "compute Indicator For All Data Locations"() {
       
                 setup:
                 setupLocationTree()
                 setupEquipment()
                 createDashboardTestData()
		when:
                 def indicator=Indicator.findByCode("DEGREE_STD_EQUIPMENTESTSTRUCTURE")
                 def result= indicatorComputationService.computeIndicatorForAllDataLocations(indicator)
		then:
                 assert result!=null
                 assert result==1
                
        
    }
    
 // Grouped data computation teast
    
   def "group Compute SQL Qury Script"() {
		 setup:
                 setupLocationTree()
                 setupEquipment()
                 createDashboardTestData()
		 when:
		  def groupResult=indicatorComputationService.groupExecuteSQL("select tmp2.typeNames as typeName, tmp2.max_eq/tmp3.denominator as final_result from (select tmp1.namess as typeNames,max(tmp1.counter1) as max_eq from (select ee.type_id,et.names_en as namess, ee.manufacturer_id, count(ee.id) as counter1 FROM memms_equipment ee,memms_equipment_type et where ee.type_id=et.id and ee.type_id is not null and ee.manufacturer_id is not null and ee.current_status not in ('FORDISPOSAL','DISPOSED') and ee.data_location_id is not null group by ee.type_id, ee.manufacturer_id) tmp1 group by tmp1.type_id) tmp2,(select sum(temp2.counter3) as denominator from (select eq.type_id as typeid,count(eq.id) as counter3 from memms_equipment eq,memms_equipment_type et where eq.type_id=et.id and current_status not in ('FORDISPOSAL','DISPOSED') and data_location_id is not null group by eq.type_id,eq.manufacturer_id) temp2) tmp3")
		then:
                  assert groupResult["Accelerometers"]==1.0
                  
                           
	}
        
    
    def "group Compute Script"(){
      
                 setup:
                 setupLocationTree()
                 setupEquipment()
                 createDashboardTestData()
		 when:
		   def groupResult=indicatorComputationService.groupComputeScript("select tmp2.typeNames as typeName, tmp2.max_eq/tmp3.denominator as final_result from (select tmp1.namess as typeNames,max(tmp1.counter1) as max_eq from (select ee.type_id,et.names_en as namess, ee.manufacturer_id, count(ee.id) as counter1 FROM memms_equipment ee,memms_equipment_type et where ee.type_id=et.id and ee.type_id is not null and ee.manufacturer_id is not null and ee.current_status not in ('FORDISPOSAL','DISPOSED') and ee.data_location_id is not null group by ee.type_id, ee.manufacturer_id) tmp1 group by tmp1.type_id) tmp2,(select sum(temp2.counter3) as denominator from (select eq.type_id as typeid,count(eq.id) as counter3 from memms_equipment eq,memms_equipment_type et where eq.type_id=et.id and current_status not in ('FORDISPOSAL','DISPOSED') and data_location_id is not null group by eq.type_id,eq.manufacturer_id) temp2) tmp3",true)
		 then:
                  assert groupResult["Accelerometers"]==1.0
                  
          
    }
   
    def "group Compute Script With DataLocation Condition"(){
        
                 setup:
                 setupLocationTree()
                 setupEquipment()
                 createDashboardTestData()
		 when:
		  def groupResult=indicatorComputationService.groupComputeScriptWithDataLocationCondition("select tmp2.typeNames as typeName, tmp2.max_eq/tmp3.denominator as final_result from (select tmp1.namess as typeNames,max(tmp1.counter1) as max_eq from (select ee.type_id,et.names_en as namess, ee.manufacturer_id, count(ee.id) as counter1 FROM memms_equipment ee,memms_equipment_type et where ee.type_id=et.id and ee.type_id is not null and ee.manufacturer_id is not null and ee.current_status not in ('FORDISPOSAL','DISPOSED') and ee.data_location_id is not null group by ee.type_id, ee.manufacturer_id) tmp1 group by tmp1.type_id) tmp2,(select sum(temp2.counter3) as denominator from (select eq.type_id as typeid,count(eq.id) as counter3 from memms_equipment eq,memms_equipment_type et where eq.type_id=et.id and current_status not in ('FORDISPOSAL','DISPOSED') and data_location_id is not null group by eq.type_id,eq.manufacturer_id) temp2) tmp3",true,"is not null")
		 then:
                  
                 assert groupResult["Accelerometers"]==1.0
                
        
    }
    
    
   
   def "group Compute Indicator For All Data Locations"() {
       
                 setup:
                 setupLocationTree()
                 setupEquipment()
                 createDashboardTestData()
		 when:
                 def indicator=Indicator.findByCode("DEGREE_STD_EQUIPMENTESTSTRUCTURE")
                 def groupResult= indicatorComputationService.groupComputeIndicatorForAllDataLocations(indicator)
		then:
                 assert groupResult["Accelerometers"]==1.0
                
       
   }
    
   
    
    def "compute Indicator For Location"(){
        
                 setup:
                 setupLocationTree()
                 setupEquipment()
                 createDashboardTestData()
		 when:
                 def indicator=Indicator.findByCode("DEGREE_STD_EQUIPMENTESTSTRUCTURE")
                 def result= indicatorComputationService.computeIndicatorForLocation(indicator,null);
		then:
                  assert result==0.0
                 
        
        
    }
     //holly report computation
    
    def "copmute current report test "(){
                setup:
                setupLocationTree()
                setupSystemUser()
                setupEquipment()
                createDashboardTestData()
		when:
		 indicatorComputationService.computeCurrentReport()
                 
		then:
               
                assert MemmsReport.count()==1
                   
    }
	
}
