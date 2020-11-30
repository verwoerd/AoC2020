plugins {
  id("aoc.kotlin")
  application
}


val implementation by configurations

dependencies {
  implementation(project(":shared"))
}

tasks {
  withType<Test>() {
    useJUnitPlatform()
  }
}
