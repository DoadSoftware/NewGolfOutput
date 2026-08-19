package com.golf.broadcaster;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import jakarta.xml.bind.JAXBException;
import com.golf.containers.Scene;
import com.golf.containers.ScoreBug;
import com.golf.model.Configurations;
import com.golf.model.GolfCourseResponse;
import com.golf.model.GolfDrawsResponse;
import com.golf.model.GolfEntryListResponse;
import com.golf.model.GolfScoresResponse;
import com.golf.model.GolfScoresResponse.PlayerScore;
import com.golf.model.GolfTourResponse;
import com.golf.service.GolfService;

public class PGTI extends Scene {

	public String session_selected_broadcaster = "PGTI";

	public ScoreBug scorebug = new ScoreBug();
	public String which_graphics_onscreen = "",which_gfx="",which_data_gfx;
	public boolean is_infobar = false;
	public long last_date = 0;
	boolean isVisited = false;
	public int whichSide = 1;
	public String photo_path = "C:\\\\Images\\\\Golf\\\\Photos\\\\";
	private String local_photo_path = "\\\\c\\\\Images\\\\Golf\\\\Photos\\\\";
	boolean lofextradata = false;
	public String logo_path = "IMAGE*/Golf/GolfLogo";
	public String status;
	
	public PGTI() {
		super();
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public ScoreBug updateScoreBug(List<Scene> scenes, GolfService GolfService, PrintWriter print_writer)
			throws InterruptedException, MalformedURLException, IOException, JAXBException {
		
		return scorebug;
	}

	public Object ProcessGraphicOption(String whatToProcess, GolfService GolfService, GolfTourResponse session_tour, GolfScoresResponse session_score, 
			GolfEntryListResponse session_entry_list, GolfCourseResponse session_course, GolfDrawsResponse session_draws, PrintWriter print_writer, List<Scene> scenes, 
		String valueToProcess,Configurations config) throws Exception {
		switch (whatToProcess.toUpperCase()) {
		//lt
		case "POPULATE-NAMESUPERR": case "POPULATE-NAMESUPERR_FREETEXT": case "POPULATE-TOPFIVE-LEADERBOARD":	case "POPULATE-TOPTEN-LEADERBOARD":
		case "POPULATE-ROUND-PLAYER_DETAILSLT":	 case "POPULATE-FF-MATCHDRAWS":
		//ff
		case "POPULATE-FF_TOPTEN-LEADERBOARD": case "POPULATE-FF_TOPTHREE-LEADERBOARD":
			
		case "POPULATE-HOLE_DETAILS_LT": case "POPULATE-LOFF-PLAYER_DETAILSLT":  case "POPULATE-LOFF-PLAYER_DETAILSLT_EXTRA":
		case "POPULATE-LT-MATCHID": case "POPULATE-LOF-PLAYER_DETAILS": case "POPULATE-HOLE_DETAILS": case "POPULATE-COF-PLAYER_DETAILSLT":
		 case "POPULATE-LT-PLAYER_DETAILS_COF":
		switch (whatToProcess.toUpperCase()) {
			case "POPULATE-NAMESUPERR":
				populatenamesuper(print_writer, session_score, session_tour, session_entry_list, session_course, session_draws, valueToProcess.split(",")[0], session_selected_broadcaster);
			break;
			case "POPULATE-FF-MATCHDRAWS":
				populatedrawsdata(print_writer, session_score, session_tour, session_entry_list, session_course, session_draws, valueToProcess.split(",")[0], session_selected_broadcaster,config);
				break;
				
			case "POPULATE-NAMESUPERR_FREETEXT":
				populatenamesuperfreetext(print_writer, session_score, session_tour, session_entry_list, session_course, session_draws, valueToProcess, session_selected_broadcaster);
				break;
			case "POPULATE-TOPTEN-LEADERBOARD":
				populateTopten(print_writer, session_score, session_tour, session_entry_list, session_course, session_draws, valueToProcess.split(",")[0], session_selected_broadcaster,whichSide,config);
				break;	
			case "POPULATE-TOPFIVE-LEADERBOARD":
				populateTopfive(print_writer, session_score, session_tour, session_entry_list, session_course, session_draws, valueToProcess.split(",")[0], session_selected_broadcaster);
				break;
			case "POPULATE-FF_TOPTEN-LEADERBOARD":
				populateFFTopten(print_writer, session_score, session_tour, session_entry_list, session_course, session_draws, valueToProcess.split(",")[0], session_selected_broadcaster,whichSide);
				break;
			case "POPULATE-FF_TOPTHREE-LEADERBOARD":
				populateFFTopthree(print_writer, session_score, session_tour, session_entry_list, session_course, session_draws, valueToProcess.split(",")[0], session_selected_broadcaster,whichSide,config);
				break;
			case "POPULATE-ROUND-PLAYER_DETAILSLT":	
				populateroundplayerdetail(print_writer, session_score, session_tour, session_entry_list, session_course, session_draws, valueToProcess.split(",")[0], session_selected_broadcaster,config);
				break;	
				
				
				//not done
			case "POPULATE-HOLE_DETAILS_LT":
				populateHoledetails(print_writer, session_score, session_tour, session_entry_list, session_course, session_draws, valueToProcess.split(",")[0], session_selected_broadcaster,whichSide);
				break;
			
			case "POPULATE-LT-MATCHID": 
				populateMatchId(print_writer, session_score, session_tour, session_entry_list, session_course, session_draws, valueToProcess.split(",")[0], session_selected_broadcaster);
				break;
			/*
			 * case"POPULATE-LOF-PLAYER_DETAILS": populateLOFPlayerDetails(print_writer,
			 * session_score, session_tour, session_entry_list, session_course,
			 * session_draws, valueToProcess.split(",")[0], session_selected_broadcaster);
			 * break;
			 */
			
			
			case "POPULATE-COF-PLAYER_DETAILSLT":
				populatecofplayerdetail(print_writer, session_score, session_tour, session_entry_list, session_course, session_draws, valueToProcess.split(",")[0], session_selected_broadcaster);
				break;
			case "POPULATE-LOFF-PLAYER_DETAILSLT_EXTRA":
				populateloffplayerextrapart(print_writer, session_score, session_tour, session_entry_list, session_course, session_draws, 
						valueToProcess, session_selected_broadcaster);
				break;
			case "POPULATE-LOFF-PLAYER_DETAILSLT":
				populateloffplayerdetail(print_writer, session_score, session_tour, session_entry_list, session_course, session_draws, valueToProcess, session_selected_broadcaster);
				break;
		}
		break;
		 case "CLEAR-ALL":
			 AnimateRest(print_writer);
			 which_graphics_onscreen = "";
			 which_data_gfx = "";
			 break;
		 case "ANIMATE-OUT_DOUBLE":
			 if(which_data_gfx != null && !which_data_gfx.isEmpty() && which_graphics_onscreen != null && !which_graphics_onscreen.isEmpty()) {
				 print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT$In_Out CONTINUE \0"); 
				 print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT_Small$In_Out CONTINUE \0");
					which_data_gfx = "";
					which_graphics_onscreen = "";
					TimeUnit.MILLISECONDS.sleep(600);
				 print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays SHOW 0.0 \0"); 
			 }
			 break;
//		 case "ANIMATE_IN-LOFEXTRA":
//
//			    if (which_data_gfx.equalsIgnoreCase("LOFF-PLAYER_DETAILSLT") && !which_data_gfx.isEmpty()) {
//
//			        if (!lofextradata) {   
//
//			            print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT_Small$ExtraData$In_Out START \0");
//			            lofextradata = true;
//
//			        } else {   
//
//			            print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT_Small$ExtraData$In_Out CONTINUE \0");
//			            lofextradata = false;
//
//			        }
//			    }
//
//			    break;
		case "ANIMATE_IN-HOLE_IN_ONE": case "ANIMATE_IN-EAGLE": case "ANIMATE_IN-BIRDIE":
			if(which_data_gfx != null && !which_data_gfx.isEmpty()) {
				switch (which_data_gfx.toUpperCase()) {
				case "LOFF-PLAYER_DETAILSLT": case "COF-PLAYER_DETAILSLT":
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT_Small$In_Out CONTINUE \0");
					break;		
				}
				TimeUnit.MILLISECONDS.sleep(600);
			}
			
			if(which_graphics_onscreen != null && !which_graphics_onscreen.isEmpty()) {
				switch (which_graphics_onscreen.toUpperCase()) {
				case "TOP10_LEADREBOARD": case "TOP5_LEADREBOARD": case "NAMESUPERR_FREETEXT": case "NAMESUPERR":
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT$In_Out CONTINUE \0");
					break;
				case "FF_TOPTEN-LEADERBOARD":  case "FF_TOPTHREE-LEADERBOARD": case "FF-MATCHDRAWS":
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Anim_Fullframe$In_Out CONTINUE \0");
					break;		
				}
				TimeUnit.MILLISECONDS.sleep(600);
			}
		
			switch (whatToProcess.toUpperCase()){
			case "ANIMATE_IN-HOLE_IN_ONE":
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$Animations$Select*FUNCTION*Omo*vis_con SET 2\0");
				break;
			case "ANIMATE_IN-EAGLE":
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$Animations$Select*FUNCTION*Omo*vis_con SET 1\0");
				break;
			case "ANIMATE_IN-BIRDIE":
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$Animations$Select*FUNCTION*Omo*vis_con SET 0\0");
				break;
			}
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Animations START \0");
			
			if(which_graphics_onscreen != null && !which_graphics_onscreen.isEmpty()) {
				TimeUnit.MILLISECONDS.sleep(5000);
				switch (which_graphics_onscreen.toUpperCase()) {
				case "TOP10_LEADREBOARD": case "TOP5_LEADREBOARD": case "NAMESUPERR_FREETEXT": case "NAMESUPERR":
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT$In_Out START \0");
					break;
				case "FF_TOPTEN-LEADERBOARD":  case "FF_TOPTHREE-LEADERBOARD": case "FF-MATCHDRAWS":
					print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Anim_Fullframe$In_Out START \0");
					break;		
				}
			}
			
			if(which_data_gfx != null && !which_data_gfx.isEmpty()) {
				if(which_graphics_onscreen == null || which_graphics_onscreen.isEmpty()) {
					TimeUnit.MILLISECONDS.sleep(5000);
				}
				switch (which_data_gfx.toUpperCase()) {
				case "LOFF-PLAYER_DETAILSLT": case "COF-PLAYER_DETAILSLT":
					print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT_Small$In_Out START \0");
					break;		
				}
			}
			break;
		case "ANIMATE-IN-COF-PLAYER_DETAILSLT": case "ANIMATE-IN-HOLE_DETAILS_LT": case "ANIMATE-IN-LOFF-PLAYER_DETAILSLT":
		case "ANIMATE-IN-LOFF-PLAYER_DETAILSLT_EXTRA":	
		case "ANIMATE-IN-LT_MATCHID": case "ANIMATE-IN-LOF_PLAYER_DETAILS": case "ANIMATE-IN-HOLE_DETAILS": case "ANIMATE-IN-PLAYER_ROUND_DETAILS":
		case "ANIMATE-IN-PLAYER_COF_LEADREBOARD":	
			
		case "ANIMATE-IN-NAMESUPERR": case "ANIMATE-IN-NAMESUPERR_FREETEXT":case "ANIMATE-IN-TOP10_LEADREBOARD": case "ANIMATE-IN-TOP5_LEADREBOARD":
		case "ANIMATE-IN-FF_TOPTEN-LEADERBOARD": case "ANIMATE-IN-FF_TOPTHREE-LEADERBOARD": case "ANIMATE-IN-ROUND-PLAYER_DETAILSLT":
		case "ANIMATE-IN-FF-MATCHDRAWS":	
			switch (whatToProcess.toUpperCase()) {
			case "ANIMATE-IN-NAMESUPERR": 
				AnimateInGraphics(print_writer, "NAMESUPERR");
				which_graphics_onscreen = "NAMESUPERR";
				break;
			case "ANIMATE-IN-FF-MATCHDRAWS":
				AnimateInGraphics(print_writer, "FF-MATCHDRAWS");
				which_graphics_onscreen = "FF-MATCHDRAWS";
				break;
			case "ANIMATE-IN-NAMESUPERR_FREETEXT":
				AnimateInGraphics(print_writer, "NAMESUPERR_FREETEXT");
				which_graphics_onscreen = "NAMESUPERR_FREETEXT";
				break;
			case "ANIMATE-IN-TOP10_LEADREBOARD":
				AnimateInGraphics(print_writer, "TOP10_LEADREBOARD");
				which_graphics_onscreen = "TOP10_LEADREBOARD";
				break;
			case "ANIMATE-IN-TOP5_LEADREBOARD":
				AnimateInGraphics(print_writer, "TOP5_LEADREBOARD");
				which_graphics_onscreen = "TOP5_LEADREBOARD";
				break;
			case "ANIMATE-IN-FF_TOPTEN-LEADERBOARD":
				AnimateInGraphics(print_writer, "FF_TOPTEN-LEADERBOARD");
				which_graphics_onscreen = "FF_TOPTEN-LEADERBOARD";
				break;	
			case "ANIMATE-IN-FF_TOPTHREE-LEADERBOARD":
				AnimateInGraphics(print_writer, "FF_TOPTHREE-LEADERBOARD");
				which_graphics_onscreen = "FF_TOPTHREE-LEADERBOARD";
				break;
			case "ANIMATE-IN-ROUND-PLAYER_DETAILSLT":	
				AnimateInGraphics(print_writer, "ROUND-PLAYER_DETAILSLT");
				which_graphics_onscreen = "ROUND-PLAYER_DETAILSLT";
				break;
				
				
			//not doen	
			case "ANIMATE-IN-HOLE_DETAILS_LT":
				AnimateInGraphics(print_writer, "HOLE_DETAILS_LT");
				which_data_gfx = "HOLE_DETAILS_LT";
				break;
			
			case "ANIMATE-IN-COF-PLAYER_DETAILSLT":
				AnimateInGraphics(print_writer, "COF-PLAYER_DETAILSLT");
				which_data_gfx = "COF-PLAYER_DETAILSLT";
				break;
			case "ANIMATE-IN-LOFF-PLAYER_DETAILSLT":	
				AnimateInGraphics(print_writer, "LOFF-PLAYER_DETAILSLT");
				which_data_gfx = "LOFF-PLAYER_DETAILSLT";
				break;
			case "ANIMATE-IN-LOFF-PLAYER_DETAILSLT_EXTRA":	
				if (lofextradata == false) {
		            print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT_Small$ExtraData$In_Out START \0");
		            lofextradata = true;
		        }else if(lofextradata == true) {
		        	print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT_Small$ExtraData$In_Out CONTINUE \0");
					lofextradata = false;
		        }
				which_data_gfx = "LOFF-PLAYER_DETAILSLT";
				break;
			case "ANIMATE-IN-LOF_PLAYER_DETAILS":
				AnimateInGraphics(print_writer, "LOF_PLATER_DETAILS");
				which_graphics_onscreen = "LOFF-PLAYER_DETAILSLT_EXTRA";
				break;
			case "ANIMATE-IN-COF_PLAYER_DETAILS":
				AnimateInGraphics(print_writer, "COF_PLATER_DETAILS");
				which_graphics_onscreen = "COF_PLATER_DETAILS";
				break;
			case "ANIMATE-IN-PLAYER_ROUND_DETAILS":
				AnimateInGraphics(print_writer, "PLAYER_ROUND_DETAILS");
				which_graphics_onscreen = "PLAYER_ROUND_DETAILS";
				break;
			}
			break;
		case "ANIMATE-OUT_ALL":
			switch (which_graphics_onscreen.toUpperCase()) {
			case "TOP10_LEADREBOARD": case "TOP5_LEADREBOARD": case "NAMESUPERR_FREETEXT": case "NAMESUPERR":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT$In_Out CONTINUE \0");
				which_graphics_onscreen = "";
				TimeUnit.MILLISECONDS.sleep(600);
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT$In_Out SHOW 0.0 \0");
				break;
			case "FF_TOPTEN-LEADERBOARD":  case "FF_TOPTHREE-LEADERBOARD": case "FF-MATCHDRAWS":
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Anim_Fullframe$In_Out CONTINUE \0");
				which_graphics_onscreen = "";
				TimeUnit.MILLISECONDS.sleep(600);
				print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Anim_Fullframe$In_Out SHOW 0.0 \0");
				break;	
			case "ROUND-PLAYER_DETAILSLT":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*ROF$In_Out CONTINUE \0");
				which_graphics_onscreen = "";
				TimeUnit.MILLISECONDS.sleep(600);
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*ROF$In_Out SHOW 0.0 \0");
				break;	
			}
			break;
		case "ANIMATE-OUT_SMALLLT":
			switch (which_data_gfx.toUpperCase()) {
			case "COF-PLAYER_DETAILSLT": 
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT_Small$In_Out CONTINUE \0");
				which_data_gfx = "";
				TimeUnit.MILLISECONDS.sleep(600);
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT_Small$In_Out SHOW 0.0 \0");
				break;
			case "LOFF-PLAYER_DETAILSLT": 
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT_Small$In_Out CONTINUE \0");
				which_data_gfx = "";
				lofextradata = false;
				TimeUnit.MILLISECONDS.sleep(600);
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT_Small$In_Out SHOW 0.0 \0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT_Small$ExtraData$In_Out SHOW 0.0 \0");
				break;	
			case "HOLE_DETAILS_LT":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT_Hole$In_Out CONTINUE \0");
				which_data_gfx ="";
				TimeUnit.MILLISECONDS.sleep(600);
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT_Hole$In_Out SHOW 0.0 \0");
				break;	
			}
			break;
		case "ANIMATE-OUT_HOLE":
			switch (which_gfx.toUpperCase()) {
			case "HOLE_DETAILS_LT":
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT_Hole$In_Out CONTINUE \0");
				which_gfx ="";
				TimeUnit.MILLISECONDS.sleep(600);
				print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT_Small$In_Out SHOW 0.0 \0");
				break;
			
			}
			break;
		}
		return null;
	}

	public void AnimateInGraphics(PrintWriter print_writer, String whichGraphic) throws InterruptedException {
      
		switch (whichGraphic) {
		 case "HOLE_DETAILS_LT":
			 print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT_Hole$In_Out START \0");
			 break;
		case "TOP10_LEADREBOARD": case "TOP5_LEADREBOARD": case "NAMESUPERR": case "NAMESUPERR_FREETEXT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT$In_Out START \0");
			break;
		case "COF-PLAYER_DETAILSLT": case "LOFF-PLAYER_DETAILSLT":
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT_Small$In_Out START \0");
			break;
		case "FF_TOPTEN-LEADERBOARD": case "FF_TOPTHREE-LEADERBOARD": case "FF-MATCHDRAWS":
			print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Anim_Fullframe$In_Out START \0");
			break;
		case "ROUND-PLAYER_DETAILSLT":	
			print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*ROF$In_Out START \0");
			break;
		case "LOFF-PLAYER_DETAILSLT_EXTRA":
			if (which_data_gfx.equalsIgnoreCase("LOFF-PLAYER_DETAILSLT") && !which_data_gfx.isEmpty()) {

		        if (!lofextradata) {   

		            print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT_Small$ExtraData$In_Out START \0");
		            lofextradata = true;

		        } else {   

		            print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays$LT_Small$ExtraData$In_Out CONTINUE \0");
		            lofextradata = false;

		        }
		    }
			break;
		}
	}
	
	public void AnimateRest(PrintWriter print_writer) throws InterruptedException { 
		print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*Anim_Overlays SHOW 0.0 \0");
		print_writer.println("-1 RENDERER*FRONT_LAYER*STAGE*DIRECTOR*ROF SHOW 0.0 \0");
		print_writer.println("-1 RENDERER*BACK_LAYER*STAGE*DIRECTOR*Anim_Fullframe SHOW 0.0 \0");
	}

	public void AnimateOutGraphics(PrintWriter print_writer, String whichGraphic) throws InterruptedException {
		
	}
	
	public void populateMatchId(PrintWriter print_writer, GolfScoresResponse session_score, GolfTourResponse session_tour, 
			GolfEntryListResponse session_entry_list, GolfCourseResponse session_course, GolfDrawsResponse session_draws, String viz_sence_path,String selectedbroadcaster) {
		if (session_score == null) {
			System.out.println("ERROR: Lt-Match -> Match is null");
		} else {
			
			System.out.println(session_tour.getData().get(0).getTourName());
			System.out.println(session_entry_list.getData().get(0).getMemName());
			System.out.println(session_course.getData().get(0).getCourseName());
			
		}
	}
	
	
	public void populateHoledetails(PrintWriter print_writer, GolfScoresResponse session_score, GolfTourResponse session_tour, 
			GolfEntryListResponse session_entry_list, GolfCourseResponse session_course, GolfDrawsResponse session_draws, String val,String selectedbroadcaster,int whichSide) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
		
