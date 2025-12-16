package ma.projet.gestionprofesseurs.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecialiteEntityTest {

    @Test
    void testNoArgsConstructor() {
        Specialite s = new Specialite();
        assertNotNull(s);
    }

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

    @Test
    void testIdSetterGetter() {
        Specialite s = new Specialite();
        s.setId(10);
        assertEquals(10, s.getId());
    }

    @Test
    void testNullValuesAllowed() {
        Specialite s = new Specialite();

        s.setCode(null);
        s.setLibelle(null);

        assertNull(s.getCode());
        assertNull(s.getLibelle());
    }

    @Test
    void testEmptyStrings() {
        Specialite s = new Specialite();

        s.setCode("");
        s.setLibelle("");

        assertEquals("", s.getCode());
        assertEquals("", s.getLibelle());
    }
}
