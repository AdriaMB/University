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

def sort_dic_by_values(d, asc=True):
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
        self.clean_re = re.compile(r'\W+')          # Había que poner r'\W+'


    """
            nº lineas:                              stats['nlines']
            nº palabras totales:                    stats['nwords']
            nº palabras sin stopwords:              stats['nwords_no_stopwords']
            vocabulario (palabras distintas):       len(stats['words'].keys())
            nº caracteres totales:                  for i in stats['symbol'].keys(): += stats['symbol'][i]
            nº caracteres distintos:                len(sats['symbol'].keys())
    """

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










    """
            nº lineas:                              stats['nlines']
            nº palabras totales:                    stats['nwords']
            nº palabras sin stopwords:              stats['nwords_no_stopwords']
            vocabulario (palabras distintas):       len(stats['words'].keys())
            nº caracteres totales:                  for i in stats['symbol'].keys(): += stats['symbol'][i]
            nº caracteres distintos:                len(sats['symbol'].keys())
    """

    
    def write_stats_json(self, filename, source_file, stats, lower, use_stopwords, full):

        total_symbols  = 0
        for i in stats['symbol'].keys():
            total_symbols += stats['symbol'][i]

        """
        Este mÃ©todo escribe en formato JSON las estadÃ­sticas de un fichero
            
        :param 
            filename: el nombre del fichero destino.
            source_file: el nombre del fichero fuente.
            stats: las estadÃ­sticas del texto.
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
                },
            
            },
            "basics_stats":{
                "lines": stats['nlines'],
                "words": stats['nwords'],
                "vocab_size":  len(stats['word'].keys()),
                "symbols": total_symbols,
                "unique_symbols": len(stats['symbol'].keys())

            },
            "entropy_analysis":{
                "shannon_entropy": 0,
                "redundancy": 0

            },
            "top_words": "a",
            "top_symbols":"a"

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

        stats = {
                'nwords': 0,
                'nwords_no_stopwords': 0,
                'nlines': 0,
                'word': {},
                'symbol': {},
                }

        if bigrams:
            stats['biword'] = {}
            stats['bisymbol'] = {}

        if entropy:
            stats['entropy'] = None
            stats['redundancy'] = None

        """
            nº lineas:                              stats['nlines']
            nº palabras totales:                    stats['nwords']
            nº palabras sin stopwords:              stats['nwords_no_stopwords']
            vocabulario (palabras distintas):       len(stats['words'].keys())
            nº caracteres totales:                  for i in stats['symbol'].keys(): += stats['symbol'][i]
            nº caracteres distintos:                len(sats['symbol'].keys())

        """
        def add_word(word):
            if word in stats['word'].keys():
                stats['word'][word] += 1       # add to the dictionary the word
            else:
                stats['word'][word] = 1        # creates a dict. entry 

        def add_symbols(symbol):
            if symbol in stats['symbol'].keys():
                stats['symbol'][symbol] += 1
            else:
                stats['symbol'][symbol] = 1


        # ANALYSIS 
        with open(filename, 'r') as file:
            for line in file:
                stats['nlines'] += 1

                line = self.clean_re.sub(' ', line)

                if lower:
                    line = line.lower()               # if we have to put the line in lower characters

                line = re.split(r'\s+', line.strip()) # line will be a list of words

                for word in line:
                    stats['nwords'] += 1

                    if word in stopwords:
                        pass    # We pass to the next word
                    # All operations forward will not count the stopwords
                    stats['nwords_no_stopwords'] += 1
                    add_word(word)

                    for symbol in word:
                        add_symbols(symbol)

        # New filename: old _ options _ .txt/.json
        options_str = ""
        options_str += 'l' if lower else ''
        options_str += 's' if stopwordsfile else ''
        options_str += 'b' if bigrams else ''
        options_str += 'f' if full else ''
        options_str += 'e' if entropy else ''
        options_str += 'j' if use_json else ''

        new_filename = filename.split(".")[0]
        new_filename += "_" + options_str if options_str != "" else ""

        if use_json:
            new_filename += "_stats.json"
            self.write_stats_json(new_filename, filename, lower, stats, stopwordsfile is not None, full)
        else:
            new_filename += "_stats.txt"
            self.write_stats_text(new_filename, stats, stopwordsfile is not None, full)








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
    


