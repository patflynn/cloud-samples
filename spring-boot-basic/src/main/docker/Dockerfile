FROM openjdk:8
EXPOSE 8080
VOLUME /tmp
RUN mkdir -p /opt/cdbg
RUN wget -qO- https://storage.googleapis.com/cloud-debugger/compute-java/debian-wheezy/cdbg_java_agent_gce.tar.gz | \
    tar xvz -C /opt/cdbg
ADD spring-boot-simple-0.1.0.jar app.jar
ADD source-context.json
ADD source-contexts.json
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-agentpath:/opt/cdbg/cdbg_java_agent.so=--logtostderr=1","-Djava.security.egd=file:/dev/./urandom","-Dcom.google.cdbg.module=sprint-boot-simple","-Dcom.google.cdbg.version=v4","-jar","/app.jar"]
