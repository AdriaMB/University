#!/usr/bin/env python
#! -*- encoding: utf8 -*-
# 3.- Mono Library

import pickle
import random
import re
import sys
from typing import List, Optional, TextIO

## Nombres: Blanca Verenguer Tomás              Adrià Marín Bayarri

########################################################################
########################################################################
###     El programa recibe como argumento el nombre de uno o más ficheros de texto, un valor n y un nombre de fichero de destino. 
###     También se puede dar el nombre del modelo de lenguaje
###     Para cada fichero de entrada, lo divide en frases, los tokeniza y crea un modelo estadístico donde acumula estadísticas de qué palabras siguen a otras
###     El resultado serán modelos de lenguaje de 2 a n-gramas
###     Guarda estos modelos en un archivo binario para su posterior uso
########################################################################
########################################################################





"""
Llenamos el diccionario de entradas que indexaremos como 2, 3, ..., n (range excluye el último valor)
Las claves de cada n-diccionario serán tuplas de tokens. El valor asociado a una clave será una tupla con 
    1) nº de veces mque la secuencia de tokens de la clave ha aparecido
    2) lista ordenada por frecuencia de pares de tokens y número de veces que es token ha aparecido después de la clave             
"""                                                                                                   
def convert_to_lm_dict(d: dict):
    for k in d:
        l = sorted(((y, x) for x, y in d[k].items()), reverse=True)
        d[k] = (sum(x for x, _ in l), l)
        
