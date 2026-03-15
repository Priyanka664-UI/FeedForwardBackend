package FoodWasteManagement.FoodWasteManagement.repository;

import FoodWasteManagement.FoodWasteManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    long countByRole(User.Role role);

    @Query("SELECT COALESCE(SUM(u.mealsSaved), 0) FROM User u")
    long sumMealsSaved();

    @Query("SELECT COUNT(DISTINCT u.city) FROM User u WHERE u.city IS NOT NULL")
    long countDistinctCities();

    @Query(value = "SELECT full_name AS name, total_donations AS count, meals_saved AS meals FROM users WHERE role = 'DONOR' ORDER BY total_donations DESC LIMIT 10", nativeQuery = true)
    List<Map<String, Object>> findTopDonors();

    @Query(value = "SELECT full_name AS name, total_claims AS pickups FROM users WHERE role = 'NGO' ORDER BY total_claims DESC LIMIT 10", nativeQuery = true)
    List<Map<String, Object>> findNgoActivity();
}
