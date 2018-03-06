
$(document).ready(function(){
		
	$("#availability-form").submit(function(event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();
			
		disableForm(true);
		var data = {}, activity = {};
		data.activities = [];
			
		$(this).find("input, select").each(function() {
			
			if($(this).val() != "" && this.name != ""){	
				
				if(this.name.startsWith('activities')){
					
					this.name = this.name.replace(/activities\[[0-9]*\]\./, '');
					this.name = this.name.replace(/activities[0-9]*\./, '');
					
					activity[this.name] = $(this).val();
					if( this.name === 'type'){
						data.activities.push(activity);
						activity = {};
					}
				} else {
					data[this.name] = $(this).val();
				}
			}
			
		});
			
		//console.log(data);
		submit(data);
	});
			
			
	var addtionalRowIndex = 100;	
	$('#addNewActi').on('click', function(){
		
			var typeSelectEl = $('#typeSelect').clone();
			typeSelectEl.attr('name','activities'+ addtionalRowIndex +'.type');
			typeSelectEl.attr('id','activities'+ addtionalRowIndex +'.type');
			typeSelectEl.attr('style', '');
			
			var tableRow = '<tr>\
			<td>\
				<input type="text" class="form-control" placeholder="Name" id="activities'+ addtionalRowIndex +'.name" name="activities['+ addtionalRowIndex +'].name">\
			</td>\
			<td>\
				<div class="bootstrap-timepicker">\
					<div class="input-group">\
					<input type="text" class="form-control timepicker" placeholder="Start" id="activities'+ addtionalRowIndex +'.start" name="activities['+ addtionalRowIndex +'].start">\
					<div class="input-group-addon"><i class="fa fa-clock-o"></i></div></div>\
				</div>\
			</td>\
			<td>\
				<div class="bootstrap-timepicker">\
					<div class="input-group">\
					<input type="text" class="form-control timepicker" placeholder="End" id="activities'+ addtionalRowIndex +'.end" name="activities['+ addtionalRowIndex +'].end">\
					<div class="input-group-addon"><i class="fa fa-clock-o"></i></div></div>\
				</div>\
			</td>\
			<td>' + typeSelectEl.prop('outerHTML') +
			'</td>\
			<td>\
				<button type="button" class="btn btn-danger btn-sm btn-flat deleteOp" data-toggle="tooltip" data-placement="top" title="delete">\
					<i class="fa fa-trash"></i>\
				</button>\
			</td>\
			</tr>';
			
			$('#activityTable > tbody').append(tableRow);
			addtionalRowIndex ++;
			
			$('.deleteOp').on('click', function(){
				$(this).closest('tr').remove();
			});
			
			//Timepicker
		    $('.timepicker').timepicker({
		      showInputs: false
		    });
	});
	
	
	
	function submit(data){
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "/availability/save",
			data : JSON.stringify(data),
			dataType : 'json',
			timeout : 100000,
			success : function(response) {
				if(response.status === 'SUCCESS'){
					alert('SUCCESS');
					window.location.replace("/availability");
				}else{
					errorInfo = "";
					for(i =0 ; i < response.result.length ; i++){
						errorInfo += "<br>" + (i + 1) +". " + response.result[i].defaultMessage;
					}
					disableForm(false);
					$('#error').html("Please correct following errors: " + errorInfo);
					$('#error').show('slow');
				}
				
			},
			error : function(e) {
				disableForm(false);
				$('#error').html(e.responseJSON.message);
				$('#error').show();
			}
		});
	}
	
	
	function disableForm(bool){
		$("#availability-form :input").prop("disabled", bool);
	}
	
	
	//Timepicker
    $('.timepicker').timepicker({
      showInputs: false
    });
	
	
});
	
	
		