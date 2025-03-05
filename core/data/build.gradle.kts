plugins {
    alias(libs.plugins.financialassistant.core.module)
}

android {
    namespace = "com.transsion.financialassistant.data"

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}