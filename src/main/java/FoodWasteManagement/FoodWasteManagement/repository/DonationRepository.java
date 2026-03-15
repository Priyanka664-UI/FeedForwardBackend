package FoodWasteManagement.FoodWasteManagement.repository;

import FoodWasteManagement.FoodWasteManagement.model.Donation;
import FoodWasteManagement.FoodWasteManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Map;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<Donation> findByDonor(User donor);

    List<Donation> findByStatus(Donation.DonationStatus status);

    List<Donation> findByFoodType(Donation.FoodType foodType);

    @Query("SELECT d FROM Donation d WHERE d.status = 'POSTED' AND d.foodType = :type")
    List<Donation> findAvailableByFoodType(@Param("type") Donation.FoodType type);

    @Query("SELECT d FROM Donation d WHERE d.status = 'POSTED' ORDER BY d.createdAt DESC")
    List<Donation> findAllAvailable();

    long countByStatus(Donation.DonationStatus status);

    @Query(value = "SELECT EXTRACT(MONTH FROM created_at) AS month, COUNT(*) AS count FROM donations WHERE created_at >= NOW() - INTERVAL '6 months' GROUP BY EXTRACT(MONTH FROM created_at) ORDER BY month", nativeQuery = true)
    List<Map<String, Object>> countMonthlyDonations();

    @Query("SELECT d FROM Donation d WHERE d.status = 'POSTED' AND " +
           "(6371 * acos(cos(radians(:lat)) * cos(radians(d.latitude)) * " +
           "cos(radians(d.longitude) - radians(:lng)) + sin(radians(:lat)) * sin(radians(d.latitude)))) < :radiusKm")
    List<Donation> findNearby(@Param("lat") double lat, @Param("lng") double lng, @Param("radiusKm") double radiusKm);
}
