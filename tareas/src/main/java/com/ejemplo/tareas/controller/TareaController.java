package com.ejemplo.tareas.controller;

import com.ejemplo.tareas.model.Tarea;
import com.ejemplo.tareas.repository.TareaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST que gestiona las operaciones CRUD sobre las tareas.
 */
@RestController
@RequestMapping("/api/tareas")
@CrossOrigin("*")
public class TareaController {

    @Autowired
    private TareaRepository tareaRepository;

    private final TareaRepository repository;

    /**
     * Constructor que inyecta el repositorio de tareas.
     *
     * @param repository el repositorio de tareas.
     */
    public TareaController(TareaRepository repository) {
        this.repository = repository;
    }

    /**
     * Obtiene todas las tareas.
     *
     * @return lista de todas las tareas.
     */
    @GetMapping
    public List<Tarea> getAll() {
        return repository.findAll();
    }

    /**
     * Crea una nueva tarea.
     *
     * @param tarea la tarea a crear.
     * @return la tarea guardada.
     */
    @PostMapping
    public Tarea create(@RequestBody Tarea tarea) {
        return repository.save(tarea);
    }

    /**
     * Actualiza una tarea existente.
     *
     * @param id ID de la tarea a actualizar.
     * @param tarea objeto con los datos actualizados.
     * @return la tarea actualizada.
     */
    @PutMapping("/{id}")
    public Tarea update(@PathVariable Long id, @RequestBody Tarea tarea) {
        tarea.setId(id);
        return repository.save(tarea);
    }

    /**
     * Elimina una tarea por ID.
     *
     * @param id ID de la tarea a eliminar.
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    /**
     * Actualiza el estado de completada de una tarea.
     *
     * @param id ID de la tarea.
     * @param completada nuevo valor del campo completada.
     * @return respuesta HTTP con la tarea actualizada o 404 si no existe.
     */
    @PutMapping("/{id}/completada")
    public ResponseEntity<Tarea> actualizarEstadoCompletada(@PathVariable Long id, @RequestBody boolean completada) {
        Optional<Tarea> tareaOpt = tareaRepository.findById(id);
        if (tareaOpt.isPresent()) {
            Tarea tarea = tareaOpt.get();
            tarea.setCompletada(completada);
            tareaRepository.save(tarea);
            return ResponseEntity.ok(tarea);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
