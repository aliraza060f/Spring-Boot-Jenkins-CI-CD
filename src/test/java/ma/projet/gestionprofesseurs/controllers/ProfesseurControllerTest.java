package ma.projet.gestionprofesseurs.controllers;

import ma.projet.gestionprofesseurs.services.ProfesseurService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfesseurController.class)
class ProfesseurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfesseurService professeurService;

    @Test
    void shouldReturnOk_onGetAll() throws Exception {
        // If your controller calls service, mock it here:
        Mockito.when(professeurService.findAll()).thenReturn(java.util.List.of());

        mockMvc.perform(get("/professeurs"))
                .andExpect(status().isOk());
    }
}
