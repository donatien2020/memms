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
package org.chai.memms.location;

import org.chai.location.DataLocation;
import org.chai.location.DataLocationType;
import org.chai.memms.AbstractEntityController;

class DataLocationTypeController extends AbstractEntityController {

	def bindParams(def entity) {
		entity.properties = params
		
		if (params.names!=null) entity.names = params.names
	}

	def getModel(def entity) {
		[dataLocationType: entity]
	}

	def getEntityClass(){
		return DataLocationType.class;
	
	}
	
	def getEntity(def id) {
		return DataLocationType.get(id);
	}

	def createEntity() {
		return new DataLocationType();
	}
	
	def deleteEntity(def entity) {
		if (DataLocation.findAllByType(entity).size() != 0) {
			flash.message = message(code: 'dataLocation.type.hasentities', args: [message(code: getLabel(), default: 'entity'), params.id], default: 'Type {0} still has associated entities.')
		}
		else {
			super.deleteEntity(entity)
		}
	}
	
	def getTemplate() {
		return '/entity/location/createDataLocationType'
	}

	def getLabel() {
		return 'datalocation.type.label';
	}
	
	def list = {
		adaptParamsForList()
		List<DataLocationType> types = DataLocationType.list(params);
		if(request.xhr){
			this.ajaxModel(types)
		}else{
			render (view: '/entity/list', model:[
				template:"location/dataLocationTypeList",
				listTop:"location/dataLocationTypeListTop",
				entities: types,
				entityCount: types.totalCount,
				code: getLabel(),
				names:names
			])
		}
	}
	def ajaxModel(def entities) {
		def model = [entities: entities,entityCount: entities.totalCount,names:names]
		def listHtml = g.render(template:"/entity/location/dataLocationTypeList",model:model)
		render(contentType:"text/json") { results = [listHtml] }
	}
}
