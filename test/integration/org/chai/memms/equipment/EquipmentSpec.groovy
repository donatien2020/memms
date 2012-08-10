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

package org.chai.memms.equipment

import org.chai.memms.Initializer;
import org.chai.memms.IntegrationTests;
import org.chai.memms.equipment.EquipmentStatus.Status;
import org.chai.memms.equipment.EquipmentType.Observation;
import org.chai.memms.location.DataLocation;
import org.chai.memms.security.User;
import org.chai.memms.IntegrationTests
import org.chai.memms.location.DataLocation;
/**
 * @author Jean Kahigiso M.
 *
 */
class EquipmentSpec extends IntegrationTests{

	def "can create and save an equipment"() {

		setup:
		setupLocationTree()
		def department = Initializer.newDepartment(['en':"testName"], CODE(123),['en':"testDescription"])
		def equipmentModel = Initializer.newEquipmentModel(['en':"testName"], CODE(123),['en':"testDescription"])
		def equipmentType = Initializer.newEquipmentType(CODE(15810),["en":"Accelerometers"],["en":"used in memms"],Observation.USEDINMEMMS,Initializer.now(),Initializer.now())
		when:
		def equipment = new Equipment(serialNumber:"test123", purchaseCost:"1,200",manufactureDate:Initializer.getDate(22,07,2010),purchaseDate:Initializer.getDate(22,07,2010),
				registeredOn:Initializer.getDate(22,07,2010), model:equipmentModel, department:department, dataLocation:DataLocation.list().first(),
				observations:['en':'Equipment Observation'],descriptions:['en':'Equipment Descriptions'], type:equipmentType)

		def manufacture = Initializer.newContact(['en':'Address Descriptions '],"Manufacture","jkl@yahoo.com","0768-889-787","Street 154","6353")
		def supplier = Initializer.newContact([:],"Supplier","jk@yahoo.com","0768-888-787","Street 1654","6353")
		def contact = Initializer.newContact([:],"Contact","jk@yahoo.com","0768-888-787","Street 654","6353")
		def warranty = Initializer.newWarranty("Code",['en':'warranty'],'warranty name','email@gmail.com',"0768-889-787","Street 154",Initializer.getDate(10, 12, 2010),Initializer.getDate(12, 12, 2012),[:],equipment)

		warranty.contact=contact
		equipment.manufacture=manufacture
		equipment.supplier=supplier
		equipment.warranty=warranty

		equipment.save(failOnError: true)
		then:
		Equipment.count() == 1
	}

	def "can't create and save an equipment without a serial number"() {

		setup:
		setupLocationTree()
		def department = Initializer.newDepartment(['en':"testName"], CODE(123),['en':"testDescription"])
		def equipmentModel = Initializer.newEquipmentModel(['en':"testName"], CODE(123),['en':"testDescription"])
		def equipmentType = Initializer.newEquipmentType(CODE(15810), ["en":"Accelerometers"],["en":"used in memms"],Observation.USEDINMEMMS,Initializer.now(),Initializer.now())
		when:
		def equipment = new Equipment(purchaseCost:"1,200",manufactureDate:Initializer.getDate(22,07,2010),purchaseDate:Initializer.getDate(22,07,2010),
				registeredOn:Initializer.getDate(22,07,2010), model:equipmentModel, department:department, dataLocation:DataLocation.list().first(),
				observations:['en':'Equipment Observation'],descriptions:['en':'Equipment Descriptions'], type:equipmentType)

		def manufacture = Initializer.newContact(['en':'Address Descriptions '],"Manufacture","jkl@yahoo.com","0768-889-787","Street 154","6353")
		def supplier = Initializer.newContact([:],"Supplier","jk@yahoo.com","0768-888-787","Street 1654","6353")
		def contact = Initializer.newContact([:],"Contact","jk@yahoo.com","0768-888-787","Street 654","6353")
		def warranty = Initializer.newWarranty("Code",['en':'warranty'],'warranty name','email@gmail.com',"0768-889-787","Street 154",Initializer.getDate(10, 12, 2010),Initializer.getDate(12, 12, 2012),[:],equipment)

		warranty.contact=contact
		equipment.manufacture=manufacture
		equipment.supplier=supplier
		equipment.warranty=warranty

		equipment.save()
		then:
		Equipment.count() == 0
		equipment.errors.hasFieldErrors('serialNumber') == true
	}

