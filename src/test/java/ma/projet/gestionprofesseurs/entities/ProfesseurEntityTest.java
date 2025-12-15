package ma.projet.gestionprofesseurs.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProfesseurEntityTest {

    @Test
    void testProfesseurSettersAndGetters() {
        Professeur p = new Professeur();

        p.setNom("Ali");
        p.setPrenom("Raza");
        p.setTelephone("123456789");
        p.setEmail("ali@example.com");

        assertEquals("Ali", p.getNom());
        assertEquals("Raza", p.getPrenom());
        assertEquals("123456789", p.getTelephone());
        assertEquals("ali@example.com", p.getEmail());
    }
}
