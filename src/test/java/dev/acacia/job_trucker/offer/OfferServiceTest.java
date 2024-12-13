package dev.acacia.job_trucker.offer;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dev.acacia.job_trucker.user.User;
import dev.acacia.job_trucker.user.UserPrincipal;
import dev.acacia.job_trucker.user.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import dev.acacia.job_trucker.exceptions.GlobalExceptionHandler;
import dev.acacia.job_trucker.exceptions.GlobalExceptionHandler.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private OfferService offerService;

    private User user;
    private UserPrincipal userPrincipal;
    private OfferDTO offerDTO;
    private Offer offer;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1L);
        userPrincipal = new UserPrincipal(user);
        offerDTO = new OfferDTO();
        offer = new Offer();
        offer.setId(1L);
        offer.setUser(user);
    }

    @Test
    public void testRegisterOffer_UserNotFoundException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> offerService.registerOffer(user.getId(), offerDTO));
    }

    @Test
    public void testDeleteOffer_Success() {
        when(offerRepository.findById(anyLong())).thenReturn(Optional.of(offer));

        offerService.deleteOffer(offer.getId(), userPrincipal);

        verify(offerRepository).deleteById(offer.getId());
    }

    @Test
    public void testDeleteOffer_OfferNotFoundException() {
        when(offerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(GlobalExceptionHandler.OfferNotFoundException.class, () -> offerService.deleteOffer(offer.getId(), userPrincipal));
    }

    @Test
    public void testGetOffer_Success() {
        when(offerRepository.findById(anyLong())).thenReturn(Optional.of(offer));

        Offer retrievedOffer = offerService.getOffer(offer.getId(), userPrincipal);

        assertNotNull(retrievedOffer);
        assertEquals(offer, retrievedOffer);
    }

    @Test
    public void testGetOffer_OfferNotFoundException() {
        when(offerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(GlobalExceptionHandler.OfferNotFoundException.class, () -> offerService.getOffer(offer.getId(), userPrincipal));
    }

    @Test
    public void testGetAllOffers_Success() {
        List<Offer> offers = new ArrayList<>();
        offers.add(offer);
        when(offerRepository.findByUser(anyLong())).thenReturn(offers);

        List<Offer> retrievedOffers = offerService.getAllOffers(userPrincipal);

        assertNotNull(retrievedOffers);
        assertEquals(offers, retrievedOffers);
    }

    @Test
    public void testGetAllOffers_OfferNotFoundException() {
        when(offerRepository.findByUser(anyLong())).thenReturn(new ArrayList<>());

        assertThrows(GlobalExceptionHandler.OfferNotFoundException.class, () -> offerService.getAllOffers(userPrincipal));
    }
}
