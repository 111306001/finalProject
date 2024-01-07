plugins {
    id("com.android.application")
}

android {

    namespace = "com.example.searchapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.searchapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.6")
    implementation("androidx.navigation:navigation-ui:2.7.6")
    implementation("com.android.volley:volley:1.2.1") // Added Volley library

    implementation(files("libs\\axis.jar"))
    implementation(files("libs\\jsoup-1.17.1.jar"))
    implementation(files("libs\\commons-discovery-0.2.jar"))
    implementation(files("libs\\commons-logging.jar"))
    implementation(files("libs\\jaxrpc.jar"))
    implementation(files("libs\\saaj.jar"))
    implementation(files("libs\\wsdl4j.jar"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
