package com.example.db_sabor_y_aroma.service;

import com.example.db_sabor_y_aroma.model.*;
import com.example.db_sabor_y_aroma.repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ReporteRepository reporteRepository;

    public ProductoService(ProductoRepository productoRepository, ReporteRepository reporteRepository) {
        this.productoRepository = productoRepository;
        this.reporteRepository = reporteRepository;
    }

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public List<Producto> findAllAdmin() {
        return productoRepository.findAllByOrderByIdDesc();
    }

    public Optional<Producto> findById(Integer id) {
        return productoRepository.findById(id);
    }

    public List<Producto> findByCategoria(String categoria) {
        return productoRepository.findByCategoriaNombreOrId(categoria);
    }

    public Producto create(Producto producto) {
        validarCamposProducto(producto);

        if (producto.getImagen() == null || producto.getImagen().trim().isEmpty()) {
            producto.setImagen("img/hamburguesa-index.webp");
        }

        producto.setId(null);
        Producto creado = productoRepository.save(producto);

        // Registro de historial / reporte
        Reporte reporte = new Reporte();
        reporte.setTipoReporte("Producto");
        reporte.setEspecialidad("Nuevo");
        reporte.setDescripcion("Se creó el producto " + creado.getNombre() + " (" + creado.getCodigo() + ")");
        reporte.setFecha(LocalDateTime.now());
        reporteRepository.save(reporte);

        return creado;
    }

    public Optional<Producto> update(Integer id, Producto producto) {
        validarCamposProducto(producto);

        return productoRepository.findById(id).map(existing -> {
            existing.setCodigo(producto.getCodigo());
            existing.setNombre(producto.getNombre());
            existing.setDescripcion(producto.getDescripcion());
            existing.setPrecio(producto.getPrecio());
            existing.setStock(producto.getStock());
            if (producto.getImagen() != null && !producto.getImagen().trim().isEmpty()) {
                existing.setImagen(producto.getImagen());
            }
            existing.setCategoria(producto.getCategoria());
            existing.setOferta(producto.getOferta());

            Producto actualizado = productoRepository.save(existing);

            // Registro de historial / reporte
            Reporte reporte = new Reporte();
            reporte.setTipoReporte("Producto");
            reporte.setEspecialidad("Editar");
            reporte.setDescripcion("Se actualizó stock o info de " + actualizado.getNombre() + " (" + actualizado.getCodigo() + ")");
            reporte.setFecha(LocalDateTime.now());
            reporteRepository.save(reporte);

            return actualizado;
        });
    }

    public boolean delete(Integer id) {
        if (!productoRepository.existsById(id)) {
            return false;
        }
        productoRepository.deleteById(id);

        // Registro de historial / reporte
        Reporte reporte = new Reporte();
        reporte.setTipoReporte("Producto");
        reporte.setEspecialidad("Eliminar");
        reporte.setDescripcion("Se eliminó el producto con ID: " + id + " del catálogo");
        reporte.setFecha(LocalDateTime.now());
        reporteRepository.save(reporte);

        return true;
    }

    private void validarCamposProducto(Producto producto) {
        if (producto.getCategoria() == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "El producto debe tener asignada una categoría válida."
            );
        }
    }
}