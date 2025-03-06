plugins {
    alias(libs.plugins.financialassistant.core.module)
}

android {
    namespace = "com.transsion.financialassistant.background"

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

}