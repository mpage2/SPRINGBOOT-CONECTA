package gm.pci.servicio;

import gm.pci.modelo.Usuario;
import gm.pci.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServicio implements  IUsuarioServicio{

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }

    @Override
    public Usuario buscarUsuarioPorId(Integer UsuarioID) {
        Usuario usuario = usuarioRepositorio.findById(UsuarioID).orElse(null);
        return usuario;
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        usuario.generarToken(); // Genera el token al guardar el usuario
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public void eliminarUsuario(Usuario usuario) {
        usuarioRepositorio.delete(usuario);
    }
    public Usuario buscarUsuarioPorNombreUsuario(String usuario) {
        return usuarioRepositorio.findByUsuario(usuario);
    }
}