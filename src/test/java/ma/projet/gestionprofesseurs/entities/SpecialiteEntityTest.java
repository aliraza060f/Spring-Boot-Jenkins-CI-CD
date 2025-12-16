package ma.projet.gestionprofesseurs.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecialiteEntityTest {

    @Test
    void testGettersAndSetters() {
        Specialite s = new Specialite();

        s.setCode("INF");
        s.setLibelle("Informatique");

        assertEquals("INF", s.getCode());
        assertEquals("Informatique", s.getLibelle());
    }

    @Test
    void testConstructor() {
        Specialite s = new Specialite("MATH", "Mathematiques");

        assertEquals("MATH", s.getCode());
        assertEquals("Mathematiques", s.getLibelle());
    }
}
