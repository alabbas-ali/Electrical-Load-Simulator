var stompClient = null;



$(document).ready(function()
{
    
    $("#simulation").submit(function(event) {

		// Prevent the form from submitting via the browser.
		event.preventDefault();
		
		var data = {}
		
		$(this).find(":input").each(function() {
		    // The selector will match buttons; if you want to filter
		    // them out, check `this.tagName` and `this.type`; see
		    // below
			data[this.name] = $(this).val();
		});
		
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "/start-simulation",
			data : JSON.stringify(data),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				console.log("SUCCESS: ", data);
				//display(data);
				connect();
			},
			error : function(e) {
				console.log("ERROR: ", e);
				$('#error').html(e);
				$('#error').show();
			}
		});

	});
    
});



function connect(){
	
	console.log("Connecting to SockJS");
    var socket = new SockJS('/sim-status');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame){
        stompClient.subscribe('/initial', function (messageOutput){
            console.log("INITIAL: " + messageOutput);
            var progressList = $.parseJSON(messageOutput.body);
            $.each(progressList,function(index, element){
                update(element);
            });
        });

        stompClient.subscribe('/simulation/sim-status', function(messageOutput) {
            console.log("New Message: " + messageOutput);
            var messageObject = $.parseJSON(messageOutput.body);
            update(messageObject);
        });


    });
}

function update(newMessage)
{
	var rows = $('#tableBody').find('#'+newMessage.jobName);
	if(rows.length === 0)
	{
		$('#tableBody').append('<div id="'+newMessage.jobName+'">' +
				'<div>'+newMessage.jobName+'</div>'+
				'<div class="progress">'+
				'<div class="progress-bar" role="progressbar" aria-valuenow="" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em;">0%</div>'+
				'</div>' +
				'</div>'+'<div class="state"></div>');
    }

	//set stuffs
    var parentDiv = $('#'+newMessage.jobName);
    parentDiv.find('.progress-bar').html(newMessage.progress +"%");
    parentDiv.find('.progress-bar').css('width',newMessage.progress+'%').attr("aria-valuenow",newMessage.progress);
    parentDiv.find('.state').text(newMessage.status);
    if(newMessage.state == "DONE")
    {
        parentDiv.removeClass("active info success").addClass("success");
    }
    else if(newMessage.state == "RUNNING")
    {
        parentDiv.removeClass("active info success").addClass("active");
    }
    else if(newMessage.state == "NEW")
    {
        parentDiv.removeClass("active info success").addClass("info");
    }
    //end set stuffs
}

