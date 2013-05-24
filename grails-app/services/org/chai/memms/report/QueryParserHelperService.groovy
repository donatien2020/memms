package org.chai.memms.report

import java.util.Map;

class QueryParserHelperService {



	public void addQueryParser(String executableScript,String classDomaine,Boolean useCountFunction,String followOperand, Boolean isDenominator, Boolean isIntermidiateVariable,Boolean isDynamicFinder,Indicator indicator,String type,IntermediateVariable intermediateVariable){
		def queryParserHelper=new QueryParserHelper(executableScript:executableScript,classDomaine:classDomaine
		,useCountFunction:useCountFunction
		,followOperand:followOperand
		, isDenominator:isDenominator
		, isIntermidiateVariable:isIntermidiateVariable
		,isDynamicFinder:it.attribute("isDynamicFinder"),indicator:indicatorr,type:indTypeValue,intermediateVariable:intermediateVariable)
		queryParserHelper.save(failOnError: true)
	}

	public def getQueryParserHelpers(){
		return QueryParserHelper.findAll()
	}
	public def searchQueryParserHelper(String text,Map<String, String> params) {
		text = text.trim()

		def criteria = QueryParserHelper.createCriteria()
		return criteria.list(offset:params.offset,max:params.max,sort:params.sort ?:"id",order: params.order ?:"desc"){

			ilike("code","%"+text+"%")
			ilike("classDomaine","%"+text+"%")
			ilike("executableScript","%"+text+"%")
			ilike("type","%"+text+"%")
		}
	}
}
