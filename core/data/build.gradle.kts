plugins {
    alias(libs.plugins.financialassistant.core.module)
}

android {
    namespace = "com.transsion.financialassistant.data"
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    //project dependencies
    implementation(projects.core.security)
    implementation(libs.androidx.appcompat)
    implementation (libs.androidx.security.crypto)

    //room
    ksp(libs.room.compiler)
    implementation(libs.bundles.room)

    //data store
    implementation(libs.datastore.preferences)

    //testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.kotlinx.coroutines.test)


}