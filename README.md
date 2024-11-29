# WireMock matcher serialization bug

_Running this example requires Java 21_

1. To start, run the app with the following command:
   ```shell
    ./gradlew run
   ```
2. Observe that the matcher passed to the stubbing fails to deserialize:
   ```
   Exception in thread "main" com.github.tomakehurst.wiremock.common.InvalidInputException: {
    "errors" : [ {
        "code" : 10,
        "source" : {
        "pointer" : "/request/bodyPatterns/0/1"
    },
        "title" : "Error parsing JSON",
        "detail" : "{\"expression\":\"$.body\",\"submatcher\":{\"matchesXPath\":{\"expression\":\"//a[@id='example-link']/@href\",\"equalTo\":\"https://example.com\"}}} is not a valid match operation"
    } ]
    }
   ```
