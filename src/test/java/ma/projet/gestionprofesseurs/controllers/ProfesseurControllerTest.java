package ma.projet.gestionprofesseurs.controllers;

import ma.projet.gestionprofesseurs.entities.Professeur;
import ma.projet.gestionprofesseurs.entities.Specialite;
import ma.projet.gestionprofesseurs.repository.SpecialiteRepository;
import ma.projet.gestionprofesseurs.services.ProfesseurService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfesseurController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProfesseurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfesseurService professeurService;

    // Controller has this dependency, so mock it to avoid ApplicationContext errors
    @MockBean
    private SpecialiteRepository specialiteRepository;

    @Test
    void getAll_shouldReturn200() throws Exception {
        Mockito.when(professeurService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/professeur"))
                .andExpect(status().isOk());

        Mockito.verify(professeurService).findAll();
    }

    @Test
    void getById_notFound_shouldReturn400() throws Exception {
        Mockito.when(professeurService.findById(99)).thenReturn(null);

        mockMvc.perform(get("/api/professeur/99"))
                .andExpect(status().isBadRequest());

        Mockito.verify(professeurService).findById(99);
    }

    @Test
    void getById_found_shouldReturn200() throws Exception {
        Professeur p = new Professeur();
        p.setId(1);
        p.setNom("Ali");
        p.setPrenom("Raza");

        Mockito.when(professeurService.findById(1)).thenReturn(p);

        mockMvc.perform(get("/api/professeur/1"))
                .andExpect(status().isOk());

        Mockito.verify(professeurService).findById(1);
    }

    @Test
    void create_shouldReturn200_andSetIdToZero() throws Exception {
        Professeur saved = new Professeur();
        saved.setId(10);

        Mockito.when(professeurService.create(Mockito.any(Professeur.class))).thenReturn(saved);

        String json = """
                {
                  "id": 999,
                  "nom": "Ali",
                  "prenom": "Raza",
                  "telephone": "123456789",
                  "email": "ali@example.com"
                }
                """;

        mockMvc.perform(post("/api/professeur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        ArgumentCaptor<Professeur> captor = ArgumentCaptor.forClass(Professeur.class);
        Mockito.verify(professeurService).create(captor.capture());
        assertEquals(0, captor.getValue().getId()); // controller sets id=0
    }

    @Test
    void update_notFound_shouldReturn400() throws Exception {
        Mockito.when(professeurService.findById(5)).thenReturn(null);

        String json = """
                { "nom": "New", "prenom": "Name", "telephone": "000", "email": "new@test.com" }
                """;

        mockMvc.perform(put("/api/professeur/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        Mockito.verify(professeurService).findById(5);
        Mockito.verify(professeurService, Mockito.never()).update(Mockito.any());
    }

    @Test
    void update_success_shouldReturn200_andSetIdFromPath() throws Exception {
        Professeur existing = new Professeur();
        existing.setId(5);

        Professeur updated = new Professeur();
        updated.setId(5);

        Mockito.when(professeurService.findById(5)).thenReturn(existing);
        Mockito.when(professeurService.update(Mockito.any(Professeur.class))).thenReturn(updated);

        String json = """
                {
                  "id": 999,
                  "nom": "Ali",
                  "prenom": "Raza",
                  "telephone": "123456789",
                  "email": "ali@example.com"
                }
                """;

        mockMvc.perform(put("/api/professeur/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        ArgumentCaptor<Professeur> captor = ArgumentCaptor.forClass(Professeur.class);
        Mockito.verify(professeurService).update(captor.capture());
        assertEquals(5, captor.getValue().getId()); // controller sets id to path id
    }

    @Test
    void delete_notFound_shouldReturn400() throws Exception {
        Mockito.when(professeurService.findById(7)).thenReturn(null);

        mockMvc.perform(delete("/api/professeur/7"))
                .andExpect(status().isBadRequest());

        Mockito.verify(professeurService).findById(7);
        Mockito.verify(professeurService, Mockito.never()).delete(Mockito.any());
    }

    @Test
    void delete_success_shouldReturn200() throws Exception {
        Professeur existing = new Professeur();
        existing.setId(7);
        Mockito.when(professeurService.findById(7)).thenReturn(existing);

        mockMvc.perform(delete("/api/professeur/7"))
                .andExpect(status().isOk());

        Mockito.verify(professeurService).findById(7);
        Mockito.verify(professeurService).delete(existing);
    }

    @Test
    void findBySpecialite_shouldReturn200_andPassSpecialiteId() throws Exception {
        Mockito.when(professeurService.findBySpecialite(Mockito.any(Specialite.class)))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/professeur/specialite/3"))
                .andExpect(status().isOk());

        ArgumentCaptor<Specialite> captor = ArgumentCaptor.forClass(Specialite.class);
        Mockito.verify(professeurService).findBySpecialite(captor.capture());
        assertEquals(3, captor.getValue().getId());
    }

    @Test
    void filterByDate_shouldReturn200() throws Exception {
        Mockito.when(professeurService.findByDateEmbaucheBetween(Mockito.any(Date.class), Mockito.any(Date.class)))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/professeur/filterByDate")
                        .param("dateDebut", "2025-01-01")
                        .param("dateFin", "2025-12-31"))
                .andExpect(status().isOk());

        Mockito.verify(professeurService)
                .findByDateEmbaucheBetween(Mockito.any(Date.class), Mockito.any(Date.class));
    }
}
