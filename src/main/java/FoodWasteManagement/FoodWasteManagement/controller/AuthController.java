package FoodWasteManagement.FoodWasteManagement.controller;

import FoodWasteManagement.FoodWasteManagement.dto.AuthDTO;
import FoodWasteManagement.FoodWasteManagement.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // POST /api/auth/register
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthDTO.AuthResponse register(@RequestBody AuthDTO.RegisterRequest request) {
        return authService.register(request);
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public AuthDTO.AuthResponse login(@RequestBody AuthDTO.LoginRequest request) {
        return authService.login(request);
    }

    // POST /api/auth/google
    @PostMapping("/google")
    public AuthDTO.AuthResponse googleLogin(@RequestBody java.util.Map<String, String> body) {
        return authService.googleLogin(body.get("email"), body.get("fullName"));
    }
}
