package com.birn.restful;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.birn.restful.Experiment;

@Path("/query")
public class queryService {
	
	Connection con = null;
	Statement st = null;
	
	@GET
	@Path("/GetAllExperiments")
	@Produces("application/xml")
	public LinkedList<Experiment> getAllExperiments() throws Exception {
		
		LinkedList<Experiment> exps = null;
		
		try{
			con = dbUtil.connectBirnDb();			
			exps = new LinkedList<Experiment>();			
			st = con.createStatement();
			ResultSet rs = st.executeQuery("Select uniqueid, name, description, storagetype, baseuri from nc_experiment");
			
			while(rs.next()){
				Experiment exp = new Experiment();
				exp.setId(rs.getInt("uniqueid"));
				exp.setName(rs.getString("name"));
				exp.setStoragetype(rs.getString("storagetype"));
				exp.setDescription(rs.getString("description"));
				exp.setBaseuri(rs.getString("baseuri"));
				exps.add(exp);
			}
			
			return exps;
			
		}catch(SQLException ex){			
			ex.printStackTrace();			
		}
		finally{
			try{
				if(con!=null && !con.isClosed()){
					con.close();
				}
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}		
		return exps;		
	}
	
	@GET
	@Path("/GetExperimentByName/{name}")
	@Produces(MediaType.APPLICATION_XML)
	public Experiment getExpByName(@PathParam("name") String name) throws Exception{
		Experiment exp = new Experiment();
		
		try{			
			con = dbUtil.connectBirnDb();
			st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * from nc_experiment Where name = '" + name + "'");
			
			while(rs.next()){
				exp.setBaseuri(rs.getString("baseuri"));
				exp.setDescription(rs.getString("description"));
				exp.setId(rs.getInt("uniqueid"));
				exp.setName(rs.getString("name"));
				exp.setStoragetype(rs.getString("storagetype"));				
			}
			
			return exp;
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return exp;
		
	}
	
	@GET
	@Path("/GetExperimentById/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Experiment getExpById(@PathParam("id") String expId) throws Exception{
		Experiment exp = new Experiment();
		
		try{			
			con = dbUtil.connectBirnDb();
			st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * from nc_experiment Where uniqueid = " + expId);
			
			while(rs.next()){
				exp.setBaseuri(rs.getString("baseuri"));
				exp.setDescription(rs.getString("description"));
				exp.setId(rs.getInt("uniqueid"));
				exp.setName(rs.getString("name"));
				exp.setStoragetype(rs.getString("storagetype"));				
			}
			
			return exp;
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return exp;
		
	}
	
//	@GET
//	@Path("/GetAllExperiments/{username}/{pwd}")
//	@Produces(MediaType.APPLICATION_XML)
//	public Experiment getExpAuthen(@PathParam("username") String username, @PathParam("pwd") String pwd){
//		Experiment exp = new Experiment();
//		
//		try{			
//			con = dbUtil.connectOtsukaDb(username, pwd);
//			st = con.createStatement();
//			ResultSet rs = st.executeQuery("Select * from nc_experiment");
//			
//			while(rs.next()){
//				exp.setBaseuri(rs.getString("baseuri"));
//				exp.setDescription(rs.getString("description"));
//				exp.setId(rs.getInt("uniqueid"));
//				exp.setName(rs.getString("name"));
//				exp.setStoragetype(rs.getString("storagetype"));				
//			}
//			
//			return exp;
//			
//		}catch(SQLException e){
//			e.printStackTrace();
//		}
//		
//		return exp;
//		
//	}

	@GET
	@Path("/GetAllAssessments")
	@Produces(MediaType.APPLICATION_XML)
	public LinkedList<Assessment> getAllAssessments() throws Exception{
		LinkedList<Assessment> assessments = new LinkedList<Assessment>();
		
		try{			
			con = dbUtil.connectBirnDb();
			st = con.createStatement();
			String sql = " SELECT a.name, a.assessmentid, a.description, " +
					 "		 s.scorename, s.scoresequence, s.scoretype, s.scorelevel,s.description as scoredescription, " +
					 "		 i.itemleadingtext as leadingtext " +
					 " FROM nc_Assessment as a,	nc_Assessmentscore as s " +
					 " LEFT OUTER JOIN nc_assessmentitem as i " +
					 " ON s.assessmentid=i.assessmentid and s.scorename=i.scorename " +
					 " WHERE a.assessmentid = s.assessmentid " +
					 "		AND a.assessmentid IN " +
					 "			( SELECT DISTINCT assessmentid FROM nc_Assessmentdata " +
					 "			  WHERE nc_storedassessment_uniqueid IN " +
					 "			(SELECT uniqueid FROM nc_Storedassessment " +
					 "				 WHERE nc_experiment_uniqueid IN " +
					 "				(SELECT uniqueid FROM nc_Experiment)))";
 
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()){
				Assessment ass = new Assessment();
				ass.setDescription(rs.getString("description"));
				ass.setId(rs.getInt("assessmentid"));
				ass.setItemLeadingText(rs.getString("leadingtext"));
				ass.setName(rs.getString("name"));
				ass.setScoreDescription(rs.getString("scoredescription"));
				ass.setScoreLevel(rs.getInt("scorelevel"));
				ass.setScoreName(rs.getString("scorename"));
				ass.setScoreSequence(rs.getInt("scoresequence"));
				ass.setScoreType(rs.getString("scoretype"));
				assessments.add(ass);
			}
			
			return assessments;
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return assessments;
		
	}
	
	@GET
	@Path("/GetAssessmentByExpId/{expid}")
	@Produces(MediaType.APPLICATION_XML)
	public LinkedList<Assessment> getAssessmentOnExpId(@PathParam("expid") String expId) throws Exception{
		LinkedList<Assessment> assessments = new LinkedList<Assessment>();
		Statement st = null;
		
		try{
			con = dbUtil.connectBirnDb();
			st = con.createStatement();
			String sql = " select a.name, a.assessmentid, a.description, " +
					 " s.scorename, s.scoresequence, s.scoretype, s.scorelevel,s.description as scoredescription, " +
					 " i.itemleadingtext as leadingtext " +
					 " from nc_Assessment as a,	nc_Assessmentscore as s " +
					 " LEFT OUTER JOIN nc_assessmentitem AS i " +
					 " ON s.assessmentid=i.assessmentid AND s.scorename=i.scorename " +
					 " WHERE a.assessmentid = s.assessmentid " +
					 " 		 and a.assessmentid in " +
					 "			( select distinct assessmentid " +
					 "			  from nc_Assessmentdata " +
					 "			  where nc_storedassessment_uniqueid in " +
					 "				(select uniqueid from nc_Storedassessment " +
					 "				 where nc_experiment_uniqueid in (" + Integer.valueOf(expId) + ")))";
			
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				Assessment ass = new Assessment();
				ass.setDescription(rs.getString("description"));
				ass.setId(rs.getInt("assessmentid"));
				ass.setItemLeadingText(rs.getString("leadingtext"));
				ass.setName(rs.getString("name"));
				ass.setScoreDescription(rs.getString("scoredescription"));
				ass.setScoreLevel(rs.getInt("scorelevel"));
				ass.setScoreName(rs.getString("scorename"));
				ass.setScoreSequence(rs.getInt("scoresequence"));
				ass.setScoreType(rs.getString("scoretype"));
				assessments.add(ass);
			}
			return assessments;
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return assessments;
	}
	
	
	 
}
