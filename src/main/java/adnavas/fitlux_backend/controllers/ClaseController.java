package adnavas.fitlux_backend.controllers;

import adnavas.fitlux_backend.entity.Clase;
import adnavas.fitlux_backend.entity.Usuario;
import adnavas.fitlux_backend.repository.UsuarioRepository;
import adnavas.fitlux_backend.service.ClaseService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clases")
public class ClaseController {
    @Autowired
    ClaseService claseService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Operation(summary = "OBTENER todas las clases", description = "Esta ruta devuelve todas las clases de la aplicación")
    @GetMapping
    public List<Clase> list() {
        return claseService.findAll();
    }

    @Operation(summary = "OBTENER todos los usuarios de una clase con ID", description = "Esta ruta devuelve todas las clases de la aplicación")
    @GetMapping("/usuarios/{idClase}")
    public ResponseEntity<?> listClassUsers(@PathVariable String idClase) {
        ObjectId objectId = new ObjectId(idClase);
        Clase claseFound = claseService.findById(objectId);
        if (claseFound != null) {
            List<Usuario> usuarios = claseFound.getUsuarios();
            return ResponseEntity.ok(usuarios);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Clase no encontrada con ID: " + idClase);
        }
    }

    @Operation(summary = "OBTENER todas las clases de una actividad a raiz de su ID", description = "Esta ruta devuelve todas las clases de la aplicación cuya actividad se le pasa como parametro")
    @GetMapping("/actividad/{idActividad}")
    public ResponseEntity<?> listClasesByActividad(@PathVariable String idActividad) {
        ObjectId objectId = new ObjectId(idActividad);
        List<Clase> clasesActividad = claseService.findClasesByActividad_id(objectId);
        if (clasesActividad != null) {
            return ResponseEntity.ok(clasesActividad);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Clases no encontradas con deporte ID: " + idActividad);
        }
    }
    @Operation(summary = "OBTENER todas las clases en las que está inscrito un usuario a raiz de su ID", description = "Esta ruta devuelve todas las clases de la aplicación cuyo usuario inscrito se le pasa como parametro")
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> listClasesByUsuario(@PathVariable String idUsuario) {
        ObjectId objectId = new ObjectId(idUsuario);
        List<Clase> clasesUsuario = claseService.findClasesByUsuario(objectId);
        if (clasesUsuario != null) {
            return ResponseEntity.ok(clasesUsuario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Clases no encontradas con usuario ID: " + idUsuario);
        }
    }

    @Operation(summary = "OBTENER la clase a través de su ID", description = "Esta ruta devuelve la clase de la aplicación cuyo ID le pasamos como parámetro")
    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable String id) {
        ObjectId objectId = new ObjectId(id);
        Clase claseFound = claseService.findById(objectId);
        if (claseFound != null) {
            return ResponseEntity.ok(claseFound);
        }
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Clase no encontrada con ID: " + id);
    }

    @Operation(summary = "CREAR una clase", description = "Esta ruta hace que puedas añadir una clase a la aplicación pasándosela como parámetro estableciendole lo que desee el administrador")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Clase clase, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        Clase claseCreate = claseService.addClase(clase);
        if (claseCreate != null){
            return ResponseEntity.status(HttpStatus.CREATED).body("Clase creada correctamente");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha podido crear la clase");
        }
    }

    @Operation(summary = "REGISTRA un usuario en una clase", description = "Esta ruta hace que puedas apuntar a un usuario a una clase")
    @PostMapping("/register/{idClase}")
    public ResponseEntity<String> register(@PathVariable String idClase) {
        ObjectId objectIdClase = new ObjectId(idClase);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();             //obtengo el usuario mediante la autenticacion de la aplicacion
        Usuario currentUser = usuarioRepository.findByUsername((String)authentication.getPrincipal());
        ObjectId objectIdUser = currentUser.get_id();

        boolean allGood = claseService.registrarUsuario(objectIdClase, objectIdUser);
        if (allGood) {
            return ResponseEntity.status(HttpStatus.OK).body("Usuario registrado en la clase correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha podido registrar al usuario en la clase");

    }

    @Operation(summary = "DESAPUNTA un usuario de una clase", description = "Esta ruta hace que puedas desapuntar a un usuario de una clase")
    @PostMapping("/unregister/{idClase}")
    public ResponseEntity<String> unregister(@PathVariable String idClase) {
        ObjectId objectIdClase = new ObjectId(idClase);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();             //obtengo el usuario mediante la autenticacion de la aplicacion
        Usuario currentUser = usuarioRepository.findByUsername((String)authentication.getPrincipal());
        ObjectId objectIdUser = currentUser.get_id();

        boolean allGood = claseService.eliminarRegistroUsuario(objectIdClase, objectIdUser);
        if (allGood) {
            return ResponseEntity.status(HttpStatus.OK).body("Usuario desapuntado de la clase correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha podido desapuntar al usuario de la clase");

    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @Operation(summary = "ACTUALIZAR una clase", description = "Esta ruta hace que puedas actualizar una clase de la aplicación pasándole el ID y el objeto nuevo")
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @Valid @RequestBody Clase clase) {
        ObjectId objectId = new ObjectId(id);
        Clase claseUpdate = claseService.updateClase(objectId, clase);
        if (claseUpdate != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Clase actualizada correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha podido encontrar la clase a actualizar");
    }

    @Operation(summary = "BORRAR una clase", description = "Esta ruta hace que puedas borrar una clase de la aplicación pasándole su ID como parámetro")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        ObjectId objectId = new ObjectId(id);
        Clase claseDelete = claseService.deleteClase(objectId);
        if (claseDelete != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Clase borrada correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha podido encontrar la clase a borrar");

    }
}
