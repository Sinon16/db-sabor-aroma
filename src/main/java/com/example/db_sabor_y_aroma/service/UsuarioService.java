
package com.example.db_sabor_y_aroma.service;

import com.example.db_sabor_y_aroma.model.Usuario;
import com.example.db_sabor_y_aroma.model.Reporte;
import com.example.db_sabor_y_aroma.repository.UsuarioRepository;
import com.example.db_sabor_y_aroma.repository.ReporteRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ReporteRepository reporteRepository;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            ReporteRepository reporteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.reporteRepository = reporteRepository;
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuario> login(String email, String password) {
        return usuarioRepository.findByEmailAndPassword(email, password);
    }

    public Usuario guardar(Usuario usuario) {
        boolean esNuevo = usuario.getId() == null;

        Boolean rolAnterior = null;

        if (!esNuevo) {
            Optional<Usuario> anterior =
                    usuarioRepository.findById(usuario.getId());

            if (anterior.isPresent()) {
                rolAnterior = anterior.get().getEsadmin();
            }
        }

        Usuario guardado = usuarioRepository.save(usuario);

        Reporte reporte = new Reporte();
        reporte.setTipoReporte("Usuario");
        reporte.setFecha(LocalDateTime.now());

        if (esNuevo) {
            reporte.setEspecialidad("Nuevo");
            reporte.setDescripcion(
                "Se registró el usuario " +
                guardado.getNombre() +
                " (ID: " + guardado.getId() + ")"
            );
        } else {
            reporte.setEspecialidad("Editar");

            boolean cambioRol = rolAnterior != null
                && !rolAnterior.equals(guardado.getEsadmin());

            if (cambioRol) {
                String rolNuevo =
                    Boolean.TRUE.equals(guardado.getEsadmin())
                    ? "Administrador"
                    : "Cliente";

                reporte.setDescripcion(
                    "Se cambió el rol del usuario " +
                    guardado.getNombre() +
                    " a " + rolNuevo
                );
            } else {
                reporte.setDescripcion(
                    "Se actualizaron los datos del usuario " +
                    guardado.getNombre() +
                    " (ID: " + guardado.getId() + ")"
                );
            }
        }

        reporteRepository.save(reporte);

        return guardado;
    }

    public void eliminar(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() ->
                new IllegalArgumentException("Usuario no encontrado")
            );

        String nombre = usuario.getNombre();

        usuarioRepository.delete(usuario);
        usuarioRepository.flush();

        Reporte reporte = new Reporte();
        reporte.setTipoReporte("Usuario");
        reporte.setEspecialidad("Eliminar");
        reporte.setDescripcion(
            "Se eliminó el usuario " + nombre + " (ID: " + id + ")"
        );
        reporte.setFecha(LocalDateTime.now());

        reporteRepository.save(reporte);
    }
}
