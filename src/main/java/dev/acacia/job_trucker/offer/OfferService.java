package dev.acacia.job_trucker.offer;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dev.acacia.job_trucker.exceptions.GlobalExceptionHandler;
import dev.acacia.job_trucker.exceptions.GlobalExceptionHandler.UserNotFoundException;
import dev.acacia.job_trucker.user.User;
import dev.acacia.job_trucker.user.UserRepository;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    
    @Autowired
    private UserRepository userRepository;

    public OfferService(OfferRepository offerRepository, UserRepository userRepository) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
    }

    public Offer registerOffer(Long userId, OfferDTO offerDTO) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Offer offer = new Offer();
                offer.setOffCompanyName(offerDTO.getOffCompanyName());
                offer.setOffContactName(offerDTO.getOffContactName());
                offer.setOffContactPhone(offerDTO.getOffContactPhone());
                offer.setOffContactEmail(offerDTO.getOffContactEmail());
                offer.setOffJobAddress(offerDTO.getOffJobAddress());
                offer.setOffLink(offerDTO.getOffLink());
                offer.setOffSummary(offerDTO.getOffSummary());
                offer.setOffRequirements(offerDTO.getOffRequirements());
                offer.setOffQuestions(offerDTO.getOffQuestions());
                offer.setOffStepComments(offerDTO.getOffStepComments());
                offer.setOffDate(offerDTO.getOffDate());
                offer.setOffStepDate(offerDTO.getOffStepDate());
                offer.setOffFavourite(offerDTO.isOffFavourite());
                offer.setOffStep(offerDTO.getOffStep());
                offer.setUser(user); // Guarda el objeto User completo

                // User userWithIdOnly = new User();
                // userWithIdOnly.setId(userId);
                // offer.setUser(userWithIdOnly);
                
            
            offerRepository.save(offer);
            return offer;
        } else {
            throw new UserNotFoundException();
        }
    }

    public void deleteOffer(Long id) {
        if (id == null || !offerRepository.existsById(id)) {
            throw new GlobalExceptionHandler.UserNotFoundException();
        }
        offerRepository.deleteById(id);
    }


}