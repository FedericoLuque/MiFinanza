package com.mycompany.mifinanza.service;

import com.mycompany.mifinanza.entity.Presupuesto;
import com.mycompany.mifinanza.repository.PresupuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PresupuestoService {

    @Autowired
    private PresupuestoRepository presupuestoRepository;

    public List<Presupuesto> findAll() {
        return presupuestoRepository.findAll();
    }

    public Optional<Presupuesto> findById(Integer id) {
        return presupuestoRepository.findById(id);
    }

    public Presupuesto save(Presupuesto presupuesto) {
        return presupuestoRepository.save(presupuesto);
    }

    public Presupuesto update(Integer id, Presupuesto presupuestoDetails) {
        Optional<Presupuesto> optionalPresupuesto = presupuestoRepository.findById(id);
        if (optionalPresupuesto.isPresent()) {
            Presupuesto presupuesto = optionalPresupuesto.get();
            presupuesto.setMontoTotal(presupuestoDetails.getMontoTotal());
            presupuesto.setMontoActual(presupuestoDetails.getMontoActual());
            presupuesto.setFechaInicio(presupuestoDetails.getFechaInicio());
            presupuesto.setFechaFin(presupuestoDetails.getFechaFin());
            presupuesto.setIdCategoria(presupuestoDetails.getIdCategoria());
            presupuesto.setIdUsuario(presupuestoDetails.getIdUsuario());
            return presupuestoRepository.save(presupuesto);
        }
        return null;
    }

    public boolean delete(Integer id) {
        if (presupuestoRepository.existsById(id)) {
            presupuestoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
