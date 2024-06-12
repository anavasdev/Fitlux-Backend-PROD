package adnavas.fitlux_backend.controllers;

import adnavas.fitlux_backend.entity.Actividad;
import adnavas.fitlux_backend.service.ActividadService;
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
@RequestMapping("/api/actividades")
public class ActividadController {
    @Autowired
    ActividadService actividadService;

    @Operation(summary = "OBTENER todas las actividades", description = "Esta ruta devuelve todas las actividades de la aplicación")
    @GetMapping
    public List<Actividad> list() {
        return actividadService.findAll();
    }

    @Operation(summary = "OBTENER la actividad a través de su ID", description = "Esta ruta devuelve la actividad de la aplicación cuya ID le pasamos como parámetro")
    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable String id) {
        ObjectId objectId = new ObjectId(id);
        Actividad actividadFound = actividadService.findById(objectId);
        if (actividadFound != null) {
            return ResponseEntity.ok(actividadFound);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Actividad no encontrada con ID: " + id);
    }

    @Operation(summary = "CREAR una actividad", description = "Esta ruta hace que puedas añadir una actividad a la aplicación pasándosela como parámetro estableciendole lo que desee el administrador")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Actividad actividad, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        Actividad actividadCreate = actividadService.addActividad(actividad);
        if (actividadCreate != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Actividad creada correctamente");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha podido crear la actividad");
        }
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @Operation(summary = "ACTUALIZAR una actividad", description = "Esta ruta hace que puedas actualizar una actividad de la aplicación pasándole el ID y el objeto nuevo")
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @Valid @RequestBody Actividad actividad) {
        ObjectId objectId = new ObjectId(id);
        Actividad actividadUpdate = actividadService.updateActividad(objectId, actividad);
        if (actividadUpdate != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Actividad actualizada correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha podido encontrar la actividad a actualizar");
    }

    @Operation(summary = "BORRAR una actividad", description = "Esta ruta hace que puedas borrar una actividad de la aplicación pasándole su ID como parámetro")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        ObjectId objectId = new ObjectId(id);
        Actividad actividadDelete = actividadService.deleteActividad(objectId);
        if (actividadDelete != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Actividad borrada correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha podido encontrar la actividad a borrar");

    }
}
