package org.chai.memms.reports.dashboard

import org.junit.*
import static org.junit.Assert.*
import org.chai.memms.IntegrationTests;
import org.chai.memms.reports.dashboard.UserDefinedVariable

import org.chai.memms.reports.dashboard.DashboardInitializer
class UserDefinedVariableControllerSpec extends IntegrationTests{

   
    def userDefinedVariableController

	def "create UserDefinedVariable with valid fields"(){
		setup:
		userDefinedVariableController = new UserDefinedVariableController()
		when:
               
		userDefinedVariableController.params.code ="WORK_ORDER_TRESH_HOL:D"
		userDefinedVariableController.params.name_en="WORK_ORDER_TRESH_HOL:D"
                userDefinedVariableController.params.name_fr="WORK_ORDER_TRESH_HOL:D" 
		userDefinedVariableController.params.currentValue=60  
                
		userDefinedVariableController.save()

		then:
		UserDefinedVariable.count() == 1;
		UserDefinedVariable.findByCode("WORK_ORDER_TRESH_HOL:D").code.equals("WORK_ORDER_TRESH_HOL:D")
		
	}

	def "list UserDefinedVariables"(){

		setup:
		userDefinedVariableController = new UserDefinedVariableController();
                createDashboardTestData()
                when:
		userDefinedVariableController.list()
		then:
		userDefinedVariableController.modelAndView.model.entities.size() == 1	
	}

	
}