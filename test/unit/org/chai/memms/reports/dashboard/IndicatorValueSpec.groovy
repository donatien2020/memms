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
import grails.test.mixin.TestFor
import grails.plugin.spock.UnitSpec;
import org.chai.memms.reports.dashboard.Indicator
import org.chai.memms.reports.dashboard.LocationReport
import org.chai.memms.reports.dashboard.DashboardInitializer
import org.chai.memms.reports.dashboard.IndicatorCategory
import org.chai.memms.reports.dashboard.Indicator
import org.chai.memms.reports.dashboard.IndicatorValue
import org.chai.memms.reports.dashboard.MemmsReport
/**
 * @author Antoine Nzeyi, Donatien Masengesho, Pivot Access Ltd
 *
 */
@TestFor(IndicatorValue)
class IndicatorValueSpec  extends UnitSpec {
	
	  def "indicator Valueis valid"() {
          setup:
          mockForConstraintsTests(LocationReport)
          mockDomain(LocationReport)
          mockDomain(MemmsReport)
          mockDomain(IndicatorCategory)
          mockDomain(Indicator)
          mockDomain(IndicatorValue)
         
          when:
          Date currentDate=new Date()
            def memmsReport=new MemmsReport(generatedAt:currentDate).save(failOnError: true, flush:true)
            memmsReport.validate()
           
            def locationReport = new LocationReport(generatedAt: currentDate, memmsReport: memmsReport, location:null).save()
          
             def categoryy=new IndicatorCategory(code:DashboardInitializer.PRIVENTIVE_MAINTENANCE+"test two",names_en:"PRIVENTIVE_MAINTENANCE",names_fr:"PRIVENTIVE_MAINTENANCE",redToYellowThreshold:60,yellowToGreenThreshold:80).save(failOnError: true, flush:true)
         
        
          def indicator=new Indicator(category:categoryy, code:"SHARE_OPE_EQUIPMENT test", names_en:"Share of operational equipment",names_fr:"Share of operational equipment",descriptions_en:"Share of operational equipment",descriptions_fr:"Share of operational equipment", formula_en:"(total number equipment with STATUS=Operational) / by (total number equipment with STATUS={Operational Partially operational Under maintenance})", formula_fr:"(total number equipment with STATUS=Operational) / by (total number equipment with STATUS={Operational Partially operational Under maintenance})",unit:"%",redToYellowThreshold:0.8,yellowToGreenThreshold:0.9, historicalPeriod:Indicator.HistoricalPeriod.QUARTERLY, historyItems:8, queryScript:DashboardInitializer.SHARE_OPERATIONAL_SIMPLE_SLD7, groupName_en:"Type of Equipment", groupName_fr:"Type of Equipment", groupQueryScript:DashboardInitializer.SHARE_OPERATIONAL_GROUP_SLD7,sqlQuery:false, active:true).save(failOnError: true, flush:true)
	  
          indicator.validate()
           def indicatorValue=new IndicatorValue(computedAt: currentDate, locationReport: locationReport, indicator: indicator, computedValue:30.0).save()
             indicatorValue.validate()
          then:
           assert indicatorValue!=null
           
        
        // this indicator value will not persist  if i set the computed value to null
        
         when:
          def indicatorValueNot
          try{
              def indicatorValueNotInit=new IndicatorValue(computedAt: currentDate, locationReport: locationReport, indicator: indicator, computedValue:null)
             indicatorValueNot=indicatorValueNotInit.save()
           
        }catch(Exception e){}
          then:
           assert indicatorValueNot==null
 
   }
   
     // validation should fail
    
     def "indicator Value with null computedValue invalid and can not be saved"() {
          setup:
          mockForConstraintsTests(LocationReport)
          mockDomain(LocationReport)
          mockDomain(MemmsReport)
          mockDomain(IndicatorCategory)
          mockDomain(Indicator)
          mockDomain(IndicatorValue)
          when:
             Date currentDate=new Date()
            def memmsReport=new MemmsReport(generatedAt:currentDate).save(failOnError: true, flush:true)
            memmsReport.validate()
            def locationReport = new LocationReport(generatedAt: currentDate, memmsReport: memmsReport, location:null).save()
            def categoryy=new IndicatorCategory(code:DashboardInitializer.PRIVENTIVE_MAINTENANCE+"test two",names_en:"PRIVENTIVE_MAINTENANCE",names_fr:"PRIVENTIVE_MAINTENANCE",redToYellowThreshold:60,yellowToGreenThreshold:80).save(failOnError: true, flush:true)
            def indicator=new Indicator(category:categoryy, code:"SHARE_OPE_EQUIPMENT test", names_en:"Share of operational equipment",names_fr:"Share of operational equipment",descriptions_en:"Share of operational equipment",descriptions_fr:"Share of operational equipment", formula_en:"(total number equipment with STATUS=Operational) / by (total number equipment with STATUS={Operational Partially operational Under maintenance})", formula_fr:"(total number equipment with STATUS=Operational) / by (total number equipment with STATUS={Operational Partially operational Under maintenance})",unit:"%",redToYellowThreshold:0.8,yellowToGreenThreshold:0.9, historicalPeriod:Indicator.HistoricalPeriod.QUARTERLY, historyItems:8, queryScript:DashboardInitializer.SHARE_OPERATIONAL_SIMPLE_SLD7, groupName_en:"Type of Equipment", groupName_fr:"Type of Equipment", groupQueryScript:DashboardInitializer.SHARE_OPERATIONAL_GROUP_SLD7,sqlQuery:false, active:true).save(failOnError: true, flush:true)	  
            indicator.validate()
            def indicatorValue=new IndicatorValue(computedAt: currentDate, locationReport: locationReport, indicator: indicator, computedValue:null)
            then:
            assert  !indicatorValue.validate()
 
   }
    
}

