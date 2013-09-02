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
import org.chai.memms.reports.dashboard.Indicator
import grails.test.mixin.TestFor
import grails.plugin.spock.UnitSpec;
import org.chai.memms.reports.dashboard.DashboardInitializer

/**
 * @author Antoine Nzeyi, Donatien Masengesho, Pivot Access Ltd
 *
 */
@TestFor(Indicator)

class IndicatorSpec extends UnitSpec{
  
	  def "indicator is valid and can be saved"() {
          setup:
          mockForConstraintsTests(Indicator)
          mockDomain(IndicatorCategory)
          when:
          def category=new IndicatorCategory(code:DashboardInitializer.CORRECTIVE_MAINTENANCE+"test",names_en:"Corrective maintenance",redToYellowThreshold:60,yellowToGreenThreshold:80).save()
          def validIndicator=new Indicator(category:category, code:"SHARE_OPE_EQUIPMENTtes", names_en:"Share of operational equipment",names_fr:"Share of operational equipment",descriptions_en:"Share of operational equipment",descriptions_fr:"Share of operational equipment", formula_en:"(total number equipment with STATUS=Operational) / by (total number equipment with STATUS={Operational Partially operational Under maintenance})", formula_fr:"(total number equipment with STATUS=Operational) / by (total number equipment with STATUS={Operational Partially operational Under maintenance})",unit:"%",redToYellowThreshold:0.8,yellowToGreenThreshold:0.9, historicalPeriod:Indicator.HistoricalPeriod.QUARTERLY, historyItems:8, queryScript:DashboardInitializer.SHARE_OPERATIONAL_SIMPLE_SLD7, groupName_en:"Type of Equipment", groupName_fr:"Type of Equipment", groupQueryScript:DashboardInitializer.SHARE_OPERATIONAL_GROUP_SLD7,sqlQuery:false, active:true).save(failOnError: true)
         
          then:
           category!=null
           validIndicator!=null
          !validIndicator.errors.hasFieldErrors("names_en")
          !validIndicator.errors.hasFieldErrors("names_fr")
          !validIndicator.errors.hasFieldErrors("descriptions_en")
          !validIndicator.errors.hasFieldErrors("descriptions_fr")
          !validIndicator.errors.hasFieldErrors("formula_en")
          !validIndicator.errors.hasFieldErrors("formula_fr")
          !validIndicator.errors.hasFieldErrors("code")
          !validIndicator.errors.hasFieldErrors("groupQueryScript")
         assert  validIndicator.validate()
         assert validIndicator!=null
             
            
         
   }
     def "indicator without code  is invalid"() {
              
         
          setup:
          mockForConstraintsTests(Indicator)
          mockDomain(IndicatorCategory)
          when:
          def category=new IndicatorCategory(code:DashboardInitializer.CORRECTIVE_MAINTENANCE+"test",names_en:"Corrective maintenance",redToYellowThreshold:60,yellowToGreenThreshold:80).save()
          def invalidIndicator=new Indicator(category:category, code:null, names_en:"Share of operational equipment",names_fr:"Share of operational equipment",descriptions_en:"Share of operational equipment",descriptions_fr:"Share of operational equipment", formula_en:"(total number equipment with STATUS=Operational) / by (total number equipment with STATUS={Operational Partially operational Under maintenance})", formula_fr:"(total number equipment with STATUS=Operational) / by (total number equipment with STATUS={Operational Partially operational Under maintenance})",unit:"%",redToYellowThreshold:0.8,yellowToGreenThreshold:0.9, historicalPeriod:Indicator.HistoricalPeriod.QUARTERLY, historyItems:8, queryScript:DashboardInitializer.SHARE_OPERATIONAL_SIMPLE_SLD7, groupName_en:"Type of Equipment", groupName_fr:"Type of Equipment", groupQueryScript:DashboardInitializer.SHARE_OPERATIONAL_GROUP_SLD7,sqlQuery:false, active:true)
         
          then:
           category!=null
           
          !invalidIndicator.errors.hasFieldErrors("names_en")
          !invalidIndicator.errors.hasFieldErrors("names_fr")
          !invalidIndicator.errors.hasFieldErrors("descriptions_en")
          !invalidIndicator.errors.hasFieldErrors("descriptions_fr")
          !invalidIndicator.errors.hasFieldErrors("formula_en")
          !invalidIndicator.errors.hasFieldErrors("formula_fr")
          !invalidIndicator.errors.hasFieldErrors("groupQueryScript")
          
            assert !invalidIndicator.validate()
          
   }
   
   
    
}

