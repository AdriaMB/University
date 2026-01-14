const {error, lineaOrdenes, traza, adios, creaPuntoConexion} = require('../tsr')
zmq = require("zeromq/v5-compat")
// 0    1         2    3           4      5      6
// node publisher port numMessages topic1 topic2 topic3 ...
if(process.argv.length < 5){
    console.error("Not enough arguments");
    process.exit(-1);
}
let port = parseInt(process.argv[2]);
let numMessages = parseInt(process.argv[3]);
let topics = process.argv.slice(4);

let msgTopics = [topics.length];

var pub = zmq.socket('pub')
creaPuntoConexion(pub, port)

function envia(tema, numMensaje, ronda) {
    msgTopics[ronda%topics.length]++;
    console.log(tema + "  " + numMensaje + "  " + ronda);
    //traza('envia','tema numMensaje ronda',[tema, numMensaje, ronda])
    pub.send([tema, numMensaje, ronda])
}
function publica(i) {
    return () => {
        envia(topics[i%topics.length], i, Math.trunc(i/topics.length)+1)
        if (i==numMessages) adios([pub],"No me queda nada que publicar. Adios")()
            else setTimeout(publica(i+1),1000)
    }
}
setTimeout(publica(0), 1000)

pub.on('error', (msg) => {error(`${msg}`)})
process.on('SIGINT', adios([pub],"abortado con CTRL-C"))
