package com.birn.restful;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Assessment")
public class Assessment {
	int id;
	String name;
	String description;
	String scoreName;
	int scoreSequence;
	int scoreLevel;
	String scoreDescription;
	String itemLeadingText;	
	String scoreType;
	
	@XmlAttribute
	public int getId(){
		return this.id;
	}
	public void setId(int value){
		this.id = value;
	}
	
	@XmlElement
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	@XmlElement
	public String getDescription(){
		return this.description;
	}
	public void setDescription(String value){
		this.description = value;
	}
	
	@XmlElement
	public String getScoreName(){
		return this.scoreName;
	}
	public void setScoreName(String value){
		this.scoreName = value;
	}
	
	@XmlElement
	public int getScoreSequence(){
		return this.scoreSequence;
	}
	public void setScoreSequence(int value){
		this.scoreSequence = value;
	}
	
	@XmlElement
	public int getScoreLevel(){
		return this.scoreLevel;
	}
	public void setScoreLevel(int value){
		this.scoreLevel = value;
	}
	
	@XmlElement
	public String getScoreDescription(){
		return this.scoreDescription;
	}
	public void setScoreDescription(String value){
		this.scoreDescription = value;
	}
	
	@XmlElement
	public String getItemLeadingText(){
		return this.itemLeadingText;
	}
	public void setItemLeadingText(String value){
		this.itemLeadingText = value;
	}
	
	@XmlElement
	public String getScoreType(){
		return this.scoreType;
	}
	public void setScoreType(String value){
		this.scoreType = value;
	}
}
