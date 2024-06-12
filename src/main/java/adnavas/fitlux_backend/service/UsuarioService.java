package adnavas.fitlux_backend.service;


import adnavas.fitlux_backend.entity.Usuario;
import adnavas.fitlux_backend.entity.UsuarioProjection;
import org.bson.types.ObjectId;

import java.util.List;

public interface UsuarioService {
    List<UsuarioProjection> findAll();

    Usuario findById(ObjectId id);
    UsuarioProjection findProjectedBy_id(ObjectId id);

    Usuario findByUsername(String username);
    UsuarioProjection findProjectedByUsername(String username);

    Usuario addUsuario (Usuario user);
    Usuario deleteUsuario(ObjectId id);
    Usuario updateUsuario(ObjectId id, Usuario usuario);

}