		if (session_score == null) {
			
		}else {
			
			if(which_graphics_onscreen == "TOP5_LEADREBOARD") {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Hole$Side" + whichSide + "$LT_Right*TRANSFORMATION*POSITION*X SET 908.0\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Hole$Side" + whichSide + "$LT_Right*TRANSFORMATION*POSITION*Y SET -461.0\0");
			}else {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Hole$Side" + whichSide + "$LT_Right*TRANSFORMATION*POSITION*x SET 908.0\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Hole$Side" + whichSide + "$LT_Right*TRANSFORMATION*POSITION*Y SET -620.0\0");
			}
			
			
			
			 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide + "$Select*FUNCTION*Omo*vis_con SET 2\0"); 
			for (int i = 0; i <session_course.getData().size(); i++) {
				 List<String> pars = session_course.getData().get(i).getAllPars();
				 List<String> yards = session_course.getData().get(i).getAllYards();
				 for(int j=1;j<pars.size()+1;j++) {
					 if (Integer.valueOf(val) == j) {
						 String pValue = pars.get(j-1);
						 String yValue = yards.get(j-1);
						 
						 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Hole$Side" + whichSide +
							        "$Select$LT_Right$Header$Header_Band$Logo_Header_Grp$Txt_Number*GEOM*TEXT SET " + val + "\0");
						 
						 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Hole$Side" + whichSide +
							        "$Select$LT_Right$Header$Header_Band$Logo_Header_Grp$Txt_Header01*GEOM*TEXT SET " + "PAR "+ pValue + "\0"); 
						 
						 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Hole$Side" + whichSide +
							        "$Select$LT_Right$Header$Header_Band$Txt_Header02*GEOM*TEXT SET " + yValue + " YDS" + "\0");
					 }
				 }
			}
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + "/Default/Overlays"
			    + " C:/Temp/Preview.png Anim_Overlays$LT_Hole$In_Out 1.140 "
			    + "Anim_Overlays$LT_Hole$In_Out$In 0.800 \0");
	}
	@SuppressWarnings("unused")
	public void populateTopten(PrintWriter print_writer, GolfScoresResponse session_score, GolfTourResponse session_tour, 
			GolfEntryListResponse session_entry_list, GolfCourseResponse session_course, GolfDrawsResponse session_draws, String viz_sence_path,String selectedbroadcaster,int whichSide,Configurations config) {
		if (session_score == null) {
			System.out.println("ERROR: Lt-Match -> Match is null");
		} else {
			
			
			 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide + "$Select*FUNCTION*Omo*vis_con SET 0\0");
			 //headre part
			 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide +
				        "$LT_LeaderboardPic$Header$Header_Band$Txt_Header01*GEOM*TEXT SET " + session_tour.getData().get(0).getTourName().toUpperCase() + "\0");
			 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide +
				        "$LT_LeaderboardPic$Header$Txt_Header02*GEOM*TEXT SET " + "LEADERBOARD" + "\0");
			int rank = 0;
		    int displayRank = 0;
		    int prevScore = Integer.MIN_VALUE;
		    String dataRank="";
			for(int i = 0;i<10;i++) {

				String fullName = session_score.getData().get(i).getMemName();

				String firstName = fullName;
				String lastName = "";

				if(fullName != null && fullName.contains(" ")) {
				    int lastSpace = fullName.lastIndexOf(" ");
				    firstName = fullName.substring(0, lastSpace);
				    lastName = fullName.substring(lastSpace + 1);
				}
				String sd1 = session_score.getData().get(i).getSd1();
				String sd2 = session_score.getData().get(i).getSd2();
				String sd3 = session_score.getData().get(i).getSd3();
				String sd4 = session_score.getData().get(i).getSd4();

				int v1 = sd1 == null ? 0 : Integer.parseInt(sd1);
				int v2 = sd2 == null ? 0 : Integer.parseInt(sd2);
				int v3 = sd3 == null ? 0 : Integer.parseInt(sd3);
				int v4 = sd4 == null ? 0 : Integer.parseInt(sd4);
				int total = v1 + v2 + v3 + v4;
				
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide + "$LT_LeaderboardPic$Data_All$Data$"
						+ (i+1) + "$PlayerData$Name_GRp$Txt_FirstName*GEOM*TEXT SET " + firstName + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide + "$LT_LeaderboardPic$Data_All$Data$"
						+ (i+1) + "$PlayerData$Name_GRp$Txt_LastName*GEOM*TEXT SET " + lastName + "\0");
				
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide + "$LT_LeaderboardPic$Data_All$Data$"
						+ (i+1) + "$PlayerData$Info_Grp$Info_Data_Grp$Txt_Info01*GEOM*TEXT SET " + (session_score.getData().get(i).getScore() == null ?"-":
					 		0 == Integer.parseInt(session_score.getData().get(i).getScore()) ? "PAR" : session_score.getData().get(i).getScore()) + "\0");
				
				int totalScore = Integer.valueOf(session_score.getData().get(i).getScore());
				String scoreValue = "0";
				if(totalScore > 0){
		            scoreValue = "+" + totalScore;
		        }
		        else if(totalScore < 0){
		            scoreValue = String.valueOf(totalScore);
		        }
				rank++;
		        if(i == 0){
		            displayRank = 1;
		        }
		        else if(totalScore != prevScore){
		            displayRank = rank;
		        }
		        boolean isTie = false;

		        if(i > 0 && totalScore == prevScore){
		            isTie = true;
		        }
		        if (i < session_score.getData().size() - 1) {

		            int nextScore = Integer.valueOf(
		                session_score.getData().get(i + 1).getScore()
		            );

		            if (nextScore == totalScore) {
		                isTie = true;
		            }
		        }
		        if(isTie){
		        	dataRank = "T" + displayRank;
		        }else{
		        	dataRank = String.valueOf(displayRank);
		        }
		        prevScore = totalScore;
				
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide + "$LT_LeaderboardPic$Data_All$Data$"+ (i+1) +
				        "$PlayerData$Txt_Rank*GEOM*TEXT SET " + dataRank + "\0");
				
				
				if( Integer.valueOf(session_score.getData().get(i).getHole())  >= 18) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide +
					        "$LT_LeaderboardPic$Data_All$Data$"+ (i+1) +
					        "$PlayerData$Info_Grp$Info_Data_Grp$Ingo02_Grp$Txt_Info02*GEOM*TEXT SET " + total + "\0");
				}else {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide +
					        "$LT_LeaderboardPic$Data_All$Data$"+ (i+1) +
					        "$PlayerData$Info_Grp$Info_Data_Grp$Ingo02_Grp$Txt_Info02*GEOM*TEXT SET " + "(" + (session_score.getData().get(i).getHole()) + ")" + "\0");
				}
				
				
				
				
				if (config.getIpAddress().equalsIgnoreCase("LOCALHOST")) {
					
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide +
					        "$LT_LeaderboardPic$Data_All$Data$"+ (i+1) +
					        "$PlayerData$Image_Grp$Img_Image*TEXTURE*IMAGE SET " + photo_path + "Blank" + ".png" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide +
					        "$LT_LeaderboardPic$Data_All$Data$"+ (i+1) +
					        "$PlayerData$Image_Grp$Img_Image_Shadow*TEXTURE*IMAGE SET " + photo_path + "Blank" + ".png" + "\0");
					
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide +
					        "$LT_LeaderboardPic$Data_All$Data$"+ (i+1) +
					        "$PlayerData$Image_Grp$Img_Image*TEXTURE*IMAGE SET " + photo_path + session_score.getData().get(i).getMemCode() + ".png" + "\0");
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide +
					        "$LT_LeaderboardPic$Data_All$Data$"+ (i+1) +
					        "$PlayerData$Image_Grp$Img_Image_Shadow*TEXTURE*IMAGE SET " + photo_path + session_score.getData().get(i).getMemCode() + ".png" + "\0");
				}else {
					
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide +
					        "$LT_LeaderboardPic$Data_All$Data$"+ (i+1) +
					        "$PlayerData$Image_Grp$Img_Image*TEXTURE*IMAGE SET " + "\\\\"+ config.getIpAddress() + "\\\\" + local_photo_path + "Blank" + ".png" + "\0");
							
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide +
							        "$LT_LeaderboardPic$Data_All$Data$"+ (i+1) +
							        "$PlayerData$Image_Grp$Img_Image_Shadow*TEXTURE*IMAGE SET " + "\\\\"+ config.getIpAddress() + "\\\\" + local_photo_path + "Blank" + ".png" + "\0");
					
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide +
					        "$LT_LeaderboardPic$Data_All$Data$"+ (i+1) +
					        "$PlayerData$Image_Grp$Img_Image*TEXTURE*IMAGE SET " + "\\\\"+ config.getIpAddress() + "\\\\" + local_photo_path + session_score.getData().get(i).getMemCode() + ".png" + "\0");
							
							print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide +
							        "$LT_LeaderboardPic$Data_All$Data$"+ (i+1) +
							        "$PlayerData$Image_Grp$Img_Image_Shadow*TEXTURE*IMAGE SET " + "\\\\"+ config.getIpAddress() + "\\\\" + local_photo_path + session_score.getData().get(i).getMemCode() + ".png" + "\0");
				}
				
				//header part
				
			}
			
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + "/Default/Overlays"
			    + " C:/Temp/Preview.png Anim_Overlays$LT$In_Out 1.140 Anim_Overlays$LT$In_Out$Header 1.140 "
			    + "Anim_Overlays$LT$In_Out$Header$In 0.840 Anim_Overlays$LT$In_Out$Bottom 1.140 "
			    + "Anim_Overlays$LT$In_Out$Bottom$In 1.140 \0");
	}
	
	@SuppressWarnings("unused")
	public void populateFFTopten(PrintWriter print_writer, GolfScoresResponse session_score, GolfTourResponse session_tour, 
			GolfEntryListResponse session_entry_list, GolfCourseResponse session_course, GolfDrawsResponse session_draws, String val,String selectedbroadcaster,int whichSide) {
		if (session_score == null) {
			System.out.println("ERROR: Lt-Match -> Match is null");
		} else {
			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Select*FUNCTION*Omo*vis_con SET 0\0");
			 //headre part
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Header$HeaderData_grp"
			 		+ "$Txt_Header*GEOM*TEXT SET " + "LEADERBOARD" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Header$HeaderData_grp"
			 		+ "$Txt_SubHeader*GEOM*TEXT SET " + "" + "\0");
			 
			 //line one omo
			 print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$1$LeaderBoardData$select_DataType*FUNCTION*Omo*vis_con SET 0\0");
			 print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$1$LeaderBoardData$select_DataType$Title$DataAll"
			 		+ "$First$Txt_StatHead*GEOM*TEXT SET " + "SCORE" + "\0");
			 print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$1$LeaderBoardData$select_DataType$Title$DataAll"
				 		+ "$Second$Txt_StatHead*GEOM*TEXT SET " + "(H)/AGG" + "\0");
			 print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Header$Logo_Grp"
						+ "$img_Logo*TEXTURE*IMAGE SET " + logo_path + "\0");
			 
			int rank = 0, displayRank = 0, start=0, max=0, rowId=1;
		    int prevScore = Integer.MIN_VALUE;
		    String dataRank="";
		    
		    if(val.equalsIgnoreCase("1-10")) {
				rank = 0;
				displayRank = 0;
				start=0; 
				max=10;
			}else if(val.equalsIgnoreCase("11-20")) {
				rank = 10;
				displayRank = 10;
				start=10; 
				max=20;
			}
			 
			for(int i=start;i<max;i++) {
				rowId++;
				String fullName = session_score.getData().get(i).getMemName();

				String firstName = fullName;
				String lastName = "";

				if(fullName != null && fullName.contains(" ")) {
				    int lastSpace = fullName.lastIndexOf(" ");
				    firstName = fullName.substring(0, lastSpace);
				    lastName = fullName.substring(lastSpace + 1);
				}
				String sd1 = session_score.getData().get(i).getSd1();
				String sd2 = session_score.getData().get(i).getSd2();
				String sd3 = session_score.getData().get(i).getSd3();
				String sd4 = session_score.getData().get(i).getSd4();

				int v1 = sd1 == null ? 0 : Integer.parseInt(sd1);
				int v2 = sd2 == null ? 0 : Integer.parseInt(sd2);
				int v3 = sd3 == null ? 0 : Integer.parseInt(sd3);
				int v4 = sd4 == null ? 0 : Integer.parseInt(sd4);
				int total = v1 + v2 + v3 + v4;
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Leaderboard$" + rowId +"$LeaderBoardData$select_DataType*FUNCTION*Omo*vis_con SET 1\0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Leaderboard$" + rowId +"$LeaderBoardData$select_DataType$RestData$DataAll"
				 		+ "$Txt_FirstName*GEOM*TEXT SET " + firstName + "\0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Leaderboard$" + rowId +"$LeaderBoardData$select_DataType$RestData$DataAll"
				 		+ "$Txt_LastName*GEOM*TEXT SET " + lastName + "\0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Leaderboard$" + rowId +"$LeaderBoardData$select_DataType$RestData$DataAll"
				 		+ "$First$Txt_StatValue*GEOM*TEXT SET " +  (session_score.getData().get(i).getScore() == null ?"-":
				 		0 == Integer.parseInt(session_score.getData().get(i).getScore()) ? "PAR" : session_score.getData().get(i).getScore()) + "\0");
				
				
				
				
				if(Integer.valueOf(session_score.getData().get(i).getHole()) >=18) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Leaderboard$" + rowId +"$LeaderBoardData$select_DataType$RestData$DataAll"
					 		+ "$Second$Txt_StatValue*GEOM*TEXT SET " + total + "\0");
				}else {
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Leaderboard$" + rowId +"$LeaderBoardData$select_DataType$RestData$DataAll"
					 		+ "$Second$Txt_StatValue*GEOM*TEXT SET " + "(" + session_score.getData().get(i).getHole() + ")" + "\0");
					}
				
				int totalScore = Integer.valueOf(session_score.getData().get(i).getScore());
				String scoreValue;
				if(totalScore > 0){
		            scoreValue = "+" + totalScore;
		        }
		        else if(totalScore < 0){
		            scoreValue = String.valueOf(totalScore);
		        }
		        else{
		            scoreValue = "0";
		        }
				rank++;
		        if(i == 0){
		            displayRank = 1;
		        }
		        else if(totalScore != prevScore){
		            displayRank = rank;
		        }
		        boolean isTie = false;

		        if(i > 0 && totalScore == prevScore){
		            isTie = true;
		        }
		        if (i < session_score.getData().size() - 1) {

		            int nextScore = Integer.valueOf(
		                session_score.getData().get(i + 1).getScore()
		            );

		            if (nextScore == totalScore) {
		                isTie = true;
		            }
		        }
		        if(isTie){
		        	dataRank = "T" + displayRank;
		        }else{
		        	dataRank = String.valueOf(displayRank);
		        }
		        prevScore = totalScore;
				
				
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Leaderboard$" + rowId +"$LeaderBoardData$select_DataType$RestData$DataAll"
				 		+ "$Number$Txt_Number*GEOM*TEXT SET " + dataRank + "\0");
				
		
			}
			
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + "/Default/Fullframes"
			    + " C:/Temp/Preview.png Anim_Fullframe$In_Out 1.960 Anim_Fullframe$In_Out$In 1.960 Anim_Fullframe$In_Out$In$In 1.540 \0");
	}
	
	
	@SuppressWarnings("unused")
	public void populateFFTopthree(PrintWriter print_writer, GolfScoresResponse session_score, GolfTourResponse session_tour, 
			GolfEntryListResponse session_entry_list, GolfCourseResponse session_course, GolfDrawsResponse session_draws, String viz_sence_path,String selectedbroadcaster,int whichSide,Configurations config) {
		if (session_score == null) {
			System.out.println("ERROR: Lt-Match -> Match is null");
		} else {
			
			
			 print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Select*FUNCTION*Omo*vis_con SET 1\0");
			 print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Select$Top3$Select*FUNCTION*Omo*vis_con SET 0\0");
			 //headre part
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Header$HeaderData_grp"
			 		+ "$Txt_Header*GEOM*TEXT SET " + "LEADERBOARD" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Header$HeaderData_grp"
			 		+ "$Txt_SubHeader*GEOM*TEXT SET " + "" + "\0");
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Header$Logo_Grp"
					+ "$img_Logo*TEXTURE*IMAGE SET " + logo_path + "\0");
			
			int rank = 0;
		    int displayRank = 0;
		    int prevScore = Integer.MIN_VALUE;
		    String dataRank="";
			for(int i = 0;i<3;i++) {
				
				String fullName = session_score.getData().get(i).getMemName();

				String firstName = fullName;
				String lastName = "";

				if(fullName != null && fullName.contains(" ")) {
				    int lastSpace = fullName.lastIndexOf(" ");
				    firstName = fullName.substring(0, lastSpace);
				    lastName = fullName.substring(lastSpace + 1);
				}
				String sd1 = session_score.getData().get(i).getSd1();
				String sd2 = session_score.getData().get(i).getSd2();
				String sd3 = session_score.getData().get(i).getSd3();
				String sd4 = session_score.getData().get(i).getSd4();

				int v1 = sd1 == null ? 0 : Integer.parseInt(sd1);
				int v2 = sd2 == null ? 0 : Integer.parseInt(sd2);
				int v3 = sd3 == null ? 0 : Integer.parseInt(sd3);
				int v4 = sd4 == null ? 0 : Integer.parseInt(sd4);
				int total = v1 + v2 + v3 + v4;
				
				int totalScore = Integer.valueOf(session_score.getData().get(i).getScore());
				String scoreValue;
				if(totalScore > 0){
		            scoreValue = "+" + totalScore;
		        }
		        else if(totalScore < 0){
		            scoreValue = String.valueOf(totalScore);
		        }
		        else{
		            scoreValue = "0";
		        }
				rank++;
		        if(i == 0){
		            displayRank = 1;
		        }
		        else if(totalScore != prevScore){
		            displayRank = rank;
		        }
		        boolean isTie = false;

		        if(i > 0 && totalScore == prevScore){
		            isTie = true;
		        }
		        if (i < session_score.getData().size() - 1) {

		            int nextScore = Integer.valueOf(
		                session_score.getData().get(i + 1).getScore()
		            );

		            if (nextScore == totalScore) {
		                isTie = true;
		            }
		        }
		        if(isTie){
		        	dataRank = "T" + displayRank;
		        }else{
		        	dataRank = String.valueOf(displayRank);
		        }
		        prevScore = totalScore;
				
		        print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Top3$Select$3$Select$Score$" + (i+1) +"$LeaderBoardData$select_DataType$RestData$DataAll"
				 		+ "$Number$Txt_Number*GEOM*TEXT SET " + dataRank + "\0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Top3$Select$3$Select$Score$" + (i+1) +"$LeaderBoardData$select_DataType$RestData$DataAll"
				 		+ "$Txt_FirstName*GEOM*TEXT SET " + firstName + " " + lastName + "\0");
				
				print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Select$Top3$Select$3$Select*FUNCTION*Omo*vis_con SET 0\0");
				
				if(Integer.valueOf(session_score.getData().get(i).getHole()) >=18) {
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Top3$Select$3$Select$Score$" + (i+1) +"$LeaderBoardData$select_DataType$RestData$DataAll"
					 		+ "$Txt_Score*GEOM*TEXT SET " + (session_score.getData().get(i).getScore() == null ?"-":
						 		0 == Integer.parseInt(session_score.getData().get(i).getScore()) ? "PAR" : session_score.getData().get(i).getScore()) + " | " + total +"\0");
				}else {
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Top3$Select$3$Select$Score$" + (i+1) +"$LeaderBoardData$select_DataType$RestData$DataAll"
					 		+ "$Txt_Score*GEOM*TEXT SET " + (session_score.getData().get(i).getScore() == null ?"-":
						 		0 == Integer.parseInt(session_score.getData().get(i).getScore()) ? "PAR" : session_score.getData().get(i).getScore())  + " " + "(" + session_score.getData().get(i).getHole() + ")" + "\0");
					
					}
				
				
				if (config.getIpAddress().equalsIgnoreCase("LOCALHOST")) {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Top3$Select$3$Select$Score$" + (i+1)
							+ "$DataAll$Number$Image*TEXTURE*IMAGE SET " + photo_path + "Blank" + ".png" + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Top3$Select$3$Select$Score$" + (i+1)
							+ "$DataAll$Number$Image*TEXTURE*IMAGE SET " + photo_path + session_score.getData().get(i).getMemCode() + ".png" + "\0");
				}else {
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Top3$Select$3$Select$Score$" + (i+1)
							+ "$DataAll$Number$Image*TEXTURE*IMAGE SET " + "\\\\"+ config.getIpAddress() + "\\\\" + local_photo_path + "Blank" + ".png" + "\0");
					
					print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Top3$Select$3$Select$Score$" + (i+1)
							+ "$DataAll$Number$Image*TEXTURE*IMAGE SET " + "\\\\"+ config.getIpAddress() + "\\\\" + local_photo_path + session_score.getData().get(i).getMemCode() + ".png" + "\0");
					
					}
		
			}
			