	def "can't create and save an equipment without a purchase cost"() {

		setup:
		setupLocationTree()
		def department = Initializer.newDepartment(['en':"testName"], CODE(123),['en':"testDescription"])
		def equipmentModel = Initializer.newEquipmentModel(['en':"testName"], CODE(123),['en':"testDescription"])
		def equipmentType = Initializer.newEquipmentType(CODE(15810), ["en":"Accelerometers"],["en":"used in memms"],Observation.USEDINMEMMS,Initializer.now(),Initializer.now())
		when:
		def equipment = new Equipment(serialNumber:"test123",manufactureDate:Initializer.getDate(22,07,2010),purchaseDate:Initializer.getDate(22,07,2010),
				registeredOn:Initializer.getDate(22,07,2010), model:equipmentModel, department:department, dataLocation:DataLocation.list().first(),
				observations:['en':'Equipment Observation'],descriptions:['en':'Equipment Descriptions'],type:equipmentType)

		def manufacture = Initializer.newContact(['en':'Address Descriptions '],"Manufacture","jkl@yahoo.com","0768-889-787","Street 154","6353")
		def supplier = Initializer.newContact([:],"Supplier","jk@yahoo.com","0768-888-787","Street 1654","6353")
		def contact = Initializer.newContact([:],"Contact","jk@yahoo.com","0768-888-787","Street 654","6353")
		def warranty = Initializer.newWarranty("Code",['en':'warranty'],'warranty name','email@gmail.com',"0768-889-787","Street 154",Initializer.getDate(10, 12, 2010),Initializer.getDate(12, 12, 2012),[:],equipment)

		warranty.contact=contact
		equipment.manufacture=manufacture
		equipment.supplier=supplier
		equipment.warranty=warranty

		equipment.save()
		then:
		Equipment.count() == 0
		equipment.errors.hasFieldErrors('purchaseCost') == true
	}

	def "can't create and save an equipment with a duplicate serial number"() {

		setup:
		setupLocationTree()
		def department = Initializer.newDepartment(['en':"testName"], CODE(123),['en':"testDescription"])
		def equipmentModel = Initializer.newEquipmentModel(['en':"testName"], CODE(123),['en':"testDescription"])
		def equipmentType = Initializer.newEquipmentType(CODE(15810), ["en":"Accelerometers"],["en":"used in memms"],Observation.USEDINMEMMS,Initializer.now(),Initializer.now())
		Initializer.newEquipment("test123","3,600",['en':"testDescription"],['en':'Equipment Observation'],Initializer.getDate(22,07,2010), Initializer.getDate(22,07,2010),
				Initializer.getDate(22,07,2010),equipmentModel,DataLocation.list().first(),department, equipmentType)
		when:
		def equipment = new Equipment(serialNumber:"test123",purchaseCost:"1,200",manufactureDate:Initializer.getDate(22,07,2010),purchaseDate:Initializer.getDate(22,07,2010),
				registeredOn:Initializer.getDate(22,07,2010), model:equipmentModel, department:department, dataLocation:DataLocation.list().first(),
				observations:['en':'Equipment Observation'],descriptions:['en':'Equipment Descriptions'],type:equipmentType)

		def manufacture = Initializer.newContact(['en':'Address Descriptions '],"Manufacture","jkl@yahoo.com","0768-889-787","Street 154","6353")
		def supplier = Initializer.newContact([:],"Supplier","jk@yahoo.com","0768-888-787","Street 1654","6353")
		def contact = Initializer.newContact([:],"Contact","jk@yahoo.com","0768-888-787","Street 654","6353")
		def warranty = Initializer.newWarranty("Code",['en':'warranty'],'warranty name','email@gmail.com',"0768-889-787","Street 154",Initializer.getDate(10, 12, 2010),Initializer.getDate(12, 12, 2012),[:],equipment)

		warranty.contact=contact
		equipment.manufacture=manufacture
		equipment.supplier=supplier
		equipment.warranty=warranty

		equipment.save()
		then:
		Equipment.count() == 1
		equipment.errors.hasFieldErrors('serialNumber') == true
	}

