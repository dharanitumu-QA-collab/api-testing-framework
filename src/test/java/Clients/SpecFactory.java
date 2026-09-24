package Clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.ContentType;

public class SpecFactory {

    public static RequestSpecification baseSpec() {
        String env = System.getProperty("env", "qa"); // defaults to qa
        String baseUrl = switch (env) {
            case "qa" -> "https://restful-booker.herokuapp.com";
            case "staging" -> "https://staging-booker.example.com";
            default -> "https://restful-booker.herokuapp.com";
        };

        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

    }


}