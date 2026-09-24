package tests;

import Clients.BookingClient;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Pojo.Booking;
import Pojo.BookingDates;

public class BookingDataDrivenTest {

    BookingClient bookingClient = new BookingClient();

    @DataProvider(name = "bookingData")
    public Object[][] getBookingData() {
        return new Object[][] {
                { "Alice", "Smith", 120 },
                { "Bob", "Jones", 200 },
                { "Carla", "Diaz", 90 }
        };
    }

    @Test(dataProvider = "bookingData",groups=("regression"))
    public void createBooking_withDifferentData(String firstname, String lastname, int price) {
        Booking booking = new Booking(
                firstname, lastname, price, true,
                new BookingDates("2026-10-01", "2026-10-05"),
                "Breakfast"
        );

        Response response = bookingClient.createBooking(booking);
        response.then().statusCode(200)
                .body("booking.firstname", org.hamcrest.Matchers.equalTo(firstname));

        System.out.println("Created: " + firstname + " " + lastname + " - $" + price);
    }
}