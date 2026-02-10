const {lineaOrdenes, traza, error, adios, conecta} = require('../tsr')
const zmq = require("zeromq/v5-compat")
lineaOrdenes("identidad ipPublicador portPublicador tema")



let sub = zmq.socket('sub')
sub.subscribe(tema)
conecta(sub, ipPublicador, portPublicador)

function noMoreMessages(){
    process.exit();
}

function recibeMensaje(tema, numero, ronda) {
    clearTimeout(timer)
	traza('recibeMensaje','tema numero ronda',[tema, numero, ronda])
    timer = setTimeout(noMoreMessages, 5000)

}

    sub.on('message', recibeMensaje)
    sub.on('error'  , (msg) => {error(`${msg}`)})
process.on('SIGINT' , adios([sub],"abortado con CTRL-C"))
let timer = setTimeout(noMoreMessages, 5000)
