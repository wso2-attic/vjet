var sys=require('sys'); 	
var http = require('http'); 
var target = "jsconf";
var connection = http.createClient(80, "search.twitter.com"); 
var since = 0;
function getTweets() {
    var request = connection.request( //<<
    	'GET', "/search.json?q=" + target + "&since_id="
    	+since, {"host": "search.twitter.com", "User-Agent": "NodeJS HTTP Client"});
    
    request.addListener("response", 
    	//> void f(org.nodejs.http.ClientResponse)
    	function(response)
    {
        var responseBody = "";
        response.setEncoding("utf8");
        response.addListener("data", function(chunk) { responseBody += chunk });
        response.addListener("end", function() {
            tweets = JSON.parse(responseBody);
            var results = tweets["results"],
                length = results.length;
            for (var i = (length-1); i >= 0; i--) {
                if (results[i].id > since) {
                  since = results[i].id;
                }
                sys.puts("From " + results[i].from_user + ": " + results[i].text);
            }
        });
    }); // end addListener("response")
    request.close();
    setTimeout(getTweets, 10000);
};
 
getTweets();

