package com.developerjo.choseongsearch

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val HANGUL_UNICODE_START = 44032
    private val HANGUL_UNICODE_END = 55203

    private val CHO = listOf("ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ", "ㅅ","ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ")
    private val JOONG = listOf("ㅏ","ㅐ","ㅑ","ㅒ","ㅓ","ㅔ","ㅕ","ㅖ","ㅗ","ㅘ", "ㅙ","ㅚ","ㅛ","ㅜ","ㅝ","ㅞ","ㅟ","ㅠ","ㅡ","ㅢ","ㅣ")
    private val JONG = listOf("","ㄱ","ㄲ","ㄳ","ㄴ","ㄵ","ㄶ","ㄷ","ㄹ","ㄺ","ㄻ","ㄼ", "ㄽ","ㄾ","ㄿ","ㅀ","ㅁ","ㅂ","ㅄ","ㅅ","ㅆ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ")
    @Test
    fun myTest() {
        val i = compare(query="Hello,ㅎ", target="Hellojjj")
        println(i)
    }


    fun compare(query:String, target:String) :Boolean {
        if (query.length > target.length){
            return false
        }

        val choIndexes = findChoIndexes(query)

        if (choIndexes.isNotEmpty()){
            for(c in choIndexes){
                val targetChar = target.chunked(1)[c]
                val targetCho = getCho(targetChar)
                if(query[c].toString() != targetCho){
                    return false
                }
            }

            val filteredTarget = target.filterIndexed { i, _ -> i !in choIndexes }
            val filteredQuery = query.filterIndexed { i, _ -> i !in choIndexes }

            return filteredTarget.contains(filteredQuery)
        }

        return target.contains(query)
    }

    fun findChoIndexes(word:String):ArrayList<Int>{
        val choIndexes = ArrayList<Int>()
        for( (i, w) in word.chunked(1).withIndex()){
            if(w in CHO){
                choIndexes.add(i)
            }
        }
        return choIndexes
    }

    fun getCho(word:String):String{
        if(!isHangul(word)){
            return " "
        }

        var cho = ""
        for(w in word.chunked(1)){
            if(w in CHO){
                cho += w
                continue
            } else if (w in JOONG || w in JONG){
                cho += " "
                continue
            }

            val uniCode = ((w.first().code - 0xAC00)/28/21).toChar().code
            println(word)
            cho += CHO[uniCode]
        }
        return cho
    }

    fun getJoong(word:String):String{
        if(!isHangul(word)){
            return " "
        }
        var joong = ""
        for(w in word.chunked(1)){
            if(w in JOONG){
                joong += w
                continue
            } else if (w in CHO || w in JONG){
                joong += " "
                continue
            }

            val uniCode = ((w.first().code-0xAC00)/28%21).toChar().code
            joong += JOONG[uniCode]
        }
        return joong
    }

    fun getJong(word:String):String{
        if(!isHangul(word)){
            return " "
        }

        var jong = ""
        for(w in word.chunked(1)){
            if(w in JONG){
                jong += w
                continue
            } else if (w in CHO || w in JOONG){
                jong += " "
                continue
            }

            val uniCode = ((w.first().code-44032)%28).toChar().code
            jong += JONG[uniCode]
        }
        return jong
    }

    fun isHangul(word: String): Boolean {
        for(w in word.chunked(1)){
            if (w in CHO || w in JOONG || w in JONG || w == " "){
                continue
            }

            val isHangul = w.single().code in HANGUL_UNICODE_START..HANGUL_UNICODE_END
            if(!isHangul){
                return false
            }
        }
        return true
    }
}