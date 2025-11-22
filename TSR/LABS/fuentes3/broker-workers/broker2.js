const {lineaOrdenes, traza, error, adios, creaPuntoConexion, conecta} = require('../tsr')
const zmq = require('zeromq/v5-compat')

lineaOrdenes("frontendPort backendPort")
let workers  =[] // workers disponibles
//let pendiente=[] // peticiones no enviadas a ningun worker
let frontend = zmq.socket('dealer')
let backend  = zmq.socket('router')
conecta(frontend, "localhost", frontendPort) // frontendPort = 8887
creaPuntoConexion(backend,  backendPort)

function procesaPeticion(cliente, msg) { // llega peticion desde el DEALER del broker1
	traza('procesaPeticion','cliente msg',[cliente,msg])
	//  If there are workers
	if (workers.length) backend.send([workers.shift(),'',cliente,msg])
	else pendiente.push([cliente,msg]) // In case there aren't
}
function procesaMsgWorker(worker,sep,cliente,resp) {
	traza('procesaMsgWorker','worker sep cliente resp',[worker,sep,cliente,resp])
	/**
	if (pendiente.length) { // hay trabajos pendientes. Le pasamos el mas antiguo al worker
		let [c,m] = pendiente.shift()
		backend.send([worker,'',c,m])
	}
	else workers.push(worker) // añadimos al worker como disponible
		*/

	workers.push(worker) // añadimos al worker como disponible
	if (cliente) frontend.send([cliente,resp]) // habia un cliente esperando esa respuesta
	else frontend.send(['', '']) // It is a connection message. We notify broker1 that there is a worker available
}

frontend.on('message', procesaPeticion)
frontend.on('error'  , (msg) => {error(`${msg}`)})
 backend.on('message', procesaMsgWorker)
 backend.on('error'  , (msg) => {error(`${msg}`)})
 process.on('SIGINT' , adios([frontend, backend],"abortado con CTRL-C"))


 function displayInfo(){



}
