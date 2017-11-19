var stompClient = null;

$(document).ready(function()
{
    $('#btnAddJobs').click(function(){
        $.get("/trigger");
        
        setTimeout(updateProgress, 10);
    });
    
    connect();
    
}

function connect(){
    var socket = new SockJS('/mystatus');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame){
        stompClient.subscribe('/app/initial', function (messageOutput){
            console.log("INITIAL: "+messageOutput);
            var progressList = $.parseJSON(messageOutput.body);
            $.each(progressList,function(index, element){
                update(element);
            });
        });

        stompClient.subscribe('/topic/status', function(messageOutput) {
            console.log("New Message: "+messageOutput);
            var messageObject = $.parseJSON(messageOutput.body);
            update(messageObject);
        });


    });
}

function update(newMessage)
{
	var rows = $('#tableBody').find('#'+element.jobName);
	if(rows.length === 0)
	{
		$('#tableBody').append('<div id="'+element.jobName+'">' +
				'<div>'+element.jobName+'</div>'+
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