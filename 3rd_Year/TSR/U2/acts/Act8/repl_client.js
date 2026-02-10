/* repl_client.js */

const net = require('net')
const sock = net.connect(8001)
process.stdin.pipe(sock)
sock.pipe(process.stdout)
