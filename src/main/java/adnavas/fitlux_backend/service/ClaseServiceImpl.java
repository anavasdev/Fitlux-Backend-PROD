package adnavas.fitlux_backend.service;

import adnavas.fitlux_backend.entity.Clase;
import adnavas.fitlux_backend.entity.Usuario;
import adnavas.fitlux_backend.entity.UsuarioProjection;
import adnavas.fitlux_backend.repository.ClaseRepository;
import adnavas.fitlux_backend.repository.UsuarioRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClaseServiceImpl implements ClaseService {

    @Autowired
    ClaseRepository claseRepository;

    @Autowired
    UsuarioRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Clase> findAll() {
        return claseRepository.findAll();
    }

    @Override
    public Clase findById(ObjectId id) {
        return claseRepository.findBy_id(id);
    }

    @Override
    @Transactional
    public Clase addClase(Clase clase) {
        return claseRepository.save(clase);
    }

    @Override
    public Clase deleteClase(ObjectId id) {
        Clase claseDelete = claseRepository.findBy_id(id);
        claseRepository.delete(claseDelete);
        return claseDelete;
    }

    @Override
    public Clase updateClase(ObjectId id, Clase clase) {
        Clase claseUpdate = claseRepository.findBy_id(id);
        claseUpdate.setSala(clase.getSala());
        claseUpdate.setActividad(clase.getActividad());
        claseUpdate.setFechainicio(clase.getFechainicio());
        claseUpdate.setFechafin(clase.getFechafin());
        claseUpdate.setProfesor(clase.getProfesor());
        claseUpdate.setUsuarios(clase.getUsuarios());
        claseUpdate.setActiva(clase.isActiva());

        claseRepository.save(claseUpdate);

        return claseUpdate;
    }

    @Override
    public List<Clase> findClasesByActividad_id(ObjectId actividadId) {
        return claseRepository.findClasesByActividad_id(actividadId);
    }

    @Override
    public List<Clase> findClasesByUsuario(ObjectId usuarioId) {
        return claseRepository.findClasesByUsuario(usuarioId);
    }

    @Override
    public boolean registrarUsuario(ObjectId claseId, ObjectId userId) {
        try {
            Clase clase = findById(claseId);
            Usuario usuarioARegistrar = userRepository.findBy_id(userId);
            if (!clase.getUsuarios().contains(usuarioARegistrar)){
                clase.getUsuarios().add(usuarioARegistrar);
                claseRepository.save(clase);
                return true;
            }else{
                return false;
            }

        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean eliminarRegistroUsuario(ObjectId claseId, ObjectId userId) {
        try {
            Clase clase = findById(claseId);
            Usuario usuarioAEliminar = userRepository.findBy_id(userId);
            if (clase.getUsuarios().contains(usuarioAEliminar)){
                clase.getUsuarios().remove(usuarioAEliminar);
                claseRepository.save(clase);
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }


}
