import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String login;
    private String senha;
    private List<String> contatos;
    private List<String> favoritos;
    // private Map<String, List<String>> historicoMensagens;
    private Map<String, List<Mensagem>> historicoMensagens;

    public Usuario(int id, String login, String senha) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.contatos = new ArrayList<>();
        this.favoritos = new ArrayList<>();
        this.historicoMensagens = new HashMap<>();
    }

    public int getId() { return id; }
    public String getLogin() { return login; }
    public String getSenha() { return senha; }
    public List<String> getContatos() { return contatos; }
    public List<String> getFavoritos() { return favoritos; }

    public List<Mensagem> getMensagens(String contato) { return historicoMensagens.getOrDefault(contato, new ArrayList<>()); }

    public void adicionarContato(String contato) {
        if (!contatos.contains(contato)) {
            contatos.add(contato);
        }
    }

    public void removerContato(String contato) {
        if (contatos.contains(contato)) {
            contatos.remove(contato);
        }
    }

    public void adicionarFavorito(String contato) {
        if (contatos.contains(contato) && !favoritos.contains(contato)) {
            favoritos.add(contato);
        }
    }

    public void adicionarMensagem(String contato, String conteudo, String remetente) {
        historicoMensagens.putIfAbsent(contato, new ArrayList<>());
        historicoMensagens.get(contato).add(new Mensagem(conteudo, remetente));
    }
    
}