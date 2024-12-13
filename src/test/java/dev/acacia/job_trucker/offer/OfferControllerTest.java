package dev.acacia.job_trucker.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dev.acacia.job_trucker.exceptions.GlobalExceptionHandler;
import dev.acacia.job_trucker.user.User;
import dev.acacia.job_trucker.user.UserPrincipal;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class OfferControllerTest {

    @InjectMocks
    private OfferController offerController;

    @Mock
    private OfferService offerService;

    @Autowired
    private OfferRepository offerRepository;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterOffer_Success() {
        UserPrincipal userPrincipal = new UserPrincipal(new User());
        OfferDTO offerDTO = new OfferDTO();

        when(offerService.registerOffer(userPrincipal.getUser().getId(), offerDTO)).thenReturn(new Offer());

        ResponseEntity<Map<String, Object>> response = offerController.registerOffer(userPrincipal, offerDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Offer registered successfully for user: " + userPrincipal.getUser().getId(), response.getBody().get("MESSAGE"));
    }

    @Test
    public void testRegisterOffer_NullUserPrincipal() {
        when(offerService.registerOffer(anyLong(), any())).thenReturn(null);

        ResponseEntity<Map<String, Object>> response = offerController.registerOffer(null, new OfferDTO());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User is not authenticated", response.getBody().get("ERROR 400:"));
    }

    @Test
    public void testDeleteOffer_Success() {
        UserPrincipal userPrincipal = new UserPrincipal(new User());

        doNothing().when(offerService).deleteOffer(anyLong(), eq(userPrincipal));

        ResponseEntity<String> response = offerController.deleteOffer(1L, userPrincipal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("\n    Offer number 1 deleted successfully", response.getBody());
    }

    public Offer getOffer(Long id, UserPrincipal userPrincipal) {
        if (id == null)  {
            throw new GlobalExceptionHandler.OfferNotFoundException();
        }
        Offer offer = new Offer();
        offer.setOffCompanyName("Company Name");
        offer.setOffContactName("Contact Name");
        offer.setOffContactPhone("123-456-7890");
        offer.setOffContactEmail("contact@example.com");
        offer.setOffJobAddress("Job Address");
        offer.setOffLink("https://example.com");
        offer.setOffSummary("Offer Summary");
        offer.setOffRequirements("Offer Requirements");
        offer.setOffQuestions("Offer Questions");
        offer.setOffStepComments("Offer Step Comments");
        LocalDate localDate = LocalDate.of(2025, 1, 6);
        offer.setOffDate(localDate);
        offer.setOffStepDate(localDate);
        offer.setOffFavourite(false);
        offer.setOffStep(OffStep.values()[0]);
        offer.setUser(userPrincipal.getUser());
        return offerRepository.findById(id).orElseThrow(() -> new GlobalExceptionHandler.OfferNotFoundException());
    }

}