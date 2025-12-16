package ma.projet.gestionprofesseurs.controllers;

import ma.projet.gestionprofesseurs.entities.Specialite;
import ma.projet.gestionprofesseurs.services.SpecialiteService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SpecialiteController.class)
@AutoConfigureMockMvc(addFilters = false)
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

        Mockito.verify(specialiteService).findAll();
    }

    @Test
    void getById_notFound_shouldReturn400() throws Exception {
        Mockito.when(specialiteService.findById(99)).thenReturn(null);

        mockMvc.perform(get("/api/specialite/99"))
                .andExpect(status().isBadRequest());

        Mockito.verify(specialiteService).findById(99);
    }

    @Test
    void getById_found_shouldReturn200() throws Exception {
        Specialite s = new Specialite("INF", "Informatique");
        s.setId(1);
        Mockito.when(specialiteService.findById(1)).thenReturn(s);

        mockMvc.perform(get("/api/specialite/1"))
                .andExpect(status().isOk());

        Mockito.verify(specialiteService).findById(1);
    }

    @Test
    void create_shouldReturn200_andSetIdToZero() throws Exception {
        Specialite saved = new Specialite("INF", "Informatique");
        saved.setId(10);

        Mockito.when(specialiteService.create(Mockito.any(Specialite.class))).thenReturn(saved);

        String json = """
                { "id": 999, "code": "INF", "libelle": "Informatique" }
                """;

        mockMvc.perform(post("/api/specialite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        // Verify controller sets id=0 before calling service
        ArgumentCaptor<Specialite> captor = ArgumentCaptor.forClass(Specialite.class);
        Mockito.verify(specialiteService).create(captor.capture());
        assertEquals(0, captor.getValue().getId());
        assertEquals("INF", captor.getValue().getCode());
        assertEquals("Informatique", captor.getValue().getLibelle());
    }

    @Test
    void update_notFound_shouldReturn400() throws Exception {
        Mockito.when(specialiteService.findById(5)).thenReturn(null);

        String json = """
                { "code": "MATH", "libelle": "Mathematiques" }
                """;

        mockMvc.perform(put("/api/specialite/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        Mockito.verify(specialiteService).findById(5);
        Mockito.verify(specialiteService, Mockito.never()).update(Mockito.any());
    }

    @Test
    void update_success_shouldReturn200_andSetIdFromPath() throws Exception {
        Specialite existing = new Specialite("OLD", "Old");
        existing.setId(5);

        Specialite updated = new Specialite("MATH", "Mathematiques");
        updated.setId(5);

        Mockito.when(specialiteService.findById(5)).thenReturn(existing);
        Mockito.when(specialiteService.update(Mockito.any(Specialite.class))).thenReturn(updated);

        String json = """
                { "id": 999, "code": "MATH", "libelle": "Mathematiques" }
                """;

        mockMvc.perform(put("/api/specialite/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        ArgumentCaptor<Specialite> captor = ArgumentCaptor.forClass(Specialite.class);
        Mockito.verify(specialiteService).update(captor.capture());
        assertEquals(5, captor.getValue().getId()); // controller sets id to path id
    }

    @Test
    void delete_notFound_shouldReturn400() throws Exception {
        Mockito.when(specialiteService.findById(7)).thenReturn(null);

        mockMvc.perform(delete("/api/specialite/7"))
                .andExpect(status().isBadRequest());

        Mockito.verify(specialiteService).findById(7);
        Mockito.verify(specialiteService, Mockito.never()).delete(Mockito.any());
    }

    @Test
    void delete_success_shouldReturn200() throws Exception {
        Specialite existing = new Specialite("INF", "Informatique");
        existing.setId(7);
        Mockito.when(specialiteService.findById(7)).thenReturn(existing);

        mockMvc.perform(delete("/api/specialite/7"))
                .andExpect(status().isOk());

        Mockito.verify(specialiteService).findById(7);
        Mockito.verify(specialiteService).delete(existing);
    }
}
