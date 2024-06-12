package adnavas.fitlux_backend.repository;

import adnavas.fitlux_backend.entity.Clase;
import adnavas.fitlux_backend.entity.Usuario;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ClaseRepository extends MongoRepository<Clase, ObjectId> {
    Clase findBy_id (ObjectId id);
    @Query("{ 'actividad_id' : ?0 }")
    List<Clase> findClasesByActividad_id(ObjectId actividadId);

    @Query("{ 'usuarios' : ?0 }")
    List<Clase> findClasesByUsuario(ObjectId usuarioId);

}

