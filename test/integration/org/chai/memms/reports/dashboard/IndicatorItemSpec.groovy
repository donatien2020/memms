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
class IndicatorItemSpec extends IntegrationTests{
    
def indicatorComputationService
def indicatorItem

def "Indicator item Contructor test "(){
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
                println" This test passed wit historicalValueItems size:"+indicatorItem.historicalValueItems.size()
                println" This test passed wit historicalValueItems size:"+indicatorItem.highestComparisonValueItems.size() 
                println" This test passed wit historicalValueItems size:"+indicatorItem.higherComparisonValueItems.size()
                println" This test passed wit historicalValueItems size:"+indicatorItem.lowestComparisonValueItems.size()
                println" This test passed wit historicalValueItems size:"+indicatorItem.geographicalValueItems.size()
                 
                assert indicatorItem.historicalValueItems.size()==1
                   
    }
     def "get Historic Value Items test "(){
                setup:
                setupLocationTree()
                setupSystemUser()
                setupEquipment()
                createDashboardTestData()
                indicatorComputationService.computeCurrentReport()
		when:
                def indicatorValues=IndicatorValue.findAll()
                indicatorItemAdd = new IndicatorItem()
                 def historicalValueValue=indicatorItemAdd.getHistoricValueItems(indicatorValues.get(0))
                then:
                println" This test passed wit computaion  expectyed :"+historicalValueValue
                assert historicalValueValue !=null
                   
    }
    
}
