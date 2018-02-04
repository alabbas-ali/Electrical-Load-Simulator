
$(document).ready(function(){
		
	$("#availability-form").submit(function(event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();
			
		disableForm(true);
		var data = {}, activity = {};
		data.activities = [];
			
		$(this).find(":input").each(function() {
			
			if($(this).val() != "" && this.name != ""){	
				
				if(this.name.startsWith('activities')){
					this.name = this.name.replace(/activities\[[0-9]*\]\./, '');
					
					//console.log(this.name);
					
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
			
		console.log(data);
		//submit(data);
	});
			
			
	var addtionalRowIndex = 100;	
	$('#addNewActi').on('click', function(){
			
			var tableRow = '<tr>\
			<td>\
				<input type="text" class="form-control" placeholder="Name" id="activities'+ addtionalRowIndex +'.name" name="activities['+ addtionalRowIndex +'].name">\
			</td>\
			<td>\
				<input type="text" class="form-control" placeholder="Description" id="activities'+ addtionalRowIndex +'.description" name="activities['+ addtionalRowIndex +'].start">\
			</td>\
			<td>\
				<input type="text" class="form-control" placeholder="Power Input On" id="activities'+ addtionalRowIndex +'.powerInputOn" name="activities['+ addtionalRowIndex +'].end">\
			</td>\
			<td>\
				<input type="text" class="form-control" placeholder="Power Input Off" id="activities'+ addtionalRowIndex +'.powerInputOff" name="activities['+ addtionalRowIndex +'].type">\
			</td>\
			<td>\
				<button type="button" class="btn btn-danger btn-sm btn-flat deleteOp" data-toggle="tooltip" data-placement="top" title="delete">\
					<i class="fa fa-trash"></i>\
				</button>\
			</td>\
			</tr>';
			
			$('#activityTable').find('tbody').append(tableRow);
			addtionalRowIndex ++;
			
			$('.deleteOp').on('click', function(){
				$(this).closest('tr').remove();
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
	
	
});
	
	
		