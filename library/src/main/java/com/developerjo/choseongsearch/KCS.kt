package com.developerjo.choseongsearch

/**
 * This library is designed to implement Choseong Search Logic, which is particularly useful for handling Korean text searches by their initial consonants (Choseong).
 * It includes the [compare] function, allowing for Korean Choseong-based search.
 * It features functions like [getCho], a function dedicated to extracting only the Choseong from given words.
 *
 * The Unicode for complete Hangul syllables is calculated using the formula:
 * Complete_Hangul = 0xAC00 + (21 * 28 * ChoSeong) + (28 * JoongSeong) + JongSeong,
 * where 0xAC00 is the starting Unicode point for Hangul, equal to 44032 in decimal.
 */

object KCS {
    private const val HANGUL_UNICODE_START = 44032
    private const val HANGUL_UNICODE_END = 55203

    private val CHO = listOf("ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ", "ㅅ","ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ")
    private val JOONG = listOf("ㅏ","ㅐ","ㅑ","ㅒ","ㅓ","ㅔ","ㅕ","ㅖ","ㅗ","ㅘ", "ㅙ","ㅚ","ㅛ","ㅜ","ㅝ","ㅞ","ㅟ","ㅠ","ㅡ","ㅢ","ㅣ")
    private val JONG = listOf("","ㄱ","ㄲ","ㄳ","ㄴ","ㄵ","ㄶ","ㄷ","ㄹ","ㄺ","ㄻ","ㄼ", "ㄽ","ㄾ","ㄿ","ㅀ","ㅁ","ㅂ","ㅄ","ㅅ","ㅆ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ")

    /**
     * Determines if the query string matches the target string based on Choseong search logic.
     *
     * @param query The query string, which is variable and entered by the user.
     * @param target The target string, which is a fixed value against which the query is compared.
     *
     * @return A boolean value based on Choseong search logic.
     *
     * @sample compare(query="안녕ㅎㅅㅇ", target="안녕하세요")
     *         This returns true.
     */
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

    /**
     * Extracts Choseongs from the given input.
     *
     * @param word The string value from which to extract the Choseong.
     *
     * @return The extracted Choseong as a string.
     *
     * @sample getCho(word="헤헤ㅋㅋ") returns "ㅎㅎㅋㅋ"
     */
    fun getCho(word:String):String{
        var cho = ""
        for(w in word.chunked(1)){
            if(!isHangul(w)){
                cho += " "
                continue
            }

            if(w in CHO){
                cho += w
                continue
            } else if (w in JOONG || w in JONG){
                cho += " "
                continue
            }

            val choIndex = (w.first().code - HANGUL_UNICODE_START)/(JONG.size*JOONG.size)
            cho += CHO[choIndex]
        }
        return cho
    }

    /**
     * Extracts Joongseongs from the given input.
     *
     * @param word The string value from which to extract the Joongseong.
     *
     * @return The extracted Joongseong as a string.
     *
     * @sample getCho(word="헤헤ㅋㅋ") returns "ㅔㅔ  "
     */
    fun getJoong(word:String):String{
        var joong = ""
        for(w in word.chunked(1)){
            if(!isHangul(w)){
                joong += " "
                continue
            }

            if(w in JOONG){
                joong += w
                continue
            } else if (w in CHO || w in JONG){
                joong += " "
                continue
            }

            val joongIndex = (w.first().code-HANGUL_UNICODE_START)/JONG.size%JOONG.size
            joong += JOONG[joongIndex]
        }
        return joong
    }

    /**
     * Extracts Jongseongs from the given input.
     *
     * @param word The string value from which to extract the Jongseong.
     *
     * @return The extracted Jongseong as a string.
     *
     * @sample getCho(word="헐개웃겨ㅋ") returns "ㅎ ㅅ "
     */
    fun getJong(word:String):String{
        var jong = ""
        for(w in word.chunked(1)){
            if(!isHangul(w)){
                jong += " "
                continue
            }

            if(w in JONG && w !in CHO){
                jong += w
                continue
            } else if (w in CHO || w in JOONG){
                jong += " "
                continue
            }

            val jongIndex = (w.first().code-HANGUL_UNICODE_START)%JONG.size
            jong += JONG[jongIndex]
        }
        return jong
    }

    /**
     * Checks if the given string consists only of Hangul characters. Hangul characters range from Unicode 44032 to 55203.
     * Spaces and Hangul Choseong, Joongseong, and Jongseong are also considered as valid.
     *
     * @param word The string to check.
     *
     * @return A boolean value indicating whether the string consists only of Hangul characters.
     *
     * @sample isHangul(word="안녕하세요 ㅎ") returns true.
     */
    fun isHangul(word: String): Boolean {
        for (w in word.chunked(1)) {
            if (w in CHO || w in JOONG || w in JONG || w == " ") {
                continue
            }

            val isHangul = w.single().code in HANGUL_UNICODE_START..HANGUL_UNICODE_END
            if (!isHangul) {
                return false
            }
        }
        return true
    }

    /**
     * Identifies indexes of Choseong-only characters within the word and returns an ArrayList of these indexes.
     *
     * @param word The string from which to identify.
     *
     * @return An ArrayList of Choseong characters within the word.
     *
     * @sample findChoIndexes(word="앜ㅋ앜ㅋ") returns [1, 3]
     */
    private fun findChoIndexes(word:String):ArrayList<Int>{
        val choIndexes = ArrayList<Int>()
        for( (i, w) in word.chunked(1).withIndex()){
            if(w in CHO){
                choIndexes.add(i)
            }
        }
        return choIndexes
    }
}
