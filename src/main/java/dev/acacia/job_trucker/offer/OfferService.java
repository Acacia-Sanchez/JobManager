package dev.acacia.job_trucker.offer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OfferService {

    private OfferRepository offerRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(OfferService.class);
    
    public OfferService(OfferRepository offerRepository, PasswordEncoder passwordEncoder) {
        this.offerRepository = offerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Offer registerOffer(OfferDTO offerDTO) {
        // lo primero comprobar qué usuario está logado, para hacer
        // todo con ese usuario y no con otro

        // algo así como if (userService.login) entonces getUserIdByEmail()

            return null;

    }


}