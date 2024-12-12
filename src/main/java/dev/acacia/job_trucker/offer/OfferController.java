package dev.acacia.job_trucker.offer;

import java.security.Principal;
import java.util.Collections;
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

import dev.acacia.job_trucker.user.User;
import dev.acacia.job_trucker.user.UserDTO;
import dev.acacia.job_trucker.user.UserPrincipal;
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
    //public ResponseEntity<Map<String, Object>> registerOffer(@RequestBody OfferDTO offerDTO, Principal principal) {
    public ResponseEntity<Map<String, Object>> registerOffer(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody OfferDTO offerDTO) {

        if (userPrincipal == null) {
            // Handle the case where the user is null
            // You can return an error response or throw an exception
            return ResponseEntity.badRequest().body(Collections.singletonMap("ERROR 400:", "User is not authenticated"));
        }
        
        //Offer registeredOffer = offerService.registerOffer(offerDTO); // Pasamos el DTO al servicio
        Offer registeredOffer = offerService.registerOffer(userPrincipal.getUser().getId(), offerDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("MESSAGE", "Offer registered successfully for user: " + offerDTO.getUserId());
        response.put("offer", registeredOffer);
        return ResponseEntity.ok(response);
    }


}