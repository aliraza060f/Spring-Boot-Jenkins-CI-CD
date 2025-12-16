package ma.projet.gestionprofesseurs.controllers;

import ma.projet.gestionprofesseurs.services.SpecialiteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SpecialiteController.class)
class SpecialiteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpecialiteService specialiteService;

    @Test
    void shouldReturnOk_onGetAllSpecialites() throws Exception {
        Mockito.when(specialiteService.findAll())
               .thenReturn(java.util.List.of());

        mockMvc.perform(get("/specialites"))
               .andExpect(status().isOk());
    }
}
