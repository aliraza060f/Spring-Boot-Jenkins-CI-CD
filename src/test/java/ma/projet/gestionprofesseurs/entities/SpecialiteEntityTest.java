package ma.projet.gestionprofesseurs.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SpecialiteEntityTest {

    @Test
    void testSpecialiteSettersAndGetters() {
        Specialite s = new Specialite();
        s.setNom("Informatique");
        assertEquals("Informatique", s.getNom());
    }
}
