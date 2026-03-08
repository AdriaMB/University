#!/usr/bin/env python
#! -*- encoding: utf8 -*-
# 3.- Mono Info

# Recibe como arg el nombre de un fichero donde se ha guardado un modelo de lenguaje y opcionalmente el nombre de un fichero destino y 
# muestra la info de los lm contenidos en el fichero
# Si no se proporciona fichero de destino se muestra la información por pantalla
#

import argparse
from SAR_p3_monkey_lib import Monkey


if __name__ == "__main__":

    parser = argparse.ArgumentParser(description='Show the information of certain language model.')
    parser.add_argument('lm_filename', type=str, action='store',
                        help='language model file.')   
    parser.add_argument('-i', '-info', dest='info_filename', type=str, action='store', default=None,
                        help='name of the file to save the language model information')

    args = parser.parse_args()
    m = Monkey()
    m.load_lm(args.lm_filename)
    if args.info_filename:    
        m.save_info(args.info_filename)
    else:
        m.show_info()
