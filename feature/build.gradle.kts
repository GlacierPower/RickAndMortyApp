plugins {
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.library)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.googleDaggerHiltAndroid)
    id ("androidx.navigation.safeargs.kotlin")

}

android {
    namespace = "com.glacierpower.feature"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures{
        viewBinding = true
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

dependencies {


    //Hilt
    kapt(libs.hilt.android.compiler)
    kapt(libs.androidx.hilt.compiler)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.work.runtime.ktx)

    // Navigation Components
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //Activity and fragments
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.activity.ktx)

    //ConstraintLayout
    implementation(libs.androidx.constraintlayout)

    //Glide
    implementation (libs.glide)
    annotationProcessor (libs.compiler)

    //Lottie
    implementation (libs.android.lottie)

    // SwipeRefreshLayout
    implementation (libs.androidx.swiperefreshlayout)

    // Coroutine Lifecycle Scopes
    implementation(libs.androidx.lifecycle.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.kotlinx.coroutines.core.jvm)
    implementation(libs.kotlinx.coroutines.android)

    //Lifecycle or LiveData
    implementation(libs.androidx.lifecycle.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.lifecycle.runtime.ktx)

    //Paging
    implementation(libs.androidx.paging.runtime.ktx)

    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}