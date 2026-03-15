package FoodWasteManagement.FoodWasteManagement.service;

import FoodWasteManagement.FoodWasteManagement.dto.ClaimDTO;
import FoodWasteManagement.FoodWasteManagement.model.Claim;
import FoodWasteManagement.FoodWasteManagement.model.Donation;
import FoodWasteManagement.FoodWasteManagement.model.User;
import FoodWasteManagement.FoodWasteManagement.repository.ClaimRepository;
import FoodWasteManagement.FoodWasteManagement.repository.DonationRepository;
import FoodWasteManagement.FoodWasteManagement.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final DonationRepository donationRepository;
    private final UserRepository userRepository;

    public ClaimService(ClaimRepository claimRepository, DonationRepository donationRepository, UserRepository userRepository) {
        this.claimRepository = claimRepository;
        this.donationRepository = donationRepository;
        this.userRepository = userRepository;
    }

    public ClaimDTO.Response claimDonation(ClaimDTO.Request req) {
        if (claimRepository.existsByDonationIdAndClaimerId(req.donationId, req.claimerId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Already claimed this donation");
        }
        Donation donation = donationRepository.findById(req.donationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Donation not found"));
        if (donation.getStatus() != Donation.DonationStatus.POSTED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Donation is not available");
        }
        User claimer = userRepository.findById(req.claimerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        donation.setStatus(Donation.DonationStatus.ACCEPTED);
        donationRepository.save(donation);

        Claim claim = new Claim();
        claim.setDonation(donation);
        claim.setClaimer(claimer);
        claim.setReferenceNumber("FF-" + System.currentTimeMillis());
        Claim saved = claimRepository.save(claim);
        return toResponse(saved);
    }

    public List<ClaimDTO.Response> getMyClaims(Long claimerId) {
        User claimer = userRepository.findById(claimerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return claimRepository.findByClaimer(claimer)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public ClaimDTO.Response updateStatus(Long claimId, String status) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Claim not found"));
        Claim.ClaimStatus newStatus = Claim.ClaimStatus.valueOf(status.toUpperCase());
        claim.setStatus(newStatus);
        if (newStatus == Claim.ClaimStatus.COLLECTED) {
            claim.setCollectedAt(LocalDateTime.now());
            claim.getDonation().setStatus(Donation.DonationStatus.COLLECTED);
            donationRepository.save(claim.getDonation());
        }
        return toResponse(claimRepository.save(claim));
    }

    private ClaimDTO.Response toResponse(Claim c) {
        ClaimDTO.Response r = new ClaimDTO.Response();
        r.id = c.getId();
        r.referenceNumber = c.getReferenceNumber();
        r.status = c.getStatus().name();
        r.claimedAt = c.getClaimedAt();
        Donation d = c.getDonation();
        r.foodName = d.getFoodName();
        r.servings = d.getServings();
        r.pickupAddress = d.getPickupAddress();
        r.pickupTime = d.getPickupTime() != null ? d.getPickupTime().toString() : null;
        r.donorName = d.getDonor().getFullName();
        r.donorOrganization = d.getDonor().getOrganization();
        r.donorPhone = d.getDonor().getPhone();
        return r;
    }
}
