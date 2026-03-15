package FoodWasteManagement.FoodWasteManagement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String organization;
    private String city;

    private int mealsSaved = 0;
    private int totalDonations = 0;
    private int totalClaims = 0;
    private double rating = 0.0;

    @Column(nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'Active'")
    private String status = "Active";

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Role { DONOR, NGO, VOLUNTEER }

    // Getters and Setters
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public String getOrganization() { return organization; }
    public void setOrganization(String organization) { this.organization = organization; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public int getMealsSaved() { return mealsSaved; }
    public void setMealsSaved(int mealsSaved) { this.mealsSaved = mealsSaved; }
    public int getTotalDonations() { return totalDonations; }
    public void setTotalDonations(int totalDonations) { this.totalDonations = totalDonations; }
    public int getTotalClaims() { return totalClaims; }
    public void setTotalClaims(int totalClaims) { this.totalClaims = totalClaims; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
