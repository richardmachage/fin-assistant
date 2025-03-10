plugins {
    alias(libs.plugins.financialassistant.feature.module)
}

android {
    namespace = "com.transsion.financialassistant.presentation"

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    debugImplementation(libs.ui.tooling)
}