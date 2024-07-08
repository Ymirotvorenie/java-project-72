setup:
	./gradlew wrapper --gradle-version 8.7

clean:
	./gradlew clean

build:
	./gradlew clean build

install:
	./gradlew clean installDist

run-dist:
	./build/install/app/bin/app

run:
	./gradlew run

lint:
	./gradlew checkstyleMain

report:
	./gradlew jacocoTestReport

test:
	./gradlew test

check-updates:
	./gradlew dependencyUpdates

.PHONY: build