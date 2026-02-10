const {error, lineaOrdenes, traza, adios, creaPuntoConexion} = require('../tsr')
zmq = require("zeromq/v5-compat")
if(process.argv.length < 5){
    console.error("Not enough arguments")
    console.exit(-1)
}
                                 // 0     1            2       3
let argv = process.argv.slice(2) // port, numMessages, topic1, topic2...

let temas = argv.slice(2);           console.log(temas)
let port = parseInt(argv[0]);        console.log(port)
let numMessages = parseInt(argv[1]); console.log(numMessages)

var pub = zmq.socket('pub')
creaPuntoConexion(pub, port)

function envia(tema, numMensaje, ronda) {
    traza('envia','tema numMensaje ronda',[tema, numMensaje, ronda])
    pub.send([tema, numMensaje, ronda])
}
function publica(i) {
    return () => {
        envia(temas[i%temas.length], i+1, Math.trunc(i/temas.length +1))
        if (i==numMessages) adios([pub],"No me queda nada que publicar. Adios")()
            else setTimeout(publica(i+1),1000)
    }
}
setTimeout(publica(0), 1000)

pub.on('error', (msg) => {error(`${msg}`)})
process.on('SIGINT', adios([pub],"abortado con CTRL-C"))