	def "manufacture date must be after today"() {

		setup:
		setupLocationTree()
		def department = Initializer.newDepartment(['en':"testName"], CODE(123),['en':"testDescription"])
		def equipmentModel = Initializer.newEquipmentModel(['en':"testName"], CODE(123),['en':"testDescription"])
		def equipmentType = Initializer.newEquipmentType("15810", ["en":"Accelerometers"],["en":"used in memms"],Observation.USEDINMEMMS,Initializer.now(),Initializer.now())
		when:
		def equipment = new Equipment(serialNumber:"test123", purchaseCost:"1,200",manufactureDate:Initializer.now().next(),purchaseDate:Initializer.getDate(22,07,2010),
				registeredOn:Initializer.getDate(22,07,2010), model:equipmentModel, department:department, dataLocation:DataLocation.list().first(),
				observations:['en':'Equipment Observation'],descriptions:['en':'Equipment Descriptions'],type:equipmentType)

		def manufacture = Initializer.newContact(['en':'Address Descriptions '],"Manufacture","jkl@yahoo.com","0768-889-787","Street 154","6353")
		def supplier = Initializer.newContact([:],"Supplier","jk@yahoo.com","0768-888-787","Street 1654","6353")
		def contact = Initializer.newContact([:],"Contact","jk@yahoo.com","0768-888-787","Street 654","6353")
		def warranty = Initializer.newWarranty("Code",['en':'warranty'],'warranty name','email@gmail.com',"0768-889-787","Street 154",Initializer.getDate(10, 12, 2010),Initializer.getDate(12, 12, 2012),[:],equipment)

		warranty.contact=contact
		equipment.manufacture=manufacture
		equipment.supplier=supplier
		equipment.warranty=warranty

		equipment.save()
		then:
		Equipment.count() == 0
		equipment.errors.hasFieldErrors('manufactureDate') == true
	}

	def "purchase date must be after today"() {

		setup:
		setupLocationTree()
		def department = Initializer.newDepartment(['en':"testName"], CODE(123),['en':"testDescription"])
		def equipmentModel = Initializer.newEquipmentModel(['en':"testName"], CODE(123),['en':"testDescription"])
		def equipmentType = Initializer.newEquipmentType("15810", ["en":"Accelerometers"],["en":"used in memms"],Observation.USEDINMEMMS,Initializer.now(),Initializer.now())
		when:
		def equipment = new Equipment(serialNumber:"test123", purchaseCost:"1,200",manufactureDate:Initializer.getDate(22,07,2010),purchaseDate:Initializer.now().next(),
				registeredOn:Initializer.getDate(22,07,2010), model:equipmentModel, department:department, dataLocation:DataLocation.list().first(),
				observations:['en':'Equipment Observation'],descriptions:['en':'Equipment Descriptions'],type:equipmentType)

		def manufacture = Initializer.newContact(['en':'Address Descriptions '],"Manufacture","jkl@yahoo.com","0768-889-787","Street 154","6353")
		def supplier = Initializer.newContact([:],"Supplier","jk@yahoo.com","0768-888-787","Street 1654","6353")
		def contact = Initializer.newContact([:],"Contact","jk@yahoo.com","0768-888-787","Street 654","6353")
		def warranty = Initializer.newWarranty("Code",['en':'warranty'],'warranty name','email@gmail.com',"0768-889-787","Street 154",Initializer.getDate(10, 12, 2010),Initializer.getDate(12, 12, 2012),[:],equipment)

		warranty.contact=contact
		equipment.manufacture=manufacture
		equipment.supplier=supplier
		equipment.warranty=warranty

		equipment.save()
		then:
		Equipment.count() == 0
		equipment.errors.hasFieldErrors('purchaseDate') == true
	}
}
