FROM openjdk:8
EXPOSE 8080
VOLUME /tmp
RUN mkdir -p /opt/cdbg
RUN wget -qO- https://storage.googleapis.com/cloud-debugger/compute-java/debian-wheezy/cdbg_java_agent_gce.tar.gz | \
    tar xvz -C /opt/cdbg
ADD ${project.build.finalName}.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-agentpath:/opt/cdbg/cdbg_java_agent.so=--logtostderr=1","-Djava.security.egd=file:/dev/./urandom","-Dcom.google.cdbg.module=${service.name}","-Dcom.google.cdbg.version=${project.version}","-jar","/app.jar"]
