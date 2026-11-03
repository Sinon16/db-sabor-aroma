package com.example.db_sabor_y_aroma.service;

import com.example.db_sabor_y_aroma.model.DetalleOrden;
import com.example.db_sabor_y_aroma.repository.DetalleOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleOrdenService {

    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;

    public List<DetalleOrden> obtenerTodos() {
        return detalleOrdenRepository.findAll();
    }

    public Optional<DetalleOrden> obtenerPorId(Long id) {
        return detalleOrdenRepository.findById(id);
    }

    public List<DetalleOrden> obtenerPorOrdenId(Long ordenId) {
        return detalleOrdenRepository.findByOrdenId(ordenId);
    }

    public DetalleOrden guardar(DetalleOrden detalleOrden) {
        return detalleOrdenRepository.save(detalleOrden);
    }

    public void eliminar(Long id) {
        detalleOrdenRepository.deleteById(id);
    }
}