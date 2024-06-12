package adnavas.fitlux_backend.controllers;

import adnavas.fitlux_backend.entity.Clase;
import adnavas.fitlux_backend.entity.Sala;
import adnavas.fitlux_backend.service.SalaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/salas")
public class SalaController {
    @Autowired
    SalaService salaService;

    @Operation(summary = "OBTENER todas las salas", description = "Esta ruta devuelve todas las salas de la aplicación")
    @GetMapping
    public List<Sala> list() {
        return salaService.findAll();
    }

    @Operation(summary = "OBTENER la sala a través de su ID", description = "Esta ruta devuelve la sala de la aplicación cuyo ID le pasamos como parámetro")
    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable String id){
        ObjectId objectId = new ObjectId(id);
        Sala salaFound = salaService.findById(objectId);
        if(salaFound != null){
            return ResponseEntity.ok(salaFound);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sala no encontrada con ID: " + id);
    }

    @Operation(summary = "CREAR una sala", description = "Esta ruta hace que puedas añadir una sala a la aplicación pasándosela como parámetro estableciendole lo que desee el administrador")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Sala sala, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        Sala salaCreate = salaService.addSala(sala);
        if (salaCreate != null){
            return ResponseEntity.status(HttpStatus.CREATED).body("Sala creada correctamente");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha podido crear la sala");
        }
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> errors.put(err.getField(),err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @Operation(summary = "ACTUALIZAR una sala", description = "Esta ruta hace que puedas actualizar una sala de la aplicación pasándole el ID y el objeto nuevo")
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @Valid @RequestBody Sala sala){
        ObjectId objectId = new ObjectId(id);
        Sala salaUpdate = salaService.updateSala(objectId, sala);
        if(salaUpdate != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Sala actualizada correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha podido encontrar la sala a actualizar");
    }

    @Operation(summary = "BORRAR una sala", description = "Esta ruta hace que puedas borrar una sala de la aplicación pasándole su ID como parámetro")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        ObjectId objectId = new ObjectId(id);
        Sala salaDelete = salaService.deleteSala(objectId);
        if(salaDelete != null){
            return ResponseEntity.status(HttpStatus.OK).body("Sala borrada correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha podido encontrar la sala a borrar");
    }
}
