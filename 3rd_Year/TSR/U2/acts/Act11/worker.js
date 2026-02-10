// File: worker.js
const net = require('net')

const server = net.createServer( 
    function(c) { //connection listener
        console.log('server: client connected')
        c.on('end', 
            function() {
                console.log('server: client disconnected')
            })
        c.on('data', 
            function(data) {
                c.write(parseInt(data+'')*3+'')
            })
    })

server.listen(parseInt(process.argv[2]) || 8001,
    function() { //listening listener
        console.log('server bound')
    })

