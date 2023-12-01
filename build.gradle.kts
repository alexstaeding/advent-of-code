import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktlint)
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    dependencies {
        testImplementation(rootProject.libs.kotest)
    }

    tasks {
        test {
            useJUnitPlatform()
        }
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "17"
        }
    }
}
