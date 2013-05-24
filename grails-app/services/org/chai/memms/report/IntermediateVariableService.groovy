package org.chai.memms.report

import java.util.List;
import java.util.Map;

import org.chai.location.DataLocation;
import org.chai.memms.util.Utils;
class IntermediateVariableService {
	static transactional = true

	def sessionFactory


	def dataSource  //Auto Injected


	public List<IntermediateVariable> intermediateVariableValueCalculator(DataLocation location){
		List<IntermediateVariable> computedIntermediateVariables=new ArrayList<IntermediateVariable>()

		def intermediateVariables=getIntermediateVariables()
		
		for(IntermediateVariable intermVar:intermediateVariables){
			intermVar.computedValue=computeIntermediateVariableValue(intermVar,location)
			computedIntermediateVariables.add(intermVar)
		}
		return computedIntermediateVariables
	}



	public int computeIntermediateVariableValue(IntermediateVariable IntermediateVariable,DataLocation location){


		int intermidiateValue=0

		try{

			def session = sessionFactory.getCurrentSession()

			int locationidentifier=location.id

			String validQueryLocation=IntermediateVariable.executableScript.replace('locationidentifier',""+locationidentifier+"")


			def query = session.createQuery(validQueryLocation)

			def results = query.list()

			intermidiateValue=results.size()

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace()
		}
		

		return intermidiateValue
	}


	public def searchIntermediateVariable(String text,Map<String, String> params) {
		text = text.trim()

		def criteria = IntermediateVariable.createCriteria()
		return criteria.list(offset:params.offset,max:params.max,sort:params.sort ?:"id",order: params.order ?:"desc"){

			ilike("code","%"+text+"%")
			ilike("indicatorName","%"+text+"%")
			ilike("indicatorType","%"+text+"%")
		}
	}
	def getIntermediateVariables(){
		return IntermediateVariable.findAll()
	}
	public void intermediateVariableWriter(){
		println"======================================started ok"
		if(!IntermediateVariable.count()){
			println"======================================started ok1"
			String intermediateVariableFileContent = new File('web-app/resources/reportData/intermidiateVariables.xml').text
			println"======================================started ok2"
			def intermediateVariables = new XmlParser().parseText(intermediateVariableFileContent)
			intermediateVariables.intermediateVariable.each{
				println"======================================started ok3"
				Map<String,String> names=new HashMap<String,String>()
				names.put('en', it.name.text())
				names.put('fr', it.name.text())
				newIntermediateVariable(names,it.attribute("intermediateVariableCode"),it.executableScript.text(),it.computedValue.text())
			}
		}else{
			println"============================================;cant read it"
		}
	}
	public  void newIntermediateVariable(Map<String,String> names,def code,def executableScript,def computedValue){
		try{
			def intermediateVariable = new IntermediateVariable(code:code,executableScript:executableScript,computedValue:computedValue)
			Utils.setLocaleValueInMap(intermediateVariable,names,"Names")
			intermediateVariable.save(failOnError:true,flush:true)
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace()

		}
	}
}
