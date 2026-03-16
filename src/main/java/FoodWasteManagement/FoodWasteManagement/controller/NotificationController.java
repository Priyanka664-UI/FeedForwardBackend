package FoodWasteManagement.FoodWasteManagement.controller;

import FoodWasteManagement.FoodWasteManagement.model.Notification;
import FoodWasteManagement.FoodWasteManagement.service.NotificationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // GET /api/notifications?userId=1
    @GetMapping
    public List<Notification> getNotifications(@RequestParam Long userId) {
        return notificationService.getNotifications(userId);
    }

    // PATCH /api/notifications/mark-all-read?userId=1
    @PatchMapping("/mark-all-read")
    public void markAllRead(@RequestParam Long userId) {
        notificationService.markAllRead(userId);
    }

    // DELETE /api/notifications/{id}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        notificationService.delete(id);
    }

    // POST /api/notifications/support
    @PostMapping("/support")
    public void submitSupport(@RequestBody Map<String, Object> body) {
        Long userId = Long.valueOf(body.get("userId").toString());
        String subject = (String) body.get("subject");
        String message = (String) body.get("message");
        String type = (String) body.get("type");
        notificationService.createSupportNotification(userId, subject, message, type);
    }
}
