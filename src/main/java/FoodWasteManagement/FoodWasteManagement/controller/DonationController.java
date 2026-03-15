package FoodWasteManagement.FoodWasteManagement.controller;

import FoodWasteManagement.FoodWasteManagement.dto.DonationDTO;
import FoodWasteManagement.FoodWasteManagement.service.DonationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/donations")
@CrossOrigin(origins = "*")
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    // POST /api/donations?donorId=1
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DonationDTO.Response create(@RequestParam Long donorId,
                                       @RequestBody DonationDTO.Request request) {
        return donationService.create(donorId, request);
    }

    // GET /api/donations?foodType=VEG
    @GetMapping
    public List<DonationDTO.Response> getAvailable(@RequestParam(required = false) String foodType) {
        return donationService.getAvailable(foodType);
    }

    // GET /api/donations/nearby?lat=18.52&lng=73.86&radius=5
    @GetMapping("/nearby")
    public List<DonationDTO.Response> getNearby(@RequestParam double lat,
                                                 @RequestParam double lng,
                                                 @RequestParam(defaultValue = "5") double radius) {
        return donationService.getNearby(lat, lng, radius);
    }

    // GET /api/donations/{id}
    @GetMapping("/{id}")
    public DonationDTO.Response getById(@PathVariable Long id) {
        return donationService.getById(id);
    }

    // GET /api/donations/my?donorId=1
    @GetMapping("/my")
    public List<DonationDTO.Response> getMyDonations(@RequestParam Long donorId) {
        return donationService.getMyDonations(donorId);
    }

    // PATCH /api/donations/{id}/status
    @PatchMapping("/{id}/status")
    public DonationDTO.Response updateStatus(@PathVariable Long id,
                                              @RequestBody Map<String, String> body) {
        return donationService.updateStatus(id, body.get("status"));
    }
}
