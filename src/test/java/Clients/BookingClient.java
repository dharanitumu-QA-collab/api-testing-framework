package Clients;

import io.restassured.response.Response;
import Pojo.Booking;
import static io.restassured.RestAssured.given;

public class BookingClient {

    public Response createBooking(Booking booking) {
        return given()
                .spec(SpecFactory.baseSpec())
                .body(booking)
                .when()
                .post("/booking");
    }

    public Response getBooking(int bookingId) {
        return given()
                .spec(SpecFactory.baseSpec())
                .when()
                .get("/booking/" + bookingId);
    }

    public Response updateBooking(int bookingId, Booking booking) {
        return given()
                .spec(SpecFactory.baseSpec())
                .auth().preemptive().basic("admin", "password123")
                .body(booking)
                .when()
                .put("/booking/" + bookingId);
    }

    public Response deleteBooking(int bookingId) {
        return given()
                .spec(SpecFactory.baseSpec())
                .auth().preemptive().basic("admin", "password123")
                .when()
                .delete("/booking/" + bookingId);
    }
}