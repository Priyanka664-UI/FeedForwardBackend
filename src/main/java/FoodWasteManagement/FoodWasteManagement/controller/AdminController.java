package FoodWasteManagement.FoodWasteManagement.controller;

import FoodWasteManagement.FoodWasteManagement.dto.DonationDTO;
import FoodWasteManagement.FoodWasteManagement.model.Donation;
import FoodWasteManagement.FoodWasteManagement.model.User;
import FoodWasteManagement.FoodWasteManagement.repository.ClaimRepository;
import FoodWasteManagement.FoodWasteManagement.repository.DonationRepository;
import FoodWasteManagement.FoodWasteManagement.repository.UserRepository;
import FoodWasteManagement.FoodWasteManagement.service.DonationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final UserRepository userRepository;
    private final DonationRepository donationRepository;
    private final ClaimRepository claimRepository;
    private final DonationService donationService;

    public AdminController(UserRepository userRepository, DonationRepository donationRepository,
                           ClaimRepository claimRepository, DonationService donationService) {
        this.userRepository = userRepository;
        this.donationRepository = donationRepository;
        this.claimRepository = claimRepository;
        this.donationService = donationService;
    }

    // GET /api/admin/dashboard
    @GetMapping("/dashboard")
    public Map<String, Object> getDashboardStats() {
        long totalUsers = userRepository.count();
        long totalDonors = userRepository.countByRole(User.Role.DONOR);
        long totalNgos = userRepository.countByRole(User.Role.NGO);
        long totalVolunteers = userRepository.countByRole(User.Role.VOLUNTEER);
        long totalDonations = donationRepository.count();
        long delivered = donationRepository.countByStatus(Donation.DonationStatus.COLLECTED);
        long mealsSaved = userRepository.sumMealsSaved();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", totalUsers);
        stats.put("totalDonors", totalDonors);
        stats.put("totalNgos", totalNgos);
        stats.put("totalVolunteers", totalVolunteers);
        stats.put("totalDonations", totalDonations);
        stats.put("completedDeliveries", delivered);
        stats.put("mealsSaved", mealsSaved);
        return stats;
    }

    // GET /api/admin/users
    @GetMapping("/users")
    public List<Map<String, Object>> getAllUsers() {
        return userRepository.findAll().stream().map(u -> {
            Map<String, Object> m = new HashMap<>();
            m.put("id", u.getId());
            m.put("fullName", u.getFullName());
            m.put("email", u.getEmail());
            m.put("phone", u.getPhone());
            m.put("role", u.getRole());
            m.put("organization", u.getOrganization());
            m.put("city", u.getCity());
            m.put("status", u.getStatus());
            m.put("createdAt", u.getCreatedAt());
            return m;
        }).collect(Collectors.toList());
    }

    // PATCH /api/admin/users/{id}/status
    @PatchMapping("/users/{id}/status")
    public Map<String, Object> updateUserStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setStatus(body.get("status"));
        userRepository.save(user);
        return Map.of("id", user.getId(), "status", user.getStatus());
    }

    // DELETE /api/admin/users/{id}
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        userRepository.deleteById(id);
    }

    // GET /api/admin/donations
    @GetMapping("/donations")
    public List<DonationDTO.Response> getAllDonations() {
        return donationRepository.findAll().stream()
                .map(donationService::toResponse)
                .collect(Collectors.toList());
    }

    // DELETE /api/admin/donations/{id}
    @DeleteMapping("/donations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDonation(@PathVariable Long id) {
        if (!donationRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Donation not found");
        donationRepository.deleteById(id);
    }

    // GET /api/admin/analytics
    @GetMapping("/analytics")
    public Map<String, Object> getAnalytics() {
        Map<String, Object> data = new HashMap<>();

        // Donations by status
        Map<String, Long> byStatus = new HashMap<>();
        for (Donation.DonationStatus s : Donation.DonationStatus.values()) {
            byStatus.put(s.name(), donationRepository.countByStatus(s));
        }
        data.put("donationsByStatus", byStatus);

        // Top donors
        data.put("topDonors", userRepository.findTopDonors());

        // NGO activity
        data.put("ngoActivity", userRepository.findNgoActivity());

        // Monthly donations (last 6 months)
        data.put("monthlyDonations", donationRepository.countMonthlyDonations());

        // Impact
        data.put("totalMealsSaved", userRepository.sumMealsSaved());
        data.put("citiesCovered", userRepository.countDistinctCities());
        data.put("totalDonations", donationRepository.count());

        return data;
    }
}
