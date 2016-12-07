package br.ufg.inf.mds.grupo.kappa.divisoes;

import br.ufg.inf.mds.grupo.kappa.excecao.CategoriaInvalida;
import java.util.List;

/**
 * Tipo enum para as categorias. Possui algumas utilidades para facilitar o
 * manuseio de categorias.
 *
 * @author Grupo Kappa
 */
public enum Categoria {
    /**
     * Categoria discente.
     */
    DISCENTE,
    /**
     * Categoria docente.
     */
    DOCENTE,
    /**
     * Categoria servidor.
     */
    SERVIDOR,
    /**
     * Categoria comunidade acadêmica.
     */
    COMUNIDADE;

    /**
     * Obtém uma string com o nome correto desta regional.
     *
     * @return String com o nome da regional.
     */
    public String getNome() {
        String resultado = null;

        switch (this) {
            case DISCENTE:
                resultado = "Discente";
                break;
            case DOCENTE:
                resultado = "Docente";
                break;
            case SERVIDOR:
                resultado = "Servidor";
                break;
            case COMUNIDADE:
                resultado = "Comunidade Acadêmica";
                break;
        }

        return resultado;
    }

    /**
     * Retorna uma Categoria com base em um texto fornecido.
     *
     * @param nome Nome da categoria buscada.
     * @return Categoria correspondente ao nome buscado.
     * @throws br.ufg.inf.mds.grupo.kappa.excecao.CategoriaInvalida
     */
    public static Categoria getCategoriaDeNome(String nome) throws
            CategoriaInvalida {
        Categoria categoria = null;
        String nomeLower = nome.toLowerCase();

        if (nomeLower.equals("discente") || nomeLower.equals("aluno")) {
            categoria = DISCENTE;
        }

        if (nomeLower.equals("docente") || nomeLower.equals("professor")) {
            categoria = DOCENTE;
        }

        if (nomeLower.equals("servidor")) {
            categoria = SERVIDOR;
        }

        if (nomeLower.equals("comunidade")
                || nomeLower.equals("comunidade academica")
                || nomeLower.equals("comunidade acadêmica")) {
            categoria = COMUNIDADE;
        }

        if (categoria == null) {
            throw new CategoriaInvalida("Categoria não encontrada. Tente "
                    + "Discente, Docente, Servidor ou Comunidade Acadêmica.");
        } else {
            return categoria;

        }
    }

    /**
     * Permite obter uma lista (separada por vírgulas) das categorias de uma
     * lista. Exemplo de resultado: "Docente, Servidor".
     *
     * @param categorias Lista das categorias a serem listadas.
     * @return StringBuilder contendo a lista formatada com vírgulas.
     */
    public static StringBuilder getNomesListados(List<Categoria> categorias) {
        StringBuilder result = new StringBuilder();
        for (Categoria cat : categorias) {
            result.append(cat.getNome());
            if (categorias.get(categorias.size() - 1) != cat) {
                result.append(", ");
            }
        }
        return result;
    }
}
