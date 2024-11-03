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

                int opcao = obterOpcao(scanner); //metodo para verificar se é um int

                switch (opcao) {
                    case 1:
                        cadastrarUsuario(scanner);
                        break;
                    case 2:
                        usuarioLogado = loginUsuario(scanner);
                        break;
                    case 3:
                        usuarioService.salvarUsuarios(); //salva dados antes de sair
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
                System.out.println("3. Logout");

                int opcao = obterOpcao(scanner);

                switch (opcao) {
                    case 1:
                        visualizarContatos(usuarioLogado);
                        break;
                    case 2:
                        visualizarFavoritos(usuarioLogado);
                        break;
                    case 3:
                        usuarioLogado = null; //desloga
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

    private static void visualizarContatos(Usuario usuario) {
        System.out.println("\n--- Contatos ---");
        if (usuario.getContatos().isEmpty() == true) {
            System.out.println("Nenhum contato cadastrado.");
        } else {
            for (String contato : usuario.getContatos()) {
                System.out.println("- " + contato);
            }
        }
    }

    private static void visualizarFavoritos(Usuario usuario) {
        System.out.println("\n--- Favoritos ---");
        if (usuario.getFavoritos().isEmpty() == true) {
            System.out.println("Nenhum favorito cadastrado.");
        } else {
            for (String favorito : usuario.getFavoritos()) {
                System.out.println("- " + favorito);
            }
        }
    }
}
