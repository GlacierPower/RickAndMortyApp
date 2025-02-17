
buildscript {
    dependencies {

        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.48")
        classpath ("com.google.gms:google-services:4.4.0")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.6")


    }
}

plugins {
    id ("com.android.application") version "8.2.0" apply false
    id ("com.android.library") version "8.2.0" apply false
    id ("org.jetbrains.kotlin.android") version "1.9.0" apply false
}