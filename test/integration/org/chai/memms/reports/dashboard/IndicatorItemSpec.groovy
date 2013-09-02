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

import org.chai.location.CalculationLocation
import org.chai.location.DataLocation
import org.chai.location.Location
import java.util.Map
/**
 * @author Antoine Nzeyi, Donatien Masengesho, Pivot Access Ltd
 *
 */
class IndicatorItemSpec extends IntegrationTests{
    
def indicatorComputationService
def indicatorItem

def "indicator item Contructor test "(){
                setup:
                setupLocationTree()
                setupSystemUser()
                setupEquipment()
                createDashboardTestData()
                indicatorComputationService.computeCurrentReport()
		when:
                def indicatorValues=IndicatorValue.findAll()
               // indicatorItem = new IndicatorItem(indicatorValues.get(0))
                indicatorItem = new IndicatorItem(indicatorValues.get(0))
                then:
                assert indicatorItem.historicalValueItems.size()==1
                assert indicatorItem.highestComparisonValueItems.size()==0
                assert indicatorItem.higherComparisonValueItems.size()==0
                assert indicatorItem.lowestComparisonValueItems.size()==0
                assert indicatorItem.geographicalValueItems.size()==1
                println"valuesPerGroup :"+indicatorItem.valuesPerGroup.size()
                   
    }
// def "get Historic Value Items test "(){
//                setup:
//                setupLocationTree()
//                setupSystemUser()
//                setupEquipment()
//                createDashboardTestData()
//                indicatorComputationService.computeCurrentReport()
//		when:
//                def indicatorValues=IndicatorValue.findAll()
//                indicatorItem = new IndicatorItem()
//                 List<IndicatorValue> historicalValueValues=indicatorItem.getHistoricValueItems(indicatorValues.get(0))
//                then:
//                assert historicalValueValues instanceof List<IndicatorValue>
//                assert historicalValueValues.size() ==1
//                   
//    }
//  def "get Geographical Value Items test "(){
//                setup:
//                setupLocationTree()
//                setupSystemUser()
//                setupEquipment()
//                createDashboardTestData()
//                indicatorComputationService.computeCurrentReport()
//		when:
//                def indicatorValues=IndicatorValue.findAll()
//                indicatorItem = new IndicatorItem()
//                List<IndicatorValue>  geographicalValues=indicatorItem.getGeographicalValueItems(indicatorValues.get(0))
//                then:
//                assert geographicalValues instanceof List<IndicatorValue>
//                assert geographicalValues.size()==1
//                
//       
//                   
//    }
//     def "get Comparison Value Items test "(){
//                setup:
//                setupLocationTree()
//                setupSystemUser()
//                setupEquipment()
//                createDashboardTestData()
//                indicatorComputationService.computeCurrentReport()
//		when:
//                def indicatorValues=IndicatorValue.findAll()
//                indicatorItem = new IndicatorItem()
//                 def historicalValueValue=indicatorItem.getComparisonValueItems(indicatorValues.get(0))
//                then:
//                assert historicalValueValue ==null
//                   
//    }
//    
//    
//    def "get Simmilar Facilities Or locations test "(){
//                setup:
//                setupLocationTree()
//                setupSystemUser()
//                setupEquipment()
//                createDashboardTestData()
//                indicatorComputationService.computeCurrentReport()
//		when:
//                def indicatorValues=IndicatorValue.findAll()
//                indicatorItem = new IndicatorItem()
//                 def SimmilarFacilities=indicatorItem.getSimmilarFacilitiesOrlocations(indicatorValues.get(0))
//                then:
//               
//                 assert SimmilarFacilities.size()==1
//                   
//    }
//    
//    
//    def "get Locations Id With Users test "(){
//                setup:
//                setupLocationTree()
//                setupSystemUser()
//                setupEquipment()
//                createDashboardTestData()
//                indicatorComputationService.computeCurrentReport()
//		when:
//                indicatorItem = new IndicatorItem()
//                 List<String> locationIds=indicatorItem.getLocationsIdWithUsers()
//                then:
//               
//                assert locationIds !=null
//                assert locationIds.size() ==1
//                   
//    }
//    
    
}
