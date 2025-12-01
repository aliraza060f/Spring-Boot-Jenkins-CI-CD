package ma.projet.gestionprofesseurs.services;

import ma.projet.gestionprofesseurs.dao.IDao;
import ma.projet.gestionprofesseurs.entities.Professeur;
import ma.projet.gestionprofesseurs.entities.Specialite;
import ma.projet.gestionprofesseurs.repository.ProfesseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProfesseurService implements IDao<Professeur> {

    @Autowired
    private ProfesseurRepository professeurRepository;

    /*@
      @ requires o != null;
      @ ensures \result != null;
      @*/
    @Override
    public Professeur create(Professeur o) {
        return professeurRepository.save(o);
    }

    /*@
      @ requires o != null;
      @*/
    @Override
    public boolean delete(Professeur o) {
        try {
            professeurRepository.delete(o);
            return true;
        }
        catch(Exception ex) {
            return false;
        }
    }

    /*@
      @ requires o != null;
      @ ensures \result != null;
      @*/
    @Override
    public Professeur update(Professeur o) {
        return professeurRepository.save(o);
    }

    @Override
    public List<Professeur> findAll() {
        return professeurRepository.findAll();
    }

    /*@
      @ requires id > 0;
      @*/
    @Override
    public Professeur findById(int id) {
        return professeurRepository.findById(id).orElse(null);
    }

    /*@
      @ requires specialite != null;
      @ ensures \result != null;
      @*/
    public List<Professeur> findBySpecialite(Specialite specialite) {
        return professeurRepository.findBySpecialite(specialite);
    }

    /*@
      @ requires dateDebut != null;
      @ requires dateFin != null;
      @ requires dateDebut.before(dateFin);
      @ ensures \result != null;
      @*/
    public List<Professeur> findByDateEmbaucheBetween(Date dateDebut, Date dateFin) {
        return professeurRepository.findByDateEmbaucheBetween(dateDebut, dateFin);
    }
}
