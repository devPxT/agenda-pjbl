import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String login;
    private String senha;
    private List<String> contatos;
    private List<String> favoritos;

    public Usuario(int id, String login, String senha) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.contatos = new ArrayList<>();
        this.favoritos = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getLogin() { return login; }
    public String getSenha() { return senha; }
    public List<String> getContatos() { return contatos; }
    public List<String> getFavoritos() { return favoritos; }

    public void adicionarContato(String contato) {
        if (!contatos.contains(contato)) {
            contatos.add(contato);
        }
    }

    public void adicionarFavorito(String contato) {
        if (contatos.contains(contato) && !favoritos.contains(contato)) {
            favoritos.add(contato);
        }
    }
}
