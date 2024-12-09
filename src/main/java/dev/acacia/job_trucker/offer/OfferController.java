package dev.acacia.job_trucker.offer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.acacia.job_trucker.user.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/offers")
public class OfferController {
 
    private OfferService offerService;

    public OfferController (OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerOffer(@RequestBody OfferDTO offerDTO) {
        offerService.registerOffer(offerDTO); // Pasamos el DTO al servicio
        return ResponseEntity.ok("\n    User registered successfully");
    }


}