package adnavas.fitlux_backend.service;

import adnavas.fitlux_backend.entity.Actividad;
import adnavas.fitlux_backend.repository.ActividadRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActividadServiceImpl implements ActividadService{

    @Autowired
    ActividadRepository actividadRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Actividad> findAll() {
        return actividadRepository.findAll();
    }

    @Override
    public Actividad findById(ObjectId id) {
        return actividadRepository.findBy_id(id);
    }

    @Override
    public Actividad addActividad(Actividad actividad) {
        return actividadRepository.save(actividad);
    }

    @Override
    public Actividad deleteActividad(ObjectId id) {
        Actividad actividadDelete = actividadRepository.findBy_id(id);
        actividadRepository.delete(actividadDelete);
        return actividadDelete;
    }

    @Override
    public Actividad updateActividad(ObjectId id, Actividad actividad) {
        Actividad actividadUpdate = actividadRepository.findBy_id(id);
        actividadUpdate.setNombre(actividad.getNombre());
        actividadUpdate.setDescripcion(actividad.getDescripcion());
        actividadRepository.save(actividadUpdate);
        return actividadUpdate;
    }
}
