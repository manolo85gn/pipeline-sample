FROM maven:3-jdk-8-alpine

RUN apk add --update git

WORKDIR /app

COPY . .
RUN cd cidr-api \
    && mvn package -Dmaven.test.skip=true
CMD ["ash", "-c", "java -cp cidr-api/target/cidr-api-1.0-SNAPSHOT-jar-with-dependencies.jar com.wizeline.App"]
# docker build -t jm .
# docker run -ti -p 8000:8000 jm
