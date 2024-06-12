package adnavas.fitlux_backend.service;

import adnavas.fitlux_backend.entity.Actividad;
import org.bson.types.ObjectId;

import java.util.List;

public interface ActividadService {
    List<Actividad> findAll();
    Actividad findById(ObjectId id);
    Actividad addActividad(Actividad actividad);
    Actividad deleteActividad(ObjectId id);
    Actividad updateActividad(ObjectId id, Actividad actividad);
}
