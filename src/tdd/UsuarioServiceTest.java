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
}
