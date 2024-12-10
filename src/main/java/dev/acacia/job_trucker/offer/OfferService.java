package dev.acacia.job_trucker.offer;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dev.acacia.job_trucker.user.User;
import dev.acacia.job_trucker.user.UserRepository;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    public OfferService(OfferRepository offerRepository, UserRepository userRepository) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
    }

    public Offer registerOffer(OfferDTO offerDTO, Principal principal) {

        // Long userId = offerDTO.getUserId(); // así, se le pasa el userId por postman, cuando funcione hay que hacer que lo coja del user logueado

        //userEmail = principal.getName(); // Obtenemos el nombre del usuario autenticado

        if (principal == null) {
            throw new RuntimeException("bb Usuario no autenticado");  // aquí no entra!!!!!!!!!!!
        }

        User user = userRepository.findByUserEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("bb User not found with Email: " + principal.getName()));

        Long userId = user.getId(); // Ahora tienes el ID del usuario

        Offer offer = new Offer(
            offerDTO.getOffCompanyName(),
            offerDTO.getOffContactName(),
            offerDTO.getOffContactPhone(),
            offerDTO.getOffContactEmail(),
            offerDTO.getOffJobAddress(),
            offerDTO.getOffLink(),
            offerDTO.getOffSummary(),
            offerDTO.getOffRequirements(),
            offerDTO.getOffQuestions(),
            offerDTO.getOffStepComments(),
            offerDTO.getOffDate(),
            offerDTO.getOffStepDate(),
            offerDTO.isOffFavourite(),
            offerDTO.getOffStep(),
            user
        );

    offerRepository.save(offer);
    return offer;
    }


}