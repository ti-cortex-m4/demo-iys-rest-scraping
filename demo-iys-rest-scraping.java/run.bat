call gradle clean build
:repeat
java -jar ./build/libs/demo-iys-rest-scraping-0.0.1.jar
timeout 300
goto repeat