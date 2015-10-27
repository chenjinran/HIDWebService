package com.birn.restful;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="experiment")
public class Experiment{
	int id;
	String name;
	String description;
	String baseuri;
	String storagetype;
	
	@XmlAttribute
	public int getId(){
		return id;
	}
	public void setId(int value){
		this.id = value;
	}
	
	@XmlElement
	public String getName(){
		return this.name;
	}
	public void setName(String value){
		this.name = value;
	}
	
	@XmlElement
	public String getDescription(){
		return this.description;
	}
	public void setDescription(String value){
		this.description = value;
	}
	
	@XmlElement
	public String getStoragetype(){
		return this.storagetype;
	}
	public void setStoragetype(String value){
		this.storagetype = value;
	}
	
	@XmlElement
	public String getBaseuri(){
		return this.baseuri;
	}
	public void setBaseuri(String value){
		this.baseuri = value;
	}
}
