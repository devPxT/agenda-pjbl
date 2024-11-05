import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    private static final String FILE_NAME = "usuarios.ser";
    private List<Usuario> usuarios = new ArrayList<>();
    private int ultimoId = 0;

    public UsuarioService() {
        carregarUsuarios();
    }

    public void salvarUsuarios() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarUsuarios() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            usuarios = (List<Usuario>) ois.readObject();
            ultimoId = usuarios.stream().mapToInt(Usuario::getId).max().orElse(0);
        } catch (IOException | ClassNotFoundException e) {
            usuarios = new ArrayList<>();
        }
    }

    public boolean cadastrarUsuario(String login, String senha) {
        if (verificarLoginExistente(login)) {
            return false;
        }
        Usuario usuario = new Usuario(++ultimoId, login, senha);
        usuarios.add(usuario);
        return true;
    }

    public Usuario buscarUsuario(String login) {
        for (Usuario usuario : usuarios) {
            if (usuario.getLogin().equals(login)) {
                return usuario;
            }
        }
        return null;
    }

    private boolean verificarLoginExistente(String login) {
        return usuarios.stream().anyMatch(u -> u.getLogin().equals(login));
    }

    public Usuario login(String login, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)) {
                return usuario;
            }
        }
        return null;
    }
    
        public boolean adicionarContato(Usuario usuario, String contato) {
            if (!usuario.getContatos().contains(contato)) {
                usuario.getContatos().add(contato);
                return true;
            }
            return false;
        }

        public boolean removerContato(Usuario usuario, String contato) {
            if (usuario.getContatos().contains(contato)) {
                usuario.getContatos().remove(contato);
                return true;
            }
            return false;
        }

    public List<String> listarContatos(Usuario usuario) {
        return usuario.getContatos();
    }

    public boolean favoritarContato(Usuario usuario, String contato) {
        if (usuario.getContatos().contains(contato)) {
            usuario.adicionarFavorito(contato);
            return true;
        } else {
            System.out.println("Erro: Contato não encontrado.");
            return false;
        }
    }

    public List<Mensagem> obterHistoricoMensagens(Usuario usuario, String contatoNome) {
        if (usuario.getContatos().contains(contatoNome)) {
            return usuario.getMensagens(contatoNome);
        } else {
            System.out.println("Erro: Contato não encontrado.");
            return new ArrayList<>();
        }
    }

    public boolean enviarMensagem(Usuario usuario, String contatoNome, String mensagem) {
        Usuario contato = buscarUsuario(contatoNome);
        if (contato != null) {
            usuario.adicionarMensagem(contatoNome, mensagem, usuario.getLogin());
            contato.adicionarMensagem(usuario.getLogin(), mensagem, usuario.getLogin()); //os dois contatos ter a mesma mensagem
            return true;
        }
        return false;
    }
}
