var match_data,home_first_player_id,away_first_player_id,home_second_player_id,away_second_player_id;
function processWaitingButtonSpinner(whatToProcess) 
{
	switch (whatToProcess) {
	case 'START_WAIT_TIMER': 
		$('.spinner-border').show();
		$(':button').prop('disabled', true);
		break;	
	case 'END_WAIT_TIMER': 
		$('.spinner-border').hide();
		$(':button').prop('disabled', false);
		break;
	}
}
function secondsTimeSpanToHMS(s) {
  var h = Math.floor(s / 3600); //Get whole hours
  s -= h * 3600;
  var m = Math.floor(s / 60); //Get remaining minutes
  s -= m * 60;
  return h + ":" + (m < 10 ? '0' + m : m) + ":" + (s < 10 ? '0' + s : s); //zero padding on minutes and seconds
}
function displayMatchTime() {
	processGolfProcedures('READ_CLOCK',null);
}
function initialiseForm(whatToProcess, dataToProcess)
{
	switch (whatToProcess) {
	case 'TIME':
		if(match_data) {
			if(document.getElementById('match_time_hdr')) {
				document.getElementById('match_time_hdr').innerHTML = 'MATCH TIME : ' + 
					secondsTimeSpanToHMS(match_data.clock.matchTotalSeconds);
			}
		}
		
		break;
	}
}

