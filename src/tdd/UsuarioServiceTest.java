import java.util.List;

public class UsuarioServiceTest {

    //TESTES USADOS PARA CADASTRO DE USUARIO
    @Test
    public void testeCadastroNovoUsuario() {
        UsuarioService usuarioService = new UsuarioService();
        boolean resultado = usuarioService.cadastrarUsuario("usuario1", "senha1"); //deve retornar true para novo cadastro
        assertTrue(resultado); //confirma o cadastro
    }

    @Test
    public void testeCadastroUsuarioComIdUnico() {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.cadastrarUsuario("usuario1", "senha1");
        Usuario usuario = usuarioService.buscarUsuario("usuario1");
        assertEquals(1, usuario.getId()); //ID do primeiro usuário deve ser 1
    }
    //TESTES USADOS PARA CRIAÇÃO DO USUARIO E CADASTRO DE USUARIO


    //TESTES USADOS PARA LOGAR O USUARIO
    @Test
    public void testeLoginValido() {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.cadastrarUsuario("usuario1", "senha1");
        Usuario usuario = usuarioService.login("usuario1", "senha1"); //deve retornar o usuário
        assertNotNull(usuario); //esperamos que o login seja válido
    }

    @Test
    public void testeLoginInvalido() {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.cadastrarUsuario("usuario1", "senha1");
        Usuario usuario = usuarioService.login("usuario1", "senhaErrada"); //senha errada
        assertNull(usuario); //esperamos que o login seja inválido
    }
    //TESTES USADOS PARA LOGAR O USUARIO

    
    //TESTES USADOS PARA VERIFICAR SE TINHA PERSISTENCIA
    @Test
    public void testePersistenciaUsuarios() {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.cadastrarUsuario("usuario1", "senha1");
        usuarioService.salvarUsuarios(); //método de salvar ainda não existe
        
        usuarioService = new UsuarioService(); //reinstancia para carregar o arquivo
        usuarioService.carregarUsuarios(); //método de carregar ainda não existe
        
        Usuario usuario = usuarioService.login("usuario1", "senha1");
        assertNotNull(usuario); //esperamos que o usuário seja carregado com sucesso
    }
    //TESTES USADOS PARA VERIFICAR SE TINHA PERSISTENCIA


    // TESTES USADOS PARA ADICIONAR CONTATOS
    @Test
    public void testeAdicionarContato() {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.cadastrarUsuario("usuario1", "senha1");
        Usuario usuario = usuarioService.buscarUsuario("usuario1");
        boolean resultado = usuarioService.adicionarContato(usuario, "contato1"); // deve retornar true para novo contato
        assertTrue(resultado); // confirma a adição do contato
    }

    @Test
    public void testeAdicionarContatoDuplicado() {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.cadastrarUsuario("usuario1", "senha1");
        Usuario usuario = usuarioService.buscarUsuario("usuario1");
        usuarioService.adicionarContato(usuario, "contato1");
        boolean resultado = usuarioService.adicionarContato(usuario, "contato1"); // tentar adicionar duplicado
        assertFalse(resultado); // deve retornar false pois o contato já existe
    }

    //TESTES USADOS PARA LISTAR CONTATOS
    @Test
    public void testeListarContatos() {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.cadastrarUsuario("usuario1", "senha1");
        Usuario usuario = usuarioService.buscarUsuario("usuario1");
        usuarioService.adicionarContato(usuario, "contato1");
        usuarioService.adicionarContato(usuario, "contato2");

        assertEquals(2, usuario.getContatos().size()); // deve ter 2 contatos na lista
        assertTrue(usuario.getContatos().contains("contato1"));
        assertTrue(usuario.getContatos().contains("contato2"));
    }

    //TESTES USADOS PARA FAVORITAR CONTATOS
    @Test
    public void testeFavoritarContato() {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.cadastrarUsuario("usuario1", "senha1");
        Usuario usuario = usuarioService.buscarUsuario("usuario1");
        usuarioService.adicionarContato(usuario, "contato1");
        boolean resultado = usuarioService.favoritarContato(usuario, "contato1"); // deve retornar true para favorito
        assertTrue(resultado); // confirma que o contato foi favoritado
        assertTrue(usuario.getFavoritos().contains("contato1")); // verifica se está na lista de favoritos
    }

    @Test
    public void testeFavoritarContatoNaoExistente() {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.cadastrarUsuario("usuario1", "senha1");
        Usuario usuario = usuarioService.buscarUsuario("usuario1");
        boolean resultado = usuarioService.favoritarContato(usuario, "contatoNaoExistente"); // contato não existe
        assertFalse(resultado); // deve retornar false pois o contato não existe
    }

    @Test
    public void testeListarFavoritos() {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.cadastrarUsuario("usuario1", "senha1");
        Usuario usuario = usuarioService.buscarUsuario("usuario1");
        usuarioService.adicionarContato(usuario, "contato1");
        usuarioService.adicionarContato(usuario, "contato2");
        usuarioService.favoritarContato(usuario, "contato1");

        assertEquals(1, usuario.getFavoritos().size()); // deve ter 1 contato na lista de favoritos
        assertTrue(usuario.getFavoritos().contains("contato1"));
        assertFalse(usuario.getFavoritos().contains("contato2")); // contato2 não deve estar nos favoritos
    }

