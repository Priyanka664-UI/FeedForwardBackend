package FoodWasteManagement.FoodWasteManagement.dto;

import java.time.LocalDateTime;

public class ClaimDTO {

    public static class Request {
        public Long donationId;
        public Long claimerId;
    }

    public static class Response {
        public Long id;
        public String referenceNumber;
        public String foodName;
        public String donorName;
        public String donorOrganization;
        public String pickupAddress;
        public String pickupTime;
        public String donorPhone;
        public String status;
        public int servings;
        public LocalDateTime claimedAt;
    }
}
