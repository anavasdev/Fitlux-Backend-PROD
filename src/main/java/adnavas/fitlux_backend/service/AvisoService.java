package adnavas.fitlux_backend.service;

import adnavas.fitlux_backend.entity.Aviso;
import adnavas.fitlux_backend.entity.Clase;
import org.bson.types.ObjectId;

import java.util.List;

public interface AvisoService {
    List<Aviso> findAll();
    Aviso findById(ObjectId id);
    List<Aviso> findByUsuarioId(ObjectId userId);
    Aviso addAviso(Aviso aviso);
    Aviso deleteAviso(ObjectId id);
    Aviso updateAviso(ObjectId id, Aviso aviso);
    boolean notificarUsuariosClase(String mensaje, Clase clase);
}
