# KoreanChoseongSearch
한글 초성 검색 기능을 위한 라이브러리입니다. ([구버전 V1](https://github.com/jhj0517/KoreanChosungSearch))

# Demo
https://github.com/jhj0517/KoreanChoseongSearch/assets/97279763/58f89429-b060-4258-bf43-8e02b126208b

# Installation
1) `settings.gradle` 에 maven url 추가 
```gradle
pluginManagement {
  //~
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url=uri("https://jitpack.io") }
    }
}
```
2) 모듈 레벨의 `build.gradle`
```gradle
dependencies {
    implementation("com.github.jhj0517:KoreanChoseongSearch:1.0.16")
}
```

위 예시는 `Gradle` 8.4 버전 기준으로, JitPack 라이브러리를 추가하는 방법은 `Gradle` 버전에 따라 조금씩 다를 수 있습니다. 

예:) 낮은 버전의 `Gradle` 에서는 프로젝트 레벨의 `build.gradle` 에 maven url 을 추가함.


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
