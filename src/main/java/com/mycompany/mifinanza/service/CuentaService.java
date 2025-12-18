package com.mycompany.mifinanza.service;

import com.mycompany.mifinanza.entity.Cuenta;
import com.mycompany.mifinanza.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Cuenta> findAll() {
        return cuentaRepository.findAll();
    }

    public Optional<Cuenta> findById(Integer id) {
        return cuentaRepository.findById(id);
    }

    public Cuenta save(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public Cuenta update(Integer id, Cuenta cuentaDetails) {
        Optional<Cuenta> optionalCuenta = cuentaRepository.findById(id);
        if (optionalCuenta.isPresent()) {
            Cuenta cuenta = optionalCuenta.get();
            cuenta.setNombre(cuentaDetails.getNombre());
            cuenta.setSaldo(cuentaDetails.getSaldo());
            cuenta.setIdUsuario(cuentaDetails.getIdUsuario());
            return cuentaRepository.save(cuenta);
        }
        return null;
    }

    public boolean delete(Integer id) {
        if (cuentaRepository.existsById(id)) {
            cuentaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
