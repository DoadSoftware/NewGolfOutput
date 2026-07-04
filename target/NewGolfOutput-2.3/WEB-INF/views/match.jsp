<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
  <sec:csrfMetaTags/>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">  
  <title>Golf</title>
  
    <script src="<c:url value='/resources/javascript/index.js'/>"></script>
	<script src="<c:url value='/webjars/jquery/3.7.1/jquery.min.js'/>"></script>
	<script src="<c:url value='/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js'/>"></script>
	<link rel="stylesheet" href="<c:url value='/webjars/bootstrap/5.3.3/css/bootstrap.min.css'/>">

  <script type="text/javascript">
	
  $(document).on("keydown", function(e){
	  
	  if($('#waiting_modal').hasClass('show')) {
		  e.cancelBubble = true;
		  e.stopImmediatePropagation();
    	  e.preventDefault();
		  return false;
	  }
	  
      var evtobj = window.event? event : e;
      
      switch(e.target.tagName.toLowerCase())
      {
      case "input": case "textarea":
    	 break;
      default:
    	  e.preventDefault();
	      var whichKey = '';
		  var validKeyFound = false;
	    
	      if(evtobj.ctrlKey) {
	    	  whichKey = 'Control';
	      }
	      if(evtobj.altKey) {
	    	  if(whichKey) {
	        	  whichKey = whichKey + '_Alt';
	    	  } else {
	        	  whichKey = 'Alt';
	    	  }
	      }
	      if(evtobj.shiftKey) {
	    	  if(whichKey) {
	        	  whichKey = whichKey + '_Shift';
	    	  } else {
	        	  whichKey = 'Shift';
	    	  }
	      }
	      
		  if(evtobj.keyCode) {
	    	  if(whichKey) {
	    		  if(!whichKey.includes(evtobj.key)) {
	            	  whichKey = whichKey + '_' + evtobj.key;
	    		  }
	    	  } else {
	        	  whichKey = evtobj.key;
	    	  }
		  }
		  validKeyFound = false;
		  if (whichKey.includes('_')) {
			  whichKey.split("_").forEach(function (this_key) {
				  switch (this_key) {
				  case 'Control': case 'Shift': case 'Alt':
					break;
				  default:
					validKeyFound = true;
					break;
				  }
			  });
		   } else {
			  if(whichKey != 'Control' && whichKey != 'Alt' && whichKey != 'Shift') {
				  validKeyFound = true;
			  }
		   }
			  
		   if(validKeyFound == true) {
			   console.log('whichKey = ' + whichKey);
			   processUserSelectionData('LOGGER_FORM_KEYPRESS',whichKey);
		   }
	      }
	  });
  setInterval(() => {
	  processGolfProcedures('READ-MATCH-AND-POPULATE');		
	}, 1000);
  </script> 
  	<style>
		table {
	        table-layout: auto;
	        width: 95%;
	    }
	    th, td {
	        white-space: nowrap;
	    }
	    thead th {
	        text-align: center !important;
	        vertical-align: middle;
	    }
	
	    tbody td {
	        text-align: center;
	        vertical-align: middle;
	    }
	    .card {
	        width: 95%;
	    }
	    .current-player {
	        background-color: #FFF3CD !important;
	        font-weight: bold;
	    }
	    .current-hole {
	        background-color: #FF4D4D !important;
	        color: white;
	        font-weight: bold;
	    }
	</style> 
