package dev.acacia.job_trucker.offer;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.acacia.job_trucker.user.UserPrincipal;

@RestController
@RequestMapping("/api/offer")
public class OfferController {
 
    private OfferService offerService;
    public OfferController (OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerOffer(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody OfferDTO offerDTO) {
        if (userPrincipal == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("ERROR 400:", "User is not authenticated"));
        }
        Offer registeredOffer = offerService.registerOffer(userPrincipal.getUser().getId(), offerDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("MESSAGE", "Offer registered successfully for user: " + userPrincipal.getUser().getId());
        response.put("offer", registeredOffer);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}") 
    public ResponseEntity<String> deleteOffer(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        offerService.deleteOffer(id, userPrincipal);
        return ResponseEntity.ok("\n    Offer number " + id + " deleted successfully");
    }

    @GetMapping("/get/{id}") 
    public ResponseEntity<Offer> getOffer(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Offer offer = offerService.getOffer(id, userPrincipal);
        return ResponseEntity.ok(offer);
    }

    @GetMapping("/getAll") 
    public ResponseEntity<List<Offer>> getAllOffers(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<Offer> offers = offerService.getAllOffers(userPrincipal);
        return ResponseEntity.ok(offers);
    }
}