package adnavas.fitlux_backend.repository;

import adnavas.fitlux_backend.entity.Usuario;
import adnavas.fitlux_backend.entity.UsuarioProjection;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface UsuarioRepository extends MongoRepository<Usuario, ObjectId> {
    List<UsuarioProjection> findProjectedBy();

    Usuario findByUsername(String username);
    UsuarioProjection findProjectedByUsername(String username);

    Usuario findBy_id (ObjectId id);
    UsuarioProjection findProjectedBy_id(ObjectId id);

    List<Usuario> findBy_idIn(List<ObjectId> userIds);
}