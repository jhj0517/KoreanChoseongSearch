# KoreanChoseongSearch
한글 초성 검색 기능을 위한 라이브러리입니다.

# Demo
![demo](https://github.com/jhj0517/KoreanChoseongSearch/assets/97279763/3bfbf3d9-b0cc-4ec7-b395-336c919a05ab)


# Installation
1) `settings.gradle`
```gradle
dependencyResolutionManagement {
    repositories {
        // ~
        maven { url=uri("https://jitpack.io") }
    }
}
```
2) `build.gradle` (모듈)
```gradle
dependencies {
    implementation("com.github.jhj0517:KoreanChoseongSearch:1.0.16")
}
```

`Gradle` 8.4 보다 낮은 버전에서는 `settings.gradle`이 아닌 프로젝트 레벨의 `build.gradle` 에 jitpack URL 을 추가하시면 됩니다.

# Usage
1. 검색
```Kotlin
import com.developerjo.choseongsearch.KCS

KCS.compare(query="ㅇㄴㅎㅅㅇ", target="안녕하세요") // returns true
KCS.compare(query="ㅇ녕하ㅅㅇ", target="안녕하세요") // returns true
KCS.compare(query="ㅇ녕하ㅅ여", target="안녕하세요") // returns false
```
2. 초성, 중성, 종성 추출하기
```Kotlin
KCS.getCho("안녕하세요") // returns "ㅇㄴㅎㅅㅇ"
KCS.getJoong("안녕하세요") // returns "ㅏㅕㅏㅔㅛ"
KCS.getJong("안녕하세요") // returns "ㄴㅇ   "
```
