package br.ufg.inf.mds.grupo.kappa.calendario;

import br.ufg.inf.mds.grupo.kappa.evento.Evento;
import br.ufg.inf.mds.grupo.kappa.excecao.EntradaInvalida;
import br.ufg.inf.mds.grupo.kappa.excecao.EventoInvalido;
import br.ufg.inf.mds.grupo.kappa.excecao.RegionalInvalida;

/**
 * Um objeto desta classe contém um evento e as informações relacionadas à
 * regional em que este evento está presente.
 *
 * @author Grupo Kappa
 */
public class Calendario {

    /**
     * Tipos de regionais possíveis.
     */
    public enum Regional {
        Todas,
        Catalao,
        Goias,
        Goiania,
        Jatai;

        /**
         * Obtém uma string com o nome correto desta regional.
         *
         * @return String com o nome da regional.
         */
        public String getNome() {
            String resultado = null;

            switch (this) {
                case Catalao:
                    resultado = "Catalão";
                    break;
                case Goiania:
                    resultado = "Goiânia";
                    break;
                case Goias:
                    resultado = "Goiás";
                    break;
                case Jatai:
                    resultado = "Jataí";
                    break;
                case Todas:
                    resultado = "Todas (Catalão, Goiânia, Goiás e Jataí)";
                    break;
            }

            return resultado;
        }

        /**
         * Retorna uma Regional com base em um texto fornecido.
         *
         * @param nome Nome da regional buscada.
         * @return Regional correspondente ao nome buscado.
         * @throws br.ufg.inf.mds.grupo.kappa.excecao.RegionalInvalida Quando a
         * regional fornecida não existir.
         */
        public static Regional getRegionalFromNome(String nome) throws
                RegionalInvalida {
            Regional regional = null;
            String nomeLower = nome.toLowerCase();

            if (nomeLower.equals("todas")) {
                regional = Todas;
            }

            if (nomeLower.equals("catalao") || nomeLower.equals("catalão")) {
                regional = Catalao;
            }

            if (nomeLower.equals("goiania") || nomeLower.equals("goiânia")) {
                regional = Goiania;
            }

            if (nomeLower.equals("goias") || nomeLower.equals("goiás")) {
                regional = Goias;
            }

            if (nomeLower.equals("jatai") || nomeLower.equals("jataí")) {
                regional = Jatai;
            }

            if (regional == null) {
                throw new RegionalInvalida("Regional não encontrada. Tente "
                        + "Catalão, Goiânia, Goiás, Jataí ou Todas.");
            } else {
                return regional;

            }
        }
    }

    /**
     * Evento deste calendário.
     */
    private Evento evento;

    /**
     * Regional deste calendário.
     */
    private Regional regional;

    /**
     * Retorna o evento deste calendário.
     *
     * @return O evento deste calendário.
     */
    public Evento getEvento() {
        return evento;
    }

    /**
     * Retorna a regional deste calendário.
     *
     * @return A regional deste calendário.
     */
    public Regional getRegional() {
        return regional;
    }

    /**
     * Define um novo evento para este calendário.
     *
     * @param evento Evento a ser definido.
     * @throws EventoInvalido Quando o evento inserido for vazio.
     */
    public void setEvento(Evento evento) throws EventoInvalido {
        if (evento != null) {
            this.evento = evento;
        } else {
            throw new EventoInvalido("O evento informado não pode ser vazio.");
        }
    }

    /**
     * Define uma nova regional para este calendário.
     *
     * @param regional Regional a ser definida.
     * @throws EntradaInvalida Quando a regional fornecida for vazia.
     */
    public void setRegional(Regional regional) throws EntradaInvalida {
        if (regional != null) {
            this.regional = regional;
        } else {
            throw new EntradaInvalida("A regional não pode ser vazia.");
        }
    }

    /**
     * Obtém o texto com os dados do evento deste calendário, para exibição em
     * consultas.
     *
     * @return StringBuilder com os dados do evento deste Calendario.
     */
    public StringBuilder getTexto() {
        StringBuilder retorno = new StringBuilder();

        retorno.append("\n");
        retorno.append("NOME:     ").append(this.getEvento()
                .getNomeEvento());
        retorno.append("\n");
        retorno.append("INÍCIO:   ")
                .append(this.getEvento().getDataInicio());
        retorno.append("\n");
        retorno.append("FINAL:    ")
                .append(this.getEvento().getDataFim());
        retorno.append("\n");
        retorno.append("REGIONAL: ").append(this.
                getRegional().getNome());
        retorno.append("\n");
        retorno.append("+===================================================+");

        return retorno;
    }

}
