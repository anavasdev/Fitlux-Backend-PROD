package adnavas.fitlux_backend.service;

import adnavas.fitlux_backend.entity.Sala;
import org.bson.types.ObjectId;

import java.util.List;

public interface SalaService {
    List<Sala> findAll();
    Sala findById(ObjectId id);
    Sala addSala(Sala sala);
    Sala deleteSala(ObjectId id);
    Sala updateSala(ObjectId id, Sala sala);
}