</head>
<!-- <body onload="afterPageLoad('MATCH');"> -->
<form:form name="golf_form" autocomplete="off" action="match" method="POST" enctype="multipart/form-data">
<div class="content py-5" style="background-color: #EAE8FF; color: #2E008B">
  <div class="container-fluid">
	<div class="row">
	 <div class="col-12 p-0">
       <span class="anchor"></span>
         <div class="card card-outline-secondary">
           <div class="card-header">
           <div class="row text-center" style="padding:15px 0;">
			    <div class="col-md-12">
			        <h3 style="font-weight:600; letter-spacing:0.5px;">
			            <span style="margin-right:40px;">
			                <span style="color:#555;">Broadcaster:</span>
			                <strong id="header_broadcaster" style="color:#2E008B;"></strong>
			            </span>
			            <span>
			                <span style="color:#555;">Course:</span>
			                <strong id="header_course" style="color:#2E008B;"></strong>
			            </span>
			        </h3>
			    </div>
			    <div class="col-md-12" style="margin-top:10px;">
			        <h3 style="font-weight:600;">
			            <span style="color:#555;">Tournament:</span>
			            <strong id="header_tournament" style="color:#2E008B;"></strong>
			        </h3>
			    </div>
			</div>
           </div>
         </div>
       </div>
    </div>
  </div>
 </div>
 		<div class="card-body">
          	<div id="match_body_div">
          	<div class="row" style="margin-bottom:20px;">
			    <div class="col-md-4">
			        <label><b>Select Round</b></label>
					<select id="round_selector" class="form-control" onchange="loadRoundFile()">
					    <option value="">--Select Round--</option>
					    <option value="SCOREDATA1.json">Round 1</option>
					    <option value="SCOREDATA2.json">Round 2</option>
					    <option value="SCOREDATA3.json">Round 3</option>
					    <option value="SCOREDATA4.json">Round 4</option>
					</select>
			    </div>
			</div>
				<div class="table-responsive" style="margin-bottom:25px; overflow-x:auto;"> 
				    <table class="table table-bordered table-striped text-center w-100"
	       				style="background-color:#ffffff; font-size:16px;">
				        
				        <thead style="background-color:#2E008B; color:#ffffff;">
						    <tr>
	    						<th colspan="8" style="text-align:center;"></th>
						        <th>01</th><th>02</th><th>03</th><th>04</th><th>05</th>
						        <th>06</th><th>07</th><th>08</th><th>09</th>
						        <th>10</th><th>11</th><th>12</th><th>13</th>
						        <th>14</th><th>15</th><th>16</th><th>17</th><th>18</th>
						    </tr>
						    <tr>
						        <th>Rank</th><th>Player Name</th><th>Score</th>
						        <th>SD1</th><th>SD2</th><th>SD3</th>
						        <th>SD4</th><th>Aggregate</th>
						
						        <th>1</th><th>2</th><th>3</th><th>4</th><th>5</th>
						        <th>6</th><th>7</th><th>8</th><th>9</th>
						        <th>10</th><th>11</th><th>12</th><th>13</th>
						        <th>14</th><th>15</th><th>16</th><th>17</th><th>18</th>
						    </tr>
	
				        </thead>
				        <tbody id="leaderboard_body">
				        </tbody>
				    </table>
				</div>
				<div class="table-responsive" style="margin-bottom:20px;max-width:800px;">
				    <table class="table table-bordered table-striped text-center"
				           style="background-color:#ffffff; font-size:16px;">
				
				        <thead style="background-color:#2E008B; color:#ffffff;">
				            <tr>
				                <th style="width:30%">Caption</th>
				                <th style="width:40%">Working Heading</th>
				                <th style="width:30%">Animate Out</th>
				            </tr>
				        </thead>
				        <tbody>
				        <tr><td>ALT_1</td><td>Player Details LOF</td><td>=</td></tr>
			            <tr><td>ALT_2</td><td>Player Details COF</td><td>=</td></tr>
			            <tr><td>H</td><td>Hole Details</td><td>=</td></tr>
			            <tr><td>ALT_3</td><td>Player Round Details</td><td>-</td></tr>
			            <tr><td>F1</td><td>LT Leaderboard Top 5</td><td>-</td></tr>
			            <tr><td>F8</td><td>Name Super</td><td>-</td></tr>
			            <tr><td>F10</td><td>Name Super FREE TEXT</td><td>-</td></tr>
			            <tr><td>SHIFT_L</td><td>LT Leaderboard Top 10</td><td>-</td></tr>
			            <tr><td>L</td><td>FF Leaderboard Top 10 </td><td>-</td></tr>
			            <tr><td>P</td><td>FF Leaderboard Top 3</td><td>-</td></tr>
			            <tr><td>M</td><td>Draws Gfx</td><td>-</td></tr>
				        </tbody>
				    </table>
				</div>
          	</div>
	          <div id="select_graphic_options_div" style="display:none;">
			  </div>
			  <%-- <div class="panel-group" id="match_configuration">
			    <div class="panel panel-default">
			      <div class="panel-heading">
			        <h5 class="panel-title">
			          <a data-toggle="collapse" data-parent="#match_configuration" href="#load_setup_match">Configuration</a>
			        </h5>
			      </div>
			      <div id="load_setup_match" class="panel-collapse collapse">
					<div class="panel-body">
					    <div class="col-sm-8 col-md-8">
						    <label for="select_golf_matches" class="col-sm-5 col-form-label text-left">Select Golf Match</label>
						      <select id="select_golf_matches" name="select_golf_matches" 
						      		class="browser-default custom-select custom-select-sm">
									<c:forEach items = "${match_files}" var = "match">
							          <option value="${match.name}">${match.name}</option>
									</c:forEach>
						      </select>
						    <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
						  		name="load_match_btn" id="load_match_btn" onclick="processUserSelection(this)">
						  		<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>
						  		<i class="fas fa-download"></i> Load Match</button>
					    </div>
				    </div>
			      </div>
			    </div>
			  </div> --%>
		    <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
			  <div id="select_event_div" style="display:none;"></div>
			  <div id="golf_div" style="display:none;"></div>
			  <div id="select_caption_div" style="display:none;"></div>
           </div>
          </div>
 <input type="hidden" id="selected_player_id" name="selected_player_id"></input>
 <input type="hidden" name="selectedBroadcaster" id="selectedBroadcaster" value="${session_selected_broadcaster}"/>
 <input type="hidden" id="matchFileTimeStamp" name="matchFileTimeStamp" value="${session_match.matchFileTimeStamp}"></input>
</form:form>
<script type="text/javascript">
    var helpPageOpened = false, helpWindow = null; 
    document.addEventListener('keydown', function(event) {
        if (event.ctrlKey && event.shiftKey && event.key === 'H') {
            event.preventDefault();           
            var helpPageUrl = '<c:url value="/Help"/>';
            if (!helpPageOpened || (helpWindow && helpWindow.closed)) {
                helpWindow = window.open(helpPageUrl, '_blank'); 
                helpPageOpened = true; 
                if (helpWindow) {
                    helpWindow.onbeforeunload = function() {
                        helpPageOpened = false; 
                    };
                }
            } else {
                helpWindow.focus();
                helpWindow.location.reload();
            }
        }
    });
    function loadRoundFile() {
        var selectedFile = document.getElementById("round_selector").value;
        console.log("Selected round file:", selectedFile);
        if(selectedFile === ""){
            console.log("No file selected");
            return;
        }
        processGolfProcedures('LOAD_MATCH', selectedFile);
    }
</script>
</body>
</html>