package com.alexyach.kotlin.translator.utils

import com.alexyach.kotlin.translator.data.retrofit.modelDto.Sense
import com.alexyach.kotlin.translator.data.retrofit.modelDto.WordTranslate
import com.alexyach.kotlin.translator.domain.model.WordTranslateModel

    /** WordDTO -> WordTranslateModel */
    fun wordDtoToWordTranslateModel(wordDto: WordTranslate): WordTranslateModel {
        val wordsList = mutableListOf<String>()
        val examplesList = mutableListOf<String>()

        val wordDtoSens: List<Sense> = wordDto.results[0].lexicalEntries[0]
            .entries[0].senses

        // Words Translate
        wordDtoSens?.forEach { sens ->
            if (!sens.translations.isNullOrEmpty()) {
                val currentWordList: List<String> = sens.translations.map { it.text }

                if (!currentWordList.isNullOrEmpty()) {
                    wordsList.add(listToString(currentWordList))
                }
            } else {
                wordsList.add("Не має перекладу")
            }

            // Examples use
            if (!sens.examples.isNullOrEmpty()) {
                val currentExample: List<String> = sens.examples.map { it.text }

                if (!currentExample.isNullOrEmpty()) {
                    examplesList.add(listToString(currentExample))
                }
            } else {
                examplesList.add("Не має застосування")
            }

        }

        val soundPath: String = soundPath(wordDto) ?: ""

        return WordTranslateModel(
            translateWordList = wordsList,
            exampleWordList = examplesList,
            soundPath = soundPath
        )
    }

    private fun listToString(list: List<String>): String {
        var listToString = ""

        list.forEach {
            listToString += " ○ $it\n"
        }
        return listToString.substring(0, listToString.length - 1)
    }

    private fun soundPath(wordDto: WordTranslate): String? {
        return wordDto.results?.get(0)?.lexicalEntries?.get(0)
            ?.entries?.get(0)?.pronunciations?.get(0)?.audioFile
    }
    /** END WordDTO -> WordTranslateModel */

