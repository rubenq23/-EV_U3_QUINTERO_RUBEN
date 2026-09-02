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
}
