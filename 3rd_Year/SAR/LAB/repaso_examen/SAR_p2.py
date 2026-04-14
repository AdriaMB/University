#! -*- encoding: utf8 -*-

## Nombres: 

########################################################################
########################################################################
###                                                                  ###
###  Todos los mÃ©todos y funciones que se aÃ±adan deben documentarse  ###
###                                                                  ###
########################################################################
########################################################################

import argparse
import re
import sys
import math
import json

def sort_dic_by_values(d):
    return sorted(d.items(), key=lambda a: (-a[1], a[0]))

def get_ngrams(l, n, add_marks=False):
    if add_marks:
        l.append('$')
        l.insert(0, '$')
    ngrams = []
    for i in range(len(l)-n+1):
        ngrams.append(l[i:i+n])
    return ngrams

class WordCounter:

    def __init__(self):
        """
           Constructor de la clase WordCounter
        """
        self.clean_re = re.compile(r'\W+')

    def write_stats_text(self, filename, stats, use_stopwords, full):
        """
        Este mÃ©todo escribe en texto plano las estadÃ­sticas de un fichero
            
        :param 
            filename: el nombre del fichero destino.
            stats: las estadÃ­sticas del texto.
            use_stopwords: booleano, si se han utilizado stopwords
            full: boolean, si se deben mostrar las stats completas

        :return: None
        """

        with open(filename, 'w', encoding='utf-8') as fh:

            # COMPLETAR
            pass


    def write_stats_json(self, filename, source_file, stats, lower, use_stopwords, full):
        """
        Este mÃ©todo escribe en formato JSON las estadÃ­sticas de un fichero
            
        :param 
            filename: el nombre del fichero destino.
            source_file: el nombre del fichero fuente.
            stats: las estadÃ­sticas del texto.
            lower: booleano, si se ha pasado a minÃºsculas el texto
            use_stopwords: booleano, si se han utilizado stopwords
            full: boolean, si se deben mostrar las stats completas

        :return: None
        """
        
        js = {
            "metadata": {
                "source_file": source_file,
                "options": {
                    "lower": lower,
                    "stopwords": use_stopwords,
                    "bigrams": 'biword' in stats,
                    "entropy": 'entropy' in stats
                }
            }
        }

        # COMPLETAR

        with open(filename, 'w', encoding='utf-8') as fh:
            json.dump(js, fh, indent=4, ensure_ascii=False)




    def file_stats(self, filename, lower, stopwordsfile, bigrams, full, entropy, use_json):
        """
        Este mÃ©todo calcula las estadÃ­sticas de un fichero de texto

        :param 
            filename: el nombre del fichero.
            lower: booleano, se debe pasar todo a minÃºsculas?
            stopwordsfile: nombre del fichero con las stopwords o None si no se aplican
            bigram: booleano, se deben calcular bigramas?
            full: booleano, se deben montrar la estadÃ­sticas completas?
            entropy: booleano, se debe calcular la entropÃ­a de Shannon?
            use_json: booleano, se debe mostrar las estadÃ­sticas en formato JSON?
        :return: None
        """

        stopwords = [] if stopwordsfile is None else open(stopwordsfile, encoding='utf-8').read().split()

        # variables for results

        sts = {
                'nwords_total': 0,
                'nwords':0,
                'nsymbols_total':0,
                'nlines': 0,
                'word': {},
                'symbol': {},
                }

        if bigrams:
            sts['biword'] = {}
            sts['bisymbol'] = {}

        if entropy:
            sts['entropy'] = None
            sts['redundancy'] = None


        options = ""
        if lower:
            options += "l"
        if stopwordsfile:
            options += "s"
        if bigrams:
            options += "b"
        if full:
            options += "f"
        if entropy:
            options += "e"
        if use_json:
            options += "j"
        filename_extension = filename.split('.')[1] if not use_json else "json"
        new_filename = filename.split('.')[0] + "_" + options + "_" + filename_extension if options != "" else filename.split('.')[0] + "_" + filename_extension

       
        def add_X(X_name:str, X):
            if X not in sts[X_name].keys():
                sts[X_name][X] = 1
            else:
                sts[X_name][X] += 1


       # Métricas básicas
        with open(filename, 'r', encoding="utf-8") as f:
            for line in f:

                if lower:
                    line = line.lower()

                sts['nlines'] += 1
                
                line = line.split(' ')
                for word in line:
                    sts['nwords_total'] += 1
                    if stopwordsfile and word in stopwords:
                        next
                    sts['nwords'] += 1
                    add_X("word", word)

                    for symbol in word:
                        sts["nsymbols_total"] += 1
                        add_X("symbol", symbol)
                        
                        




        if use_json:
            self.write_stats_json(new_filename, filename, sts, lower, stopwordsfile is not None, full)
        else:
            self.write_stats_text(new_filename, sts, stopwordsfile is not None, full)


    def compute_files(self, filenames, **args):
        """
        Este mÃ©todo calcula las estadÃ­sticas de una lista de ficheros de texto

        :param 
            filenames: lista con los nombre de los ficheros.
            args: argumentos que se pasan a "file_stats".

        :return: None
        """

        for filename in filenames:
            self.file_stats(filename, **args)

if __name__ == "__main__":

    parser = argparse.ArgumentParser(description='Compute comprehensive statistics from text files.')
    parser.add_argument('file', metavar='file', type=str, nargs='+',
                        help='text files to analyze.')

    parser.add_argument('-l', '--lower', dest='lower',
                        action='store_true', default=False, 
                        help='lowercase all words before computing stats.')

    parser.add_argument('-s', '--stop', dest='stopwords', action='store',
                        help='filename with the stopwords.')

    parser.add_argument('-b', '--bigram', dest='bigram',
                        action='store_true', default=False, 
                        help='compute bigram stats.')

    parser.add_argument('-f', '--full', dest='full',
                        action='store_true', default=False, 
                        help='show full stats (instead of top 20).')
    
    parser.add_argument('-e', '--entropy', dest='entropy',
                        action='store_true', default=False, 
                        help='compute Shannon entropy and redundancy.')
    
    parser.add_argument('-j', '--json', dest='json',
                        action='store_true', default=False,
                        help='output statistics in JSON format.')

    args = parser.parse_args()
    wc = WordCounter()
    wc.compute_files(args.file,
                     lower=args.lower,
                     stopwordsfile=args.stopwords,
                     bigrams=args.bigram,
                     full=args.full,
                     entropy=args.entropy,
                     use_json=args.json)
    


