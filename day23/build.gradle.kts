plugins {
  id("aoc.problem")
}
project.application.mainClass.set("MainKt")

tasks {
  test {
    testLogging {
      showStandardStreams = true
    }
  }
}
