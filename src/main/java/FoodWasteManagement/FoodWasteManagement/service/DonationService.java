package FoodWasteManagement.FoodWasteManagement.service;

import FoodWasteManagement.FoodWasteManagement.dto.DonationDTO;
import FoodWasteManagement.FoodWasteManagement.model.Donation;
import FoodWasteManagement.FoodWasteManagement.model.User;
import FoodWasteManagement.FoodWasteManagement.repository.DonationRepository;
import FoodWasteManagement.FoodWasteManagement.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final UserRepository userRepository;

    public DonationService(DonationRepository donationRepository, UserRepository userRepository) {
        this.donationRepository = donationRepository;
        this.userRepository = userRepository;
    }

    public DonationDTO.Response create(Long donorId, DonationDTO.Request req) {
        User donor = getUser(donorId);
        Donation d = new Donation();
        d.setDonor(donor);
        d.setFoodName(req.foodName);
        d.setFoodType(Donation.FoodType.valueOf(req.foodType.toUpperCase()));
        d.setQuantity(req.quantity);
        d.setUnit(Donation.QuantityUnit.valueOf(req.unit.toUpperCase()));
        d.setServings(req.servings);
        d.setSpecialNotes(req.specialNotes);
        d.setPickupAddress(req.pickupAddress);
        d.setLatitude(req.latitude);
        d.setLongitude(req.longitude);
        d.setPickupDate(req.pickupDate);
        d.setPickupTime(req.pickupTime);
        d.setExpiryTime(req.expiryTime);
        d.setImageUrl(req.imageUrl);
        return toResponse(donationRepository.save(d));
    }

    public List<DonationDTO.Response> getAvailable(String foodType) {
        List<Donation> list;
        if (foodType != null && !foodType.isBlank()) {
            list = donationRepository.findAvailableByFoodType(Donation.FoodType.valueOf(foodType.toUpperCase()));
        } else {
            list = donationRepository.findAllAvailable();
        }
        return list.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<DonationDTO.Response> getNearby(double lat, double lng, double radiusKm) {
        return donationRepository.findNearby(lat, lng, radiusKm)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public DonationDTO.Response getById(Long id) {
        return toResponse(getDonation(id));
    }

    public List<DonationDTO.Response> getMyDonations(Long donorId) {
        User donor = getUser(donorId);
        return donationRepository.findByDonor(donor)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public DonationDTO.Response updateStatus(Long id, String status) {
        Donation d = getDonation(id);
        d.setStatus(Donation.DonationStatus.valueOf(status.toUpperCase()));
        return toResponse(donationRepository.save(d));
    }

    private Donation getDonation(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Donation not found"));
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public DonationDTO.Response toResponse(Donation d) {
        DonationDTO.Response r = new DonationDTO.Response();
        r.id = d.getId();
        r.donorId = d.getDonor().getId();
        r.donorName = d.getDonor().getFullName();
        r.donorOrganization = d.getDonor().getOrganization();
        r.foodName = d.getFoodName();
        r.foodType = d.getFoodType() != null ? d.getFoodType().name() : null;
        r.quantity = d.getQuantity();
        r.unit = d.getUnit() != null ? d.getUnit().name() : null;
        r.servings = d.getServings();
        r.specialNotes = d.getSpecialNotes();
        r.pickupAddress = d.getPickupAddress();
        r.latitude = d.getLatitude();
        r.longitude = d.getLongitude();
        r.pickupDate = d.getPickupDate();
        r.pickupTime = d.getPickupTime();
        r.expiryTime = d.getExpiryTime();
        r.imageUrl = d.getImageUrl();
        r.status = d.getStatus().name();
        r.createdAt = d.getCreatedAt();
        return r;
    }
}
