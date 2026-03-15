package FoodWasteManagement.FoodWasteManagement.dto;

public class AuthDTO {

    public static class RegisterRequest {
        public String fullName;
        public String email;
        public String phone;
        public String password;
        public String role; // DONOR, NGO, VOLUNTEER
        public String organization;
        public String city;
    }

    public static class LoginRequest {
        public String email;
        public String password;
    }

    public static class AuthResponse {
        public Long userId;
        public String fullName;
        public String email;
        public String phone;
        public String role;
        public String organization;
        public String city;
        public int mealsSaved;
        public int totalDonations;
        public int totalClaims;
        public double rating;
        public String token;

        public AuthResponse(Long userId, String fullName, String email, String phone, String role,
                            String organization, String city, int mealsSaved, int totalDonations,
                            int totalClaims, double rating) {
            this.userId = userId;
            this.fullName = fullName;
            this.email = email;
            this.phone = phone;
            this.role = role;
            this.organization = organization;
            this.city = city;
            this.mealsSaved = mealsSaved;
            this.totalDonations = totalDonations;
            this.totalClaims = totalClaims;
            this.rating = rating;
            this.token = "token-" + userId;
        }
    }
}