//			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Top3$4$LeaderBoardData$Txt_FirstName"
//			 		+ "*GEOM*TEXT SET " + session_tour.getData().get(0).getCourseName().toUpperCase() + ", " + session_tour.getData().get(0).getCourseVenue().toUpperCase()  + "\0");
//			
			print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Top3$4$LeaderBoardData$Txt_FirstName"
			 		+ "*GEOM*TEXT SET " + "ZION HILLS GOLF COUNTY, KOLAR, KARNATAKA"  + "\0");
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + "/Default/Fullframes"
			    + " C:/Temp/Preview.png Anim_Fullframe$In_Out 1.960 Anim_Fullframe$In_Out$In 1.960 Anim_Fullframe$In_Out$In$In 1.540 \0");
	}
	
	public void populatecofplayerdetail(PrintWriter print_writer, GolfScoresResponse session_score, GolfTourResponse session_tour, 
			GolfEntryListResponse session_entry_list, GolfCourseResponse session_course, GolfDrawsResponse session_draws,String val,String selectedbroadcaster) {
		
		if (session_score == null) {
			System.out.println("ERROR: Lt-Match -> Match is null");
		} else {
			if(which_graphics_onscreen == "TOP5_LEADREBOARD") {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide + "$LT_Center*TRANSFORMATION*POSITION*X SET 0.0\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide + "$LT_Center*TRANSFORMATION*POSITION*Y SET -406.0\0");
			}else {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide + "$LT_Center*TRANSFORMATION*POSITION*x SET 0.0\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide + "$LT_Center*TRANSFORMATION*POSITION*Y SET -620.0\0");
			}
			
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide + "$Select*FUNCTION*Omo*vis_con SET 0\0");
			
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide + "$Select"
					+ "$LT_Center$Header$Logo_Header_Grp$Img_Logo*TEXTURE*IMAGE SET " + logo_path + "\0");
			for(int i=0;i<session_score.getData().size(); i++) {
				if(session_score.getData().get(i).getMemCode().equalsIgnoreCase(val)) {
					
					 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide +
						        "$LT_Center$Header$Logo_Header_Grp$Txt_Header01*GEOM*TEXT SET " + session_score.getData().get(i).getMemName() + "\0");
					 
					 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide +
						        "$LT_Center$Header$Txt_Header02*GEOM*TEXT SET " + (session_score.getData().get(i).getScore() == null ?"-":
							 		0 == Integer.parseInt(session_score.getData().get(i).getScore()) ? "PAR" : session_score.getData().get(i).getScore()) + "\0");
				}
			}		
			
			 print_writer.println("-1 RENDERER PREVIEW SCENE*" + "/Default/Overlays"
					    + " C:/Temp/Preview.png Anim_Overlays$LT_Small$In_Out 1.140 "
					    + "Anim_Overlays$LT_Small$In_Out$In 0.840 \0");	
	}
	}
	
	public void populateroundplayerdetail(PrintWriter print_writer, GolfScoresResponse session_score, GolfTourResponse session_tour, 
			GolfEntryListResponse session_entry_list, GolfCourseResponse session_course, GolfDrawsResponse session_draws,String val,String selectedbroadcaster,Configurations config) {
		
		for(int i=0;i<session_score.getData().size(); i++) {
			if(session_score.getData().get(i).getMemCode().equalsIgnoreCase(val)) {
				
				

				if (config.getIpAddress().equalsIgnoreCase("LOCALHOST")) {
					
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$ROF_GRP$ROF$AllDataGrp$Data$Header"
							+ "$Image_Name_Grp$Image$Img_Image*TEXTURE*IMAGE SET " + photo_path + "Blank" + ".png" + "\0");
					
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$ROF_GRP$ROF$AllDataGrp$Data$Header"
							+ "$Image_Name_Grp$Image$Img_Image*TEXTURE*IMAGE SET " + photo_path + session_score.getData().get(i).getMemCode() + ".png" + "\0");
				}else {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$ROF_GRP$ROF$AllDataGrp$Data$Header"
							+ "$Image_Name_Grp$Image$Img_Image*TEXTURE*IMAGE SET " + "\\\\"+ config.getIpAddress() + "\\\\" + local_photo_path + "Blank" + ".png" + "\0");
					
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$ROF_GRP$ROF$AllDataGrp$Data$Header"
							+ "$Image_Name_Grp$Image$Img_Image*TEXTURE*IMAGE SET " + "\\\\"+ config.getIpAddress() + "\\\\" + local_photo_path + session_score.getData().get(i).getMemCode() + ".png" + "\0");
					}
				
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$ROF_GRP$ROF$AllDataGrp$Data$Header$Image_Name_Grp$NameGrp$txt_LastName*GEOM*TEXT SET " 
						+ session_score.getData().get(i).getMemName() + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$ROF_GRP$ROF$AllDataGrp$Data$Header$Other_Sub_Heads$txt_SubHead01*GEOM*TEXT SET " 
						+ "ROUND " + session_score.getData().get(i).getTourDay() + "\0");	
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$ROF_GRP$ROF$AllDataGrp$Data$Header$Other_Sub_Heads$txt_SubHead02*GEOM*TEXT SET " 
						+ "PAR" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$ROF_GRP$ROF$AllDataGrp$Data$Header$Other_Sub_Heads$txt_SubHead03*GEOM*TEXT SET " 
						+ "PAR" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$ROF_GRP$ROF$AllDataGrp$Data$Header$Other_Sub_Heads$txt_SubHead04*GEOM*TEXT SET " 
						+ "FRONT 9" + "\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$ROF_GRP$ROF$AllDataGrp$Data$Header$Other_Sub_Heads$txt_SubHead05*GEOM*TEXT SET " 
						+ "BACK 9" + "\0");
				

				GolfCourseResponse.Course course = session_course.getData().get(0);

				List<String> pars = course.getAllPars();
				
				for(int j = 1; j <= 9; j++)
				{
				    String sFront = getS(j, session_score.getData(), i);
				    String sBack  = getS((j+9), session_score.getData(), i);
				    
				    String front = pars.get(j-1);      
				    String back  = pars.get((j-1) + 9);

				    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$ROF_GRP$ROF$AllDataGrp$Data$Data$DataGrp$Row" + j + 
				    		"$Dehighlight$img_Text2$txt_Data2*GEOM*TEXT SET " + (sFront == null ? "" : sFront) + "\0");
				    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$ROF_GRP$ROF$AllDataGrp$Data$Data$DataGrp$Row" + j + 
				    		"$Dehighlight$img_Text2$txt_Data3*GEOM*TEXT SET " + (sBack == null ? "" : sBack) + "\0");
				    
				    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$ROF_GRP$ROF$AllDataGrp$Data$Data$DataGrp$Row" + j + 
				    		"$Dehighlight$img_Text2$txt_Data1*GEOM*TEXT SET " + (front == null ? "" : front) + "\0");
				    print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$ROF_GRP$ROF$AllDataGrp$Data$Data$DataGrp$Row" + j + 
				    		"$Dehighlight$img_Text2$txt_Data4*GEOM*TEXT SET " + (back == null ? "" : back) + "\0");
				}
				
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$ROF_GRP$ROF$AllDataGrp$Data$BottomData$txt_FrontTotal*GEOM*TEXT SET "
				+  (session_score.getData().get(i).getSout() == null ? "0" : session_score.getData().get(i).getSout()) + "\0");
				
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$ROF_GRP$ROF$AllDataGrp$Data$BottomData$txt_BackTotal*GEOM*TEXT SET "
						+  (session_score.getData().get(i).getSin() == null ? "0" : session_score.getData().get(i).getSin()) + "\0");
				
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$ROF_GRP$ROF$AllDataGrp$Data$BottomData$txt_Total*GEOM*TEXT SET "
						+ "TOTAL " + "\0");
				
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$ROF_GRP$ROF$AllDataGrp$Data$BottomData$txt_Total_Dig*GEOM*TEXT SET "
						+ (session_score.getData().get(i).getScore() == null ?"-":
					 		0 == Integer.parseInt(session_score.getData().get(i).getScore()) ? "PAR" : session_score.getData().get(i).getScore()) + "\0");
			}
		}
		

		print_writer.println("-1 RENDERER PREVIEW SCENE*" + "/Default/Overlays"
			    + " C:/Temp/Preview.png ROF$In_Out 1.180 "
			    + "ROF$In_Out$In 1.152 ROF$In_Out$In$DataIn 1.152 \0");
		
	}
	
	
	public void populateloffplayerdetail(PrintWriter print_writer, GolfScoresResponse session_score, GolfTourResponse session_tour, 
			GolfEntryListResponse session_entry_list, GolfCourseResponse session_course, GolfDrawsResponse session_draws,String val,String selectedbroadcaster) {
		
		
		
		System.out.println("valut to process" + val);
		if (session_score == null) {
			System.out.println("ERROR: Lt-Match -> Match is null");
		} else {
			
			/*
			 * if(which_graphics_onscreen != null) { CricketFunctions.
			 * DoadWriteCommandToAllViz("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$Players$PlayerAll"
			 * + (i + 1) + "*TRANSFORMATION" + "*POSITION*X SET " + ScaleX + "\0",
			 * print_writers); CricketFunctions.
			 * DoadWriteCommandToAllViz("-1 RENDERER*TREE*$Main$All$Geom_GroundAll$RotationGrp$Players$PlayerAll"
			 * + (i + 1) + "*TRANSFORMATION" + "*POSITION*Z SET " + ScaleY + "\0",
			 * print_writers);
			 * 
			 * 
			 * print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" +
			 * whichSide +
			 * "$LT_Left$Header$Header_Band$Logo_Header_Grp$Txt_Header01*GEOM*TEXT SET " +
			 * session_score.getData().get(i).getMemName() + "\0"); }
			 */			
			
			if(which_graphics_onscreen == "TOP5_LEADREBOARD") {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide + "$LT_Left*TRANSFORMATION*POSITION*X SET -621.0\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide + "$LT_Left*TRANSFORMATION*POSITION*Y SET -379.0\0");
			}else {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide + "$LT_Left*TRANSFORMATION*POSITION*x SET -574.0\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide + "$LT_Left*TRANSFORMATION*POSITION*Y SET -620.0\0");
			}
			
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide + "$Select*FUNCTION*Omo*vis_con SET 1\0");
			for(int i=0;i<session_score.getData().size(); i++) {
				if(session_score.getData().get(i).getMemCode().equalsIgnoreCase(val.split(",")[0])) {
					
					 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide +
						        "$LT_Left$Header$Header_Band$Logo_Header_Grp$Txt_Header01*GEOM*TEXT SET " + session_score.getData().get(i).getMemName() + "\0");
					 
					 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide +
						        "$LT_Left$Header$Header_Band$Logo_Header_Grp$Txt_Number*GEOM*TEXT SET " + Integer.valueOf(val.split(",")[1]) + "\0");
					 
					 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide +
						        "$LT_Left$Header$Header_Band$Txt_Header02*GEOM*TEXT SET " + (session_score.getData().get(i).getScore() == null ?"-":
							 		0 == Integer.parseInt(session_score.getData().get(i).getScore()) ? "PAR" : session_score.getData().get(i).getScore()) + "\0");
					 
					 
					 
						/*
						 * print_writer.println( "-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side"
						 * + whichSide +
						 * "$LT_Left$Header$LowerInfo_grp$LowerInfo$Txt_Info02*GEOM*TEXT SET " +
						 * (!val.split(",")[1].equalsIgnoreCase("EMPTY") ?
						 * val.split(",")[1].toUpperCase() : "") + "\0");
						 */
				}
				for (int j = 0; j < session_course.getData().size(); j++) {
					 List<String> pars = session_course.getData().get(j).getAllPars();
					 System.out.println(val);
					 System.out.println(pars.size());
					 for(int h=1;h<pars.size() +1;h++) {
						 if (Integer.valueOf((val.split(",")[1])) == h) {
							 String pValue = pars.get(h-1);
							
							 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide +
								        "$LT_Left$Header$LowerInfo_grp$LowerInfo$Txt_Info01*GEOM*TEXT SET " + "PAR "+ pValue + "\0"); 
						 }
					 }
				}
			}		
	}
		
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + "/Default/Overlays"
			    + " C:/Temp/Preview.png Anim_Overlays$LT_Small$In_Out 1.140 "
			    + "Anim_Overlays$LT_Small$In_Out$In 0.840 \0");
	}
	public void populateloffplayerextrapart(PrintWriter print_writer, GolfScoresResponse session_score, GolfTourResponse session_tour, 
			GolfEntryListResponse session_entry_list, GolfCourseResponse session_course, GolfDrawsResponse session_draws,String val,String selectedbroadcaster) {
		
		
		
		System.out.println("valut to process" + val);
		if (session_score == null) {
			System.out.println("ERROR: Lt-Match -> Match is null");
		} else {	
			
			if(which_graphics_onscreen == "TOP5_LEADREBOARD") {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide + "$LT_Left*TRANSFORMATION*POSITION*X SET -621.0\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide + "$LT_Left*TRANSFORMATION*POSITION*Y SET -379.0\0");
			}else {
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide + "$LT_Left*TRANSFORMATION*POSITION*x SET -574.0\0");
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide + "$LT_Left*TRANSFORMATION*POSITION*Y SET -620.0\0");
			}
			
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide + "$Select*FUNCTION*Omo*vis_con SET 1\0");
			
			if(val.split(",")[0].equalsIgnoreCase("EMPTY")) {
				
			}else {
				String part = val.split(",")[0].trim();

				String result = part;

				print_writer.println(
				    "-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT_Small$Side" + whichSide +
				    "$LT_Left$Header$LowerInfo_grp$LowerInfo$Txt_Info02*GEOM*TEXT SET " +
				    result.toUpperCase() + "\0");
			}
			
				
	}
		
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + "/Default/Overlays"
			    + " C:/Temp/Preview.png Anim_Overlays$LT_Small$ExtraData$In_Out 0.500 "
			    + "Anim_Overlays$LT_Small$ExtraData$In_Out$In 0.500 \0");
	}
	
	
	@SuppressWarnings("unused")
	public void populateTopfive(PrintWriter print_writer, GolfScoresResponse session_score, GolfTourResponse session_tour, 
			GolfEntryListResponse session_entry_list, GolfCourseResponse session_course, GolfDrawsResponse session_draws, String viz_sence_path,String selectedbroadcaster) {
		if (session_score == null) {
			System.out.println("ERROR: Lt-Match -> Match is null");
		} else {
			
			
			 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide + "$Select*FUNCTION*Omo*vis_con SET 1\0");
			 //headre part
			 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide +
				        "$LT_Leaderboard$Header$Logo_Header_Grp$Txt_Header01*GEOM*TEXT SET " + session_tour.getData().get(0).getTourName().toUpperCase() + "\0");
			 int rank = 0;
			    int displayRank = 0;
			    int prevScore = Integer.MIN_VALUE;
			    String dataRank="";
			 
			for(int i = 0;i<5;i++) {
				
				String fullName = session_score.getData().get(i).getMemName();

				String firstName = fullName;
				String lastName = "";

				if(fullName != null && fullName.contains(" ")) {
				    int lastSpace = fullName.lastIndexOf(" ");
				    firstName = fullName.substring(0, lastSpace);
				    lastName = fullName.substring(lastSpace + 1);
				}
				String sd1 = session_score.getData().get(i).getSd1();
				String sd2 = session_score.getData().get(i).getSd2();
				String sd3 = session_score.getData().get(i).getSd3();
				String sd4 = session_score.getData().get(i).getSd4();

				int v1 = sd1 == null ? 0 : Integer.parseInt(sd1);
				int v2 = sd2 == null ? 0 : Integer.parseInt(sd2);
				int v3 = sd3 == null ? 0 : Integer.parseInt(sd3);
				int v4 = sd4 == null ? 0 : Integer.parseInt(sd4);
				int total = v1 + v2 + v3 + v4;
				
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide +
				        "$LT_Leaderboard$Data_All$Data$"+ (i+1) +
				        "$PlayerData$Name_GRp$Txt_FirstName*GEOM*TEXT SET " + firstName + "\0");

				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide +
				        "$LT_Leaderboard$Data_All$Data$"+ (i+1) +
				        "$PlayerData$Name_GRp$Txt_LastName*GEOM*TEXT SET " + lastName + "\0");
				
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide +
				        "$LT_Leaderboard$Data_All$Data$"+ (i+1) +
				        "$PlayerData$Info_Grp$Info_Data_Grp$Txt_Info01*GEOM*TEXT SET " + (session_score.getData().get(i).getScore() == null ?"-":
					 		0 == Integer.parseInt(session_score.getData().get(i).getScore()) ? "PAR" : session_score.getData().get(i).getScore()) + "\0");
				int totalScore = Integer.valueOf(session_score.getData().get(i).getScore());
				String scoreValue;
				if(totalScore > 0){
		            scoreValue = "+" + totalScore;
		        }
		        else if(totalScore < 0){
		            scoreValue = String.valueOf(totalScore);
		        }
		        else{
		            scoreValue = "0";
		        }
				rank++;
		        if(i == 0){
		            displayRank = 1;
		        }
		        else if(totalScore != prevScore){
		            displayRank = rank;
		        }
		        boolean isTie = false;

		        if(i > 0 && totalScore == prevScore){
		            isTie = true;
		        }
		        if (i < session_score.getData().size() - 1) {

		            int nextScore = Integer.valueOf(
		                session_score.getData().get(i + 1).getScore()
		            );

		            if (nextScore == totalScore) {
		                isTie = true;
		            }
		        }
		        if(isTie){
		        	dataRank = "T" + displayRank;
		        }else{
		        	dataRank = String.valueOf(displayRank);
		        }
		        prevScore = totalScore;
				
				print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide +
				        "$LT_Leaderboard$Data_All$Data$"+ (i+1) +
				        "$PlayerData$Txt_Rank*GEOM*TEXT SET " + dataRank + "\0");
				
				if(Integer.valueOf(session_score.getData().get(i).getHole()) >=18) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide +
					        "$LT_Leaderboard$Data_All$Data$"+ (i+1) +
					        "$PlayerData$Info_Grp$Info_Data_Grp$Txt_Info02*GEOM*TEXT SET " + total + "\0");
				}else {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide +
					        "$LT_Leaderboard$Data_All$Data$"+ (i+1) +
					        "$PlayerData$Info_Grp$Info_Data_Grp$Txt_Info02*GEOM*TEXT SET " + "(" + session_score.getData().get(i).getHole() + ")" + "\0");
				}
				
				
				//header part
				
			}
			
		}
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + "/Default/Overlays"
			    + " C:/Temp/Preview.png Anim_Overlays$LT$In_Out 1.140 Anim_Overlays$LT$In_Out$Header 1.140 "
			    + "Anim_Overlays$LT$In_Out$Header$In 0.840 Anim_Overlays$LT$In_Out$Bottom 1.140 "
			    + "Anim_Overlays$LT$In_Out$Bottom$In 1.140 \0");
	}
	
	/*
	 * public void populateLOFPlayerDetails(PrintWriter print_writer,
	 * GolfScoresResponse session_score, GolfTourResponse session_tour,
	 * GolfEntryListResponse session_entry_list, GolfCourseResponse session_course,
	 * GolfCourseResponse session_draws, String viz_sence_path,String
	 * selectedbroadcaster) { if (session_score == null) {
	 * System.out.println("ERROR: Lt-Match -> Match is null"); } else {
	 * 
	 * System.out.println(session_tour.getData().get(0).getTourName());
	 * System.out.println(session_entry_list.getData().get(0).getMemName());
	 * System.out.println(session_course.getData().get(0).getCourseName());
	 * 
	 * } }
	 */
	
	
	public void populatedrawsdata(PrintWriter print_writer, GolfScoresResponse session_score, GolfTourResponse session_tour, 
			GolfEntryListResponse session_entry_list, GolfCourseResponse session_course, GolfDrawsResponse session_draws, String val,String selectedbroadcaster,Configurations config) {
	 
		int count = 0;
		System.out.println("coming " + val);
		if (session_draws != null && session_draws.getData() != null) {

	        for (GolfDrawsResponse.DrawPlayer ps : session_draws.getData()) {

	            String matchNo = ps.getTourSrno();

	            if (matchNo != null && matchNo.equals(val)) {

	                System.out.println("Player = " + ps.getMemName());
	                count++;
	            }
	        }
	    }
		 print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Select*FUNCTION*Omo*vis_con SET 1\0");
		
		System.out.println("count" + count);
		if(count == 2) {
			 print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Select$Top3$Select*FUNCTION*Omo*vis_con SET 1\0");
		}else {
			 print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Select$Top3$Select*FUNCTION*Omo*vis_con SET 0\0");
		}
		
		 print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Select$Top3$Select$" + count + "$Select*FUNCTION*Omo*vis_con SET 1\0");
		 
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Header$HeaderData_grp"
		 		+ "$Txt_Header*GEOM*TEXT SET " + "ROUND " + session_draws.getData().get(0).getTourDay() + " - " + "MATCH NO. " + val +"\0");
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Header$HeaderData_grp"
		 		+ "$Txt_SubHeader*GEOM*TEXT SET " + "" + "\0");
		
		print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Header$Logo_Grp"
				+ "$img_Logo*TEXTURE*IMAGE SET " + logo_path + "\0");
		
		 int index = 0;

		    for (GolfDrawsResponse.DrawPlayer ps : session_draws.getData()) {

		        if (val.equals(ps.getTourSrno())) {

		            String fullName = ps.getMemName();

		            String firstName = fullName;
		            String lastName = "";

		            if (fullName != null && fullName.contains(" ")) {

		                int lastSpace = fullName.lastIndexOf(" ");

		                firstName = fullName.substring(0, lastSpace);
		                lastName = fullName.substring(lastSpace + 1);
		            }

		            print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Top3$" + count + "$NoScore$" + (index+1) + "$LeaderBoardData$select_DataType$RestData$DataAll"
					 		+ "$Txt_FirstName*GEOM*TEXT SET "+ firstName + " " + lastName + "\0");
		            print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Top3$" + count + "$NoScore$" +  (index+1) +"$LeaderBoardData$select_DataType$RestData$DataAll"
					 		+ "$Number$Txt_Number*GEOM*TEXT SET " +  " " + "\0");
		            
		           
		            if (config.getIpAddress().equalsIgnoreCase("LOCALHOST")) {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Top3$Select$" + count + "$NoScore$" + (index+1)
								+ "$DataAll$Number$Image*TEXTURE*IMAGE SET " + photo_path + "Blank" + ".png" + "\0");
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Top3$Select$"  + count + "$NoScore$" + (index+1)
								+ "$DataAll$Number$Image*TEXTURE*IMAGE SET " + photo_path + ps.getMemCode() + ".png" + "\0");
					}else {
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Top3$Select$" + count + "$NoScore$"  + (index+1)
								+ "$DataAll$Number$Image*TEXTURE*IMAGE SET " + "\\\\"+ config.getIpAddress() 
								+ "\\\\" + local_photo_path + "Blank" + ".png" + "\0");
						
						
						print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Top3$Select$" + count + "$NoScore$" + (index+1)
								+ "$DataAll$Number$Image*TEXTURE*IMAGE SET " + "\\\\"+ config.getIpAddress() + "\\\\" + 
								local_photo_path + ps.getMemCode() + ".png" + "\0");
						}
					

		            index++;
		        }
		    }
		
		
