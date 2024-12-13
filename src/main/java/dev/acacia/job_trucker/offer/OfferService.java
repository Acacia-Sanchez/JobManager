package dev.acacia.job_trucker.offer;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.acacia.job_trucker.exceptions.GlobalExceptionHandler;
import dev.acacia.job_trucker.exceptions.GlobalExceptionHandler.UserNotFoundException;
import dev.acacia.job_trucker.user.User;
import dev.acacia.job_trucker.user.UserPrincipal;
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
                offer.setUser(user);

            offerRepository.save(offer);
            return offer;
        } else {
            throw new UserNotFoundException();
        }
    }

    public void deleteOffer(Long id, UserPrincipal userPrincipal) {
        if (id == null) {
            throw new GlobalExceptionHandler.UserNotFoundException();
        }
        Offer offer = offerRepository.findById(id).orElseThrow(() -> new GlobalExceptionHandler.OfferNotFoundException());
        if (offer.getUser().getId() != userPrincipal.getUser().getId()) {
            throw new GlobalExceptionHandler.OfferNotFoundException();
        }
        offerRepository.deleteById(id);
    }

    public Offer getOffer(Long id, UserPrincipal userPrincipal) {
        if (id == null)  {
            throw new GlobalExceptionHandler.OfferNotFoundException();
        }
        Offer offer = offerRepository.findById(id).orElseThrow(() -> new GlobalExceptionHandler.OfferNotFoundException());
        if (offer.getUser().getId() != userPrincipal.getUser().getId()) {
            throw new GlobalExceptionHandler.OfferNotFoundException();
        }
        return offer;
    }

    public List<Offer> getAllOffers(UserPrincipal userPrincipal) {
        List<Offer> offers = offerRepository.findByUser(userPrincipal.getUser().getId());
        if (offers.isEmpty()) {
            if (userPrincipal.getUser() == null || userPrincipal.getUser().getId() == 0) {
                throw new GlobalExceptionHandler.UserNotFoundException("El usuario no existe");
            } else {
                throw new GlobalExceptionHandler.OfferNotFoundException("El usuario logado no tiene ofertas asignadas");
            }
        }
        return offers;
    }
}