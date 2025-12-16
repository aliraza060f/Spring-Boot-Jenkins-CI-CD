package ma.projet.gestionprofesseurs.services;

import ma.projet.gestionprofesseurs.entities.Specialite;
import ma.projet.gestionprofesseurs.repository.SpecialiteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpecialiteServiceTest {

    private SpecialiteRepository specialiteRepository;
    private SpecialiteService specialiteService;

    @BeforeEach
    void setUp() {
        specialiteRepository = mock(SpecialiteRepository.class);
        specialiteService = new SpecialiteService();

        // inject mock into service
        try {
            var field = SpecialiteService.class.getDeclaredField("specialiteRepository");
            field.setAccessible(true);
            field.set(specialiteService, specialiteRepository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void create_shouldSaveAndReturnSpecialite() {
        Specialite s = new Specialite();
        when(specialiteRepository.save(s)).thenReturn(s);

        Specialite result = specialiteService.create(s);

        assertNotNull(result);
        verify(specialiteRepository, times(1)).save(s);
    }

    @Test
    void update_shouldSaveAndReturnSpecialite() {
        Specialite s = new Specialite();
        when(specialiteRepository.save(s)).thenReturn(s);

        Specialite result = specialiteService.update(s);

        assertNotNull(result);
        verify(specialiteRepository, times(1)).save(s);
    }

    @Test
    void delete_shouldReturnTrueWhenDeleteSuccess() {
        Specialite s = new Specialite();
        doNothing().when(specialiteRepository).delete(s);

        boolean result = specialiteService.delete(s);

        assertTrue(result);
        verify(specialiteRepository, times(1)).delete(s);
    }

    @Test
    void delete_shouldReturnFalseWhenDeleteThrowsException() {
        Specialite s = new Specialite();
        doThrow(new RuntimeException("delete error")).when(specialiteRepository).delete(s);

        boolean result = specialiteService.delete(s);

        assertFalse(result);
        verify(specialiteRepository, times(1)).delete(s);
    }

    @Test
    void findAll_shouldReturnAllSpecialites() {
        List<Specialite> list = Arrays.asList(new Specialite(), new Specialite());
        when(specialiteRepository.findAll()).thenReturn(list);

        List<Specialite> result = specialiteService.findAll();

        assertEquals(2, result.size());
        verify(specialiteRepository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnSpecialiteWhenFound() {
        when(specialiteRepository.findById(1)).thenReturn(Optional.of(new Specialite()));

        Specialite result = specialiteService.findById(1);

        assertNotNull(result);
        verify(specialiteRepository, times(1)).findById(1);
    }

    @Test
    void findById_shouldReturnNullWhenNotFound() {
        when(specialiteRepository.findById(99)).thenReturn(Optional.empty());

        Specialite result = specialiteService.findById(99);

        assertNull(result);
        verify(specialiteRepository, times(1)).findById(99);
    }
}
