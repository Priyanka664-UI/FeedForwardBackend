package FoodWasteManagement.FoodWasteManagement.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id", nullable = false)
    private User donor;

    @Column(nullable = false)
    private String foodName;

    @Enumerated(EnumType.STRING)
    private FoodType foodType;

    private double quantity;

    @Enumerated(EnumType.STRING)
    private QuantityUnit unit;

    private int servings;
    private String specialNotes;
    private String pickupAddress;
    private double latitude;
    private double longitude;
    private LocalDate pickupDate;
    private LocalTime pickupTime;
    private LocalTime expiryTime;
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private DonationStatus status = DonationStatus.POSTED;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum FoodType { VEG, NONVEG }
    public enum QuantityUnit { KG, SERVINGS }
    public enum DonationStatus { POSTED, ACCEPTED, COLLECTED, EXPIRED, CANCELLED }

    // Getters and Setters
    public Long getId() { return id; }
    public User getDonor() { return donor; }
    public void setDonor(User donor) { this.donor = donor; }
    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }
    public FoodType getFoodType() { return foodType; }
    public void setFoodType(FoodType foodType) { this.foodType = foodType; }
    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
    public QuantityUnit getUnit() { return unit; }
    public void setUnit(QuantityUnit unit) { this.unit = unit; }
    public int getServings() { return servings; }
    public void setServings(int servings) { this.servings = servings; }
    public String getSpecialNotes() { return specialNotes; }
    public void setSpecialNotes(String specialNotes) { this.specialNotes = specialNotes; }
    public String getPickupAddress() { return pickupAddress; }
    public void setPickupAddress(String pickupAddress) { this.pickupAddress = pickupAddress; }
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public LocalDate getPickupDate() { return pickupDate; }
    public void setPickupDate(LocalDate pickupDate) { this.pickupDate = pickupDate; }
    public LocalTime getPickupTime() { return pickupTime; }
    public void setPickupTime(LocalTime pickupTime) { this.pickupTime = pickupTime; }
    public LocalTime getExpiryTime() { return expiryTime; }
    public void setExpiryTime(LocalTime expiryTime) { this.expiryTime = expiryTime; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public DonationStatus getStatus() { return status; }
    public void setStatus(DonationStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
