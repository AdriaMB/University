#!/usr/bin/env python
#! -*- encoding: utf8 -*-

# 1.- Pig Latin

import re
import sys
from typing import Optional, Text
from os.path import isfile

class Translator():

    def __init__(self, punt:Optional[Text]=None):
        """
        Constructor de la clase Translator

        :param punt(opcional): una cadena con los signos de puntuación
                                que se deben respetar
        :return: el objeto de tipo Translator
        """
        if punt is None:
            punt = ".,;?!"
        self.re = re.compile(r"(\w+)([" + punt + r"]*)")





    def translate_word(self, word:Text) -> Text:
        """
        Recibe una palabra en inglés y la traduce a Pig Latin

        :param word: la palabra que se debe pasar a Pig Latin
        :return: la palabra traducida
        """
        

        capitalize = word[0].isupper() and word[1].islower()

        vowels = "aeiouyAEIOUY"

        new_word = word
        # The word contains a number or other non-alphabetic character
        if not word[0].isalpha():
            return new_word 

        # else, if the word starts by a vowel...
        elif word[0] in vowels:
            new_word += "YAY" if word.isupper() else "yay" # OLA -> OLAYAY    ola -> olayay    Ola -> Olayay
        # else, the word stars by a consonant
        else:
            first_vowel = null    # default index (En python los índices pueden ser negativos sin que eso rompa el programa: simplemente comienza por atrás... Por esta razón, mejor dejar first_vowel como null y no como un valor negativo)
            for i in word:
                if i in vowels:
                    first_vowel = word.find(i)      # Store the index of the first vowel
                    break
            if first_vowel != 0:                    # Technically, is always going to be different to 0. If there was a vowel in the first position, we wouldn't be here...'
                new_word = word[first_vowel:] + word[:first_vowel] + "ay"
                new_word = new_word.lower()
            else:
                new_word = word

        if(word.isupper()): new_word = new_word.upper()             # If the original word was UPPER, convert it
        elif(capitalize): new_word = new_word.lower().capitalize()  # If the original word had the first letter in capital form, convert it

        return new_word






    def translate_sentence(self, sentence:Text) -> Text:
        """
        Recibe una frase en inglés y la traduce a Pig Latin

        :param sentence: la frase que se debe pasar a Pig Latin
        :return: la frase traducida
        """

        new_sentence = "" # SUSTITUIR ESTA PARTE
        for word in sentence.split(" "):                        # We separate the words of the line by the " "
                                                                # Now, we filter the punctuation marks that the resulting words might have
            #If the word ends in a comma, keep it for later
            if word[len(word)-1] == ",":
                word = word.replace(",", "")
                new_sentence += self.translate_word(word) + ", "
            # If the word ends in a point, same
            elif word[len(word)-1] == ".":
                word = word.replace(".", "")
                new_sentence += self.translate_word(word) + ". "
            # Same with ;
            elif word[len(word)-1] == ";":
                word = word.replace(";", "")
                new_sentence += self.translate_word(word) + "; "
            #
            elif word[len(word)-1] == "?":
                word = word.replace("?", "")
                new_sentence += self.translate_word(word) + "? "
            #
            elif word[len(word)-1] == "!":
                word = word.replace("!", "")
                new_sentence += self.translate_word(word) + "! "
            # Else, if the word doesn't have any punctiation point...
            else:
                new_sentence += self.translate_word(word) + " "
        
        return new_sentence

        """ In this commented version, the punctuation symbols are handled in the translate_word method """
        # aux = [self.translate_word(word) for word in sentence .split(' ')]
        # new_sentence = ' '.join(aux)



    def translate_file(self, filename:Text):
        """
        Recibe un fichero y crea otro con su tradución a Pig Latin

        :param filename: el nombre del fichero que se debe traducir
        :return: None
        """
        
        if not isfile(filename):
            print(f'{filename} no existe o no es un nombre de fichero', file=sys.stderr)

        with (
            open(filename, "r", encoding="utf-8") as f,
            open("new_file.txt", "w", encoding="utf-8") as new_file
        ):
            for line in f.readlines():
                print(line)
                aux = self.translate_sentence(line.replace("\n", ""))
                new_file.write(aux + "\n")
                
        """
        with open(filename, 'r') as original:
            filename_arr = filename.split('.')
            new_filename = filename_arr[0] + '_latin' + '.' + filename_arr[1]

            # Empty the file in case it already exists
            open(new_filename, 'w').close()

            with open(new_filename, 'a') as newfile:
                for line in original:
                    newfile.write(self.translate_sentence(line.strip()) + '\n')

        """







if __name__ == "__main__":
    if len(sys.argv) > 2:                                       # Si hay demasiados argumentos
        print(f'Syntax: python {sys.argv[0]} [filename]')
        exit()
    t = Translator()
    if len(sys.argv) == 2:                                      # Si Hay 1 argumento: el nombre del fichero de texto
        t.translate_file(sys.argv[1])
    else:                                                       # Sino, entra en loop donde pida palabras que traducir
        sentence = input("ENGLISH: ")
        while len(sentence) > 1:
            print("PIG LATIN:", t.translate_sentence(sentence))
            sentence = input("ENGLISH: ")
