package dev.acacia.job_trucker.offer;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequestMapping("/api/offer")
public class OfferController {
 
    private OfferService offerService;

    public OfferController (OfferService offerService) {
        this.offerService = offerService;
    }

    // EL MANEJO DE ERRORES LO HAGO DESDE EL SERVICE, QUE A SU VEZ LLAMA AL GLOBAL
    // EXCEPTION HANDLER ///

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerOffer(@RequestBody OfferDTO offerDTO, Principal principal) {

    /* if (principal != null) {
        String email = principal.getName();
    } else {
        System.out.println("xana Principal is null, user not authenticated.");
    } */

        //Offer registeredOffer = offerService.registerOffer(offerDTO, principal); // Pasamos el DTO al servicio
        Offer registeredOffer = offerService.registerOffer(offerDTO); // Pasamos el DTO al servicio
        Map<String, Object> response = new HashMap<>();
        response.put("MESSAGE", "Offer registered successfully for user: " + offerDTO.getUserId());
        response.put("offer", registeredOffer);
        return ResponseEntity.ok(response);
    }


}