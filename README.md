# A simple full stack app example
+ jetty as embedded server
+ jersey as Rest framework





# To run app use folling:

```bash
 ./gradlew clean build &&  ./gradlew run 
```

# To package a FatJar and compile as a jar file

```bash
./gradlew shadowJar  && java -jar ./app/build/libs/app-all.jar
```