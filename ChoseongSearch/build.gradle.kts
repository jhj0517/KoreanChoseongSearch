import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("maven-publish")
}

android {
    namespace = "com.developerjo.choseongsearch"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

val githubProperties = Properties()
githubProperties.load(FileInputStream(rootProject.file("github.properties")))

publishing{
    publications{
        register<MavenPublication>("release"){
            groupId = "com.jhj0517"
            artifactId = "koreanchoseongsearch"
            version = "1.0.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }

    repositories {
        maven {
            name = "ChoseongSearch"
            url = uri("https://maven.pkg.github.com/jhj0517/KoreanChoseongSearch")
            credentials{
                username = githubProperties.getProperty("gpr.usr") ?: System.getenv("GITHUB_USERNAME")
                password = githubProperties.getProperty("gpr.key") ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}