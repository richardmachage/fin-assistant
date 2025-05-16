plugins {
    alias(libs.plugins.financialassistant.core.module)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)

}

android {
    namespace = "com.transsion.financialassistant.background"

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(projects.core.data)


    //worker
    implementation(libs.androidx.work.ktx)
    implementation(libs.hilt.work)
    ksp(libs.hilt.ext.compiler)
    testImplementation(libs.work.testing)
    androidTestImplementation(libs.work.testing)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.kotlinx.coroutines.test)


    //firestore
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.installations)


    implementation(libs.kotlinx.serialization.json)
    implementation(libs.datastore.preferences)

    implementation(libs.guava) // Or the latest compatible version
    //worker
    implementation(libs.androidx.work.ktx)
    implementation(libs.hilt.work)
    implementation(libs.androidx.appcompat)
    ksp(libs.hilt.ext.compiler)
    testImplementation(libs.work.testing)
    androidTestImplementation(libs.work.testing)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.junit)
}

