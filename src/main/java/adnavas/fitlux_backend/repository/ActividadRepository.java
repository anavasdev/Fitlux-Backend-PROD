package adnavas.fitlux_backend.repository;

import adnavas.fitlux_backend.entity.Actividad;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActividadRepository extends MongoRepository<Actividad, ObjectId> {
    Actividad findBy_id (ObjectId id);
}
