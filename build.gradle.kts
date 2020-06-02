import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.3.61"

  // Apply the application plugin to add support for building a CLI application.
  application
}


java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
  jcenter()
  mavenCentral()
}

dependencies {
  // Align versions of all Kotlin components
  //implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

  implementation("software.amazon.awscdk:core:1.42.0")
  implementation("software.amazon.awscdk:iam:1.42.0")

  testImplementation("org.jetbrains.kotlin:kotlin-test")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
  // Define the main class for the application.
  mainClassName = "com.example.AppKt"
}


tasks {
  val buildZip by registering(Zip::class) {
    from(compileKotlin)
    from(processResources)
    into("lib") {
      from(configurations.compileClasspath)
    }
  }
  named("build") {
    dependsOn(buildZip)
  }
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "1.8"
  }
}
