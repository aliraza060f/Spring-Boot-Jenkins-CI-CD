package ma.projet.gestionprofesseurs.controllers;

import ma.projet.gestionprofesseurs.entities.Specialite;
import ma.projet.gestionprofesseurs.services.SpecialiteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SpecialiteController.class)
@AutoConfigureMockMvc(addFilters = false) // disables security filters if present
class SpecialiteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpecialiteService specialiteService;

    @Test
    void getAll_shouldReturn200() throws Exception {
        Mockito.when(specialiteService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/specialite"))
                .andExpect(status().isOk());
    }

    @Test
    void getById_notFound_shouldReturn400() throws Exception {
        Mockito.when(specialiteService.findById(99)).thenReturn(null);

        mockMvc.perform(get("/api/specialite/99"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_shouldReturn200() throws Exception {
        Specialite saved = new Specialite("INF", "Informatique");
        Mockito.when(specialiteService.create(Mockito.any(Specialite.class))).thenReturn(saved);

        String json = """
                {
                  "code": "INF",
                  "libelle": "Informatique"
                }
                """;

        mockMvc.perform(post("/api/specialite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }
}
