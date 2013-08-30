package org.chai.memms.reports.dashboard
import org.junit.*
import static org.junit.Assert.*
import org.chai.memms.IntegrationTests;
import org.chai.memms.reports.dashboard.IndicatorCategory
import org.chai.memms.reports.dashboard.DashboardInitializer

class DashboardControllerSpec extends IntegrationTests{

    def dashboardController
    def indicatorComputationService

	def "indicators test"(){
		setup:
		dashboardController = new DashboardController();
                setupLocationTree()
                setupSystemUser()
                createDashboardTestData()
                indicatorComputationService.computeCurrentReport()
		when:
		dashboardController.indicators()
		then:
		println" mdel pending "
		
	}
        
    
    
	
}
