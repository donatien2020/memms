package org.chai.memms.reports.dashboard
import static org.junit.Assert.*
import org.junit.*
import org.chai.memms.IntegrationTests

import org.chai.location.CalculationLocation
import org.chai.location.DataLocation
import org.chai.location.Location
/**
 * @author Antoine Nzeyi, Donatien Masengesho, Pivot Access Ltd
 *
 */
class HistoricalValueItemSpec extends IntegrationTests{
    
def indicatorComputationService
def historicalValueItem

def "historical Value Item item Contructor test "(){
                setup:
                setupLocationTree()
                setupSystemUser()
                setupEquipment()
                createDashboardTestData()
                indicatorComputationService.computeCurrentReport()
		when:
                def indicatorValues=IndicatorValue.findAll()
               
                historicalValueItem = new HistoricalValueItem(indicatorValues.get(0))
                then:
                assert historicalValueItem.color=="green"
                   
    }
}
