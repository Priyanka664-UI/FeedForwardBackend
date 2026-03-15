package FoodWasteManagement.FoodWasteManagement.service;

import FoodWasteManagement.FoodWasteManagement.config.JwtUtil;
import FoodWasteManagement.FoodWasteManagement.dto.AuthDTO;
import FoodWasteManagement.FoodWasteManagement.model.User;
import FoodWasteManagement.FoodWasteManagement.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public AuthDTO.AuthResponse register(AuthDTO.RegisterRequest req) {
        if (userRepository.existsByEmail(req.email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }
        User user = new User();
        user.setFullName(req.fullName);
        user.setEmail(req.email);
        user.setPhone(req.phone);
        user.setPassword(req.password);
        user.setRole(User.Role.valueOf(req.role.toUpperCase()));
        user.setOrganization(req.organization);
        user.setCity(req.city);
        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    public AuthDTO.AuthResponse login(AuthDTO.LoginRequest req) {
        User user = userRepository.findByEmail(req.email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
        if (!user.getPassword().equals(req.password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        return toResponse(user);
    }

    public AuthDTO.AuthResponse googleLogin(String email, String fullName) {
        return userRepository.findByEmail(email)
                .map(this::toResponse)
                .orElseGet(() -> {
                    User user = new User();
                    user.setFullName(fullName != null ? fullName : email);
                    user.setEmail(email);
                    user.setPassword("GOOGLE_AUTH");
                    user.setRole(User.Role.DONOR);
                    return toResponse(userRepository.save(user));
                });
    }

    private AuthDTO.AuthResponse toResponse(User user) {
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().name());
        return new AuthDTO.AuthResponse(
                user.getId(), user.getFullName(), user.getEmail(), user.getPhone(),
                user.getRole().name(), user.getOrganization(), user.getCity(),
                user.getMealsSaved(), user.getTotalDonations(), user.getTotalClaims(),
                user.getRating(), token
        );
    }
}
