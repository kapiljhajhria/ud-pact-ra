# Pact

SpringBootService - Consumer

CoursesApplication - Provider




# Steps
This section will be used to make empty commits with some information

- After running your successfully test in prev commit. Check target/pacts folder.
  You will see a pact file which is automatically generated by pact-mock-service.
- Copy the generated contract from consumer(RestServices) and place it in provider(Courses Service)
- Pact Broker is used to automate above task


## Docker Pact Broker

Create a table in postgres named "pact_broker"

db- postgres

db username - postgres

db password - postgres

Change your postgres username and password accordingly
```bash
docker run -e PACT_BROKER_DATABASE_USERNAME=postgres \
-e PACT_BROKER_DATABASE_PASSWORD=postgres \
-e PACT_BROKER_DATABASE_HOST=host.docker.internal \
-e PACT_BROKER_DATABASE_NAME=pact_broker \
-e PACT_BROKER_DATABASE_PORT=5432 \
-p 9292:9292 \
pactfoundation/pact-broker 
```

## Publish Pact Test results to broker

```bash
mvn clean test -Dpact.verifier.publishResults=true
```

## Provide token to verify the pact, using mvn system property
```bash
mvn clean test -Dpact.verifier.publishResults=true -Dpact.provider.token=my-string-token

```

## Publish Consumer Generated Pact to broker

```bash
mvn pact:publish -Dpact.consumer.token=my-token-here
```

test commit