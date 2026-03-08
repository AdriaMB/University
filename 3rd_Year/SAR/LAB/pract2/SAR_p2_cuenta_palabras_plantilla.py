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

    def write_stats_text(self, filename, source_file, lower, stats, use_stopwords, bigrams, entropy, full):
        """
        Este mÃ©todo escribe en texto plano las estadÃ­sticas de un fichero
            
        :param 
            filename: el nombre del fichero destino.
            stats: las estadÃ­sticas del texto.
            use_stopwords: booleano, si se han utilizado stopwords
            full: boolean, si se deben mostrar las stats completas

        :return: None
        """

        total_symbols  = 0
        for i in stats['symbol'].keys():
            total_symbols += stats['symbol'][i]

        if entropy:
            entropy = round(stats['entropy'], 4)
            redundancy = round(stats['redundancy']*100, 2)



        def return_top_X(X)-> dict:
            top_X = [(stats[X][k], k ) for k in stats[X].keys()]
            top_X.sort()

            left = right = -1   # el elemento más a la derecha
            limit = -len(top_X) # son negativos porque al moverse a la izquierda deberemos movernos mientras seamos >= a ellos

            top_X_dict = {}


            while left >= limit:
                right = left
                aux_n = top_X[left][0] # take the number. The list is ordered, therefore the number are too
                
                while left >= limit and top_X[left][0] == aux_n:
                    left -= 1   # me muevo a la izquierda
                
                # stops when top_words[left][0] has a different number. Between left and right, all tuples have the same number and are ordered from a...z
                for i in range(left+1, right+1, +1):
                    #print(f"{top_X[i][1]}        \t{i}")
                    top_X_dict[top_X[i][1]] = top_X[i][0]
                    if not full and len(top_X_dict) == 20:
                        return top_X_dict
            return top_X_dict
        


        def return_alphabetical_order(X) -> dict:
            top_X = [(k, stats[X][k]) for k in stats[X].keys()]
            top_X.sort()

            alpha_X_dict = {}

            for i in range(0, len(top_X)):
                alpha_X_dict[top_X[i][0]] = top_X[i][1]
                if not full and len(alpha_X_dict) == 20:
                    return alpha_X_dict
            return alpha_X_dict


        # Words dictionaries
        words_alphabetic_order = return_alphabetical_order('word')
        words_frequency_order  = return_top_X('word')

        # Symbols dictionaries
        symbols_alphabetic_order = return_alphabetical_order('symbol')
        symbols_frequency_order  = return_top_X('symbol')

        # Word pairs dictionaries
        if bigrams:
            word_pairs_alphabetic_order = return_alphabetical_order('biword')
            word_pairs_frequency_order  = return_top_X('biword')

        # Symbol pairs dictionaries
            symbol_pairs_alphabetic_order = return_alphabetical_order('bisymbol')
            symbol_pairs_frequency_order  = return_top_X('bisymbol')


        with open(filename, 'w', encoding='utf-8') as fh:

            fh.write(f"Lines: {stats['nlines']}\n")
            fh.write(f"Number of words (including stopwords): {stats['nwords']}\n")
            if use_stopwords:
                fh.write(f"Number of words (without stopwords): {stats['nwords_no_stopwords']}\n")

            fh.write(f"Vocabulary size: {len(stats['word'].keys())}\n")
            fh.write(f"Number of symbols: {total_symbols}\n")
            fh.write(f"Number of different symbols: {len(stats['symbol'].keys())}\n")
            if entropy:
                fh.write(f"Shannon entropy: {entropy:.4f} bits/symbol\n")
                fh.write(f"Redundancy: {redundancy:.2f}%\n")

            # words
            fh.write(f"Words (alphabetical order):\n")
            for i in words_alphabetic_order.keys():
                fh.write(f"\t{i}: {words_alphabetic_order[i]}\n")
            fh.write(f"Words (by frequency):\n")
            for i in words_frequency_order.keys():
                fh.write(f"\t{i}: {words_frequency_order[i]}\n")

            # symbols
            fh.write(f"Symbols (alphabetical order):\n")
            for i in symbols_alphabetic_order.keys():
                fh.write(f"\t{i}: {symbols_alphabetic_order[i]}\n")
            fh.write(f"Symbols (by frequency):\n")
            for i in symbols_frequency_order.keys():
                fh.write(f"\t{i}: {symbols_frequency_order[i]}\n")

            if bigrams:
                #word pairs
                fh.write(f"Word pairs (alphabetical order):\n")
                for i in word_pairs_alphabetic_order.keys():
                    fh.write(f"\t{i}: {word_pairs_alphabetic_order[i]}\n")
                fh.write(f"Word pairs (by frequency):\n")
                for i in word_pairs_frequency_order.keys():
                    fh.write(f"\t{i}: {word_pairs_frequency_order[i]}\n")

                # symbol pairs
                fh.write(f"Symbol pairs (alphabetical order):\n")
                for i in symbol_pairs_alphabetic_order.keys():
                    fh.write(f"\t{i}: {symbol_pairs_alphabetic_order[i]}\n")
                fh.write(f"Symbol pairs (by frequency):\n")
                for i in symbol_pairs_frequency_order.keys():
                    fh.write(f"\t{i}: {symbol_pairs_frequency_order[i]}\n")














    """
            nº lineas:                              stats['nlines']
            nº palabras totales:                    stats['nwords']
            nº palabras sin stopwords:              stats['nwords_no_stopwords']
            vocabulario (palabras distintas):       len(stats['words'].keys())
            nº caracteres totales:                  for i in stats['symbol'].keys(): += stats['symbol'][i]
            nº caracteres distintos:                len(sats['symbol'].keys())
    """

    
    def write_stats_json(self, filename, source_file, lower, stats, use_stopwords, bigrams, entropy, full):
        """
        Este mÃ©todo escribe en formato JSON las estadÃ­sticas de un fichero
            
        :param 
            filename: el nombre del fichero destino.
            source_file: el nombre del fichero fuente.
            lower: booleano, si está todo en minúsculas
            stats: las estadÃ­sticas del texto.
            use_stopwords: booleano, si se han utilizado stopwords
            bigrams: booleano, si se ha pedido análisis de bigramas
            full: boolean, si se deben mostrar las stats completas

        :return: None
        """

        total_symbols  = 0
        for i in stats['symbol'].keys():
            total_symbols += stats['symbol'][i]

        #### Función de orden genérica
        # sort, cuando ordenamos una lista de tuplas, primero ordena respecto al primer elemento y luego, en
        # caso de coincidencia, ordena alfabéticamente respecto al segundo elemento
        
        # esta funcion se movera del elemento más a la derecha a la izquierda. Utilizará pointers negativos
        def return_top_X(X)-> dict:
            top_X = [(stats[X][k], k ) for k in stats[X].keys()]
            top_X.sort()


            left = right = -1   # el elemento más a la derecha
            limit = -len(top_X) # son negativos porque al moverse a la izquierda deberemos movernos mientras seamos >= a ellos

            top_X_dict = {}


            while left >= limit:
                right = left
                aux_n = top_X[left][0] # take the number. The list is ordered, therefore the number are too
                
                while left >= limit and top_X[left][0] == aux_n:
                    left -= 1   # me muevo a la izquierda
                
                # stops when top_words[left][0] has a different number. Between left and right, all tuples have the same number and are ordered from a...z
                for i in range(left+1, right+1, +1):
                    #print(f"{top_X[i][1]}        \t{i}")
                    top_X_dict[top_X[i][1]] = top_X[i][0]
                    if not full and len(top_X_dict) == 20:
                        return top_X_dict

            
            return top_X_dict

        ####### Top_words 
        top_words_dict = return_top_X('word')
        

        ######  Top symbols
        top_symbols_dict = return_top_X('symbol')

        ######  Top word pairs
        if bigrams: 
            top_word_pairs_dict = return_top_X('biword')

        ######  Top symbol pairs
            top_symbol_pairs_dict = return_top_X('bisymbol')


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
            "basic_stats":{
                "lines": stats['nlines'],
                "words": stats['nwords']
            },
        }

        if use_stopwords:
            js["basic_stats"]["words_without_stopwords"] = stats['nwords_no_stopwords']
        
        js["basic_stats"]["vocab_size"] = len(stats['word'].keys())
        js["basic_stats"]["symbols"] = total_symbols
        js["basic_stats"]["unique_symbols"] = len(stats['symbol'].keys())


        # if we activate the option of entropy, we display that data. Else, we dont
        if entropy:
            js['entropy_analysis'] = {
                "shannon_entropy": stats['entropy'],
                "redundancy": stats['redundancy']
            }
        # we are always going to display this part. We want to display it in certain order, though
        js['top_words'] = top_words_dict
        js['top_symbols'] = top_symbols_dict

        # if we activate the option of bigrams, we display that data. Else, we dont
        if bigrams:
            js['top_word_pairs'] = top_word_pairs_dict
            js['top_symbol_pairs'] = top_symbol_pairs_dict

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

        
        ############### MÉTRICAS BÁSICAS Y DISTRIBUCIONES DE FRECUENCIA ##############33
        """
            nº lineas:                              stats['nlines']
            nº palabras totales:                    stats['nwords']
            nº palabras sin stopwords:              stats['nwords_no_stopwords']
            vocabulario (palabras distintas):       len(stats['words'].keys())
            nº caracteres totales:                  for i in stats['symbol'].keys(): += stats['symbol'][i]
            nº caracteres distintos:                len(sats['symbol'].keys())
        """

        # generic adder
        def add_X(X_name, X):
            if X in stats[X_name].keys():
                stats[X_name][X] += 1       # add to the dictionary the word
            else:
                stats[X_name][X] = 1        # creates a dict. entry 


        # ANALYSIS 
        with open(filename, 'r') as file:
            for line in file:
                stats['nlines'] += 1

                if lower:
                    line = line.lower()               # if we have to put the line in lower characters

                line = self.clean_re.sub(' ', line)
 
                line = line.split()# line will be a list of words

                if not line or line == ['']:
                    continue
                

                if bigrams:

                    line_bigrams = get_ngrams(line.copy(), 2, True)
                    for bigram in line_bigrams:
                        # We have to ignore the stopwords
                        if bigram[0] in stopwords or bigram[1] in stopwords:
                            continue
                        bigram = bigram[0] + " " + bigram[1]
                        add_X('biword', bigram)


                for word in line:
                        
                    stats['nwords'] += 1

                    if word not in stopwords:

                        # All operations forward will not count the stopwords
                        stats['nwords_no_stopwords'] += 1
                        add_X('word', word)

                        if bigrams:
                            for i in range(len(word)-1):

                                bigram = word[i] + word[i+1]
                                add_X('bisymbol', bigram)

                        for symbol in word:
                            add_X('symbol', symbol)
                        
                

        ################################ ANÁLISIS DE BIGRAMAS ###################

        

        ################################ ENTROPÍA DE SHANNON  ###################
        total_symbols  = 0
        for i in stats['symbol'].keys():
            total_symbols += stats['symbol'][i]
        
        
        if entropy:
            stats['entropy'] = 0.0
            for i in stats['symbol'].keys():
                P_xi = stats['symbol'][i] / total_symbols
                stats['entropy'] += P_xi * math.log2(P_xi)
            stats['entropy'] *= -1

        ################################ REDUNDANCIA ############################
            H_max = math.log2(len(stats['symbol'].keys()))

            stats['redundancy'] = 1 - (stats['entropy'] / H_max )



        ################################ NOMBRAMIENTO DEL ARCHIVO ################
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
            self.write_stats_json(new_filename, filename, lower, stats, stopwordsfile is not None, bigrams, entropy, full)
        else:
            new_filename += "_stats.txt"
            self.write_stats_text(new_filename, filename, lower, stats, stopwordsfile is not None, bigrams, entropy, full)








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
    


