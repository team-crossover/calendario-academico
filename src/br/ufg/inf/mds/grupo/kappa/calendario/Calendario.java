package br.ufg.inf.mds.grupo.kappa.calendario;

import br.ufg.inf.mds.grupo.kappa.divisoes.Categoria;
import br.ufg.inf.mds.grupo.kappa.divisoes.Regional;
import br.ufg.inf.mds.grupo.kappa.evento.Evento;
import br.ufg.inf.mds.grupo.kappa.excecao.EntradaInvalida;
import br.ufg.inf.mds.grupo.kappa.excecao.EventoInvalido;
import java.util.ArrayList;
import java.util.List;

/**
 * Um objeto desta classe contém um evento e as informações relacionadas à
 * regional e categoria em que este evento está presente.
 *
 * @author Grupo Kappa
 */
public class Calendario {

    /**
     * Evento deste calendário.
     */
    private Evento evento;

    /**
     * Regionais deste calendário.
     */
    private final List<Regional> regionais = new ArrayList<>();

    /**
     * Categoria deste evento.
     */
    private final List<Categoria> categorias = new ArrayList();

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
    public List<Regional> getRegionais() {
        return regionais;
    }

    /**
     * Retorna a regional deste calendário.
     *
     * @return A regional deste calendário.
     */
    public List<Categoria> getCategorias() {
        return categorias;
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
     * Adiciona uma nova regional para este calendário.
     *
     * @param regional Regional a ser adicionada.
     * @throws EntradaInvalida Quando a regional fornecida for vazia.
     */
    public void addRegional(Regional regional) throws EntradaInvalida {
        if (regional != null) {
            this.regionais.add(regional);
        } else {
            throw new EntradaInvalida("A regional não pode ser vazia.");
        }
    }

    /**
     * Adiciona uma nova categoria para este calendário.
     *
     * @param categoria Categoria a ser definida.
     * @throws EntradaInvalida Quando a categoria fornecida for vazia.
     */
    public void addCategoria(Categoria categoria) throws EntradaInvalida {
        if (categoria != null) {
            this.categorias.add(categoria);
        } else {
            throw new EntradaInvalida("A categoria não pode ser vazia.");
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
        retorno.append("\n	  | NOME:    ").append(this.getEvento()
                .getNomeEvento());
        retorno.append("\n");
        retorno.append("          | INÍCIO:  ")
                .append(this.getEvento().getDataInicioString());
        retorno.append(" às ");
        retorno.append(this.getEvento().getHoraInicioString());
        retorno.append("\n");
        retorno.append("          | TÉRMINO: ")
                .append(this.getEvento().getDataFimString());
        retorno.append(" às ");
        retorno.append(this.getEvento().getHoraFimString());
        retorno.append("\n");
        retorno.append("          | REGION.: ");
        retorno.append(Regional.getNomesListados(regionais));
        retorno.append("\n");
        retorno.append("          | CATEG.:  ");
        retorno.append(Categoria.getNomesListados(categorias));
        retorno.append("\n");
        retorno.append("	  | - - - - - - - - - - - - - - - - - - - - - "
                + "+");
        return retorno;
    }

}