    // TESTES USADOS PARA REMOVER CONTATOS
    @Test
    public void testeRemoverContatoExistente() {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.cadastrarUsuario("usuario1", "senha1");
        Usuario usuario = usuarioService.buscarUsuario("usuario1");
        usuarioService.adicionarContato(usuario, "contato1");
        boolean resultado = usuarioService.removerContato(usuario, "contato1"); // deve retornar true para remoção
        assertTrue(resultado); // confirma a remoção do contato
        assertFalse(usuario.getContatos().contains("contato1")); // verifica se o contato foi removido
    }

    @Test
    public void testeRemoverContatoInexistente() {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.cadastrarUsuario("usuario1", "senha1");
        Usuario usuario = usuarioService.buscarUsuario("usuario1");
        boolean resultado = usuarioService.removerContato(usuario, "contatoInexistente"); // contato não existe
        assertFalse(resultado); // deve retornar false pois o contato não existe
    }

    @Test
    public void testeRemoverContatoFavoritado() {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.cadastrarUsuario("usuario1", "senha1");
        Usuario usuario = usuarioService.buscarUsuario("usuario1");
        usuarioService.adicionarContato(usuario, "contato1");
        usuarioService.favoritarContato(usuario, "contato1"); // favoritar o contato
        boolean resultado = usuarioService.removerContato(usuario, "contato1"); // deve retornar true para remoção
        assertTrue(resultado); // confirma a remoção do contato
        assertFalse(usuario.getContatos().contains("contato1")); // verifica se o contato foi removido
        assertFalse(usuario.getFavoritos().contains("contato1")); // deve ser removido dos favoritos também
    }
    // TESTES USADOS PARA REMOVER CONTATOS


    // TESTES PARA ENVIO DE MENSAGENS
    @Test
    public void testeEnvioMensagemValida() {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.cadastrarUsuario("usuario1", "senha1");
        usuarioService.cadastrarUsuario("contato1", "senha2");

        Usuario usuario = usuarioService.buscarUsuario("usuario1");
        Usuario contato = usuarioService.buscarUsuario("contato1");

        boolean resultado = usuarioService.enviarMensagem(usuario, "contato1", "Olá, contato1!");
        assertTrue(resultado); //confirma que a mensagem foi enviada com sucesso

        //verifica se o histórico do usuário e do contato foram atualizados corretamente
        assertEquals(1, usuario.getMensagens("contato1").size());
        assertEquals(1, contato.getMensagens("usuario1").size());
    }

    @Test
    public void testeEnvioMensagemParaContatoNaoExistente() {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.cadastrarUsuario("usuario1", "senha1");

        Usuario usuario = usuarioService.buscarUsuario("usuario1");

        boolean resultado = usuarioService.enviarMensagem(usuario, "contatoInexistente", "Olá!");
        assertFalse(resultado); //deve retornar false pois o contato não existe
    }
    // TESTES PARA ENVIO DE MENSAGENS

    // TESTES PARA VISUALIZAR HISTÓRICO DE MENSAGENS
    @Test
    public void testeVisualizarHistoricoComContato() {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.cadastrarUsuario("usuario1", "senha1");
        usuarioService.cadastrarUsuario("contato1", "senha2");

        Usuario usuario = usuarioService.buscarUsuario("usuario1");
        usuarioService.enviarMensagem(usuario, "contato1", "Olá, contato1!");

        //verifica o histórico de mensagens com "contato1"
        List<Mensagem> historicoMensagens = usuario.getMensagens("contato1");
        assertEquals(1, historicoMensagens.size());
        assertEquals("Olá, contato1!", historicoMensagens.get(0).getConteudo());
        assertEquals("usuario1", historicoMensagens.get(0).getRemetente());
    }

    @Test
    public void testeVisualizarHistoricoSemMensagens() {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.cadastrarUsuario("usuario1", "senha1");
        usuarioService.cadastrarUsuario("contato1", "senha2");

        Usuario usuario = usuarioService.buscarUsuario("usuario1");

        //verifica que o histórico de mensagens está vazio com "contato1"
        List<Mensagem> historicoMensagens = usuario.getMensagens("contato1");
        assertTrue(historicoMensagens.isEmpty());
    }

    @Test
    public void testeVisualizarHistoricoSemContato() {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.cadastrarUsuario("usuario1", "senha1");

        Usuario usuario = usuarioService.buscarUsuario("usuario1");

        //tentativa de visualizar histórico com um contato inexistente (não adicionado na lista de contatos)
        List<Mensagem> historicoMensagens = usuario.getMensagens("contatoInexistente");
        assertTrue(historicoMensagens == null || historicoMensagens.isEmpty(), "O histórico deve ser vazio ou inexistente para contatos não adicionados.");
    }
    // TESTES PARA VISUALIZAR HISTÓRICO DE MENSAGENS
}
