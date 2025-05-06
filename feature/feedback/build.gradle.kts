plugins {
    alias(libs.plugins.financialassistant.feature.module)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.transsion.financialassistant.feedback"
}

dependencies {
    //project dependencies
    implementation(projects.core.presentation)
    implementation(projects.core.data)



    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.rules)
    implementation(libs.firebase.firestore)

    /*implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)*/

    debugImplementation(libs.ui.tooling)


    //data store
    implementation(libs.datastore.preferences)

    //cloudinary
    implementation(libs.cloudinary.android)
    implementation(libs.coil.compose)

}