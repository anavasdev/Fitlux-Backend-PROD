package adnavas.fitlux_backend.repository;

import adnavas.fitlux_backend.entity.Sala;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SalaRepository extends MongoRepository<Sala, ObjectId> {
    Sala findBy_id (ObjectId id);
}
