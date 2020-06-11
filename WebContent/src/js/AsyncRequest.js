/**
 * 
 */



var AsyncRequest = {
		xhr : new XMLHttpRequest(),
		ready : function(method, url, callback){
			this.xhr.open(method, url, true);
			this.xhr.onreadystatechange = function(){
				if(this.readyState == 4 && this.status == 200){
					console.log(this.responseText)
					callback(JSON.parse(this.responseText));
				}
			}
		},
		submit: function(data){
			this.xhr.send(data);
		}
		
}