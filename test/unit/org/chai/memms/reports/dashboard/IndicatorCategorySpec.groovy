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
import grails.plugin.spock.UnitSpec;
import org.chai.memms.reports.dashboard.IndicatorCategory
import grails.test.mixin.TestFor
import grails.plugin.spock.UnitSpec;
import org.chai.memms.reports.dashboard.DashboardInitializer
import grails.plugin.spock.UnitSpec;
/**
 * @author Antoine Nzeyi, Donatien Masengesho, Pivot Access Ltd
 *
 */
@TestFor(IndicatorCategory)

class IndicatorCategorySpec extends UnitSpec {
	 def "indicator category is valid"() {
          setup:
          mockForConstraintsTests(IndicatorCategory)
          mockDomain(IndicatorCategory)

          when:
          def code=DashboardInitializer.CORRECTIVE_MAINTENANCE+"TEST"
          def category=new IndicatorCategory(code:code,names_en:"Corrective maintenance",redToYellowThreshold:60,yellowToGreenThreshold:80).save(failOnError: true, flush:true)
          
          then:
          assert(category.validate())
          !category.errors.hasFieldErrors("names_en")
          !category.errors.hasFieldErrors("names_fr")
          !category.errors.hasFieldErrors("redToYellowThreshold")
          !category.errors.hasFieldErrors("yellowToGreenThreshold")
          assert category!=null
       

   }
   // category.validate() should fail
    def "indicator category  with null code is inavlid"(){
          setup:
          mockForConstraintsTests(IndicatorCategory)
          mockDomain(IndicatorCategory)

          when:
         
          def category=new IndicatorCategory(code:null,names_en:"Corrective maintenance",redToYellowThreshold:60,yellowToGreenThreshold:80)
          then:
          assert !category.validate()
          assert category.errors.hasFieldErrors("code")
          assert !category.errors.hasFieldErrors("names_en")
          assert !category.errors.hasFieldErrors("names_fr")
          assert  !category.errors.hasFieldErrors("redToYellowThreshold")
          assert  !category.errors.hasFieldErrors("yellowToGreenThreshold")
         
         
   }
    
}

