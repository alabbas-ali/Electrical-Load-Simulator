
$(document).ready(function(){
	
	
	$("#appliance-form").submit(function(event) {

		// Prevent the form from submitting via the browser.
		event.preventDefault();
		
		disableForm(true);
		var data = {}, operationalMode = {}, loadCurve = {}, measurement = {};
		data.operationalModes = [];
		
		$(this).find(":input").each(function() {
			if($(this).val() != "" && this.name != "")
			{	
				if(this.name.startsWith('operationalModes'))
				{
					this.name = this.name.replace(/operationalModes\[[0-9]*\]\./, '');
					if( this.name === 'name'){
						operationalMode = {};
					}
					
					if(this.name.startsWith('loadCurve')){
						this.name = this.name.replace(/loadCurve\./, '');
						
						if(this.name === 'name'){
							loadCurve = {};
							loadCurve.measurements = [];
						}
						
						if(this.name.startsWith('measurements')){
							this.name = this.name.replace(/measurements\[[0-9]*\]\./, '');
							if(this.name === 'time'){
								measurement = {};
								measurement.time = $(this).val();
							} else {
								measurement.value = $(this).val();
								loadCurve.measurements.push(measurement);
							}
						} else {
							loadCurve[this.name] = $(this).val();
						}
						operationalMode.loadCurve = loadCurve;
					} else{
						operationalMode[this.name] = $(this).val();
					} 
					if(this.name === 'leftRestartDelay'){
						data.operationalModes.push(operationalMode);
					}
				} else {
					data[this.name] = $(this).val();
				}
			}
		});
		
		
		
		console.log(data);
		//submit(data);
	});
	
	
	function submit(data){
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
	}
	
	
	function disableForm(bool){
		$("#appliance-form :input").prop("disabled", bool);
	}

	var addtionalRowIndex = 100;
	
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
			<div class="modal modal-primary fade" id="modal-default'+ addtionalRowIndex +'" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">\
	          	<div class="modal-dialog" role="document">\
	            	<div class="modal-content">\
	              		<div class="modal-header">\
	                		<button type="button" class="close" data-dismiss="modal" aria-label="Close">\
	                  		<span aria-hidden="true">×</span></button>\
	                		<h4 class="modal-title">Edit Load Curve</h4>\
	              		</div>\
	              		<div class="modal-body">\
	                		<div class="form-group">\
								<label for="curve-name-'+ addtionalRowIndex +'" class="col-lg-2 control-label">\
									Curve Name\
								</label>\
								<div class="col-lg-10">\
									<input type="text" class="form-control" id="curve-name-'+ addtionalRowIndex +'" placeholder="Curve Name" name="operationalModes['+ addtionalRowIndex +'].loadCurve.name" >\
								</div>\
							</div>\
							\
							<div class="form-group">\
								<label for="curve-description-'+ addtionalRowIndex +'" class="col-lg-2 control-label">\
									Curve Description\
								</label>\
								<div class="col-lg-10">\
									<textarea cols="150" rows="3" class="form-control" id="curve-description-'+ addtionalRowIndex +'" placeholder="Curve Description" name="operationalModes['+ addtionalRowIndex +'].loadCurve.description"></textarea>\
								</div>\
							</div>\
							<div class="form-group">\
								<label for="inputPassword" class="col-lg-2 control-label">\
								Measurements\
								<br>\
								<br>\
								</label>\
								<div class="col-lg-10">\
									<button class="btn btn-primary btn-flat" data-toggle="tooltip" data-placement="top" data-target="#opTable'+ addtionalRowIndex +'"  data-id="'+ addtionalRowIndex +'" type="button" id="addNewMeas" title="Add New Measurements">\
										<i class="fa fa-plus"></i> Add New Measurements\
									</button>\
									<br>\
									<br>\
								</div>\
							</div>\
	                		<table id="opTable'+ addtionalRowIndex +'" class="table table-bordered table-hover dataTable">\
								<thead>\
									<tr>\
										<th>Time</th>\
										<th>Load Value</th>\
										<td>Option</td>\
									</tr>\
								</thead>\
								<tbody>\
								\
								</tbody>\
							</table>\
	              		</div>\
	              		<div class="modal-footer">\
	                		<button type="button" class="btn btn-outline pull-left" data-dismiss="modal">Save And Close</button>\
	              		</div>\
	            	</div>\
	          	</div>\
	        </div>\
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
		
		$('button[data-toggle="modal"]').on('click', function(){
			$($(this).attr('data-target')).show();
		});
	});
	
	
	
	var measurementsIndex = 1000;
	$('#addNewMeas').on('click', function(){
		
		var index = $(this).attr('data-id');
		measurementsIndex ++;
		var tableRow = '<tr>\
							<td>\
							<input type="number" class="form-control" placeholder="Left Restart Delay" name="operationalModes['+ index +'].loadCurve.measurements[' + measurementsIndex + '].time" />\
						</td>\
						<td>\
						    <input type="number" class="form-control" placeholder="Left Restart Delay" name="operationalModes['+ index +'].loadCurve.measurements[' + measurementsIndex + '].value" />\
						</td>\
						<td>\
							<button type="button" class="btn btn-danger btn-sm btn-flat deleteOp" data-toggle="tooltip" data-placement="top" title="delete">\
								<i class="fa fa-trash"></i>\
							</button>\
						</td>\
					</tr>';
		
		$($(this).attr('data-target')).find('tbody').append(tableRow);
		
		$('.deleteOp').on('click', function(){
			$(this).closest('tr').remove();
		});
	});
	
	
	$('.deleteOp').on('click', function(){
		$(this).closest('tr').remove();
	});
	
});



