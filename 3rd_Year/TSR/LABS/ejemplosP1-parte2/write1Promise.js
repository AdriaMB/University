const fs = require('fs')

function writeFilePromise(){
    return new Promise( (resolve, reject) =>{
        fs.writeFile('/tmp/f', 'contents of a new file', 'utf8',
                     function(err){
                         if(err) return console.log(err)
                    }

        )
    })

}


writeFilePromise().then(console.log("Write completed"))
