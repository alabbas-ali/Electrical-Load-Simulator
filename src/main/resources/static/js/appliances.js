
var addtionalRowIndex = 100;
$(document).ready(function(){
	
	
	$("#appliance-form").submit(function(event) {

		// Prevent the form from submitting via the browser.
		event.preventDefault();
		
		disableForm(true);
		var data = {};
		data['operationalModes'] = [];
		prefex = '';
		var i = 0;
		
		$(this).find(":input").each(function() {
			if($(this).val() != "" && this.name != "")
			{	
				if(this.name.startsWith('operationalModes')){
					var array = this.name.split(".");
					var name = array[array.length - 1];
					if( name === 'name'){
						data['operationalModes'][i] = {};
					}
					data['operationalModes'][i][name] = $(this).val();
					if( name === 'leftRestartDelay'){
						i ++;
					}
				}else{
					data[this.name] = $(this).val();
				}
			}
		});
		
		
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "/appliance/save",
			data : JSON.stringify(data),
			dataType : 'json',
			timeout : 100000,
			success : function(response) {
				if(response.status === 'SUCCESS'){
					alert('SUCCESS');
					window.location.replace("/appliance");
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
		
		
	});
	
	
	function disableForm(bool){
		$("#appliance-form :input").prop("disabled", bool);
	}
	
	$('#addNewOp').on('click', function(){
		
		var tableRow = 
			'<tr>\
		<td>\
			<input type="text" class="form-control" placeholder="Name" id="operationalModes'+ addtionalRowIndex +'.name" name="operationalModes['+ addtionalRowIndex +'].name">\
		</td>\
		<td>\
			<textarea cols="150" rows="3" class="form-control" placeholder="Description" id="operationalModes'+ addtionalRowIndex +'.description" name="operationalModes['+ addtionalRowIndex +'].description"></textarea>\
		</td>\
		<td>\
			<input type="text" class="form-control" placeholder="Power Input On" id="operationalModes'+ addtionalRowIndex +'.powerInputOn" name="operationalModes['+ addtionalRowIndex +'].powerInputOn">\
		</td>\
		<td>\
			<input type="text" class="form-control" placeholder="Power Input Off" id="operationalModes'+ addtionalRowIndex +'.powerInputOff" name="operationalModes['+ addtionalRowIndex +'].powerInputOff">\
		</td>\
		<td>\
			<input type="text" class="form-control" placeholder="Restart Delay" id="operationalModes'+ addtionalRowIndex +'.restartDelay" name="operationalModes['+ addtionalRowIndex +'].restartDelay">\
		</td>\
		<td>\
			<input type="text" class="form-control" placeholder="Scale Factor" id="operationalModes'+ addtionalRowIndex +'.scaleFactor" name="operationalModes['+ addtionalRowIndex +'].scaleFactor">\
		</td>\
		<td>\
			<input type="text" class="form-control" placeholder="Duration" id="operationalModes'+ addtionalRowIndex +'.duration" name="operationalModes['+ addtionalRowIndex +'].duration">\
		</td>\
		<td>\
			<input type="text" class="form-control" placeholder="Left Cycle Time" id="operationalModes'+ addtionalRowIndex +'.leftCycleTime" name="operationalModes['+ addtionalRowIndex +'].leftCycleTime">\
		</td>\
		<td>\
			<input type="text" class="form-control" placeholder="Left Restart Delay" id="operationalModes'+ addtionalRowIndex +'.leftRestartDelay" name="operationalModes['+ addtionalRowIndex +'].leftRestartDelay">\
		</td>\
		<td>\
			<button type="button" class="btn btn-info btn-sm btn-flat" data-toggle="modal" data-target="#modal-default'+ addtionalRowIndex +'">\
				<i class="fa fa-edit"></i>\
			</button>\
			\
			<div class="modal modal-primary fade" id="modal-default'+ addtionalRowIndex +'" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="display: none;">\
		      	<div class="modal-dialog" role="document">\
		        	<div class="modal-content">\
		          		<div class="modal-header">\
		            		<button type="button" class="close" data-dismiss="modal" aria-label="Close">\
		              		<span aria-hidden="true">×</span></button>\
		            		<h4 class="modal-title">Primary Modal</h4>\
		          		</div>\
		          		<div class="modal-body">\
		            		<p>One fine body '+ addtionalRowIndex +'</p>\
		          		</div>\
		          		<div class="modal-footer">\
		            		<button type="button" class="btn btn-outline pull-left" data-dismiss="modal">Save &amp; Close</button>\
		          		</div>\
		        	</div>\
		        	<!-- /.modal-content -->\
		      	</div>\
		      	<!-- /.modal-dialog -->\
		    </div>\
			\
		</td>\
		<td>\
			<button type="button" class="btn btn-danger btn-sm btn-flat deleteOp" data-toggle="tooltip" data-placement="top" title="delete">\
				<i class="fa fa-trash"></i>\
			</button>\
		</td>\
		</tr>';
		$('#opTable').find('tbody').append(tableRow);
		addtionalRowIndex ++;
		
		$('.deleteOp').on('click', function(){
			$(this).closest('tr').remove();
		});
	});
	
	
	$('.deleteOp').on('click', function(){
		$(this).closest('tr').remove();
	});
	
});



