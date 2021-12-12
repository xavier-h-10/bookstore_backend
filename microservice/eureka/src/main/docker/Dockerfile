FROM java:8
EXPOSE 8040
VOLUME /tmp
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8
ADD eureka-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
