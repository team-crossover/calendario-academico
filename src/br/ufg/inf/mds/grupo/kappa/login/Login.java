package br.ufg.inf.mds.grupo.kappa.login;

/**
 * Esta classe tem a finalidade de autenticar um usuário com perfil de
 * administrador. Esta classe não apresenta padrões de segurança e deve ser
 * substituida por um sistema mais robusto posteriormente.
 *
 * @author Grupo Kappa
 */
public class Login {

    /**
     * Usuário padrão do administrador.
     */
    private final String USUARIO = "adm";

    /**
     * Senha padrão do administrador.
     */
    private final String SENHA = "123";

    /**
     * Verifica se usuário e senhas fornecidos são corretos.
     *
     * @param usuario Usuario fornecido na tentativa.
     * @param senha Senha fornecida na tentativa.
     * @return Verdadeiro quando a senha e usuário são corretos, ou falso quando
     * algum dos dois é incorreto.
     */
    public boolean validarSenha(String usuario, String senha) {
        return this.USUARIO.equals(usuario) && this.SENHA.equals(senha);
    }

}
