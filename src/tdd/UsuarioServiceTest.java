public class UsuarioServiceTest {

    //TESTES USADOS PARA CADASTRO DE USUARIO
    public void testeCadastroNovoUsuario() {
        UsuarioService usuarioService = new UsuarioService();
        boolean resultado = usuarioService.cadastrarUsuario("usuario1", "senha1"); //deve retornar true para novo cadastro
        assertTrue(resultado); //confirma o cadastro
    }

    public void testeCadastroUsuarioComIdUnico() {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.cadastrarUsuario("usuario1", "senha1");
        Usuario usuario = usuarioService.buscarUsuario("usuario1");
        assertEquals(1, usuario.getId()); //ID do primeiro usuário deve ser 1
    }
    //TESTES USADOS PARA CRIAÇÃO DO USUARIO E CADASTRO DE USUARIO


    //TESTES USADOS PARA LOGAR O USUARIO
    public void testeLoginValido() {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.cadastrarUsuario("usuario1", "senha1");
        Usuario usuario = usuarioService.login("usuario1", "senha1"); //deve retornar o usuário
        assertNotNull(usuario); //esperamos que o login seja válido
    }

    public void testeLoginInvalido() {
        UsuarioService usuarioService = new UsuarioService();
        usuarioService.cadastrarUsuario("usuario1", "senha1");
        Usuario usuario = usuarioService.login("usuario1", "senhaErrada"); //senha errada
        assertNull(usuario); //esperamos que o login seja inválido
    }
    //TESTES USADOS PARA LOGAR O USUARIO

    
    //TESTES USADOS PARA VERIFICAR SE TINHA PERSISTENCIA
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

}
