import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.0"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
	kotlin("kapt") version "1.6.10"
}

group = "com.pan"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	// Validation:
	implementation("org.springframework.boot:spring-boot-starter-validation:2.7.0")
	// JPA, MySQL and H2 Database:
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("mysql:mysql-connector-java")
	runtimeOnly("com.h2database:h2")
	testRuntimeOnly("com.h2database:h2")
	// Mapstruct DTO mapping:
	compileOnly("org.mapstruct:mapstruct:1.5.1.Final")
	kapt("org.mapstruct:mapstruct-processor:1.5.1.Final")
	// Swagger documentation:
	implementation("io.springfox:springfox-swagger-ui:2.9.2")
	implementation("io.springfox:springfox-swagger2:2.9.2")
	// Rabbit MQ Messaging:
	implementation ("org.springframework.boot:spring-boot-starter-amqp")
	// Flyway DB Migration:
	implementation("org.flywaydb:flyway-core:8.5.12")
	// Tests:
	implementation("com.google.code.gson:gson:2.9.0")
	testImplementation("com.github.tomakehurst:wiremock:2.27.2")
	testImplementation ("org.springframework.boot:spring-boot-starter-test")
	testImplementation ("org.springframework.amqp:spring-rabbit-test")
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
	//testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")
	testImplementation("io.mockk:mockk:1.12.4")
}

kapt {
	useBuildCache = true
	arguments {
		arg("mapstruct.defaultComponentModel", "spring")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
