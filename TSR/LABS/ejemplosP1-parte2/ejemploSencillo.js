const http = require('http');			// import http module
function dd(i) {return (i<10?"0":"")+i;}	//  dd(8) = "08", dd(16) = "16"

const server = http.createServer(
	function (req,res) {
 		res.writeHead(200,{'Content-Type':'text/html'});   // creates the server
 		res.end('<marquee>Node y Http</marquee>');	   // associate this func
 		var d = new Date();				   // which returns a fixed
 		console.log('alguien ha accedido a las '+	   // response
 		d.getHours() +":"+
 		dd(d.getMinutes()) + ":"+ dd(d.getSeconds()) );    //and also writes the time
	}).listen(8000);					   //to the console
	// the server listens to port 80000
