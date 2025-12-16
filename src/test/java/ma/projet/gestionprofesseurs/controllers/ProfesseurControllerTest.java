package ma.projet.gestionprofesseurs.controllers;

import ma.projet.gestionprofesseurs.entities.Professeur;
import ma.projet.gestionprofesseurs.entities.Specialite;
import ma.projet.gestionprofesseurs.repository.SpecialiteRepository;
import ma.projet.gestionprofesseurs.services.ProfesseurService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfesseurController.class)
@AutoConfigureMockMvc(addFilters = false) // disables security filters if present
class ProfesseurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfesseurService professeurService;

    // IMPORTANT: Controller has this dependency, so we must mock it for WebMvcTest
    @MockBean
    private SpecialiteRepository specialiteRepository;

    @Test
    void getAll_shouldReturn200() throws Exception {
        Mockito.when(professeurService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/professeur"))
                .andExpect(status().isOk());
    }

    @Test
    void getById_notFound_shouldReturn400() throws Exception {
        Mockito.when(professeurService.findById(99)).thenReturn(null);

        mockMvc.perform(get("/api/professeur/99"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_shouldReturn200() throws Exception {
        Professeur saved = new Professeur();
        Mockito.when(professeurService.create(Mockito.any(Professeur.class))).thenReturn(saved);

        // Minimal JSON body (only what your entity likely accepts)
        String json = """
                {
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
    }

    @Test
    void findBySpecialite_shouldReturn200() throws Exception {
        Mockito.when(professeurService.findBySpecialite(Mockito.any(Specialite.class)))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/professeur/specialite/1"))
                .andExpect(status().isOk());
    }

    @Test
    void filterByDate_shouldReturn200() throws Exception {
        Mockito.when(professeurService.findByDateEmbaucheBetween(Mockito.any(Date.class), Mockito.any(Date.class)))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/professeur/filterByDate")
                        .param("dateDebut", "2025-01-01")
                        .param("dateFin", "2025-12-31"))
                .andExpect(status().isOk());
    }
}
