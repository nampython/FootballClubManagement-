$().ready(function(){
	$("#player-manage").click(function(){
		window.location.replace(localIp + 'players');	
	});
	$("#club-manage").click(function(){
		window.location.replace(localIp + 'clubs');	
	});
});