function processUserSelectionData(whatToProcess,dataToProcess){
	switch (whatToProcess) {
	case 'LOGGER_FORM_KEYPRESS':
		if($('#log_game_undo_btn').val()) { // Ignore keypress when user is working with UNDO
			return false;
		}
		switch (dataToProcess) {
			case '-': //-
				if(confirm('Are You Sure To Animate Out?') == true){
					processGolfProcedures('ANIMATE-OUT_ALL');
				}
			break;
			/*case '0': //-
				if(confirm('It will Also Delete Your Preview from Directory...\r\n\r\n Are You Sure To Animate Out?') == true){
					processGolfProcedures('ANIMATE-OUT_HOLE');
				}
			break;*/
			case '=': //-
				if(confirm('Are You Sure To Animate Out?') == true){
					processGolfProcedures('ANIMATE-OUT_SMALLLT');
				}
				break;
			case '`': //-
				if(confirm('Are You Sure To Animate Out?') == true){
					processGolfProcedures('ANIMATE-OUT_DOUBLE');
				}
			break;	
			case ' ':
				processGolfProcedures('CLEAR-ALL');
				break;
			case 'F8':
			    processGolfProcedures('POPULATE-NAMESUPER');
				break; 
			case 'F10':
			    addItemsToList('POPULATE-NAMESUPER-FREETEXT');
				break;
			case 'm':
			    processGolfProcedures('POPULATE-DRAWDATA-MATCH');
				break;		 
			/*case 'l':
				processGolfProcedures('POPULATE-FF_TOPTEN-LEADERBOARD');
				break;*/
			case 'l':
				addItemsToList('POPULATE-FF-LEADERBOARD');
				break;	
			case 'p':
				processGolfProcedures('POPULATE-FF_TOPTHREE-LEADERBOARD');
				break;	
			case 'Shift_L':
				processGolfProcedures('POPULATE-TOPTEN-LEADERBOARD');
				break;	
			case "F1":
				processGolfProcedures('POPULATE-TOPFIVE-LEADERBOARD');
				break;
			case 'Alt_3':
				processGolfProcedures('POPULATE-PLAYER_ROUND_DETAILS');
				break; 
			case 'h':
				processGolfProcedures('POPULATE-HOLE_DETAILS');
				break; 
			case 'q':
				processGolfProcedures('POPULATE-TLOGO');
				break; 	
			case 'w':
				processGolfProcedures('POPULATE-CHANGETLOGO');
				break;				
			case 'Alt_2':
			    processGolfProcedures('POPULATE-COF-PLAYER_DETAILS');
			    break;	
			case 'Alt_1':
				processGolfProcedures('POPULATE-LOF-PLAYER_DETAILS');
				break;
			/*case '9':
				processGolfProcedures('ANIMATE_IN-LOFEXTRA');
				break;*/	
			case '9':
				addItemsToList('POPULATE-LOF-LOFEXTRA');
				break;	
			case 'z':
				processGolfProcedures('ANIMATE_IN-HOLE_IN_ONE');
				break;
			case 'x':
				processGolfProcedures('ANIMATE_IN-EAGLE');
				break;	 
			case 'c':
				processGolfProcedures('ANIMATE_IN-BIRDIE');
				break;
			case 'r':
				processGolfProcedures('POPULATE-EYE_OFF');
				break;	
			case 'e':
				processGolfProcedures('POPULATE-EYE_ONN');
				break;								
				
			case 'Alt_5':
				processGolfProcedures('POPULATE_TEST-LOF-PLAYER_DETAILS');
				break;	 	 
			
			//NOT doNE
			
			 case 'Alt_r':
				processGolfProcedures('RE_READ');
				break;
			case 'l':
				switch ($('#selected_broadcaster').val().toUpperCase()) {
				case'PGTI':	
					addItemsToList('POPULATE-FF-LEADERBOARD_TOP_10');
					break;
				}
				break;
			}
		break;
	}
}
function processUserSelection(whichInput)
{	
	var error_msg = '';

	switch ($(whichInput).attr('name')) {
	case 'matchType':
		if($('#matchType option:selected').val() == 'singles') {
			document.getElementById('select_double_player_row').style.display = 'none';
		} else {
			document.getElementById('select_double_player_row').style.display = '';
		}
		break;
	case 'load_scene_btn':
		/*if(checkEmpty($('#vizIPAddress'),'IP Address Blank') == false
			|| checkEmpty($('#vizPortNumber'),'Port Number Blank') == false) {
			return false;
		}*/
	  	document.initialise_form.submit();
		break;
	case 'cancel_match_setup_btn':
		document.setup_form.method = 'post';
		document.setup_form.action = 'setup_to_match';
	   	document.setup_form.submit();
		break;
	case 'matchFileName':
		if(document.getElementById('matchFileName').value) {
			document.getElementById('matchFileName').value = 
				document.getElementById('matchFileName').value.replace('.json','') + '.json';
		}
		break;
	case 'load_match_btn':
		processWaitingButtonSpinner('START_WAIT_TIMER');
		processGolfProcedures('LOAD_MATCH',$('#select_golf_matches option:selected'));
		break;
	case 'cancel_graphics_btn':
	    $('#select_graphic_options_div').hide();
	    $('#match_body_div').show();
	    $("#select_event_div").show();
	    $("#match_configuration").show();
	    $("#golf_div").show();
		break;
	case'populate_ff_leaderboard_top_10_btn':
	switch ($('#selected_broadcaster').val().toUpperCase()) {
		case 'PGTI':
			$('#select_graphic_options_div').empty();
			document.getElementById('select_graphic_options_div').style.display = 'none';
			$("#select_event_div").show();
			$("#match_configuration").show();
			$("#golf_div").show();
			processGolfProcedures('POPULATE-FF-LEADERBOARD_TOP_10-OPTIONS', null);
			break;
		}
		break;
	case 'populate_namesuper_btn':
		processGolfProcedures('POPULATE-NAMESUPERR');
		break;
	case 'populate_match_btn':
		processGolfProcedures('POPULATE-FF-MATCHDRAWS');
		break;
	
	case 'populate_namesuperfreetext_btn':
		processGolfProcedures('POPULATE-NAMESUPERR_FREETEXT');
		break;	
	/*case 'populate_lof_player_btn':
		var value_to_process = '/Default/AR/LOF_PlayerDetails,' + $('#selectLOFPlayer').val();
		alert(value_to_process);
		processGolfProcedures('POPULATE-LOF-PLAYER_DETAILS', value_to_process);
		break;*/
	case 'populate_cof_player_btn':
		processGolfProcedures('POPULATE-COF-PLAYER_DETAILSLT');
		break;
	case 'populate_round_player_btn':
		processGolfProcedures('POPULATE-ROUND-PLAYER_DETAILSLT');
		break;
	case 'populate_ffleaderboard_btn':
		processGolfProcedures('POPULATE-FF_TOPTEN-LEADERBOARD');
		break;
		
	case 'populate_lofff_player_btn':
		processGolfProcedures('POPULATE-LOFF-PLAYER_DETAILSLT');
		break;
	case 'populate_lofextra_btn':
		processGolfProcedures('POPULATE-LOFF-PLAYER_DETAILSLT_EXTRA');
		break;
	case 'populate_hole_details_btn':
		processGolfProcedures('POPULATE-HOLE_DETAILS_LT');
		break;
	case 'select_existing_golf_matches':
		if(whichInput.value.toLowerCase().includes('new_match')) {
			initialiseForm('SETUP',null);
		} else {
			processWaitingButtonSpinner('START_WAIT_TIMER');
			processGolfProcedures('LOAD_SETUP',$('#select_existing_golf_matches option:selected'));
		}
		break;
	/*default:
		if(whichInput) {
			if(whichInput.id.includes('_score_btn')) { 
				
				error_msg = 'Cannot find any started set. Please start a set first before logging an event';
				match_data.sets.forEach(function(set,set_index,set_arr){
					if(set.set_status.toLowerCase() == 'start') {
						error_msg = '';
					}
				});
				if(error_msg) {
					alert(error_msg);
					return false;			
				} else {
					error_msg = 'Cannot find any started game. Please start a game first before logging an event';
					match_data.sets.forEach(function(set,set_index,set_arr){
						if(set.set_status.toLowerCase() == 'start') {
							set.games.forEach(function(game,game_index,game_arr){
								if(game.game_status.toLowerCase() == 'start') {
									error_msg = '';
								}
							});
						}
					});
					if(error_msg) {
						alert(error_msg);
						return false;			
					} else {
						processWaitingButtonSpinner('START_WAIT_TIMER');
						processGolfProcedures('LOG_SCORE',whichInput);
					}
				}
				
			} else if(whichInput.id.includes('_increment_') || whichInput.id.includes('_decrement_')) {

				if(whichInput.id.includes('_decrement_')) {
					if(parseInt($('#' + whichInput.id.replace('_decrement_','_').replace('_btn','_txt')).val()) <= 0) {
						alert('Cannot use decrement button when the value is zero');
						return false;
					}
				}
				if(whichInput.id.includes('_increment_')) {
					$('#' + whichInput.id.replace('_increment_','_').replace('_btn','_txt')).val(
						parseInt($('#' + whichInput.id.replace('_increment_','_').replace('_btn','_txt')).val()) + parseInt(1));
				}else if(whichInput.id.includes('_decrement_')) {
					$('#' + whichInput.id.replace('_decrement_','_').replace('_btn','_txt')).val(
						parseInt($('#' + whichInput.id.replace('_increment_','_').replace('_btn','_txt')).val()) - parseInt(1));
				}
				
				processWaitingButtonSpinner('START_WAIT_TIMER');
				processGolfProcedures('LOG_STAT',whichInput);
			}
		}
		break;*/
	}
}
function processGolfProcedures(whatToProcess, whichInput)
{
	var value_to_process; 
	
	switch(whatToProcess) {
	case 'POPULATE-NAMESUPERR':
	    value_to_process = $('#selectPlayer').val() +  ',' + $('#selecttype').val();
		break;
		
	case 'POPULATE-FF-MATCHDRAWS':
	    value_to_process = $('#selectMatch').val();
		break;
	case 'POPULATE-NAMESUPERR_FREETEXT':
		value_to_process = $('#text1').val()+ ',' + $('#text2').val();
		break;
	case 'POPULATE-ROUND-PLAYER_DETAILSLT':
		value_to_process = $('#selectRoundPlayer').val();
		break;
	case 'POPULATE-FF_TOPTEN-LEADERBOARD':	
		value_to_process = $('#ffleaderboard_range').val();
		break;
		//not ode
	case 'POPULATE-HOLE_DETAILS_LT':
	   value_to_process = $('#selectHole').val();
	break;	
	
	/*case 'POPULATE-LOF-PLAYER_DETAILS':
	    value_to_process = '/Default/AR/LOF_PlayerDetails';
		break;*/
	case 'POPULATE-COF-PLAYER_DETAILSLT':
	    value_to_process = $('#selectCOFPlayer').val();
		break;
		
	/*case 'POPULATE-LOFF-PLAYER_DETAILSLT': 
	    value_to_process = $('#selectCOFPlayer').val() + "," + $('#selectShotType').val() + ","+ $('#selectHoleNumber').val();
		alert(value_to_process)
		break;*/	
		
		case 'POPULATE-LOFF-PLAYER_DETAILSLT': 
		value_to_process = $('#selectedPlayerCode').val()  + "," + $('#selectedHole').val();
		break;
		
	case 'POPULATE-LOFF-PLAYER_DETAILSLT_EXTRA': 
		value_to_process = $('#selectedExtraType').val();
		break;
	case 'READ-MATCH-AND-POPULATE':
		value_to_process = $('#matchFileTimeStamp').val();
		break;
	case 'LOAD_MATCH':
	    value_to_process = whichInput;
	    break;
	}

	$.ajax({    
        type : 'Get',     
        url : 'processGolfProcedures.html',     
        data : 'whatToProcess=' + whatToProcess + '&valueToProcess=' + value_to_process, 
        dataType : 'json',
        success : function(data) {
			//match_data = data;
        	switch(whatToProcess) {
			case 'READ_MATCH_FOR_STATS': case 'LOG_STAT':
				initialiseForm('LOAD_STAT',data);
				break;
			case 'POPULATE-NAMESUPER':
			    addItemsToList('POPULATE-NAMESUPER-OPTIONS', data);
				break;
				
			case 'POPULATE-DRAWDATA-MATCH':
			    addItemsToList('POPULATE-DRAWDATA-MATCH-OPTIONS', data);
				break;
				
			/*case 'POPULATE-LOF-PLAYER_DETAILS':
			    addItemsToList('POPULATE-LOF-PLAYER_DETAILS-OPTIONS', data);
				break;*/
			case 'POPULATE-COF-PLAYER_DETAILS':
			    addItemsToList('POPULATE-COF-PLAYER_DETAILS-OPTIONS', data);
				break;
			case 'POPULATE-PLAYER_ROUND_DETAILS':
				 addItemsToList('POPULATE-PLAYER_ROUND_DETAILS-OPTIONS', data);
				break;	
			case 'POPULATE-LOF-PLAYER_DETAILS':	
				addItemsToList('POPULATE-LOF-PLAYER_DETAILS-OPTIONS', data);
				break;
			case 'POPULATE-HOLE_DETAILS':
			    addItemsToList('POPULATE-HOLE_DETAILS-OPTIONS', data);
				break;
			case 'READ-MATCH-AND-POPULATE':
			    if (data) {
			        match_data = data;
			        if (data.broadcaster) {
			            $('#header_broadcaster').text(data.broadcaster);
			        }
			        if (data.tournament) {
			            $('#header_tournament').text(data.tournament);
			        }
			        if (data.course) {
			            $('#header_course').text(data.course);
			        }
			        if (data.parRow) {
			            let headerHtml = `<tr><th colspan="8">PAR SCORE</th>`;
			            for (let i = 0; i < data.parRow.length; i++) {
			                headerHtml += `<th>${data.parRow[i] || '-'}</th>`;
			            }
			            headerHtml += `</tr>`;
			            headerHtml += `
			                <tr>
			                    <th>Rank</th><th>Player Name</th><th>Score</th>
			                    <th>SD1</th><th>SD2</th><th>SD3</th><th>SD4</th>
			                    <th>Aggregate</th>`;
			
			            for (let i = 1; i <= 18; i++) {
			                headerHtml += `<th>${i}</th>`;
			            }
			            headerHtml += `</tr>`;
			            document.querySelector("table thead").innerHTML = headerHtml;
			        }
			        if (data.top10) {
			            let tableRows = "";
			            data.top10.forEach(function(player) {
			                let currentHole = parseInt(player.currentHole || 0);
			                tableRows += `<tr>
			                    <td>${player.rank}</td>
			                    <td style="text-align:left; padding-left:15px;">
			                        ${player.name}
			                    </td>`;
			                let score = (player.score !== null && player.score !== undefined && player.score !== "") ? player.score : "-";
			                let strikeRate = (player.strikeRate !== null && player.strikeRate !== undefined && player.strikeRate !== "") ? player.strikeRate : "-";
			                let total = (player.total !== null && player.total !== undefined && player.total !== "") ? player.total : "-";
			                let sd1 = player.sd1 ? player.sd1 : "-";
							let sd2 = player.sd2 ? player.sd2 : "-";
							let sd3 = player.sd3 ? player.sd3 : "-";
							let sd4 = player.sd4 ? player.sd4 : "-";
			                let scoreColor = "black";
			                if (score !== "-" && !isNaN(score)) {
			                    scoreColor = score < 0 ? "black" : (score > 0 ? "black" : "black");
			                }
			                tableRows += `
			                    <td style="color:${scoreColor}">${score}</td>
			                    <td>${sd1}</td><td>${sd2}</td>
							    <td>${sd3}</td><td>${sd4}</td>
			                    <td>${total}</td>`;
			                if (player.holes) {
			                    for (let i = 1; i <= 18; i++) {
			                        let holeValue = player.holes[i - 1] || "-";
			                        let style = "";
			                        if (currentHole >= 1 && currentHole <= 18 && i === currentHole) {
			                            style = 'style="background-color:#FF4D4D;color:white;font-weight:bold;"';
			                        }
			                        tableRows += `<td ${style}>${holeValue}</td>`;
			                    }
			                }
			                tableRows += `</tr>`;
			            });
			            document.getElementById("leaderboard_body").innerHTML = tableRows;
			        }
			    }
			break;
			
			case 'LOAD_MATCH':
				addItemsToList('LOAD_EVENTS',data);
				addItemsToList('LOAD_MATCH_DETAIL',data);
				document.getElementById('golf_div').style.display = '';
				document.getElementById('select_event_div').style.display = '';
				document.getElementById('select_caption_div').style.display = '';
				break;
			
			case 'POPULATE-COF-PLAYER_DETAILSLT': case 'POPULATE-HOLE_DETAILS_LT': 
			case 'POPULATE-LT-MATCHID': case'POPULATE-LOF-PLAYER_DETAILS':  case'POPULATE-LT-PLAYER_DETAILS_COF':
			 
			
			case "POPULATE-NAMESUPERR_FREETEXT": case "POPULATE-NAMESUPERR": case"POPULATE-TOPTEN-LEADERBOARD": case 'POPULATE-TOPFIVE-LEADERBOARD':
			case 'POPULATE-FF_TOPTEN-LEADERBOARD': case 'POPULATE-FF_TOPTHREE-LEADERBOARD': case 'POPULATE-ROUND-PLAYER_DETAILSLT': case'POPULATE-HOLE_DETAILS': 
			case 'POPULATE-LOFF-PLAYER_DETAILSLT': case 'POPULATE-FF-MATCHDRAWS':  case 'POPULATE-LOFF-PLAYER_DETAILSLT_EXTRA':
			if(confirm('Animate In?') == true){
				switch(whatToProcess){
				case "POPULATE-NAMESUPERR_FREETEXT": 
					processGolfProcedures('ANIMATE-IN-NAMESUPERR_FREETEXT');	
					break;
				case "POPULATE-NAMESUPERR":	
					processGolfProcedures('ANIMATE-IN-NAMESUPERR');	
					break;
				case 'POPULATE-FF-MATCHDRAWS':
					processGolfProcedures('ANIMATE-IN-FF-MATCHDRAWS');	
					break;	
				case 'POPULATE-FF_TOPTEN-LEADERBOARD':
				   	processGolfProcedures('ANIMATE-IN-FF_TOPTEN-LEADERBOARD');	
					break;
				
				case 'POPULATE-FF_TOPTHREE-LEADERBOARD':
				   	processGolfProcedures('ANIMATE-IN-FF_TOPTHREE-LEADERBOARD');	
					break;	
				case"POPULATE-TOPTEN-LEADERBOARD":	
					processGolfProcedures('ANIMATE-IN-TOP10_LEADREBOARD');	
					break;
				case'POPULATE-TOPFIVE-LEADERBOARD':
					processGolfProcedures('ANIMATE-IN-TOP5_LEADREBOARD');	
					break;	
				case 'POPULATE-ROUND-PLAYER_DETAILSLT':	
					processGolfProcedures('ANIMATE-IN-ROUND-PLAYER_DETAILSLT');	
					break;
					
					//notdone
					
				case 'POPULATE-HOLE_DETAILS_LT':	
					processGolfProcedures('ANIMATE-IN-HOLE_DETAILS_LT');	
					break;
				case 'POPULATE-LOFF-PLAYER_DETAILSLT':
					processGolfProcedures('ANIMATE-IN-LOFF-PLAYER_DETAILSLT');	
					break;
				case 'POPULATE-LOFF-PLAYER_DETAILSLT_EXTRA':
					processGolfProcedures('ANIMATE-IN-LOFF-PLAYER_DETAILSLT_EXTRA');	
					break;
				case 'POPULATE-COF-PLAYER_DETAILSLT':
					processGolfProcedures('ANIMATE-IN-COF-PLAYER_DETAILSLT');	
					break;
				case 'POPULATE-LT-MATCHID':
					processGolfProcedures('ANIMATE-IN-LT_MATCHID');				
					break;
				case'POPULATE-LOF-PLAYER_DETAILS':
					processGolfProcedures('ANIMATE-IN-LOF_PLAYER_DETAILS');
					break;
				case'POPULATE-HOLE_DETAILS':
					processGolfProcedures('ANIMATE-IN-HOLE_DETAILS');
					break;
				case'POPULATE-COF-PLAYER_DETAILS':
					processGolfProcedures('ANIMATE-IN-COF_PLAYER_DETAILS');
					break;
				case 'POPULATE-PLAYER_ROUND_DETAILS':
					processGolfProcedures('ANIMATE-IN-PLAYER_ROUND_DETAILS');
					break;
				case 'POPULATE-FF-LEADERBOARD_TOP_10':
					processGolfProcedures('ANIMATE-FF-LEADERBOARD_TOP_10');
					break;
				}
			}
        	}
    		processWaitingButtonSpinner('END_WAIT_TIMER');
	    },    
	    error : function(e) {    
	  	 	console.log('Error occured in ' + whatToProcess + ' with error description = ' + e);     
	    }    
	});
}
function addItemsToList(whatToProcess, dataToProcess)
{
	var div,row,header_text,select,option,tr,th,thead,text,table,tbody, captionTable, captionTbody,header_text1,header_text2,header_text3;
	var cellCount=0;
	switch (whatToProcess) {
		
		
		case 'POPULATE-LOF-LOFEXTRA':
			$("#match_body_div").hide();
			$("#select_graphic_options_div").show();
			
			var div = document.getElementById('select_graphic_options_div');
			div.innerHTML = "";
			
			var h = document.createElement("h3");
			h.innerHTML = "LOF EXTRA";
			h.style.textAlign = "center";
			div.appendChild(h);
			
			var hiddenType = document.createElement("input");
			hiddenType.type = "hidden";
			hiddenType.id = "selectedExtraType";
			div.appendChild(hiddenType);
			
			
			var table = document.createElement("table");
			table.className = "table table-bordered";
			
			var tbody = document.createElement("tbody");
			table.appendChild(tbody);
			
			div.appendChild(table);
			var selectedExtraType = "";
			
			var row = tbody.insertRow();
			
			row.insertCell(0).innerHTML = "Type";
			
			var typeDiv = document.createElement("div");
			
			
			var types = ["Empty","Albatross","Birdie Putt",
			    "Par Putt","Bogey Putt","Hole In One","Eagle putt"];
			
			var shotNames = ["1st SHOT","2nd SHOT","3rd SHOT",
              "4th SHOT","5th SHOT","6th SHOT","7th SHOT"
			];
			
			
			for (var s = 0; s < shotNames.length; s++) {
			    types.push(shotNames[s]);
			}
			
			for (var i = 0; i < types.length; i++) {
			
			    var btn = document.createElement("button");
			
			    btn.type = "button";
			    btn.innerHTML = types[i];
			    btn.style.margin = "2px";
			
			    btn.onclick = function () {
			
			        selectedExtraType = this.innerHTML;
			
			        document.getElementById("selectedExtraType").value =
			            selectedExtraType;
			
			        var b = typeDiv.getElementsByTagName("button");
			
			        for (var k = 0; k < b.length; k++) {
			            b[k].style.background = "";
			        }
			
			        this.style.background = "yellow";
			    };
			
			    typeDiv.appendChild(btn);
			}
			
			row.insertCell(1).appendChild(typeDiv);
			
			row = tbody.insertRow();
			
			var populateBtn = document.createElement("input");
			populateBtn.type = "button";
			populateBtn.value = "Populate";
			populateBtn.name = "populate_lofextra_btn";
			populateBtn.setAttribute(
			    "onclick",
			    "processUserSelection(this)"
			);
			
			var cancelBtn = document.createElement("input");
			cancelBtn.type = "button";
			cancelBtn.value = "Cancel";
			cancelBtn.name = "cancel_graphics_btn";
			cancelBtn.setAttribute(
			    "onclick",
			    "processUserSelection(this)"
			);
			
			var d = document.createElement("div");
			
			d.appendChild(populateBtn);
			d.appendChild(cancelBtn);
			
			var c = row.insertCell(0);
			c.colSpan = 2;
			c.appendChild(d);
			
			break;
	
	case 'POPULATE-HOLE_DETAILS-OPTIONS':
		$("#match_body_div").hide();
		$("#select_graphic_options_div").show();
		
		div = document.getElementById('select_graphic_options_div');
		div.innerHTML = "";
		
		header_text = document.createElement('h3');
		header_text.innerHTML = "HOLE DETAILS";
		header_text.style.textAlign = "center";
		header_text.style.marginBottom = "20px";
		
		div.appendChild(header_text);
		
		table = document.createElement('table');
		table.setAttribute('class','table table-bordered');
		
		tbody = document.createElement('tbody');
		table.appendChild(tbody);
		
		div.appendChild(table);
		
		row = tbody.insertRow();
		
		select = document.createElement('select');
		select.style = 'width:300px';
		select.id = 'selectHole';
		
		for(var i=1;i<=18;i++){
		    option = document.createElement('option');
		    option.value = i;
		    option.text = i;
		    select.appendChild(option);
		}
		
		cell = row.insertCell(0);
		cell.innerHTML = "Hole";
		
		cell = row.insertCell(1);
		cell.appendChild(select);
		
		row = tbody.insertRow();
		
		option = document.createElement('input');
		option.type = 'button';
		option.name = 'populate_hole_details_btn';
		option.value = 'Populate';
		option.setAttribute('onclick',"processUserSelection(this)");
		
		btnDiv = document.createElement('div');
		btnDiv.append(option);
		
		option = document.createElement('input');
		option.type = 'button';
		option.name = 'cancel_graphics_btn';
		option.value = 'Cancel';
		option.setAttribute('onclick','processUserSelection(this)');
		
		btnDiv.append(option);
		
		cell = row.insertCell(0);
		cell.colSpan = 2;
		cell.appendChild(btnDiv);
		break;


	case 'POPULATE-COF-PLAYER_DETAILS-OPTIONS': 
		$("#match_body_div").hide();
		$("#select_graphic_options_div").show();
		
		div = document.getElementById('select_graphic_options_div');
		div.innerHTML = "";
		
		header_text = document.createElement('h3');
		header_text.innerHTML = "COF PLAYER DETAILS";
		header_text.style.textAlign = "center";
		header_text.style.marginBottom = "20px";
		
		div.appendChild(header_text);
		
		table = document.createElement('table');
		table.setAttribute('class','table table-bordered');
		
		tbody = document.createElement('tbody');
		table.appendChild(tbody);
		
		div.appendChild(table);
		
		row = tbody.insertRow();
		
		select = document.createElement('select');
		select.style = 'width:300px';
		select.id = 'selectCOFPlayer';
		
		var players = dataToProcess.players || [];
		players.sort(function(a,b){
			return a.name.localeCompare(b.name)
		});
		
		players.forEach(function(player){
		    option = document.createElement('option');
		    option.value = player.code;
		    option.text = player.name;
		    select.appendChild(option);
		});
		
		cell = row.insertCell(0);
		cell.innerHTML = "Player";
		
		cell = row.insertCell(1);
		cell.appendChild(select);
		
		row = tbody.insertRow();
		
		option = document.createElement('input');
		option.type = 'button';
		option.name = 'populate_cof_player_btn';
		option.value = 'Populate';
		option.setAttribute('onclick',"processUserSelection(this)");
		
		btnDiv = document.createElement('div');
		btnDiv.append(option);
		
		option = document.createElement('input');
		option.type = 'button';
		option.name = 'cancel_graphics_btn';
		option.value = 'Cancel';
		option.setAttribute('onclick','processUserSelection(this)');
		
		btnDiv.append(option);
		
		cell = row.insertCell(0);
		cell.colSpan = 2;
		cell.appendChild(btnDiv);
		break;
	case 'POPULATE-FF-LEADERBOARD':

    $("#match_body_div").hide();
    $("#select_graphic_options_div").show();
    
    div = document.getElementById('select_graphic_options_div');
    div.innerHTML = "";
    
    header_text = document.createElement('h3');
    header_text.innerHTML = "FF LEADERBOARD";
    header_text.style.textAlign = "center";
    header_text.style.marginBottom = "20px";
    
    div.appendChild(header_text);
    
    table = document.createElement('table');
    table.setAttribute('class','table table-bordered');
    
    tbody = document.createElement('tbody');
    table.appendChild(tbody);
    
    div.appendChild(table);
    
    row = tbody.insertRow();
    
    
    // ✅ Dropdown
    select = document.createElement('select');
    select.style = 'width:300px';
    select.id = 'ffleaderboard_range';
    
    option = document.createElement('option');
    option.value = "1-10";
    option.text = "1 - 10";
    select.appendChild(option);
    
    option = document.createElement('option');
    option.value = "11-20";
    option.text = "11 - 20";
    select.appendChild(option);
    
    
    cell = row.insertCell(0);
    cell.innerHTML = "Range";
    
    cell = row.insertCell(1);
    cell.appendChild(select);
    
    
    // ✅ Button row
    row = tbody.insertRow();
    
    option = document.createElement('input');
    option.type = 'button';
    option.name = 'populate_ffleaderboard_btn';
    option.value = 'Populate';
    option.setAttribute('onclick',"processUserSelection(this)");
    
    btnDiv = document.createElement('div');
    btnDiv.append(option);
    
    option = document.createElement('input');
    option.type = 'button';
    option.name = 'cancel_graphics_btn';
    option.value = 'Cancel';
    option.setAttribute('onclick','processUserSelection(this)');
    
    btnDiv.append(option);
    
    cell = row.insertCell(0);
    cell.colSpan = 2;
    cell.appendChild(btnDiv);
    
    break;
	case 'POPULATE-PLAYER_ROUND_DETAILS-OPTIONS':
		$("#match_body_div").hide();
		$("#select_graphic_options_div").show();
		
		div = document.getElementById('select_graphic_options_div');
		div.innerHTML = "";
		
		header_text = document.createElement('h3');
		header_text.innerHTML = "ROF PLAYER DETAILS";
		header_text.style.textAlign = "center";
		header_text.style.marginBottom = "20px";
		
		div.appendChild(header_text);
		
		table = document.createElement('table');
		table.setAttribute('class','table table-bordered');
		
		tbody = document.createElement('tbody');
		table.appendChild(tbody);
		
		div.appendChild(table);
		
		row = tbody.insertRow();
		
		select = document.createElement('select');
		select.style = 'width:300px';
		select.id = 'selectRoundPlayer';
		
		var players = dataToProcess.players || [];
		players.sort(function(a,b){
			return a.name.localeCompare(b.name)
		});
		players.forEach(function(player){
		    option = document.createElement('option');
		    option.value = player.code;
		    option.text = player.name;
		    select.appendChild(option);
		});
		
		cell = row.insertCell(0);
		cell.innerHTML = "Player";
		
		cell = row.insertCell(1);
		cell.appendChild(select);
		
		row = tbody.insertRow();
		
		option = document.createElement('input');
		option.type = 'button';
		option.name = 'populate_round_player_btn';
		option.value = 'Populate';
		option.setAttribute('onclick',"processUserSelection(this)");
		
		btnDiv = document.createElement('div');
		btnDiv.append(option);
		
		option = document.createElement('input');
		option.type = 'button';
		option.name = 'cancel_graphics_btn';
		option.value = 'Cancel';
		option.setAttribute('onclick','processUserSelection(this)');
		
		btnDiv.append(option);
		
		cell = row.insertCell(0);
		cell.colSpan = 2;
		cell.appendChild(btnDiv);
		break;	
	
	
	/*case 'POPULATE-LOF-PLAYER_DETAILS-OPTIONS':

    $("#match_body_div").hide();
    $("#select_graphic_options_div").show();

    div = document.getElementById('select_graphic_options_div');
    div.innerHTML = "";

    // ---------- HEADER ----------

    header_text = document.createElement('h3');
    header_text.innerHTML = "LOF PLAYER DETAILS";
    header_text.style.textAlign = "center";
    header_text.style.marginBottom = "20px";

    div.appendChild(header_text);


    // ---------- TABLE ----------

    table = document.createElement('table');
    table.setAttribute('class','table table-bordered');

    tbody = document.createElement('tbody');
    table.appendChild(tbody);

    div.appendChild(table);


    // ================= PLAYER =================

    row = tbody.insertRow();

    select = document.createElement('select');
    select.style = 'width:300px';
    select.id = 'selectCOFPlayer';

    var players = dataToProcess.players || [];
    players.sort(function(a,b){
			return a.name.localeCompare(b.name)
		});

    players.forEach(function(player){
        var option = document.createElement('option');
        option.value = player.code;
        option.text = player.name;
        select.appendChild(option);
    });

    cell = row.insertCell(0);
    cell.innerHTML = "Player";

    cell = row.insertCell(1);
    cell.appendChild(select);


    // ================= HOLE =================

    row = tbody.insertRow();

    cell = row.insertCell(0);
    cell.innerHTML = "Hole No";

    var holeSelect = document.createElement("select");
    holeSelect.id = "selectHoleNumber";
    holeSelect.style.width = "120px";

    for (var h = 1; h <= 18; h++) {
        var option = document.createElement("option");
        option.value = h;
        option.text = h;
        holeSelect.appendChild(option);
    }

    cell = row.insertCell(1);
    cell.appendChild(holeSelect);


    // ================= TYPE =================

    row = tbody.insertRow();

    cell = row.insertCell(0);
    cell.innerHTML = "Type";

    var typeSelect = document.createElement("select");
    typeSelect.id = "selectShotType";
    typeSelect.style.width = "150px";


    var types = [" ",
        "Albatross",
        "Birdie Putt",
        "Par Putt",
        "Bogey Putt",
        "Hole In One",
        "Eagle putt"
    ];
    for (var i = 1; i <= 20; i++) {
    types.push("SHOT " + i);
   }

    types.forEach(function(t){
        var option = document.createElement("option");
        option.value = t;
        option.text = t;
        typeSelect.appendChild(option);
    });

    cell = row.insertCell(1);
    cell.appendChild(typeSelect);


    // ================= BUTTONS =================

    row = tbody.insertRow();

    var populateBtn = document.createElement('input');
    populateBtn.type = 'button';
    populateBtn.name = 'populate_lofff_player_btn';
    populateBtn.value = 'Populate';
    populateBtn.setAttribute('onclick',"processUserSelection(this)");

    btnDiv = document.createElement('div');
    btnDiv.append(populateBtn);

    var cancelBtn = document.createElement('input');
    cancelBtn.type = 'button';
    cancelBtn.name = 'cancel_graphics_btn';
    cancelBtn.value = 'Cancel';
    cancelBtn.setAttribute('onclick','processUserSelection(this)');

    btnDiv.append(cancelBtn);

    cell = row.insertCell(0);
    cell.colSpan = 2;
    cell.appendChild(btnDiv);

    break;*/
   
	case 'POPULATE-LOF-PLAYER_DETAILS-OPTIONS':

$("#match_body_div").hide();
$("#select_graphic_options_div").show();

var div = document.getElementById('select_graphic_options_div');
div.innerHTML = "";

var selectedHole = "";


/* ---------- HEADER ---------- */

var h = document.createElement("h3");
h.innerHTML = "LOF PLAYER DETAILS";
h.style.textAlign = "center";
div.appendChild(h);


/* ---------- PREVIEW ---------- */

var preview = document.createElement("div");
preview.id = "previewBox";
preview.style.border = "2px solid black";
preview.style.padding = "10px";
preview.style.margin = "10px";
preview.innerHTML = "Preview";

div.appendChild(preview);


/* ---------- HIDDEN VALUES ---------- */

var hiddenPlayer = document.createElement("input");
hiddenPlayer.type = "hidden";
hiddenPlayer.id = "selectedPlayerCode";
div.appendChild(hiddenPlayer);

var hiddenHole = document.createElement("input");
hiddenHole.type = "hidden";
hiddenHole.id = "selectedHole";
div.appendChild(hiddenHole);


/* ---------- TABLE ---------- */

var table = document.createElement("table");
table.className = "table table-bordered";

var tbody = document.createElement("tbody");
table.appendChild(tbody);

div.appendChild(table);


/* ================= PLAYER ================= */

var row = tbody.insertRow();

row.insertCell(0).innerHTML = "Player";

var playerDiv = document.createElement("div");


var searchBox = document.createElement("input");
searchBox.type = "text";
searchBox.placeholder = "Search player...";
searchBox.style.width = "300px";
searchBox.style.marginBottom = "5px";

playerDiv.appendChild(searchBox);


var select = document.createElement("select");
select.id = "selectCOFPlayer";
select.style.width = "300px";
select.size = 6;

playerDiv.appendChild(select);

row.insertCell(1).appendChild(playerDiv);


var players = dataToProcess.players || [];

players.sort(function(a,b){
    return a.name.localeCompare(b.name);
});


function fillPlayers(list){

    select.innerHTML = "";

    for (var i=0;i<list.length;i++){

        var op = document.createElement("option");
        op.value = list[i].code;
        op.text = list[i].name;

        select.appendChild(op);
    }
}

fillPlayers(players);


searchBox.onkeyup = function(){

    var txt = this.value.toLowerCase();

    var filtered = [];

    for (var i=0;i<players.length;i++){

        var name = players[i].name.toLowerCase();

        if(name.indexOf(txt) !== -1){
            filtered.push(players[i]);
        }
    }

    fillPlayers(filtered);
};


select.onchange = function(){

    var sel = document.getElementById("selectCOFPlayer");

    if(sel.selectedIndex>=0){

        document.getElementById("selectedPlayerCode").value =
            sel.value;
    }

    updatePreview();
};


/* ================= HOLE ================= */

row = tbody.insertRow();

row.insertCell(0).innerHTML = "Hole";

var holeDiv = document.createElement("div");

for (var h1=1; h1<=18; h1++){

    var btn = document.createElement("button");

    btn.type = "button";
    btn.innerHTML = h1;
    btn.style.margin = "2px";

    btn.onclick = function(){

        selectedHole = this.innerHTML;

        document.getElementById("selectedHole").value =
            selectedHole;

        var b = holeDiv.getElementsByTagName("button");

        for(var k=0;k<b.length;k++){
            b[k].style.background="";
        }

        this.style.background="yellow";

        updatePreview();
    };

    holeDiv.appendChild(btn);
}

row.insertCell(1).appendChild(holeDiv);


/* ================= PREVIEW ================= */

function updatePreview(){

    var player = "";

    var sel = document.getElementById("selectCOFPlayer");

    if(sel.selectedIndex>=0){
        player = sel.options[sel.selectedIndex].text;
    }

    preview.innerHTML =
        "Player : "+player+
        " | Hole : "+selectedHole;
}


/* ================= BUTTONS ================= */

row = tbody.insertRow();

var populateBtn = document.createElement("input");
populateBtn.type="button";
populateBtn.value="Populate";
populateBtn.name="populate_lofff_player_btn";
populateBtn.setAttribute(
"onclick",
"processUserSelection(this)"
);

var cancelBtn = document.createElement('input');
cancelBtn.type = 'button';
cancelBtn.name = 'cancel_graphics_btn';
cancelBtn.value = 'Cancel';
cancelBtn.setAttribute('onclick','processUserSelection(this)');

var d = document.createElement("div");

d.appendChild(populateBtn);
d.appendChild(cancelBtn);

var c = row.insertCell(0);
c.colSpan=2;
c.appendChild(d);

break;

	case 'POPULATE-NAMESUPER-FREETEXT':
		$("#match_body_div").hide();
		$("#select_graphic_options_div").show();
		
		div = document.getElementById('select_graphic_options_div');
		div.innerHTML = "";
		
		header_text = document.createElement('h3');
		header_text.innerHTML = "NAMESUPER";
		header_text.style.textAlign = "center";
		header_text.style.marginBottom = "20px";
		
		div.appendChild(header_text);
		
		table = document.createElement('table');
		table.setAttribute('class','table table-bordered');
		
		tbody = document.createElement('tbody');
		table.appendChild(tbody);
		
		div.appendChild(table);
		
		row = tbody.insertRow();

		input1 = document.createElement('input');
		input1.type = 'text';
		input1.id = 'text1';
		input1.style = 'width:300px';
		
		cell = row.insertCell(0);
		cell.innerHTML = "Text 1";
		
		cell = row.insertCell(1);
		cell.appendChild(input1);
		
		row = tbody.insertRow();
		
		input2 = document.createElement('input');
		input2.type = 'text';
		input2.id = 'text2';
		input2.style = 'width:300px';
		
		cell = row.insertCell(0);
		cell.innerHTML = "Text 2";
		
		cell = row.insertCell(1);
		cell.appendChild(input2);
		
		row = tbody.insertRow();
		
		option = document.createElement('input');
		option.type = 'button';
		option.name = 'populate_namesuperfreetext_btn';
		option.value = 'Populate';
		option.setAttribute('onclick',"processUserSelection(this)");
		
		btnDiv = document.createElement('div');
		btnDiv.append(option);
		
		option = document.createElement('input');
		option.type = 'button';
		option.name = 'cancel_graphics_btn';
		option.value = 'Cancel';
		option.setAttribute('onclick','processUserSelection(this)');
		
		btnDiv.append(option);
		
		cell = row.insertCell(0);
		cell.colSpan = 2;
		cell.appendChild(btnDiv);

		break;
	case 'POPULATE-NAMESUPER-OPTIONS':
		$("#match_body_div").hide();
		$("#select_graphic_options_div").show();
		
		div = document.getElementById('select_graphic_options_div');
		div.innerHTML = "";
		
		header_text = document.createElement('h3');
		header_text.innerHTML = "NAMESUPER";
		header_text.style.textAlign = "center";
		header_text.style.marginBottom = "20px";
		
		div.appendChild(header_text);
		
		table = document.createElement('table');
		table.setAttribute('class','table table-bordered');
		
		tbody = document.createElement('tbody');
		table.appendChild(tbody);
		
		div.appendChild(table);
		
		row = tbody.insertRow();
		
		select = document.createElement('select');
		select.style = 'width:300px';
		select.id = 'selectPlayer';
		
		var players = dataToProcess.players || [];
		
		players.sort(function(a,b){
			return a.name.localeCompare(b.name)
		});
		
		players.forEach(function(player){
		    option = document.createElement('option');
		    option.value = player.code;
		    option.text = player.name;
		    select.appendChild(option);
		});
		
		cell = row.insertCell(0);
		cell.innerHTML = "Player";
		
		cell = row.insertCell(1);
		cell.appendChild(select);
		
		select = document.createElement('select');
		select.style = 'width:300px';
		select.id = 'selecttype';
		
		option = document.createElement('option');
	    option.value = 'Winner';
	    option.text = 'Winner';
	    select.appendChild(option);
		
		option = document.createElement('option');
	    option.value = 'TName';
	    option.text = 'TName';
	    select.appendChild(option);
					
		cell = row.insertCell(2);
		cell.appendChild(select);
			
		row = tbody.insertRow();
		
		option = document.createElement('input');
		option.type = 'button';
		option.name = 'populate_namesuper_btn';
		option.value = 'Populate';
		option.setAttribute('onclick',"processUserSelection(this)");
		
		btnDiv = document.createElement('div');
		btnDiv.append(option);
		
		option = document.createElement('input');
		option.type = 'button';
		option.name = 'cancel_graphics_btn';
		option.value = 'Cancel';
		option.setAttribute('onclick','processUserSelection(this)');
		
		btnDiv.append(option);
		
		cell = row.insertCell(0);
		cell.colSpan = 2;
		cell.appendChild(btnDiv);
		break;
	case 'POPULATE-DRAWDATA-MATCH-OPTIONS':

    $("#match_body_div").hide();
    $("#select_graphic_options_div").show();

    div = document.getElementById('select_graphic_options_div');
    div.innerHTML = "";

    // ---------- HEADER ----------

    header_text = document.createElement('h3');
    header_text.innerHTML = "SELECT MATCH";
    header_text.style.textAlign = "center";
    header_text.style.marginBottom = "20px";

    div.appendChild(header_text);


    // ---------- TABLE ----------

    table = document.createElement('table');
    table.setAttribute('class','table table-bordered');

    tbody = document.createElement('tbody');
    table.appendChild(tbody);

    div.appendChild(table);


    // ---------- ROW ----------

    row = tbody.insertRow();


    // ---------- SELECT ----------

    select = document.createElement('select');
    select.style = 'width:300px';
    select.id = 'selectMatch';


    var matches = dataToProcess.matches || [];


    matches.forEach(function(m){

        option = document.createElement('option');
        option.value = m.match;
        option.text = "Match " + m.match;

        select.appendChild(option);

    });


    cell = row.insertCell(0);
    cell.innerHTML = "Match";

    cell = row.insertCell(1);
    cell.appendChild(select);


    // ---------- BUTTON ROW ----------

    row = tbody.insertRow();


    btnPopulate = document.createElement('input');
    btnPopulate.type = 'button';
    btnPopulate.name = 'populate_match_btn';
    btnPopulate.value = 'Populate';
    btnPopulate.setAttribute('onclick',"processUserSelection(this)");


    btnCancel = document.createElement('input');
    btnCancel.type = 'button';
    btnCancel.name = 'cancel_graphics_btn';
    btnCancel.value = 'Cancel';
    btnCancel.setAttribute('onclick','processUserSelection(this)');


    btnDiv = document.createElement('div');
    btnDiv.append(btnPopulate);
    btnDiv.append(btnCancel);


    cell = row.insertCell(0);
    cell.colSpan = 2;
    cell.appendChild(btnDiv);

    break;	
	
	case'POPULATE-FF-LEADERBOARD_TOP_10':
	switch ($('#selectedBroadcaster').val().toUpperCase()){
		case'PGTI':
			header_text = document.createElement('h6');
			header_text.innerHTML = 'Select Graphic Options';
			document.getElementById('select_graphic_options_div').appendChild(header_text);
			
			table = document.createElement('table');
			table.setAttribute('class', 'table table-bordered');
					
			tbody = document.createElement('tbody');
	
			table.appendChild(tbody);
			document.getElementById('select_graphic_options_div').appendChild(table);
			
			row = tbody.insertRow(tbody.rows.length);
			
			select = document.createElement('select');
			select.style = 'width:300px';
			select.id = 'selectPhoto';
			select.name = select.id;
			
			option = document.createElement('option');
			option.value = 'without';
			option.text = 'Without Photo';
			select.appendChild(option);
			
			option = document.createElement('option');
			option.value = 'with';
			option.text = 'With Photo';
			select.appendChild(option);
			
			row.insertCell(cellCount).appendChild(select);
			cellCount = cellCount + 1;

			option = document.createElement('input');
			option.type = 'button';
			option.name = 'populate_ff_leaderboard_top_10_btn';
			option.value = 'Populate FF Leaderboard Top 10';

			option.id = option.name;
			option.setAttribute('onclick', "processUserSelection(this)");

			div = document.createElement('div');
			div.append(option);

			option = document.createElement('input');
			option.type = 'button';
			option.name = 'cancel_graphics_btn';
			option.id = option.name;
			option.value = 'Cancel';
			option.setAttribute('onclick','processUserSelection(this)');
	
		    div.append(option);
		    
		    row.insertCell(1).appendChild(div);
		    
			document.getElementById('select_graphic_options_div').style.display = '';
			break;
	}
	break;
	
	/*case 'LOAD_MATCH_DETAIL':
		
		$('#golf_div').empty();
		
		//alert(dataToProcess.homeFirstPlayer.full_name);
		if (dataToProcess)
		{
			 var style = document.createElement('style');
	        style.innerHTML = `
	            th, td {
	                padding: 12px;
	                text-align: center;
	                border: 1px solid #ddd;
	                font-family: Arial, sans-serif;
	                font-weight: bold;
	                font-size: 16px;
	            }
	            table {
	                width: 100%;
	                border-collapse: collapse;
	                margin-top: 20px;
	            }
	            th {
	                background-color: #f2f2f2;
	                color: #333;
	            }
	            td {
	                background-color: #fff;
	                color: #555;
	            }
	            table td, table th {
	                border: 1px solid #ddd;
	            }
	            table tr:nth-child(even) {
	                background-color: #f9f9f9;
	            }
	            table tr:hover {
	                background-color: #f1f1f1;
	            }
	        `;
	        document.head.appendChild(style);
			table = document.createElement('table');
			table.setAttribute('class', 'table table-bordered');
			tbody = document.createElement('tbody');
			row = tbody.insertRow(tbody.rows.length);
			
			
			table.appendChild(tbody);
			document.getElementById('golf_div').appendChild(table);
		}
	break;*/
	}
}
function removeSelectDuplicates(select_id)
{
	var this_list = {};
	$("select[id='" + select_id + "'] > option").each(function () {
	    if(this_list[this.text]) {
	        $(this).remove();
	    } else {
	        this_list[this.text] = this.value;
	    }
	});
}
function checkEmpty(inputBox,textToShow) {

	var name = $(inputBox).attr('id');
	
	document.getElementById(name + '-validation').innerHTML = '';
	document.getElementById(name + '-validation').style.display = 'none';
	$(inputBox).css('border','');
	if(document.getElementById(name).value.trim() == '') {
		$(inputBox).css('border','#E11E26 2px solid');
		document.getElementById(name + '-validation').innerHTML = textToShow + ' required';
		document.getElementById(name + '-validation').style.display = '';
		document.getElementById(name).focus({preventScroll:false});
		return false;
	}
	return true;	
}
document.addEventListener("keydown", function(e){

    if(e.key === "Escape" && $('#select_graphic_options_div').is(':visible')){

        $('#select_graphic_options_div').hide();
        $('#match_body_div').show();
        $("#select_event_div").show();
        $("#match_configuration").show();
        $("#golf_div").show();

    }

});

$(document).on('click','button,input[type="button"]',function(){
    setTimeout(function(){
        document.activeElement.blur();
    },10);
});	
