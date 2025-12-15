package ma.projet.gestionprofesseurs.repository;

import ma.projet.gestionprofesseurs.entities.Specialite;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SpecialiteRepositoryTest {

    @Autowired
    private SpecialiteRepository repository;

    @Test
    void saveAndFindSpecialite() {
        Specialite s = new Specialite();
        s.setCode("INFO");
        s.setLibelle("Informatique");

        Specialite saved = repository.save(s);

        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findById(saved.getId())).isPresent();
    }
}
