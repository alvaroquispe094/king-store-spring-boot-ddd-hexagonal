plugins {
	java
	id("org.springframework.boot") version "3.1.5"
	id("io.spring.dependency-management") version "1.1.3"
	id("jacoco")
}

group = "com.groupal"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// Database
	runtimeOnly("org.postgresql:postgresql")

	// Lombok
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// Kafka
	implementation("org.springframework.kafka:spring-kafka")

	// JWT
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")

	// Parser
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	//swagger
	//implementation ("io.springfox:springfox-boot-starter:3.0.0") // o la versión más reciente
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

	// Logstash
	implementation("net.logstash.logback:logstash-logback-encoder:7.2")

	// ArchUnit
	testImplementation("com.tngtech.archunit:archunit:0.23.1")
	testImplementation("com.tngtech.archunit:archunit-junit5:0.23.1")

	// Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<JavaCompile> {
	options.isDeprecation = true
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.bootBuildImage {
	builder.set("paketobuildpacks/builder-jammy-base:latest")
}

jacoco {
	toolVersion = "0.8.10"
}

tasks.jacocoTestReport {
	dependsOn(tasks.test) // Asegura que los tests se ejecuten antes del reporte

	reports {
		//xml.required.set(true) // Para la integración con otras herramientas
		html.required.set(true) // Para generar un reporte HTML
	}
}

tasks.jacocoTestCoverageVerification {
	dependsOn(tasks.jacocoTestReport) // Verificación después del reporte
	violationRules {
		rule {
			limit {
				counter = "INSTRUCTION" // Contar líneas de código
				value = "COVEREDRATIO"
				minimum = "0.00".toBigDecimal() // Umbral del 0% de cobertura por el momento, hasta hacer los tests
			}
		}
	}
}

tasks.check {
	dependsOn(tasks.jacocoTestCoverageVerification) // Falla si no se cumple el umbral de cobertura
}

tasks.build {
	dependsOn(tasks.test) // Ejecutar las pruebas antes del build
	dependsOn(tasks.jacocoTestReport) // Generar el reporte como parte del build
	dependsOn(tasks.jacocoTestCoverageVerification) // Verificar la cobertura como parte del build
}

tasks.test {
	finalizedBy(tasks.jacocoTestCoverageVerification) // Ejecuta el reporte después de los tests.
}