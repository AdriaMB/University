const fs = require('fs')
const net = require('net')


if(process.argv.length < 3){

    console.error("Not enough arguments")
    process.exit(-1)
}


let arg = process.argv.slice(2)


const server = net.createServer( function(client) {

    console.log("Client connected")

    client.on("data", function(msg, who){
        console.log("The client is: " + who)

    })

    client.on("end", function(){
        console.log("bye")
        process.exit(-1)

    })

})

server.listen(process.argv[2], function() { //listening listener
    console.log('server bound');
});
//
