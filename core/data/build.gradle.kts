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
    ksp(libs.room.compiler)
    implementation(libs.bundles.room)
}