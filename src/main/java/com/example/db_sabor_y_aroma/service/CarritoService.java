package com.saboraroma.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saboraroma.model.Carrito;
import com.saboraroma.model.Producto;
import com.saboraroma.model.Usuario;
import com.saboraroma.repository.CarritoRepository;
import com.saboraroma.repository.ProductoRepository;
import com.saboraroma.repository.UsuarioRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    public CarritoService(CarritoRepository carritoRepository, 
                          UsuarioRepository usuarioRepository, 
                          ProductoRepository productoRepository) {
        this.carritoRepository = carritoRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
    }

    public List<Carrito> findByUsuario(Integer usuarioId) {
        return carritoRepository.findByUsuarioId(usuarioId);
    }

    public Carrito addProducto(Integer usuarioId, Integer productoId, Integer cantidad) {
        int cantidadFinal = (cantidad != null && cantidad > 0) ? cantidad : 1;

        Optional<Carrito> existente = carritoRepository.findByUsuarioIdAndProductoId(usuarioId, productoId);

        if (existente.isPresent()) {
            Carrito item = existente.get();
            item.setCantidad(item.getCantidad() + cantidadFinal);
            return carritoRepository.save(item);
        } else {
            Usuario usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
            Producto producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

            Carrito nuevo = new Carrito();
            nuevo.setUsuario(usuario);
            nuevo.setProducto(producto);
            nuevo.setCantidad(cantidadFinal);
            return carritoRepository.save(nuevo);
        }
    }

    public Optional<Carrito> updateCantidad(Integer id, Integer usuarioId, Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad debe ser mayor a 0.");
        }

        Optional<Carrito> itemOpt = (usuarioId != null) 
                ? carritoRepository.findByUsuarioIdAndProductoId(usuarioId, id).or(() -> carritoRepository.findById(id)) 
                : carritoRepository.findById(id);

        return itemOpt.map(item -> {
            item.setCantidad(cantidad);
            return carritoRepository.save(item);
        });
    }

    public boolean deleteItem(Integer id, Integer usuarioId) {
        if (usuarioId != null) {
            Optional<Carrito> item = carritoRepository.findByUsuarioIdAndProductoId(usuarioId, id);
            if (item.isPresent()) {
                carritoRepository.delete(item.get());
                return true;
            }
        }

        if (carritoRepository.existsById(id)) {
            carritoRepository.deleteById(id);
            return true;
        }

        return false;
    }

    public boolean vaciarCarrito(Integer usuarioId) {
        if (usuarioId == null) {
            return false;
        }
        carritoRepository.deleteByUsuarioId(usuarioId);
        return true;
    }
}