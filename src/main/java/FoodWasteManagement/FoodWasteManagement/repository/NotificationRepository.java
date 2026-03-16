package FoodWasteManagement.FoodWasteManagement.repository;

import FoodWasteManagement.FoodWasteManagement.model.Notification;
import FoodWasteManagement.FoodWasteManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserOrderByCreatedAtDesc(User user);

    @Modifying
    @Query("UPDATE Notification n SET n.unread = false WHERE n.user = :user")
    void markAllReadByUser(@Param("user") User user);

    @Query("SELECT n FROM Notification n WHERE n.type = FoodWasteManagement.FoodWasteManagement.model.Notification.NotificationType.SUPPORT ORDER BY n.createdAt DESC")
    List<Notification> findAllSupportTickets();
}
