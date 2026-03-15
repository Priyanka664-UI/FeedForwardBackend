package FoodWasteManagement.FoodWasteManagement.repository;

import FoodWasteManagement.FoodWasteManagement.model.Claim;
import FoodWasteManagement.FoodWasteManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
    List<Claim> findByClaimer(User claimer);
    List<Claim> findByClaimerAndStatus(User claimer, Claim.ClaimStatus status);
    Optional<Claim> findByReferenceNumber(String referenceNumber);
    boolean existsByDonationIdAndClaimerId(Long donationId, Long claimerId);
}
