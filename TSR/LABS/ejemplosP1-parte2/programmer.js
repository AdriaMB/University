const net = require('net');

if(process.argv.length < 5){
    console.error("Not enough arguments: <IP proxy>   <new_server_IP>   <new_server_PORT>")
    process.exit(-1);
}
let arg = process.argv.slice(2);
let IP_PROXY = arg[0];
let IP_NEW_SERVER = arg[1];
let PORT_NEW_SERVER = arg[2];


const programmer = net.connect({host: IP_PROXY, port:8001}, function() { //connect listener
    console.log('programmer connected');
    var msg = JSON.stringify({remote_ip: IP_NEW_SERVER, remote_port: PORT_NEW_SERVER})
    programmer.write(msg);
    console.log("Message sent")
    process.exit(0)


});
