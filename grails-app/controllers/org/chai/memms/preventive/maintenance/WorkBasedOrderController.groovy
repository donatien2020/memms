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
package org.chai.memms.preventive.maintenance

import org.chai.location.CalculationLocation;
import org.chai.location.DataLocation;
import org.chai.memms.AbstractEntityController;
import org.chai.memms.inventory.Equipment;
import org.chai.memms.security.User.UserType;
import org.chai.memms.preventive.maintenance.PreventiveOrder.PreventiveOrderType
import org.chai.memms.preventive.maintenance.PreventiveOrder.PreventiveOrderStatus

/**
 * @author Jean Kahigiso M.
 *
 */
class WorkBasedOrderController extends AbstractEntityController {
	
	def userService

	def bindParams(def entity) {
		def oldStatus = entity.status
		if(!entity.id){
			entity.addedBy = user
			entity.type = PreventiveOrderType.WORKBASED
			entity.status = PreventiveOrderStatus.OPEN
		}else{
			entity.lastModifiedBy = user
			if(!params['preventionResponsible'].equals('HCTECHNICIAN')){
				params['technicianInCharge.id'] = ''
				entity.technicianInCharge = null
			}

		}
		entity.properties = params
	}

	def getModel(def entity) {
		def equipments =  []
		def dataLocation = DataLocation.get(params.long("dataLocation.id"))
		dataLocation = (dataLocation)?dataLocation:entity.equipment?.dataLocation
		def usersInCharge = userService.getActiveUserByTypeAndLocation([UserType.HOSPITALDEPARTMENT,UserType.TITULAIREHC,UserType.TECHNICIANDH],dataLocation,['sort':'firstname'])
		if(entity.technicianInCharge != null) usersInCharge.add(entity.technicianInCharge)
		if(entity.equipment) equipments << entity.equipment

		[
			order:entity,
			equipments: equipments,
			dataLocation: dataLocation,
			currencies: grailsApplication.config.site.possible.currency,
			technicians : usersInCharge
		]
	}

	def getEntity(def id) {
		return WorkBasedOrder.get(id);
	}

	def createEntity() {
		return new WorkBasedOrder();
	}

	def getTemplate() {
		return "/entity/preventiveOrder/createWorkBasedOrder";
	}

	def getLabel() {
		return "preventive.order.label";
	}

	def getEntityClass() {
		return WorkBasedOrder.class;
	}
	
}
