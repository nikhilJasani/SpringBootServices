##
#   basic image built using
#       gradle version 6.8.3
#       openjdk version 11
#
#   build image using
#       docker build . -t "atm-locator-app"
#
#   run container using
#       docker run -it -p 8080:8080 atm-locator-app
##
FROM gradle:6.8.3-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build
FROM openjdk:11
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/atm-locator-application.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/atm-locator-application.jar"]