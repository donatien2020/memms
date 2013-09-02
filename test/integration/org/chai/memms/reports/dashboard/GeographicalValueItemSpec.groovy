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
class GeographicalValueItemSpec extends IntegrationTests{
    
def indicatorComputationService
def geographicalValueItem

def "geographical Value Item Contructor test "(){
                setup:
                setupLocationTree()
                setupSystemUser()
                setupEquipment()
                createDashboardTestData()
                indicatorComputationService.computeCurrentReport()
		when:
                def indicatorValues=IndicatorValue.findAll()
               
                geographicalValueItem = new GeographicalValueItem(indicatorValues.get(0))
                then:
                assert geographicalValueItem.color=="green"
                   
    }
}
