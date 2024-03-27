package com.developerjo.choseongsearch

private const val HANGUL_UNICODE_START = 44032
private const val HANGUL_UNICODE_END = 55203


private val CHO = listOf("ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ", "ㅅ","ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ")
private val JOONG = listOf("ㅏ","ㅐ","ㅑ","ㅒ","ㅓ","ㅔ","ㅕ","ㅖ","ㅗ","ㅘ", "ㅙ","ㅚ","ㅛ","ㅜ","ㅝ","ㅞ","ㅟ","ㅠ","ㅡ","ㅢ","ㅣ")
private val JONG = listOf("","ㄱ","ㄲ","ㄳ","ㄴ","ㄵ","ㄶ","ㄷ","ㄹ","ㄺ","ㄻ","ㄼ", "ㄽ","ㄾ","ㄿ","ㅀ","ㅁ","ㅂ","ㅄ","ㅅ","ㅆ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ")

/*
Korean unicode formula = (cho * 21 + jung) * 28 + jong + 0xAC00
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
private fun findChoIndexes(word:String):ArrayList<Int>{
    val choIndexes = ArrayList<Int>()
    for( (i, w) in word.chunked(1).withIndex()){
        if(w in CHO){
            choIndexes.add(i)
        }
    }
    return choIndexes
}

fun getCho(word:String):String{
    var word_cho = word

    if(isHangul(word.single())){
        val charuni = word[0]
        val cho_uniIndex = ((charuni.code-0xAC00) / 28 /21).toChar().code
        word_cho = CHO[cho_uniIndex]
    }

    return word_cho
}

fun getJoong(word:String):String{
    var word_joong = word

    if(isHangul(word.single())){
        val charuni = word[0]
        val joong_uniIndex = ((charuni.code-0xAC00) / 28 %21).toChar().code
        word_joong = JOONG[joong_uniIndex]
    }
    return word_joong
}

fun getJong(word:String):String{
    var word_jong = word

    if(isHangul(word.single())){
        val charuni = word[0]
        val jong_uniIndex = ((charuni.code-0xAC00) %28).toChar().code
        word_jong = JONG[jong_uniIndex]
    }
    return word_jong
}

fun isHangul(word: Char): Boolean {
    return word.code in HANGUL_UNICODE_START..HANGUL_UNICODE_END
}