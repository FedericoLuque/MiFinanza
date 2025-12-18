package com.mycompany.mifinanza.controller;

import com.mycompany.mifinanza.entity.Presupuesto;
import com.mycompany.mifinanza.service.PresupuestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/MiFinanza/presupuesto")
public class PresupuestoController {

    @Autowired
    private PresupuestoService presupuestoService;

    // Obtener todos los registros
    @GetMapping
    public ResponseEntity<List<Presupuesto>> getAllPresupuestos() {
        try {
            List<Presupuesto> presupuestos = presupuestoService.findAll();
            if (presupuestos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(presupuestos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Insertar un registro
    @PostMapping
    public ResponseEntity<Presupuesto> createPresupuesto(@RequestBody Presupuesto presupuesto) {
        try {
            Presupuesto _presupuesto = presupuestoService.save(presupuesto);
            return new ResponseEntity<>(_presupuesto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar un registro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Presupuesto> getPresupuestoById(@PathVariable("id") Integer id) {
        Optional<Presupuesto> presupuestoData = presupuestoService.findById(id);

        if (presupuestoData.isPresent()) {
            return new ResponseEntity<>(presupuestoData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar un registro
    @PutMapping("/{id}")
    public ResponseEntity<Presupuesto> updatePresupuesto(@PathVariable("id") Integer id, @RequestBody Presupuesto presupuesto) {
        Presupuesto updatedPresupuesto = presupuestoService.update(id, presupuesto);
        if (updatedPresupuesto != null) {
            return new ResponseEntity<>(updatedPresupuesto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar un registro
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePresupuesto(@PathVariable("id") Integer id) {
        try {
            if (presupuestoService.delete(id)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
