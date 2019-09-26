FROM openjdk:8-jre-alpine
RUN  apk update && apk  upgrade && apk add netcat-openbsd
RUN mkdir -p /usr/local/produitimmobilierservice
ADD  produit-immobilier-0.0.1-SNAPSHOT.jar /usr/local/produitimmobilierservice/
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh