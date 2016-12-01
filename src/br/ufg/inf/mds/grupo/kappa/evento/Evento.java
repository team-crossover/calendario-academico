package br.ufg.inf.mds.grupo.kappa.evento;

import br.ufg.inf.mds.grupo.kappa.excecao.EntradaInvalida;
import br.ufg.inf.mds.grupo.kappa.excecao.NomeInvalido;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Grupo Kappa
 *
 * Essa classe é responsável por controlar os eventos (estrutura heterogenea de
 * armazenamento).
 */
public class Evento {

    /**
     * Data inicial do evento.
     */
    private Date dataInicio;

    /**
     * Data final do evento (pode ser a mesma que a inicial).
     */
    private Date dataFim;

    /**
     * Nome/descrição do evento.
     */
    private String nomeEvento;

    /**
     * Objeto responsável por formatar datas em texto ("dd/mm/aaaa") para Date.
     */
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Objeto Date a ser manipulado pelos métodos dessa classe.
     */
    Date d = new Date();

    /**
     * Retorna a data inicial deste evento.
     *
     * @return Data inicial deste evento.
     */
    public Date getInicio() {
        return this.dataInicio;
    }

    /**
     * Retorna a data final deste evento.
     *
     * @return Data final deste evento.
     */
    public Date getFim() {
        return this.dataFim;
    }

    /**
     * Retorna em texto a data inicial deste evento.
     *
     * @return String com a data inicial deste evento, no formato dd/mm/aaaa.
     */
    public String getDataInicio() {
        SimpleDateFormat viraString = new SimpleDateFormat("dd/MM/yyyy");
        String dataString = viraString.format(this.dataInicio);
        return dataString;
    }

    /**
     * Retorna em texto a data final deste evento.
     *
     * @return String com a data final deste evento, no formato dd/mm/aaaa.
     */
    public String getDataFim() {
        SimpleDateFormat viraString = new SimpleDateFormat("dd/MM/yyyy");
        String dataString = viraString.format(this.dataFim);
        return dataString;
    }

    /**
     * Retorna o nome deste evento.
     *
     * @return Nome deste evento.
     */
    public String getNomeEvento() {
        return nomeEvento;
    }

    /**
     * Define uma nova data inicio a este evento.
     *
     * @param dataInicio Nova data inicio deste evento, no formato "dd/mm/aaaa".
     * @throws ParseException Quando a data fornecida não for correta.
     * @throws EntradaInvalida Quando o ano fornecido for inferior a 1754.
     */
    public void setDataInicio(String dataInicio) throws ParseException,
            EntradaInvalida {
        this.dataInicio = dataValida(dataInicio);

    }

    /**
     * Define uma nova data final a este evento.
     *
     * @param dataFim Nova data final deste evento, no formato "dd/mm/aaaa".
     * @throws ParseException Quando a data fornecida não for correta.
     * @throws EntradaInvalida Quando a data final for anterior à data inicial
     * ou o ano fornecido for inferior a 1754.
     */
    public void setDataFim(String dataFim) throws ParseException,
            EntradaInvalida {
        if (this.dataInicio.after(dataValida(dataFim))) {
            throw new EntradaInvalida("A data final deve ser maior ou igual à "
                    + "data de início.");
        } else {
            this.dataFim = dataValida(dataFim);
        }
    }

    /**
     * Define um novo nome a este evento.
     *
     * @param nomeEvento Novo nome a ser definido.
     * @throws NomeInvalido Quando o nome for curto demais ou possuir caracteres
     * inválidos.
     */
    public void setNomeEvento(String nomeEvento) throws NomeInvalido {
        try {
            float aux = nomeValido(nomeEvento);
            throw new NomeInvalido("O nome de um evento não pode apenas ser "
                    + "um número.");
        } catch (NumberFormatException e) {
            if (nomeEvento != null && !"".equals(nomeEvento)) {
                this.nomeEvento = nomeEvento;
            } else {
                throw new NomeInvalido("O nome de um evento não pode ser "
                        + "vazio.");
            }
        }
    }

    /**
     * Retorna um objeto "Date" para uma data fornecida em String no formato
     * "dd/mm/aaaa".
     *
     * @param data Data no formato dd/mm/aaaa a ser convertida.
     * @return Objeto "Date" para a data fornecida.
     * @throws ParseException Não foi possível transformar String pra Date.
     * @throws EntradaInvalida O ano inserido é inválido (anterior a 1754).
     */
    public Date dataValida(String data) throws EntradaInvalida, ParseException {

        int ano = Integer.parseInt(data.substring(6));
        if (ano > 1753) {
            formatter.setLenient(false);
            d = formatter.parse(data);
            return d;
        } else {
            throw new EntradaInvalida("Ano inválido (deve ser 1754 ou "
                    + "superior).");
        }
    }

    /**
     * Verifica se um nome é válido para um evento e tentar retornar um float
     * caso o nome seja um número.
     *
     * @param nomeEvento Nome a ser verificado.
     * @return Se possível, um float correspondente ao nome fornecido.
     * @throws NomeInvalido Quando o nome possuir menos de 5 caracteres, ou
     * possuir caracteres especiais, ou for apenas espaços.
     */
    public float nomeValido(String nomeEvento) throws NomeInvalido {

        if (nomeEvento.length() < 5) {
            throw new NomeInvalido("O nome de um evento deve possuir 5 "
                    + "caracteres ou mais.");
        }

        if (nomeEvento.matches("[!#@$%''&*0-9].*")) {
            throw new NomeInvalido("O nome de um evento não pode conter"
                    + " caracteres especiais.");
        }

        if (nomeEvento.replace(" ", "").trim().isEmpty()) {
            throw new NomeInvalido("O nome de um evento não pode ser vazio.");
        }

        float teste = Float.parseFloat(nomeEvento);
        return teste;
    }

}
