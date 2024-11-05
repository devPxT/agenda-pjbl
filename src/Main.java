import java.util.List;
import java.util.Scanner;

public class Main {

    private static UsuarioService usuarioService = new UsuarioService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Usuario usuarioLogado = null;

        while (true) {
            if (usuarioLogado == null) {
                System.out.println("\n--- Cadastro e Login ---");
                System.out.println("1. Cadastrar novo usuário");
                System.out.println("2. Fazer login");
                System.out.println("3. Sair");
        
                int opcao = obterOpcao(scanner);
        
                switch (opcao) {
                    case 1:
                        cadastrarUsuario(scanner);
                        break;
                    case 2:
                        usuarioLogado = loginUsuario(scanner);
                        break;
                    case 3:
                        usuarioService.salvarUsuarios();
                        System.out.println("Saindo... Dados salvos com sucesso.");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Opção inválida, tente novamente.");
                }
            } else {
                System.out.println("\n--- Agenda ---");
                System.out.println("1. Ver contatos");
                System.out.println("2. Ver favoritos");
                System.out.println("3. Favoritar contato");
                System.out.println("4. Remover contato");
                System.out.println("5. Enviar mensagem");
                System.out.println("6. Ver histórico de mensagens");
                System.out.println("7. Logout");
        
                int opcao = obterOpcao(scanner);
        
                switch (opcao) {
                    case 1:
                        visualizarContatos(scanner, usuarioLogado);
                        break;
                    case 2:
                        visualizarFavoritos(scanner, usuarioLogado);
                        break;
                    case 3:
                        System.out.print("Digite o nome do contato para favoritar: ");
                        String contatoFavorito = scanner.nextLine();
                        boolean sucessoFavorito = usuarioService.favoritarContato(usuarioLogado, contatoFavorito);
                        if (sucessoFavorito) {
                            System.out.println("Contato adicionado aos favoritos com sucesso!");
                        } else {
                            System.out.println("Erro ao favoritar contato. Verifique se o contato existe.");
                        }
                        break;
                    case 4:
                        System.out.print("Digite o nome do contato para remover: ");
                        String contatoRemover = scanner.nextLine();
                        if (usuarioLogado.getContatos().contains(contatoRemover)) {
                            usuarioLogado.removerContato(contatoRemover);
                            System.out.println("Contato removido com sucesso.");
                        } else {
                            System.out.println("Contato não encontrado.");
                        }
                        break;
                    case 5:
                        enviarMensagem(scanner, usuarioLogado);
                        break;
                    case 6:
                        visualizarHistoricoMensagens(scanner, usuarioLogado);
                        break;
                    case 7:
                        usuarioLogado = null;
                        System.out.println("Logout realizado com sucesso.");
                        break;
                    default:
                        System.out.println("Opção inválida, tente novamente.");
                }
            }
        }
    }

    private static int obterOpcao(Scanner scanner) {
        int opcao = -1;
        while (true) {
            try {
                System.out.print("Escolha uma opção: ");
                opcao = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número.");
            }
        }
        return opcao;
    }

    private static void cadastrarUsuario(Scanner scanner) {
        System.out.println("");
        System.out.print("Digite o login: ");
        String login = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        boolean sucesso = usuarioService.cadastrarUsuario(login, senha);
        if (sucesso) {
            System.out.println("Usuário cadastrado com sucesso!");
        } else {
            System.out.println("Erro: Login já existe. Tente novamente.");
        }
    }

    private static Usuario loginUsuario(Scanner scanner) {
        System.out.println("");
        System.out.print("Digite o login: ");
        String login = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        Usuario usuario = usuarioService.login(login, senha);
        if (usuario != null) {
            System.out.println("Login realizado com sucesso! Bem-vindo, " + usuario.getLogin() + ".");
            return usuario;
        } else {
            System.out.println("Erro: Login ou senha incorretos.");
            return null;
        }
    }

    private static void visualizarContatos(Scanner scanner, Usuario usuarioLogado) {
        System.out.println("\n--- Contatos ---");
        if (usuarioLogado.getContatos().isEmpty()) {
            System.out.println("Nenhum contato cadastrado.");
        } else {
            for (String contato : usuarioLogado.getContatos()) {
                System.out.println("- " + contato);
            }
        }

        System.out.print("\nDeseja adicionar um novo contato? (s/n): ");
        if ("s".equalsIgnoreCase(scanner.nextLine())) {
            System.out.print("Digite o nome do contato: ");
            String contato = scanner.nextLine();
            boolean sucesso = usuarioService.adicionarContato(usuarioLogado, contato);
            if (sucesso) {
                System.out.println("Contato adicionado com sucesso!");
            } else {
                System.out.println("O contato já existe.");
            }
        }
    }

    private static void visualizarFavoritos(Scanner scanner, Usuario usuarioLogado) {
        System.out.println("\n--- Favoritos ---");
        if (usuarioLogado.getFavoritos().isEmpty()) {
            System.out.println("Nenhum favorito cadastrado.");
        } else {
            for (String favorito : usuarioLogado.getFavoritos()) {
                System.out.println("- " + favorito);
            }
        }
    }

    private static void enviarMensagem(Scanner scanner, Usuario usuarioLogado) {
        System.out.print("Digite o nome do contato para enviar mensagem: ");
        String contatoNome = scanner.nextLine();
        
        if (usuarioLogado.getContatos().contains(contatoNome)) {
            System.out.print("Digite a mensagem: ");
            String mensagem = scanner.nextLine();

            if (usuarioService.enviarMensagem(usuarioLogado, contatoNome, mensagem) == true) {
                System.out.println("Mensagem enviada com sucesso!");
                return;
            }
            System.out.println("Erro: Contato não encontrado. Por favor adicione apenas contatos existentes no sistema!");

        } else {
            System.out.println("Erro: Contato não encontrado.");
        }
    }

    private static void visualizarHistoricoMensagens(Scanner scanner, Usuario usuarioLogado) {
        System.out.print("Digite o nome do contato para ver o histórico de mensagens: ");
        String contatoNome = scanner.nextLine();

        List<Mensagem> historicoMensagens = usuarioService.obterHistoricoMensagens(usuarioLogado, contatoNome);

        if (historicoMensagens.isEmpty()) {
            System.out.println("Nenhuma mensagem encontrada com o contato " + contatoNome + ".");
        } else {
            System.out.println("\n--- Histórico de Mensagens com " + contatoNome + " ---");
            for (Mensagem mensagem : historicoMensagens) {
                System.out.println(mensagem.getRemetente() + ": " + mensagem.getConteudo());
            }
        }
    }
}