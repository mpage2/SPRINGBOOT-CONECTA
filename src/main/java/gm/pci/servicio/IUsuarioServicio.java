package gm.pci.servicio;

import gm.pci.modelo.Usuario;

import java.util.List;

public interface IUsuarioServicio {

    public List<Usuario> listarUsuarios();

    public Usuario buscarUsuarioPorId(Integer UsuarioID);

    public Usuario guardarUsuario(Usuario usuario);

    public void eliminarUsuario(Usuario usuario);

    Usuario buscarUsuarioPorNombreUsuario(String usuario);

}
