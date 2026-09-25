package tests;

import Clients.BookingClient;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import Pojo.Booking;
import Pojo.BookingDates;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.awaitility.Awaitility.await;
import java.util.concurrent.TimeUnit;

public class BookingTest {

    BookingClient bookingClient = new BookingClient();
    Booking booking = new Booking(
            "Dharani", "Tumu", 150, true,
            new BookingDates("2026-10-01", "2026-10-05"),
            "Breakfast"
    );

    @Test(groups = "smoke")
    public void fullCrudFlow() {


        // CREATE
        Response createResponse = bookingClient.createBooking(booking);
        createResponse.then().statusCode(200);
        int bookingId = createResponse.path("bookingid");

        // READ
        bookingClient.getBooking(bookingId)
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Dharani"))
                .body(matchesJsonSchemaInClasspath("schemas/booking-schema.json"));

        Booking retrieved = bookingClient.getBooking(bookingId).as(Booking.class);
        System.out.println(retrieved.getFirstname());
        System.out.println("testing the build");

        // UPDATE
        booking.setTotalprice(200);
        bookingClient.updateBooking(bookingId, booking)
                .then()
                .statusCode(200)
                .body("totalprice", equalTo(200));

        // DELETE
        bookingClient.deleteBooking(bookingId)
                .then()
                .statusCode(201);

        // CONFIRM GONE
        bookingClient.getBooking(bookingId)
                .then()
                .statusCode(404);
    }

    @Test
    public void asyncBookingExample() {
        // Suppose creating a booking triggers async processing before it's retrievable
        int bookingId = bookingClient.createBooking(booking).path("bookingid");

        await()
                .atMost(10, TimeUnit.SECONDS)      // give up after 10 seconds total
                .pollInterval(1, TimeUnit.SECONDS) // check once every second
                .until(() -> bookingClient.getBooking(bookingId).statusCode() == 200);

        // by the time we reach here, we KNOW the booking is retrievable — no guessing
        bookingClient.getBooking(bookingId)
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Dharani"));
    }
}