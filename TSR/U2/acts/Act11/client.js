// File: client.js
const net = require('net')

const client = net.connect(parseInt(process.argv[2]) || 8000, 
    function() { //connect listener
        console.log('client connected')
        client.write(process.pid+'')
    })

client.on('data', 
    function(data) {
        console.log(data.toString())
    })

client.on('end', 
    function() {
        console.log('client disconnected')
    })
