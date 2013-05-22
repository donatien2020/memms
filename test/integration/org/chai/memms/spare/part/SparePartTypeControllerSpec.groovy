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
package org.chai.memms.spare.part

import org.chai.memms.Initializer;
import org.chai.memms.IntegrationTests;
import org.chai.memms.spare.part.SparePartTypeController;
import org.chai.memms.spare.part.SparePartType;
import org.chai.memms.inventory.Provider.Type;
import java.util.Date;
/**
 * @author Alain Inema
 *
 */
class SparePartTypeControllerSpec extends IntegrationTests {
	
  def sparePartTypeController
  def sparePartTypeService
  
//working
 def "can create and save a spare part type with default values - all locales"(){
	 setup:
	sparePartTypeController = new SparePartTypeController()
	def manufactureContact = Initializer.newContact(['en':'Address Descriptions '],"Manufacture","jkl@yahoo.com","0768-889-787","Street 154","6353")
	def manufacturer = Initializer.newProvider("TEST" + CODE(1), Type.MANUFACTURER,manufactureContact)
	
	when:
	sparePartTypeController.params.code = CODE(123)	
	grailsApplication.config.i18nFields.locales.each{
		sparePartTypeController.params."names_$it" = "Spare part name $it"
		sparePartTypeController.params."descriptions_$it" = "some kind of description $it"
	}
	sparePartTypeController.params.partNumber="7654-HGT"
	sparePartTypeController.params."manufacturer.id"=manufacturer.id
	sparePartTypeController.save()
	
	then:
	SparePartType.count() == 1
	SparePartType.list()[0].code.equals(CODE(123))
}
def "list spare part types"(){
	setup:
	sparePartTypeController = new SparePartTypeController();
	def manufactureContact = Initializer.newContact(['en':'Address Descriptions '],"Manufacture","jkl@yahoo.com","0768-889-787","Street 154","6353")
	def manufacturer = Initializer.newProvider("TEST" + CODE(1), Type.MANUFACTURER,manufactureContact)
	Initializer.newSparePartType(CODE(123),["en":"names spare part type one"],["en":"descriptions spare part type one"],"7654-HGT",manufacturer,new Date())
	Initializer.newSparePartType(CODE(124),["en":"names spare part type two"],["en":"descriptions spare part type two"],"7655-HGT",manufacturer,new Date())
	Initializer.newSparePartType(CODE(125),["en":"names spare part type three"],["en":"descriptions spare part type three"],"7656-HGT",manufacturer,new Date())
	Initializer.newSparePartType(CODE(126),["en":"names spare part type four"],["en":"descriptions spare part type four"],"7657-HGT",manufacturer,new Date())
	
	when: "none ajax"
	sparePartTypeController.list()
	
	then:
	SparePartType.count() == 4
	sparePartTypeController.modelAndView.model.entities.size() == 4
	
	when: "with ajax"
	
	sparePartTypeController.request.makeAjaxRequest()
	sparePartTypeController.list()
	
	then:
	SparePartType.count() == 4
	sparePartTypeController.response.json.results[0].contains("7654-HGT")
	sparePartTypeController.response.json.results[0].contains("7655-HGT")
	sparePartTypeController.response.json.results[0].contains("7656-HGT")
	sparePartTypeController.response.json.results[0].contains("7657-HGT")
}

def "search spare part types"(){
	setup:
	sparePartTypeController = new SparePartTypeController();
	def manufactureContact = Initializer.newContact(['en':'Address Descriptions '],"Manufacture","jkl@yahoo.com","0768-889-787","Street 154","6353")
	def manufacturer = Initializer.newProvider("TEST" + CODE(1), Type.MANUFACTURER,manufactureContact)
	Initializer.newSparePartType(CODE(123),["en":"names spare part type one"],["en":"descriptions spare part type one"],"7654-HGT",manufacturer,new Date())
	Initializer.newSparePartType(CODE(124),["en":"names spare part type two"],["en":"descriptions spare part type two"],"7655-HGT",manufacturer,new Date())
	Initializer.newSparePartType(CODE(125),["en":"names spare part type three"],["en":"descriptions spare part type three"],"7656-HGT",manufacturer,new Date())
	Initializer.newSparePartType(CODE(126),["en":"names spare part type four"],["en":"descriptions spare part type four"],"7657-HGT",manufacturer,new Date())
	
	when: "none ajax fails"
	sparePartTypeController.params.q = "three"
	sparePartTypeController.search()
	
	then:
	SparePartType.count() == 4
	sparePartTypeController.modelAndView.model.entities == [SparePartType.findByCode(CODE(125))]
	
	when: "with ajax"
	sparePartTypeController.params.q = "three"
	sparePartTypeController.request.makeAjaxRequest()
	sparePartTypeController.search()
	
	then:
	SparePartType.count() == 4
	sparePartTypeController.modelAndView.model.entities.size() == 1 
	sparePartTypeController.response.json.results[0].contains("7656-HGT")
	!sparePartTypeController.response.json.results[0].contains("7657-HGT")
	!sparePartTypeController.response.json.results[0].contains("7655-HGT")
	!sparePartTypeController.response.json.results[0].contains("7654-HGT")
}

}

