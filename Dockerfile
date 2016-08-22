FROM java:7

MAINTAINER Stefan Bergmann <stefan.bergmann.ext@sintec.de>

RUN apt-get update && \
        apt-get install -y maven

COPY src/* /usr/src/

WORKDIR /usr/src/

#RUN mvn myProject.xml

#RUN javac Main.java
#CMD ["java", "Main"]

