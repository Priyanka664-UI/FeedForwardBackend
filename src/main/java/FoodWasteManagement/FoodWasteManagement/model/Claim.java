package FoodWasteManagement.FoodWasteManagement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "claims")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donation_id", nullable = false)
    private Donation donation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "claimer_id", nullable = false)
    private User claimer;

    @Enumerated(EnumType.STRING)
    private ClaimStatus status = ClaimStatus.ACCEPTED;

    private String referenceNumber;

    @Column(updatable = false)
    private LocalDateTime claimedAt = LocalDateTime.now();

    private LocalDateTime collectedAt;

    public enum ClaimStatus { ACCEPTED, PICKUP_SCHEDULED, COLLECTED, DELIVERED, CANCELLED }

    // Getters and Setters
    public Long getId() { return id; }
    public Donation getDonation() { return donation; }
    public void setDonation(Donation donation) { this.donation = donation; }
    public User getClaimer() { return claimer; }
    public void setClaimer(User claimer) { this.claimer = claimer; }
    public ClaimStatus getStatus() { return status; }
    public void setStatus(ClaimStatus status) { this.status = status; }
    public String getReferenceNumber() { return referenceNumber; }
    public void setReferenceNumber(String referenceNumber) { this.referenceNumber = referenceNumber; }
    public LocalDateTime getClaimedAt() { return claimedAt; }
    public LocalDateTime getCollectedAt() { return collectedAt; }
    public void setCollectedAt(LocalDateTime collectedAt) { this.collectedAt = collectedAt; }
}
