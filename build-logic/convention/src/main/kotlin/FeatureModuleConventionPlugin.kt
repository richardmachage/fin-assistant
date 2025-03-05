import com.android.build.gradle.LibraryExtension
import com.android.builder.model.v2.ide.Library
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class FeatureModuleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.kotlin.android")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            extensions.configure<LibraryExtension> {
                compileSdk = 35

                defaultConfig {
                    minSdk = 26
                    targetSdk = 35
                }

                buildFeatures {
                    compose = true
                }

                dependencies {
                    add(
                        "implementation", platform(
                            versionCatalogLibs.findLibrary("androidx-compose-bom").get()
                        )
                    )
                    add("implementation", versionCatalogLibs.findBundle("default-dependencies").get())
                }
            }
        }
    }
}