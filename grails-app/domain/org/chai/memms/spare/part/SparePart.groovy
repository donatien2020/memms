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

import java.util.Date;

import groovy.transform.EqualsAndHashCode;

import javax.persistence.Transient;

import org.chai.location.DataLocation;
import org.chai.memms.Period;
import org.chai.memms.Warranty;
import org.chai.memms.inventory.Equipment;
import org.chai.memms.inventory.Provider;
import org.chai.memms.security.User;
import org.chai.memms.spare.part.SparePartStatus.Status;

/**
 * @author Jean Kahigiso M.
 *
 */
@i18nfields.I18nFields
@EqualsAndHashCode(includes="code")
class SparePart {
	
	enum StockLocation{
		
		NONE('none'),
		MMC("mmc"),
		FACILITY("facility")
		
		String messageCode = "stock.location"
		final String name
		StockLocation(String name){ this.name=name }
		String getKey() { return name() }
	}
	
	String code
	String names
	String descriptions
	String currency
	String serialNumber
	
	Double purchaseCost
	Period warrantyPeriod
	
	Equipment usedOnEquipment
	Boolean sameAsManufacturer = false
	Provider supplier
	Warranty warranty
	
	Date purchaseDate
	Date dateCreated
	Date lastUpdated
	
	StockLocation stockLocation
	DataLocation location
	Status currentStatus
	
	User addedBy
	User lastModifiedBy
	
	static belongsTo = [type: SparePartType]
	static hasMany = [status: SparePartStatus]	
	static i18nFields = ["descriptions","names"]
	static embedded = ["warranty","warrantyPeriod"]
	
	static constraints = {
		code nullable: false, unique :true
		serialNumber nullable: true, validator: { val, obj ->
			if(!obj.currentStatus.equals(Status.PENDINGORDER)) return (val!=null)
		}
		purchaseDate nullable: true
		purchaseCost nullable: true, validator: {val, obj ->
			if(obj.currency) return (val==null)
		}
		currency nullable: true, validator:{
			if(obj.purchaseCost) return (val==null)
		}
		stockLocation  nullable: false, inList:[StockLocation.MMC, StockLocation.FACILITY]
		location nullable: true, validator: {val,obj ->
			if(obj.stockLocation.equals(StockLocation.FACILITY)) return (val==null)
		}
	}
	
	static mapping = {
		table "memms_spare_part"
		version false
		cache true
	}
	
	@Transient
	def getTimeBasedStatus(){
		if(!status) return null
		SparePartStatus currentState = status.asList()[0]
		for(SparePartStatus state : status){
			currentState.dateOfEvent.clearTime()
			state.dateOfEvent.clearTime()
			if(state.dateOfEvent.after(currentState.dateOfEvent)){
				currentState= state;
			}
			if(state.dateOfEvent.compareTo(currentState.dateOfEvent)==0){
				if(state.dateCreated.after(currentState.dateCreated))
					currentState = state
				if(state.dateCreated.compareTo(currentState.dateCreated)==0)
					currentState = (currentState.id > state.id)?currentState:state
			}
			
		}
		return currentState
	}
	def beforeUpdate(){
		dateUpdated = new Date()
	}
	
	def beforeValidate(){
		this.getGenerateAndSetCode()
	}
	
	@Transient
	def getGenerateAndSetCode(){
		if(!code){
			def randomInt = RandomUtils.nextInt(99999)
			def now = new Date()
			def sparePartCode = "${dataLocation.code}-${randomInt}-${now.month}-${now.year+1900}"
			if(log.isDebugEnabled()) log.debug("Generated code:" + sparePartCode)
			if(SparePart.findByCode(sparePartCode.toString()) == null) code = sparePartCode
			else getGenerateAndSetCode()
		}
	}
	
	String toString() {
		return "SparePart [id= " + id + " code= "+code+" serialNumber= "+serialNumber+" currentState= "+currentStatus+"]";

	}
}