class Monkey():

    def __init__(self):
        self.r1 = re.compile('[.;?!]')  # se utiliza para separar por los signos de puntuacion
        self.r2 = re.compile('\W+')     # quitar todos los alfanuméricos
        self.info = {}


    def get_n(self):
        return self.info.get('n', 0)


    """
    index_sentence: recive una frase y añade sus estadísticas a los modelos de lenguaje. 
                    Debemos llenar self.info['lm] con entradas de diccionario (cada una siendo un lm distinto), donde:
                        1) Las claves son tuplas de n-1 tokens
                        2) Los valores son diccionarios con claves = palabras que van después de la tupla y valores = frecuencia de esa palabra
    """
    def index_sentence(self, sentence:str):
        n = self.info['n'] # longintud del n-grama: n-1 serán las palabras que consideraremos como historia previa. 
        # Deberemos añadir n-1 $ al inicio. El $ final ya está añadido en compute_lm
        # Debemos rellenar cada lm.

        for i in range(2, n+1):
            # Cada lm tiene una n distinta. Crearemos para cada iteración una nueva frase con n-1 $ al inicio
            new_sentence = "$ "*(i-1) + sentence
            token_list = new_sentence.split(' ')
            
            for j in range(0, len(token_list)-i+1):
                # Cogemos los siguientes n-1 elementos, y los almacenamos en una tupla
                token = tuple(token_list[i] for i in range(j, j+i-1))
                # Obtenemos la palabra siguiente a la tupla
                next_element = token_list[j+i-1] 

                # Accedemos al diccionario del lm correspondiente
                # Si ya hemos encontrado este token...
                if token in self.info['lm'][i].keys():
                    # Si ya hemos encontrado la siguiente palabra, sumamos 1 en su frecuencia...
                    if next_element in self.info['lm'][i][token].keys():
                        self.info['lm'][i][token][next_element] += 1 
                    # o la inicializamos a 1 si es la primera vez que la vemos
                    else:
                        self.info['lm'][i][token][next_element] = 1
                # Si es la primera vez que encontramos este token, inicializamos su diccionario y añadimos la palabra
                else:
                    self.info['lm'][i][token] = {}
                    self.info['lm'][i][token][next_element] = 1 

                # Si la siguiente palabra era $, significa que hemos llegado al final de la frase, y podemos pasar
                if next_element == "$":  
                    break



    """
    compute_lm: recibe una  lista de nombres de fichero, los procesa, 
                extrae las frases y llama a index_sentence para crear el modelo de lenguaje 
                (modelo computacional capaz de calcular las probabilidades de una secuencia de palabras y de a siguiente palabra)
    """
    def compute_lm(self, filenames:List[str], lm_name:str, n:int):
        self.info = {'name': lm_name, 'filenames': filenames, 'n': n, 'lm': {}}
        # Dada una n, SE CREARÁN n-1 MODELOS DISTINTOS (2-gramas, 3-gramas, ..., n-gramas)
        # Cada modelo de i-gramas se guarda en un diccionario de Python (estamos creando un diccionario de diccionarios):
        for i in range(2, n+1):
            self.info['lm'][i] = {} # Llenamos el diccionario de entradas que indexaremos como 2, 3, ..., n (range excluye el último valor)                                                                                                                                            

            # LAS FRASES SE DEBEN SEPARAR:
        for filename in filenames:
            with open(filename, encoding='utf-8') as fh:

                content = fh.read()
                # Primero separamos por párrafos
                paragraphs = re.split('\n\n', content)

                for line in paragraphs:

                    # Tokenización: separamos las frases por signos de puntuación
                    phrases = re.split(self.r1, line)

                    
                    for sentence in phrases: 
                        # Eliminamos los símbolos no alfanuméricos, substituyendolos por espacios en blanco y pasamos las frases a minúsculas
                        sentence = self.r2.sub(' ', sentence).lower().strip()
                        # Añadimos un $ para simbolizar el final de frase
                        if sentence == "":
                            continue
                        
                        sentence += " $"
                        # Llamamos a index_sentence, que recive una frase y la dividirá en tokens de n-gramas, para su posterior análisis
                        self.index_sentence(sentence.strip())
                        # Por ende, estamos cogiendo cada frase individualmente y, por cada una, la estudiamos y 
                        # añadimos los tokens n dimensión a los lm

                        
        for i in range(2, n+1):
            # Convertimos cada uno de los lm que hemos creado en index_sentence a la forma diccionario más eficiente
            convert_to_lm_dict(self.info['lm'][i])  
            
    def load_lm(self, filename:str):
        with open(filename, "rb") as fh:
            self.info = pickle.load(fh)


    """
    save_lm: guarda un modelo de lenguaje en el archivo filename
    """
    def save_lm(self, filename:str):
        with open(filename, "wb") as fh:
            pickle.dump(self.info, fh)

    def save_info(self, filename:str):
        with open(filename, "w", encoding='utf-8', newline='\n') as fh:
            self.print_info(fh=fh)

    def show_info(self):
        self.print_info(fh=sys.stdout)

    def print_info(self, fh:TextIO):
        print("#" * 20, file=fh)
        print("#" + "INFO".center(18) + "#", file=fh)
        print("#" * 20, file=fh)
        print(f"language model name: {self.info['name']}", file=fh)
        print(f'filenames used to learn the language model: {self.info["filenames"]}', file=fh)
        print("#" * 20, file=fh)
        print(file=fh)
        for i in range(2, self.info['n']+1):
            print("#" * 20, file=fh)
            print("#" + f'{i}-GRAMS'.center(18) + "#", file=fh)
            print("#" * 20, file=fh)
            for prev in sorted(self.info['lm'][i].keys()):
                wl = self.info['lm'][i][prev]
                print(f"'{' '.join(prev)}'\t=>\t{wl[0]}\t=>\t{', '.join(['%s:%s' % (x[1], x[0]) for x in wl[1]])}" , file=fh)




    """
    generate_sentences: genera frases aleatorias utilizando el modelo de lenguaje
    """
    def generate_sentences(self, n:Optional[int], nsentences:int=10, prefix:Optional[str]=None):
        # Se genera una historia inicial = tupla de talla n-1
        # Si no se proporciona prefijo, se inicia con n-1 "$". Si hay prefijo, se limpia, se separa por palabras y se crea el inicio rellenando con "$" por la izquierda
        ini = ""
        n = n if n else self.info['n']      # CAMBIAR: n es optativo. Por defecto es el mayor número posible

        if prefix:
            ini = self.r2.sub(' ', prefix).lower() # Quitamos todos los NO símbolos alfanuméricos
            ini_length = len(ini.split(' ')) 

            if ini_length <= n-1:          # Si la longitud del ini es menor a la longitud de las llaves en el lm de n, entonces debemos añadir $ al principio
                ini = "$ "*(n-1-ini_length) + ini
                ini = ini.strip()
                ini = ini.split(' ')
            else:
                ini = ini.strip()
                ini = ini.split(' ')
                ini = ini[len(ini)-(n-1):len(ini)] # si el prefijo es más largo que n-1, entonces debemos coger las últimas palabras de él
        else:
            ini = "$ "*(n-1)        # El inicio será n-1 $s
            ini = ini.strip()
            ini = ini.split(' ')    # Separamos por espacios en blancoS
        

        # Dado un inicio, la siguiente palabra se elige de manera aleatoria entre las sucesorias posibles teniendo en cuenta el nº de veces que han aparecido y el nº de veces que el antecedente ha aparecido
        # Para cada palabra que añadamos, descartamos la primera de nuestra tupla y añadimos la palabra que hayamos escogido en la decision anterior (vamos actualizando la historia) 
        ini = tuple(ini)
        res = prefix if prefix else ""       # string donde guardaremos el resultado que escribiremos al final
        
        backup_ini  = ini      # Guardamos el valor de ini para no perderlo

        for i in range(0, nsentences):
            # Hay que generar nsentences. En cada iteración generaremos una frase
            # La frase termina cuando la siguiente palabra sea $ , o cuando se llega a 50 palabras
            # Reseteamos todos los valores
            word = ""
            count = 0
            res = prefix if prefix else ""
            ini = backup_ini

            while count < 50:
                ini_info = self.info['lm'][n][ini] # tupla de: 1) apariciones TOTALES de ini 2) lista de palabras siguientes con sus frecuencias
                weights = []                    # lista de pesos de cada posible continuacion
                next_words = []                 # lista de palabras siguientes
                ini_freq = ini_info[0]          # probabilidad de la historia
                # Añadimos a las listas de palabras y probabilidades las palabras posibles y sus probabilidades de aparecer
                for word_info in ini_info[1]:
                    next_words.append(word_info[1])
                    weights.append(word_info[0] / ini_freq) # la prob = veces que aparece esa palabra después del antecedente / veces que aparece el antecedente
                
                # Method from random. Chooses randomly k elements of a list population given some weights. If no weights given, the probability is evenly distributed
                word = random.choices(population=next_words, weights=weights,k=1)[0] # WATCH OUT: IT RETURNS A LIST OF 1 ELEMENT, that is why we put a [0]
                # Añadimos a la frase la siguiente palabra si no es $
                if word != "$":
                    res = res + " " + word
                    count+=1                # Aumentamos el counter de palabras
                else:
                    break       # Hemos llegado al final. salimos
                
            
                ini = [ini[i] for i in range(1, len(ini))]      # Creamos una lista con las todas las palabras de ini excepto la primera...
                ini.append(word)                                # Añadimos el precedente a esta lista...
                ini = tuple(ini)                                # Volvemos a convertir la lista en tupla

            # Eliminamos espacios en blanco al inicio y final (por si acaso) y printeamos el resultado
            res = res.strip()
            print(f"{res}")




if __name__ == "__main__":
    print("Este fichero es una librerÃ­a, no se puede ejecutar directamente")


