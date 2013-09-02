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

import org.junit.*
import static org.junit.Assert.*
import org.chai.memms.IntegrationTests;
import org.chai.memms.reports.dashboard.UserDefinedVariable

import org.chai.memms.reports.dashboard.DashboardInitializer
/**
 * @author Antoine Nzeyi, Donatien Masengesho, Pivot Access Ltd
 *
 */
class UserDefinedVariableControllerSpec extends IntegrationTests{

   
    def userDefinedVariableController

	def "create UserDefinedVariable with valid fields"(){
		setup:
		userDefinedVariableController = new UserDefinedVariableController()
		when:
               
		userDefinedVariableController.params.code ="WORK_ORDER_TRESH_HOL:D"
		userDefinedVariableController.params.names_en="WORK_ORDER_TRESH_HOL:D"
                userDefinedVariableController.params.names_fr="WORK_ORDER_TRESH_HOL:D" 
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