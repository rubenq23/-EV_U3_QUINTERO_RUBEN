package ipss.TechSolutions.controller;

import ipss.TechSolutions.model.Proyecto;
import ipss.TechSolutions.repository.ProyectoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.util.Optional;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {
    private final ProyectoRepository proyectoRepository;

    public ProyectoController(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    // Ruta para obtener todos los proyectos (GET)
    @GetMapping
    public ResponseEntity<List<Proyecto>> listarProyectos() {
        List<Proyecto> proyectos = proyectoRepository.findAll();
        return ResponseEntity.ok(proyectos);
    }

    // Ruta para obtener un proyecto específico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtenerProyectoPorId(@PathVariable Long id) {
        Optional<Proyecto> proyectoOpt = proyectoRepository.findById(id);

        if (proyectoOpt.isEmpty()) {
            return ResponseEntity.notFound().build(); // Retorna 404 si no existe
        }

        return ResponseEntity.ok(proyectoOpt.get()); // Retorna 200 con el proyecto
    }

    // Ruta para crear un proyecto base
    @PostMapping
    public ResponseEntity crearProyecto(@RequestBody Proyecto proyecto) {
        Proyecto guardado = (Proyecto) proyectoRepository.save(proyecto);
        return ResponseEntity.ok(guardado);
    }

    // Ruta para actualizar un proyecto existente
    @PutMapping("/{id}")
    public ResponseEntity actualizarProyecto(@PathVariable Long id, @RequestBody Proyecto datosActualizados) {
        Optional proyectoOpt = proyectoRepository.findById(id);

        if (proyectoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Proyecto proyectoExistente = (Proyecto) proyectoOpt.get();

        // Actualizamos los campos requeridos
        proyectoExistente.setNombre(datosActualizados.getNombre());
        proyectoExistente.setFechaInicio(datosActualizados.getFechaInicio());
        proyectoExistente.setEstado(datosActualizados.getEstado());
        proyectoExistente.setResponsable(datosActualizados.getResponsable());
        proyectoExistente.setMonto(datosActualizados.getMonto());
        proyectoExistente.setCreatedBy(datosActualizados.getCreatedBy());

        Proyecto actualizado = (Proyecto) proyectoRepository.save(proyectoExistente);
        return ResponseEntity.ok(actualizado);
    }

    // Ruta para actualizar parcialmente un proyecto
    @PatchMapping("/{id}")
    public ResponseEntity<Proyecto> actualizarProyectoParcial(@PathVariable Long id, @RequestBody Proyecto datosActualizados) {
        Optional<Proyecto> proyectoOpt = proyectoRepository.findById(id);

        if (proyectoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Proyecto proyectoExistente = proyectoOpt.get();

        // Validamos uno por uno: si no es null, lo actualizamos
        if (datosActualizados.getNombre() != null) {
            proyectoExistente.setNombre(datosActualizados.getNombre());
        }
        if (datosActualizados.getFechaInicio() != null) {
            proyectoExistente.setFechaInicio(datosActualizados.getFechaInicio());
        }
        if (datosActualizados.getEstado() != null) {
            proyectoExistente.setEstado(datosActualizados.getEstado());
        }
        if (datosActualizados.getResponsable() != null) {
            proyectoExistente.setResponsable(datosActualizados.getResponsable());
        }
        if (datosActualizados.getMonto() != null) {
            proyectoExistente.setMonto(datosActualizados.getMonto());
        }
        if (datosActualizados.getCreatedBy() != null) {
            proyectoExistente.setCreatedBy(datosActualizados.getCreatedBy());
        }

        Proyecto actualizado = proyectoRepository.save(proyectoExistente);
        return ResponseEntity.ok(actualizado);
    }

    // Ruta para eliminar un proyecto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        if (!proyectoRepository.existsById(id)) {
            return ResponseEntity.notFound().build(); // Retorna 404 si no existe
        }

        proyectoRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // Retorna 204 indicando éxito sin cuerpo
    }

}
