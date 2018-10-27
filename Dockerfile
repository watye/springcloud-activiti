FROM openjdk:8-jre
VOLUME /tmp
ADD springcloud-provider.jar springcloud-provider.jar
RUN bash -c 'touch /springcloud-provider.jar'
EXPOSE 8081
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Xms100m","-Xmx100m","-jar","/springcloud-provider.jar"]