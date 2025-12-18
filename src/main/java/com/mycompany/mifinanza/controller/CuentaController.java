package com.mycompany.mifinanza.controller;

import com.mycompany.mifinanza.entity.Cuenta;
import com.mycompany.mifinanza.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/MiFinanza/cuenta")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    // Obtener todos los registros
    @GetMapping
    public ResponseEntity<List<Cuenta>> getAllCuentas() {
        try {
            List<Cuenta> cuentas = cuentaService.findAll();
            if (cuentas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cuentas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Insertar un registro
    @PostMapping
    public ResponseEntity<Cuenta> createCuenta(@RequestBody Cuenta cuenta) {
        try {
            Cuenta _cuenta = cuentaService.save(cuenta);
            return new ResponseEntity<>(_cuenta, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar un registro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> getCuentaById(@PathVariable("id") Integer id) {
        Optional<Cuenta> cuentaData = cuentaService.findById(id);

        if (cuentaData.isPresent()) {
            return new ResponseEntity<>(cuentaData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar un registro
    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> updateCuenta(@PathVariable("id") Integer id, @RequestBody Cuenta cuenta) {
        Cuenta updatedCuenta = cuentaService.update(id, cuenta);
        if (updatedCuenta != null) {
            return new ResponseEntity<>(updatedCuenta, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar un registro
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCuenta(@PathVariable("id") Integer id) {
        try {
            if (cuentaService.delete(id)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
