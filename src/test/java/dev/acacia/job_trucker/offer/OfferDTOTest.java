package dev.acacia.job_trucker.offer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

public class OfferDTOTest {

    @Test
    public void testConstructor() {
        OfferDTO offerDTO = new OfferDTO(
                "Company Name", "Contact Name", "123456789", "contact@example.com",
                "Job Address", "https://example.com", "Summary", "Requirements", "Questions", "Comments",
                LocalDate.now(), LocalDate.now(), true, OffStep.CANDIDATURA_RECHAZADA, 1L
        );

        assertEquals("Company Name", offerDTO.getOffCompanyName());
        assertEquals("Contact Name", offerDTO.getOffContactName());
        assertEquals("123456789", offerDTO.getOffContactPhone());
        assertEquals("contact@example.com", offerDTO.getOffContactEmail());
        assertEquals("Job Address", offerDTO.getOffJobAddress());
        assertEquals("https://example.com", offerDTO.getOffLink());
        assertEquals("Summary", offerDTO.getOffSummary());
        assertEquals("Requirements", offerDTO.getOffRequirements());
        assertEquals("Questions", offerDTO.getOffQuestions());
        assertEquals("Comments", offerDTO.getOffStepComments());
        assertEquals(LocalDate.now(), offerDTO.getOffDate());
        assertEquals(LocalDate.now(), offerDTO.getOffStepDate());
        assertEquals(true, offerDTO.isOffFavourite());
        assertEquals(OffStep.CANDIDATURA_RECHAZADA, offerDTO.getOffStep());
        assertEquals(1L, offerDTO.getUserId());
    }

    @Test
    public void testGettersAndSetters() {
        OfferDTO offerDTO = new OfferDTO(
                "Company Name", "Contact Name", "123456789", "contact@example.com",
                "Job Address", "https://example.com", "Summary", "Requirements", "Questions", "Comments",
                LocalDate.now(), LocalDate.now(), true, OffStep.CANDIDATURA_RECHAZADA, 1L
        );

        offerDTO.setOffCompanyName("New Company Name");
        assertEquals("New Company Name", offerDTO.getOffCompanyName());

        offerDTO.setOffContactName("New Contact Name");
        assertEquals("New Contact Name", offerDTO.getOffContactName());

        offerDTO.setOffContactPhone("987654321");
        assertEquals("987654321", offerDTO.getOffContactPhone());

        offerDTO.setOffContactEmail("newcontact@example.com");
        assertEquals("newcontact@example.com", offerDTO.getOffContactEmail());

        offerDTO.setOffJobAddress("New Job Address");
        assertEquals("New Job Address", offerDTO.getOffJobAddress());

        offerDTO.setOffLink("https://newexample.com");
        assertEquals("https://newexample.com", offerDTO.getOffLink());

        offerDTO.setOffSummary("New Summary");
        assertEquals("New Summary", offerDTO.getOffSummary());

        offerDTO.setOffRequirements("New Requirements");
        assertEquals("New Requirements", offerDTO.getOffRequirements());

        offerDTO.setOffQuestions("New Questions");
        assertEquals("New Questions", offerDTO.getOffQuestions());

        offerDTO.setOffStepComments("New Comments");
        assertEquals("New Comments", offerDTO.getOffStepComments());

        offerDTO.setOffDate(LocalDate.now().plusDays(1));
        assertEquals(LocalDate.now().plusDays(1), offerDTO.getOffDate());

        offerDTO.setOffStepDate(LocalDate.now().plusDays(2));
        assertEquals(LocalDate.now().plusDays(2), offerDTO.getOffStepDate());

        offerDTO.setOffFavourite(false);
        assertEquals(false, offerDTO.isOffFavourite());

        offerDTO.setOffStep(OffStep.ENTREVISTA);
        assertEquals(OffStep.ENTREVISTA, offerDTO.getOffStep());

        offerDTO.setUserId(2L);
        assertEquals(2L, offerDTO.getUserId());
    }
}