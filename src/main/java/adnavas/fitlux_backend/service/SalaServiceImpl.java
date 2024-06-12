package adnavas.fitlux_backend.service;

import adnavas.fitlux_backend.entity.Sala;
import adnavas.fitlux_backend.repository.SalaRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SalaServiceImpl implements SalaService {

    @Autowired
    SalaRepository salaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Sala> findAll() {
        return salaRepository.findAll();
    }

    @Override
    public Sala findById(ObjectId id) {
        return salaRepository.findBy_id(id);
    }

    @Override
    @Transactional
    public Sala addSala(Sala sala) {
        return salaRepository.save(sala);
    }

    @Override
    public Sala deleteSala(ObjectId id) {
        Sala salaDelete = salaRepository.findBy_id(id);
        salaRepository.delete(salaDelete);
        return salaDelete;
    }

    @Override
    public Sala updateSala(ObjectId id, Sala sala) {
        Sala salaUpdate = salaRepository.findBy_id(id);
        salaUpdate.setNombre(sala.getNombre());
        salaUpdate.setAforo(sala.getAforo());
        salaRepository.save(salaUpdate);
        return salaUpdate;
    }
}
