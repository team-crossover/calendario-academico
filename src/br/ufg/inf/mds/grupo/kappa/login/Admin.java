package br.ufg.inf.mds.grupo.kappa.login;

import br.ufg.inf.mds.grupo.kappa.excecao.EntradaInvalida;

/**
 * Esta classe tem a finalidade de autenticar um usuário com perfil de
 * administrador. Esta classe NÃO É SEGURA e deve ser substituida por um sistema
 * mais robusto posteriormente.
 *
 * @author Grupo Kappa
 */
public class Admin {

    /**
     * Usuário padrão do administrador.
     */
    private String usuario = "adm";
    /**
     * Senha padrão do administrador.
     */
    private String senha = "123";

    /**
     * Verifica se usuário e senhas fornecidos são corretos.
     *
     * @param usuario Usuario fornecido na tentativa.
     * @param senha Senha fornecida na tentativa.
     * @return Verdadeiro quando a senha e usuário são corretos, ou falso quando
     * algum dos dois é incorreto.
     */
    public boolean validarSenha(String usuario, String senha) {
        return this.usuario.equals(usuario) && this.senha.equals(senha);
    }

    /**
     * Permite alterar o usuário deste administrador.
     *
     * @param novoUsuario Novo usuário.
     * @throws EntradaInvalida Quando o novo usuário for vazio, conter espaços
     * ou tiver menos de 3 caracteres.
     */
    public void alterarUsuario(String novoUsuario) throws EntradaInvalida {

        if (novoUsuario == null || novoUsuario.isEmpty()) {
            throw new EntradaInvalida("Usuário não pode ser vazio.");
        }

        if (novoUsuario.contains(" ")) {
            throw new EntradaInvalida("Usuário não pode conter espaços");
        }

        if (novoUsuario.length() < 3) {
            throw new EntradaInvalida("Usuário deve possuir 3 ou mais "
                    + "caracteres.");
        }

        this.usuario = novoUsuario;
    }

    /**
     * Permite alterar a senha deste administrador.
     *
     * @param novaSenha Nova senha.
     * @throws EntradaInvalida Quando a nova senha for vazia ou tiver menos de 3
     * caracteres.
     */
    public void alterarSenha(String novaSenha) throws EntradaInvalida {

        if (novaSenha == null || novaSenha.isEmpty()) {
            throw new EntradaInvalida("Senha não pode ser vazia.");
        }

        if (novaSenha.length() < 3) {
            throw new EntradaInvalida("Senha deve possuir 3 ou mais "
                    + "caracteres.");
        }

        this.senha = novaSenha;
    }

    /**
     * Retorna o usuário atual.
     *
     * @return O usuário do administrador.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Retorna a senha atual.
     *
     * @return A senha do administrador.
     */
    public String getSenha() {
        return senha;
    }

}
