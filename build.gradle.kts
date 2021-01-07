import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    id ("org.springframework.boot") version "2.3.3.RELEASE"
    id ("io.spring.dependency-management") version "1.0.8.RELEASE"
    application
}

group = "org.javacord.nijicord"
version = "1.0.0"

description = "An example for the Javacord library."

java {
    sourceCompatibility = VERSION_1_8
}

// Javacord is on Maven central
repositories {
    mavenCentral()
}

// The dependencies of the bot. Javacord and Log4J for logging
dependencies {
    implementation("org.javacord:javacord:3.1.1")
    implementation("org.apache.logging.log4j:log4j-api:2.11.1")
    implementation("org.junit.jupiter:junit-jupiter:5.4.2")
    implementation("org.junit.jupiter:junit-jupiter:5.4.2")
    compile ("mysql","mysql-connector-java", "5.1.40")
    runtimeOnly("org.apache.logging.log4j:log4j-core:2.11.1")
    compile ("com.oracle.ojdbc:ojdbc8:19.3.0.0")
    compile ("com.google.api-client:google-api-client:1.23.0")
    compile ("com.google.oauth-client:google-oauth-client-jetty:1.23.0")
    compile ("com.google.apis:google-api-services-sheets:v4-rev581-1.25.0")
    compile ("io.github.stepio.jgforms:jgforms:1.0.1")
    compile ("io.github.cdimascio:dotenv-java:2.2.0")

}

application {
    mainClass.set("org.javacord.nijicord.Main")
}
