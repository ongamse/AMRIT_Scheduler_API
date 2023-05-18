package com.iemr.tm.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StringListConverter implements AttributeConverter<List<Integer>, String> {

  @Override
  public String convertToDatabaseColumn(List<Integer> list) {
    // Java 8
	  StringBuilder strbul  = new StringBuilder();
	  if(list!=null && list.size()>0){
		  Iterator<Integer> iter = list.iterator();
		     while(iter.hasNext())
		     {
		         strbul.append(iter.next());
		        if(iter.hasNext()){
		         strbul.append(",");
		        }
		     } 
	  }else{
		  return null;
	  }
	     
	 return strbul.toString();
//    return String.join(",", list);
  }

  @Override
  public List<Integer> convertToEntityAttribute(String joined) {
	  List<Integer> list=new ArrayList();
	  if(joined!=null){
		  String[] strArray = joined.split(",");
		  for(int i = 0; i < strArray.length; i++) {
			  list.add( Integer.parseInt(strArray[i]));
		  }  
	  }
	  
    return list;
  }

}