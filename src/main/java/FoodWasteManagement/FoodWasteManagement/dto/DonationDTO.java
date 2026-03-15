package FoodWasteManagement.FoodWasteManagement.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DonationDTO {

    public static class Request {
        public String foodName;
        public String foodType;   // VEG, NONVEG
        public double quantity;
        public String unit;       // KG, SERVINGS
        public int servings;
        public String specialNotes;
        public String pickupAddress;
        public double latitude;
        public double longitude;
        public LocalDate pickupDate;
        public LocalTime pickupTime;
        public LocalTime expiryTime;
        public String imageUrl;
    }

    public static class Response {
        public Long id;
        public Long donorId;
        public String donorName;
        public String donorOrganization;
        public String foodName;
        public String foodType;
        public double quantity;
        public String unit;
        public int servings;
        public String specialNotes;
        public String pickupAddress;
        public double latitude;
        public double longitude;
        public LocalDate pickupDate;
        public LocalTime pickupTime;
        public LocalTime expiryTime;
        public String imageUrl;
        public String status;
        public LocalDateTime createdAt;
    }
}
