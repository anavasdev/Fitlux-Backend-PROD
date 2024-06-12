package adnavas.fitlux_backend.service;

import adnavas.fitlux_backend.entity.Usuario;
import adnavas.fitlux_backend.entity.UsuarioProjection;
import adnavas.fitlux_backend.repository.UsuarioRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioProjection> findAll() {
        return userRepository.findProjectedBy();
    }

    @Override
    public Usuario findById(ObjectId id) {
        return userRepository.findBy_id(id);
    }

    @Override
    public UsuarioProjection findProjectedBy_id(ObjectId id) {
        return userRepository.findProjectedBy_id(id);
    }

    @Override
    public Usuario findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UsuarioProjection findProjectedByUsername(String username) {
        return userRepository.findProjectedByUsername(username);
    }

    @Override
    @Transactional
    public Usuario addUsuario(Usuario user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Usuario deleteUsuario(ObjectId id) {
        Usuario usuarioDelete = userRepository.findBy_id(id);
        userRepository.delete(usuarioDelete);
        return usuarioDelete;
    }

    @Override
    public Usuario updateUsuario(ObjectId id, Usuario usuario) {
        Usuario usuarioUpdate = userRepository.findBy_id(id);
        usuarioUpdate.setDni(usuario.getDni());
        usuarioUpdate.setFullname(usuario.getFullname());
        usuarioUpdate.setRole(usuario.getRole());
        //usuarioUpdate.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioUpdate.setPassword(usuarioUpdate.getPassword());
        usuarioUpdate.setEmail(usuario.getEmail());
        usuarioUpdate.setUsername(usuario.getUsername());
        usuarioUpdate.setEnabled(usuario.isEnabled());

        userRepository.save(usuarioUpdate);

        return usuarioUpdate;
    }
}
