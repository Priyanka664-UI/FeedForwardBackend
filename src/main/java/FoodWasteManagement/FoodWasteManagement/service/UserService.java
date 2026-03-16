package FoodWasteManagement.FoodWasteManagement.service;

import FoodWasteManagement.FoodWasteManagement.model.User;
import FoodWasteManagement.FoodWasteManagement.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getProfile(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public User updateProfile(Long userId, Map<String, String> updates) {
        User user = getProfile(userId);
        if (updates.containsKey("fullName")) user.setFullName(updates.get("fullName"));
        if (updates.containsKey("phone")) user.setPhone(updates.get("phone"));
        if (updates.containsKey("organization")) user.setOrganization(updates.get("organization"));
        if (updates.containsKey("city")) user.setCity(updates.get("city"));
        return userRepository.save(user);
    }

    public void changePassword(Long userId, String currentPassword, String newPassword) {
        User user = getProfile(userId);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(currentPassword, user.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password is incorrect");
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }

    public Map<String, Object> getImpactStats(Long userId) {
        User user = getProfile(userId);
        return Map.of(
                "mealsSaved", user.getMealsSaved(),
                "totalDonations", user.getTotalDonations(),
                "totalClaims", user.getTotalClaims(),
                "rating", user.getRating()
        );
    }
}
