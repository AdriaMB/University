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
        if not word.isalpha():
            return word
        
        upper = word.isupper()
        capitalize = word[0].isupper() is word[1].islower()

        vowels = "aeiouy"

        print("Hello")

        new_word = word.lower()
        if new_word[0] not in vowels:
            print(new_word)
            first_vowel = 0
            for i in range(0, len(new_word)):
                first_vowel = i
                if new_word[i] in vowels: 
                    break # found first vowel
            aux = new_word
            new_word = aux[first_vowel:len(new_word)] + aux[0:first_vowel] + "ay"
        else:
            new_word = new_word + "yay"

        if upper:
            new_word = new_word.upper()
        elif capitalize:
            new_word = new_word.capitalize()

        return new_word





    def translate_sentence(self, sentence:Text) -> Text:
        """
        Recibe una frase en inglés y la traduce a Pig Latin

        :param sentence: la frase que se debe pasar a Pig Latin
        :return: la frase traducida
        """

        new_sentence = ""
        sentence = sentence.split(' ')

        for word in sentence:
            if word[len(word)-1] in ".,;?!":
                punctuation = word[len(word)-1]
                word = self.translate_word(word[0:len(word)])
                word = word + punctuation
            else:
                
                word = self.translate_word(word)
            new_sentence = word if new_sentence == "" else new_sentence + " " + word

        return new_sentence + "\n"





    def translate_file(self, filename:Text):
        """
        Recibe un fichero y crea otro con su tradución a Pig Latin

        :param filename: el nombre del fichero que se debe traducir
        :return: None
        """
        print("hoelefjkfjsl")

        if not isfile(filename):
            print(f'{filename} no existe o no es un nombre de fichero', file=sys.stderr)
            return
        
        with open(filename, 'r', encoding="utf-8") as f:
            aux = filename.split('.')
            output_filename = aux[0] + "_latin" + aux[1]
            with open(output_filename, 'w', encoding="utf-8") as out:
                content = f.read()
                for sentence in content:
                    sentence = self.translate_sentence(sentence)
                    out.write(sentence)
                






if __name__ == "__main__":
    if len(sys.argv) > 2:
        print(f'Syntax: python {sys.argv[0]} [filename]')
        exit()
    t = Translator()
    if len(sys.argv) == 2:
        t.translate_file(sys.argv[1])
    else:
        sentence = input("ENGLISH: ")
        while len(sentence) > 1:
            print("PIG LATIN:", t.translate_sentence(sentence))
            sentence = input("ENGLISH: ")
