package br.ufg.inf.mds.grupo.kappa.divisoes;

import br.ufg.inf.mds.grupo.kappa.excecao.RegionalInvalida;
import java.util.ArrayList;
import java.util.List;

/**
 * Tipo enum para as regonais. Possui algumas utilidades para facilitar o
 * manuseio de regionais.
 *
 * @author Grupo Kappa
 */
public enum Regional {

    /**
     * Regional de Catalão.
     */
    CATALAO,
    /**
     * Regional de Goiás.
     */
    GOIAS,
    /**
     * Regional de Goiânia.
     */
    GOIANIA,
    /**
     * Regional de Jataí.
     */
    JATAI;

    /**
     * Obtém uma string com o nome correto desta regional.
     *
     * @return String com o nome da regional.
     */
    public String getNome() {
        String resultado = null;

        switch (this) {
            case CATALAO:
                resultado = "Catalão";
                break;
            case GOIANIA:
                resultado = "Goiânia";
                break;
            case GOIAS:
                resultado = "Goiás";
                break;
            case JATAI:
                resultado = "Jataí";
                break;
        }

        return resultado;
    }

    /**
     * Retorna uma regional com base em um texto fornecido.
     *
     * @param nome Nome da regional buscada.
     * @return Regional correspondente ao nome buscado.
     * @throws br.ufg.inf.mds.grupo.kappa.excecao.RegionalInvalida Quando a
     * regional fornecida não existir.
     */
    public static Regional getRegionalDeNome(String nome) throws
            RegionalInvalida {
        Regional regional = null;
        String nomeLower = nome.toLowerCase();

        if (nomeLower.equals("catalao") || nomeLower.equals("catalão")) {
            regional = CATALAO;
        }

        if (nomeLower.equals("goiania") || nomeLower.equals("goiânia")) {
            regional = GOIANIA;
        }

        if (nomeLower.equals("goias") || nomeLower.equals("goiás")) {
            regional = GOIAS;
        }

        if (nomeLower.equals("jatai") || nomeLower.equals("jataí")) {
            regional = JATAI;
        }

        if (regional == null) {
            throw new RegionalInvalida("Regional não encontrada. Tente "
                    + "Catalão, Goiânia, Goiás ou Jataí.");
        } else {
            return regional;

        }
    }

    /**
     * Permite obter uma lista (separada por vírgulas) das regionais de uma
     * lista. Exemplo de resultado: "Catalão, Goiânia".
     *
     * @param regionais Lista das regionais a serem listadas.
     * @return StringBuilder contendo a lista formatada com vírgulas.
     */
    public static StringBuilder getNomesListados(List<Regional> regionais) {
        StringBuilder result = new StringBuilder();

        List<Regional> all = new ArrayList<>();
        all.add(Regional.GOIAS);
        all.add(Regional.CATALAO);
        all.add(Regional.GOIANIA);
        all.add(Regional.JATAI);
        if (regionais.containsAll(all)) {
            result.append("Todas Regionais");
        } else {
            for (Regional reg : regionais) {
                result.append(reg.getNome());
                if (regionais.get(regionais.size() - 1) != reg) {
                    result.append(", ");
                }
            }
        }
        return result;
    }
}
