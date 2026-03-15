package FoodWasteManagement.FoodWasteManagement.controller;

import FoodWasteManagement.FoodWasteManagement.dto.ClaimDTO;
import FoodWasteManagement.FoodWasteManagement.service.ClaimService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/claims")
@CrossOrigin(origins = "*")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    // POST /api/claims
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClaimDTO.Response claimDonation(@RequestBody ClaimDTO.Request request) {
        return claimService.claimDonation(request);
    }

    // GET /api/claims/my?claimerId=1
    @GetMapping("/my")
    public List<ClaimDTO.Response> getMyClaims(@RequestParam Long claimerId) {
        return claimService.getMyClaims(claimerId);
    }

    // PATCH /api/claims/{id}/status
    @PatchMapping("/{id}/status")
    public ClaimDTO.Response updateStatus(@PathVariable Long id,
                                           @RequestBody Map<String, String> body) {
        return claimService.updateStatus(id, body.get("status"));
    }
}