//		    print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Top3$4$LeaderBoardData$Txt_FirstName"
//			 		+ "*GEOM*TEXT SET " + session_tour.getData().get(0).getCourseName().toUpperCase() + ", " + session_tour.getData().get(0).getCourseVenue().toUpperCase()  + "\0");
//		    
		    
		    print_writer.println("-1 RENDERER*BACK_LAYER*TREE*$main$All$Front$Data$Top3$4$LeaderBoardData$Txt_FirstName"
			 		+ "*GEOM*TEXT SET " + "ZION HILLS GOLF COUNTY, KOLAR, KARNATAKA"  + "\0");
		    print_writer.println("-1 RENDERER PREVIEW SCENE*" + "/Default/Fullframes"
				    + " C:/Temp/Preview.png Anim_Fullframe$In_Out 1.960 Anim_Fullframe$In_Out$In 1.960 Anim_Fullframe$In_Out$In$In 1.540 \0");
		
	}
	
	public void populatenamesuper(PrintWriter print_writer, GolfScoresResponse session_score, GolfTourResponse session_tour, 
			GolfEntryListResponse session_entry_list, GolfCourseResponse session_course, GolfDrawsResponse session_draws, String val,String selectedbroadcaster) {
		if (session_score == null) {
			System.out.println("ERROR: Lt-Match -> Match is null");
		} else {
			
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide + "$Select*FUNCTION*Omo*vis_con SET 2\0");
			
			for (int i=0;i< session_score.getData().size();i++) {
				if(session_score.getData().get(i).getMemCode().equalsIgnoreCase(val)) {
					print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide +
					        "$LT_Super$Header$Header_Band$Txt_Header01*GEOM*TEXT SET " + session_score.getData().get(i).getMemName() + "\0");
				}
			}
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side1$Select"
					+ "$LT_Super$Header$Header_Band$Logo_Header_Grp$Img_Logo*TEXTURE*IMAGE SET " + logo_path + "\0");
			 
			 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide +
				        "$LT_Super$Header$Header_Band$Txt_Header02*GEOM*TEXT SET " + "WINNER" + "\0");
			 
			 print_writer.println("-1 RENDERER PREVIEW SCENE*" + "/Default/Overlays"
					    + " C:/Temp/Preview.png Anim_Overlays$LT$In_Out 1.140 Anim_Overlays$LT$In_Out$Header 1.140 "
					    + "Anim_Overlays$LT$In_Out$Header$In 0.840 Anim_Overlays$LT$In_Out$Bottom 1.140 "
					    + "Anim_Overlays$LT$In_Out$Bottom$In 1.140 \0");
		}
	}
	
	public void populatenamesuperfreetext(PrintWriter print_writer, GolfScoresResponse session_score, GolfTourResponse session_tour, 
			GolfEntryListResponse session_entry_list, GolfCourseResponse session_course, GolfDrawsResponse session_draws, String val,String selectedbroadcaster) {
		if (session_score == null) {
			System.out.println("ERROR: Lt-Match -> Match is null");
		} else {
			
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide + "$Select*FUNCTION*Omo*vis_con SET 2\0");
			
			print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side1$Select"
					+ "$LT_Super$Header$Header_Band$Logo_Header_Grp$Img_Logo*TEXTURE*IMAGE SET " + logo_path + "\0");
			
			 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide +
				        "$LT_Super$Header$Header_Band$Txt_Header01*GEOM*TEXT SET " + val.split(",")[0] + "\0");
			 
			 print_writer.println("-1 RENDERER*FRONT_LAYER*TREE*$Overlays$LT$Side" + whichSide +
				        "$LT_Super$Header$Header_Band$Txt_Header02*GEOM*TEXT SET " + val.split(",")[1] + "\0");
			 
			 print_writer.println("-1 RENDERER PREVIEW SCENE*" + "/Default/Overlays"
					    + " C:/Temp/Preview.png Anim_Overlays$LT$In_Out 1.140 Anim_Overlays$LT$In_Out$Header 1.140 "
					    + "Anim_Overlays$LT$In_Out$Header$In 0.840 Anim_Overlays$LT$In_Out$Bottom 1.140 "
					    + "Anim_Overlays$LT$In_Out$Bottom$In 1.140 \0");
		}
	}
	public String getS(int index, List<PlayerScore> player, int i) {
	    switch (index) {
	        case 1: return player.get(i).getS1();
	        case 2: return player.get(i).getS2();
	        case 3: return player.get(i).getS3();
	        case 4: return player.get(i).getS4();
	        case 5: return player.get(i).getS5();
	        case 6: return player.get(i).getS6();
	        case 7: return player.get(i).getS7();
	        case 8: return player.get(i).getS8();
	        case 9: return player.get(i).getS9();
	        case 10: return player.get(i).getS10();
	        case 11: return player.get(i).getS11();
	        case 12: return player.get(i).getS12();
	        case 13: return player.get(i).getS13();
	        case 14: return player.get(i).getS14();
	        case 15: return player.get(i).getS15();
	        case 16: return player.get(i).getS16();
	        case 17: return player.get(i).getS17();
	        case 18: return player.get(i).getS18();
	        default: return "";
	    }
	}
}
