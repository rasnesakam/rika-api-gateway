plugins {
	java
	id("org.springframework.boot") version "3.1.5"
	id("io.spring.dependency-management") version "1.1.3"
	id("com.google.protobuf") version "0.9.4"
}

val grpcVersion = "1.60.0-SNAPSHOT" // CURRENT_GRPC_VERSION
val protobufVersion = "3.24.0"
val protocVersion = protobufVersion

group = "com.rika"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	implementation ("io.grpc:grpc-protobuf:${grpcVersion}")
	implementation ("io.grpc:grpc-services:${grpcVersion}")
	implementation ("io.grpc:grpc-stub:${grpcVersion}")

	runtimeOnly ("io.grpc:grpc-netty-shaded:${grpcVersion}")

	testImplementation ("io.grpc:grpc-testing:${grpcVersion}")
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:${protocVersion}"
	}
	plugins {
		create("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:${grpcVersion}"
		}
	}
	generateProtoTasks {
		all().forEach {
			it.plugins {
				create("grpc")
			}
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
