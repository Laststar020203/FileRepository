/**
 * 
 */



var AsyncRequest = {
		xhr : new XMLHttpRequest(),
		ready : function(method, url, callback){
			this.xhr.open(method, url, true);
			this.xhr.onreadystatechange = function(){
				if(xhr.readyState == 4 && xhr.status == 200){
					callback(JSON.parse(xhr.responseText));
				}
			}
		},
		submit: function(data){
			this.xhr.send(data);
		}
		
}