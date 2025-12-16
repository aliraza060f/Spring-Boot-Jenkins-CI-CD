package ma.projet.gestionprofesseurs.services;

import ma.projet.gestionprofesseurs.entities.Professeur;
import ma.projet.gestionprofesseurs.entities.Specialite;
import ma.projet.gestionprofesseurs.repository.ProfesseurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfesseurServiceTest {

    private ProfesseurRepository professeurRepository;
    private ProfesseurService professeurService;

    @BeforeEach
    void setUp() {
        professeurRepository = mock(ProfesseurRepository.class);
        professeurService = new ProfesseurService();

        // inject mock into service (because service uses @Autowired)
        try {
            var field = ProfesseurService.class.getDeclaredField("professeurRepository");
            field.setAccessible(true);
            field.set(professeurService, professeurRepository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void create_shouldSaveAndReturnProfesseur() {
        Professeur p = new Professeur();
        when(professeurRepository.save(p)).thenReturn(p);

        Professeur result = professeurService.create(p);

        assertNotNull(result);
        verify(professeurRepository, times(1)).save(p);
    }

    @Test
    void update_shouldSaveAndReturnProfesseur() {
        Professeur p = new Professeur();
        when(professeurRepository.save(p)).thenReturn(p);

        Professeur result = professeurService.update(p);

        assertNotNull(result);
        verify(professeurRepository, times(1)).save(p);
    }

    @Test
    void delete_shouldReturnTrueWhenDeleteSuccess() {
        Professeur p = new Professeur();
        doNothing().when(professeurRepository).delete(p);

        boolean result = professeurService.delete(p);

        assertTrue(result);
        verify(professeurRepository, times(1)).delete(p);
    }

    @Test
    void delete_shouldReturnFalseWhenDeleteThrowsException() {
        Professeur p = new Professeur();
        doThrow(new RuntimeException("delete error")).when(professeurRepository).delete(p);

        boolean result = professeurService.delete(p);

        assertFalse(result);
        verify(professeurRepository, times(1)).delete(p);
    }

    @Test
    void findAll_shouldReturnAllProfesseurs() {
        List<Professeur> list = Arrays.asList(new Professeur(), new Professeur());
        when(professeurRepository.findAll()).thenReturn(list);

        List<Professeur> result = professeurService.findAll();

        assertEquals(2, result.size());
        verify(professeurRepository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnProfesseurWhenFound() {
        when(professeurRepository.findById(1)).thenReturn(Optional.of(new Professeur()));

        Professeur result = professeurService.findById(1);

        assertNotNull(result);
        verify(professeurRepository, times(1)).findById(1);
    }

    @Test
    void findById_shouldReturnNullWhenNotFound() {
        when(professeurRepository.findById(99)).thenReturn(Optional.empty());

        Professeur result = professeurService.findById(99);

        assertNull(result);
        verify(professeurRepository, times(1)).findById(99);
    }

    @Test
    void findBySpecialite_shouldCallRepository() {
        Specialite sp = new Specialite();
        List<Professeur> list = List.of(new Professeur());
        when(professeurRepository.findBySpecialite(sp)).thenReturn(list);

        List<Professeur> result = professeurService.findBySpecialite(sp);

        assertEquals(1, result.size());
        verify(professeurRepository, times(1)).findBySpecialite(sp);
    }

    @Test
    void findByDateEmbaucheBetween_shouldCallRepository() {
        Date d1 = new Date();
        Date d2 = new Date(d1.getTime() + 1000);
        List<Professeur> list = List.of(new Professeur(), new Professeur());
        when(professeurRepository.findByDateEmbaucheBetween(d1, d2)).thenReturn(list);

        List<Professeur> result = professeurService.findByDateEmbaucheBetween(d1, d2);

        assertEquals(2, result.size());
        verify(professeurRepository, times(1)).findByDateEmbaucheBetween(d1, d2);
    }
}
