package com.golf.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.golf.broadcaster.PGTI;
import com.golf.containers.Scene;
import com.golf.containers.ScoreBug;
import com.golf.model.Clock;
import com.golf.model.Configurations;
import com.golf.model.Fixture;
import com.golf.model.GolfCourseResponse;
import com.golf.model.GolfDrawsResponse;
import com.golf.model.GolfEntryListResponse;
import com.golf.model.GolfScoresResponse;
import com.golf.model.GolfScoresResponse.PlayerScore;
import com.golf.model.GolfTourResponse;
import com.golf.service.GolfService;
import com.golf.util.GolfFunctions;
import com.golf.util.GolfUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class IndexController 
{
	@Autowired
	GolfService golfService;
	
	public static String expiry_date = "2026-12-31";
	public static String current_date = "";
	public static String error_message = "";
	public static PGTI this_PGTI;
	public static Clock session_clock = new Clock();
	public static Configurations session_Configurations = new Configurations();
	
	List<Scene> session_selected_scenes = new ArrayList<Scene>();
	public static String session_selected_broadcaster;
	public static Socket session_socket;
	public static PrintWriter print_writer;
	public static List<File> all_match_files;
	public static List<Fixture> all_db_fixture;
	public static File this_file = null;
	public static String MatchFile = "";
	public static String currentScoreFile = GolfUtil.SESSION_SCORE;
	public static String currentdrawsFile = GolfUtil.SESSION_DRAWS;
	public static ObjectMapper mapper = new ObjectMapper();
	public static Configurations session_configuration = new Configurations();
	public static JSONArray top10Array = new JSONArray();
	public static GolfTourResponse session_tour = new GolfTourResponse();
	public static GolfScoresResponse session_score = new GolfScoresResponse();
	public static GolfEntryListResponse session_entry_list = new GolfEntryListResponse();
	public static GolfCourseResponse session_course = new GolfCourseResponse();
	public static GolfDrawsResponse session_draws = new GolfDrawsResponse();
	
	@RequestMapping(value = {"/","/initialise"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String initialisePage(ModelMap model) throws JAXBException, IOException, ParseException 
	{		
		if(current_date == null || current_date.isEmpty()) {
			current_date = GolfFunctions.getOnlineCurrentDate();
		}
		
		all_db_fixture = golfService.getFixtures();
		
		if(new File(GolfUtil.GOLF_DIRECTORY + GolfUtil.CONFIGURATIONS_DIRECTORY + GolfUtil.OUTPUT_XML).exists()) {
			session_Configurations = (Configurations)JAXBContext.newInstance(
					Configurations.class).createUnmarshaller().unmarshal(
					new File(GolfUtil.GOLF_DIRECTORY + GolfUtil.CONFIGURATIONS_DIRECTORY 
					+ GolfUtil.OUTPUT_XML));
		} else {
			session_Configurations = new Configurations();
			JAXBContext.newInstance(Configurations.class).createMarshaller().marshal(session_Configurations, 
					new File(GolfUtil.GOLF_DIRECTORY + GolfUtil.CONFIGURATIONS_DIRECTORY + 
					GolfUtil.OUTPUT_XML));
		}
		
		model.addAttribute("session_Configurations",session_Configurations);
	
		return "initialise";
	}
	
	@RequestMapping(value = {"/Help"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String HelpPage()  
	{
		return "Help";
	}
	@RequestMapping(value = {"/match"}, method = {RequestMethod.POST,RequestMethod.GET})
	public String golfMatchPage(ModelMap model, 
		@RequestParam(value = "selectedBroadcaster", required = false, defaultValue = "") String selectedBroadcaster,
		@RequestParam(value = "vizIPAddress", required = false, defaultValue = "") String vizIPAddresss,
		@RequestParam(value = "vizPortNumber", required = false, defaultValue = "") int vizPortNumber,
		@RequestParam(value = "vizScene", required = false, defaultValue = "") String vizScene)
			throws IOException, ParseException, JAXBException, InterruptedException  
	{
		if(current_date == null || current_date.isEmpty()) {
		
			model.addAttribute("error_message","You must be connected to the internet online");
			return "error";
		
		} else if(new SimpleDateFormat("yyyy-MM-dd").parse(expiry_date).before(new SimpleDateFormat("yyyy-MM-dd").parse(current_date))) {
			
			model.addAttribute("error_message","This software has expired");
			return "error";
			
		}else {
			
			session_tour = readSafely(GolfUtil.GOLF_DIRECTORY + GolfUtil.SESSION_TOUR, GolfTourResponse.class);

			session_score = readSafely(GolfUtil.GOLF_DIRECTORY + GolfUtil.SESSION_SCORE, GolfScoresResponse.class);

			session_entry_list = readSafely(GolfUtil.GOLF_DIRECTORY + GolfUtil.SESSION_ENTRY_LIST, GolfEntryListResponse.class);

			session_course = readSafely(GolfUtil.GOLF_DIRECTORY + GolfUtil.SESSION_COURSE, GolfCourseResponse.class);
			
			session_draws = readSafely(GolfUtil.GOLF_DIRECTORY + GolfUtil.SESSION_DRAWS, GolfDrawsResponse.class);
			
			session_configuration = new Configurations(selectedBroadcaster, vizIPAddresss, vizPortNumber,"");
			
			model.addAttribute("licence_expiry_message",
				"Software licence expires on " + new SimpleDateFormat("E, dd MMM yyyy").format(
				new SimpleDateFormat("yyyy-MM-dd").parse(expiry_date)));
			
			session_Configurations.setBroadcaster(selectedBroadcaster);
			session_Configurations.setVizscene(vizScene);
			session_Configurations.setIpAddress(vizIPAddresss);

			if(!vizIPAddresss.isEmpty()) {
				session_Configurations.setPortNumber(Integer.valueOf(vizPortNumber));
				session_socket = new Socket(vizIPAddresss, Integer.valueOf(vizPortNumber));
				print_writer = new PrintWriter(session_socket.getOutputStream(), true);
				switch (session_selected_broadcaster.toUpperCase()) {
				case GolfUtil.PGTI:
					session_selected_scenes.add(new Scene("/Default/Overlays","FRONT_LAYER")); // Front layer
					session_selected_scenes.add(new Scene("/Default/Fullframes","BACK_LAYER"));
					session_selected_scenes.get(0).scene_load(print_writer, session_selected_broadcaster);
					session_selected_scenes.get(1).scene_load(print_writer, session_selected_broadcaster);
					this_PGTI = new PGTI();
					this_PGTI.scorebug = new ScoreBug();
					this_PGTI.AnimateRest(print_writer);
					break;
				}
			}
			session_selected_broadcaster = selectedBroadcaster;
			
			JAXBContext.newInstance(Configurations.class).createMarshaller().marshal(session_Configurations, 
					new File(GolfUtil.GOLF_DIRECTORY + GolfUtil.CONFIGURATIONS_DIRECTORY + GolfUtil.OUTPUT_XML));
			
			model.addAttribute("session_selected_broadcaster", session_selected_broadcaster);

			return "match";
		}
	}
	
	@RequestMapping(value = {"/processGolfProcedures"}, method={RequestMethod.GET,RequestMethod.POST})    
	public @ResponseBody String processGolfProcedures(
			@RequestParam(value = "whatToProcess", required = false, defaultValue = "") String whatToProcess,
			@RequestParam(value = "valueToProcess", required = false, defaultValue = "") String valueToProcess)throws Exception
	{	
		switch (whatToProcess.toUpperCase()) {
		case "LOAD_MATCH":
			System.out.println("val is  "  + valueToProcess);
		    currentScoreFile = valueToProcess.trim();
		    File scoreFile = new File(GolfUtil.GOLF_DIRECTORY + currentScoreFile);
		    if(scoreFile.exists()){
		        session_score = mapper.readValue(scoreFile, GolfScoresResponse.class);
		    }
		    
		    String num = valueToProcess
		            .replace("SCOREDATA", "")
		            .replace(".json", "");
		    System.out.println("num" + num);	
		    currentdrawsFile = "DRAWSDATA" + num + ".json";
		    System.out.println(currentdrawsFile);
		    
		    File drawsfile = new File(GolfUtil.GOLF_DIRECTORY + currentdrawsFile);
		    if(drawsfile.exists()){
		        session_draws = mapper.readValue(drawsfile, GolfDrawsResponse.class);
		    }
		    return null;
		case "POPULATE-NAMESUPER": case "POPULATE-LOF-PLAYER_DETAILS": case "POPULATE-COF-PLAYER_DETAILS": case "POPULATE-PLAYER_ROUND_DETAILS":
		    List<Map<String,String>> players = new ArrayList<>();

		    if(session_score != null && session_score.getData() != null){
		        for(PlayerScore ps : session_score.getData()){
		        	Map<String,String> player = new HashMap<>();
		            player.put("name", ps.getMemName());
		            player.put("code", ps.getMemCode());
		            
		            players.add(player);
		        }
		    }
		    Map<String,Object> obj =new HashMap<>();
		    obj.put("players", players);
		    return JSONObject.fromObject(obj).toString();
		   
		case "POPULATE-DRAWDATA-MATCH":

		    Set<String> matchSet = new LinkedHashSet<>();

		    if (session_draws != null && session_draws.getData() != null) {

		        for (GolfDrawsResponse.DrawPlayer ps : session_draws.getData()) {

		            String matchNo = ps.getTourSrno();

		            if (matchNo != null && !matchNo.isEmpty()) {
		                matchSet.add(matchNo);
		            }
		        }
		    }


		    List<Map<String, String>> matches = new ArrayList<>();

		    for (String matchNo : matchSet) {

		        Map<String, String> m = new HashMap<>();
		        m.put("match", matchNo);

		        matches.add(m);
		    }


		    Map<String, Object> obj2 = new HashMap<>();
		    obj2.put("matches", matches);

		    return JSONObject.fromObject(obj2).toString();
		   
		case "POPULATE-HOLE_DETAILS":
		    JSONObject obj1 = new JSONObject();
		    obj1.put("status", "ok");
		    return obj1.toString();
		    
		case "READ-MATCH-AND-POPULATE":
			session_score = readSafely(GolfUtil.GOLF_DIRECTORY + currentScoreFile, GolfScoresResponse.class);
			session_draws = readSafely(GolfUtil.GOLF_DIRECTORY + currentdrawsFile, GolfDrawsResponse.class);
		    JSONObject headerData = new JSONObject();
		    
		    headerData.put("broadcaster", session_selected_broadcaster);
		    
		    String tournamentName = "";
		    if (session_tour != null && session_tour.getData() != null && !session_tour.getData().isEmpty()) {
		        tournamentName = session_tour.getData().get(0).getTourName();
		    }
		    headerData.put("tournament", tournamentName);
		    
		    String courseName = "";
		    if (session_course != null && session_course.getData() != null && !session_course.getData().isEmpty()) {
		        courseName = session_course.getData().get(0).getCourseName();
		    }
		    headerData.put("course", courseName);
		    
		    JSONArray parArray = new JSONArray();

		    if (session_course != null &&
		        session_course.getData() != null &&
		        !session_course.getData().isEmpty()) {
		    	
		        List<String> pars = session_course.getData().get(0).getAllPars();
		        for (String p : pars) {
		            parArray.add(p);
		        }
		    }
		    headerData.put("parRow", parArray);
		    
		    List<PlayerScore> playerList = new ArrayList<>();
		    if(session_score != null && session_score.getData() != null){
		        playerList = session_score.getData();
		    }
		    
		    Collections.sort(playerList, new GolfFunctions.leaderboardComparator());
		   
		    top10Array.clear();
		    
		    int rank = 0;
		    int displayRank = 0;
		    int prevScore = Integer.MIN_VALUE;
		    int limit = Math.min(10, playerList.size());
		    for (int i = 0; i < limit; i++) {
		        PlayerScore ps = playerList.get(i);
		        JSONObject playerJson = new JSONObject();
		        playerJson.put("name", ps.getMemName());
		        String[] strokeRounds = {ps.getSd1(), ps.getSd2(), ps.getSd3(), ps.getSd4()};
		        String[] scoreRounds = {ps.getPd1(), ps.getPd2(), ps.getPd3(), ps.getPd4()};
		        int round = Integer.parseInt(currentScoreFile.replaceAll("\\D", ""));
		        int totalStrokes = 0;
		        int totalScore = 0;

		        for(int j = 0; j < round; j++){
		            if(strokeRounds[j] != null && !strokeRounds[j].trim().isEmpty()){
		                totalStrokes += Integer.parseInt(strokeRounds[j]);
		            }
		            if(scoreRounds[j] != null && !scoreRounds[j].trim().isEmpty()){
		                totalScore += Integer.parseInt(scoreRounds[j]);
		            }
		        }

		        String totalValue = totalStrokes == 0 ? "-" : String.valueOf(totalStrokes);

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
		        if(i < limit - 1){
		            PlayerScore nextPs = playerList.get(i+1);
		            int nextScore = 0;
		            String[] nextRounds = {nextPs.getPd1(), nextPs.getPd2(), nextPs.getPd3(), nextPs.getPd4()};

		            for(int j=0;j<round;j++){
		                if(nextRounds[j] != null && !nextRounds[j].trim().isEmpty()){
		                    nextScore += Integer.parseInt(nextRounds[j]);
		                }
		            }
		            if(nextScore == totalScore){
		                isTie = true;
		            }
		        }
		        if(isTie){
		            playerJson.put("rank", "T" + displayRank);
		        }else{
		            playerJson.put("rank", displayRank);
		        }
		        prevScore = totalScore;

		        playerJson.put("total", totalValue);
		        playerJson.put("score", scoreValue);

		        playerJson.put("sd1", round >= 1 ? ps.getSd1() : "-");
		        playerJson.put("sd2", round >= 2 ? ps.getSd2() : "-");
		        playerJson.put("sd3", round >= 3 ? ps.getSd3() : "-");
		        playerJson.put("sd4", round >= 4 ? ps.getSd4() : "-");

		        playerJson.put("currentHole", ps.getHole());

		        List<String> holes = ps.getAllHoleScores();
		        JSONArray holeArray = new JSONArray();

		        for (String hole : holes) {
		            holeArray.add(hole);
		        }
		        playerJson.put("holes", holeArray);

		        top10Array.add(playerJson);
		    }
		    headerData.put("top10", top10Array);
		    
		    return headerData.toString();
		case "RE_READ":
			session_tour = readSafely(GolfUtil.GOLF_DIRECTORY + GolfUtil.SESSION_TOUR, GolfTourResponse.class);

			session_score = readSafely(GolfUtil.GOLF_DIRECTORY + currentScoreFile, GolfScoresResponse.class);

			session_entry_list = readSafely( GolfUtil.GOLF_DIRECTORY + GolfUtil.SESSION_ENTRY_LIST, GolfEntryListResponse.class);

			session_course = readSafely(GolfUtil.GOLF_DIRECTORY + GolfUtil.SESSION_COURSE, GolfCourseResponse.class);
			
			session_draws = readSafely(GolfUtil.GOLF_DIRECTORY + GolfUtil.SESSION_DRAWS, GolfDrawsResponse.class);
			return(null);
			
		default:
			if(session_selected_broadcaster != null) {
				switch (session_selected_broadcaster) {
				case GolfUtil.PGTI:
					this_PGTI.ProcessGraphicOption(whatToProcess, golfService, session_tour, session_score, session_entry_list, session_course, 
							session_draws, print_writer, session_selected_scenes, valueToProcess,session_configuration);
					return JSONObject.fromObject(this_PGTI).toString();
				}
			}
			return JSONObject.fromObject(this_PGTI).toString();
		}
	}
	private <T> T readSafely(String path, Class<T> clazz) {
	    File file = new File(path);
	    try {
	        if (!file.exists()) {
	            return clazz.getDeclaredConstructor().newInstance();
	        }
	        return mapper.readValue(file, clazz);

	    } catch (com.fasterxml.jackson.core.JsonParseException e) {
	        System.err.println("Invalid JSON format in file: " + file.getName());
	        
	    } catch (com.fasterxml.jackson.databind.JsonMappingException e) {
	        System.err.println("JSON structure mismatch in file: " + file.getName());

	    } catch (Exception e) {
	        System.err.println("Error reading file: " + file.getName());
	        e.printStackTrace();
	    }
	    
	    try {
	        return clazz.getDeclaredConstructor().newInstance();
	    } catch (Exception ex) {
	        throw new RuntimeException("Cannot create default instance for " + clazz.getSimpleName());
	    }
	}
}