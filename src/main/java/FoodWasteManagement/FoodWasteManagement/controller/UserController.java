package FoodWasteManagement.FoodWasteManagement.controller;

import FoodWasteManagement.FoodWasteManagement.model.User;
import FoodWasteManagement.FoodWasteManagement.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET /api/users/{id}
    @GetMapping("/{id}")
    public User getProfile(@PathVariable Long id) {
        return userService.getProfile(id);
    }

    // PATCH /api/users/{id}
    @PatchMapping("/{id}")
    public User updateProfile(@PathVariable Long id, @RequestBody Map<String, String> updates) {
        return userService.updateProfile(id, updates);
    }

    // PATCH /api/users/{id}/change-password
    @PatchMapping("/{id}/change-password")
    public void changePassword(@PathVariable Long id, @RequestBody Map<String, String> body) {
        userService.changePassword(id, body.get("currentPassword"), body.get("newPassword"));
    }

    // GET /api/users/{id}/impact
    @GetMapping("/{id}/impact")
    public Map<String, Object> getImpactStats(@PathVariable Long id) {
        return userService.getImpactStats(id);
    }
}
