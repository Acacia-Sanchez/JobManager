package dev.acacia.job_trucker.offer;

import org.junit.jupiter.api.Test;

import dev.acacia.job_trucker.user.User;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class OfferTest {

    @Test
    public void testConstructor() {
        Offer offer = new Offer("Empresa", "Juan", "123456789", "juan@example.com", "Dirección", "https://example.com", "Resumen", "Requisitos", "Preguntas", "Comentarios", LocalDate.now(), LocalDate.now(), true, OffStep.CANDIDATURA_RECHAZADA, new User());
        assertNotNull(offer);
        assertEquals("Empresa", offer.getOffCompanyName());
        assertEquals("Juan", offer.getOffContactName());
        assertEquals("123456789", offer.getOffContactPhone());
        assertEquals("juan@example.com", offer.getOffContactEmail());
        assertEquals("Dirección", offer.getOffJobAddress());
        assertEquals("https://example.com", offer.getOffLink());
        assertEquals("Resumen", offer.getOffSummary());
        assertEquals("Requisitos", offer.getOffRequirements());
        assertEquals("Preguntas", offer.getOffQuestions());
        assertEquals("Comentarios", offer.getOffStepComments());
        assertEquals(LocalDate.now(), offer.getOffDate());
        assertEquals(LocalDate.now(), offer.getOffStepDate());
        assertTrue(offer.isOffFavourite());
        assertEquals(OffStep.CANDIDATURA_RECHAZADA, offer.getOffStep());
        assertNotNull(offer.getUser());
    }

    @Test
    public void testGetOffCompanyName_ReturnsCorrectValue() {
        Offer offer = new Offer();
        offer.setOffCompanyName("Empresa de prueba");
        assertEquals("Empresa de prueba", offer.getOffCompanyName());
    }

    @Test
    public void testSetOffCompanyName_SetsCorrectValue() {
        Offer offer = new Offer();
        offer.setOffCompanyName("Empresa de prueba");
        assertEquals("Empresa de prueba", offer.getOffCompanyName());
    }

    @Test
    public void testGetOffContactName_ReturnsCorrectValue() {
        Offer offer = new Offer();
        offer.setOffContactName("Nombre de contacto");
        assertEquals("Nombre de contacto", offer.getOffContactName());
    }

    @Test
    public void testSetOffContactName_SetsCorrectValue() {
        Offer offer = new Offer();
        offer.setOffContactName("Nombre de contacto");
        assertEquals("Nombre de contacto", offer.getOffContactName());
    }

    @Test
    public void testGetOffContactEmail_ReturnsCorrectValue() {
        Offer offer = new Offer();
        offer.setOffContactEmail("contacto@example.com");
        assertEquals("contacto@example.com", offer.getOffContactEmail());
    }

    @Test
    public void testSetOffContactEmail_SetsCorrectValue() {
        Offer offer = new Offer();
        offer.setOffContactEmail("contacto@example.com");
        assertEquals("contacto@example.com", offer.getOffContactEmail());
    }

    @Test
    public void testGetOffContactPhone_ReturnsCorrectValue() {
        Offer offer = new Offer();
        offer.setOffContactPhone("987-654-3210");
        assertEquals("987-654-3210", offer.getOffContactPhone());
    }

    @Test
    public void testSetOffContactPhone_SetsCorrectValue() {
        Offer offer = new Offer();
        offer.setOffContactPhone("987-654-3210");
        assertEquals("987-654-3210", offer.getOffContactPhone());
    }

    @Test
    public void testGetOffSummary_ReturnsCorrectValue() {
        Offer offer = new Offer();
        offer.setOffSummary("Resumen de oferta");
        assertEquals("Resumen de oferta", offer.getOffSummary());
    }

    @Test
    public void testSetOffSummary_SetsCorrectValue() {
        Offer offer = new Offer();
        offer.setOffSummary("Resumen de oferta");
        assertEquals("Resumen de oferta", offer.getOffSummary());
    }

    @Test
    public void testGetOffLink_ReturnsCorrectValue() {
        Offer offer = new Offer();
        offer.setOffLink("https://www.example.com");
        assertEquals("https://www.example.com", offer.getOffLink());
    }

    @Test
    public void testSetOffLink_SetsCorrectValue() {
        Offer offer = new Offer();
        offer.setOffLink("https://www.example.com");
        assertEquals("https://www.example.com", offer.getOffLink());
    }

    @Test
    public void testGetOffDate_ReturnsCorrectValue() {
        Offer offer = new Offer();
        offer.setOffDate(LocalDate.now());
        assertEquals(LocalDate.now(), offer.getOffDate());
    }

    @Test
    public void testSetOffDate_SetsCorrectValue() {
        Offer offer = new Offer();
        offer.setOffDate(LocalDate.now());
        assertEquals(LocalDate.now(), offer.getOffDate());
    }
    
    @Test
    public void testEquals_ReturnsFalseForDifferentObjects() {
        Offer offer1 = new Offer();
        offer1.setOffCompanyName("Empresa de prueba");
        offer1.setOffContactName("Nombre de contacto");
        offer1.setOffContactEmail("contacto@example.com");
        offer1.setOffContactPhone("987-654-3210");
        offer1.setOffSummary("Resumen de oferta");
        offer1.setOffLink("https://www.example.com");
        offer1.setOffDate(LocalDate.now());
    
        Offer offer2 = new Offer();
        offer2.setOffCompanyName("Otra empresa");
        offer2.setOffContactName("Otro nombre de contacto");
        offer2.setOffContactEmail("otro_contacto@example.com");
        offer2.setOffContactPhone("123-456-7890");
        offer2.setOffSummary("Otro resumen de oferta");
        offer2.setOffLink("https://www.otra_empresa.com");
        offer2.setOffDate(LocalDate.now());
    
        assertFalse(offer1.equals(offer2));
    }
    
    @Test
    public void testToString() {
        Offer offer = new Offer("Empresa", "Juan", "123456789", "juan@example.com", "Dirección", "https://example.com", "Resumen", "Requisitos", "Preguntas", "Comentarios", LocalDate.now(), LocalDate.now(), true, OffStep.CANDIDATURA_RECHAZADA, new User());
        String expected = "Offer{offCompanyName=Empresa, offContactName=Juan, offContactPhone=123456789, offContactEmail=juan@example.com, offJobAddress=Dirección, offLink=https://example.com, offSummary=Resumen, offRequirements=Requisitos, offQuestions=Preguntas, offStepComments=Comentarios, offDate=" + LocalDate.now() + ", offStepDate=" + LocalDate.now() + ", offFavourite=true, offStep=CANDIDATURA_RECHAZADA, user=User{id=0, username='null', password='null', email='null', role='null'}}";
        assertEquals(expected, offer.toString());
    }
    
    @Test
    public void testHashCode_DifferentObjectsHaveDifferentHashCodes() {
        Offer offer1 = new Offer();
        offer1.setOffContactName("Juan");
    
        Offer offer2 = new Offer();
        offer2.setOffContactName("Nombre de contacto");
    
        assertNotEquals(offer1.hashCode(), offer2.hashCode());
    }
}