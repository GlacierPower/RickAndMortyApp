
buildscript {
    dependencies {

        classpath (libs.hilt.android.gradle.plugin)
        classpath (libs.google.services)
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)


    }
}

plugins {
    alias(libs.plugins.library) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.googleDaggerHiltAndroid) apply false
    alias(libs.plugins.org.jetbrains.kotlin.kapt) apply false
}