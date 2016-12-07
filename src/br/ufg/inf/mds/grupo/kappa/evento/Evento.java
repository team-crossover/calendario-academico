package br.ufg.inf.mds.grupo.kappa.evento;

import br.ufg.inf.mds.grupo.kappa.excecao.EntradaInvalida;
import br.ufg.inf.mds.grupo.kappa.excecao.NomeInvalido;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * Essa classe é responsável por controlar os eventos (estrutura heterogenea de
 * armazenamento).
 *
 * @author Grupo Kappa
 */
public class Evento {

    /**
     * Data inicial do evento.
     */
    private Date dataInicio;

    /**
     * Horário inicial do evento.
     */
    private LocalTime horaInicio;

    /**
     * Data final do evento (pode ser a mesma que a inicial).
     */
    private Date dataFim;

    /**
     * Horário final do evento.
     */
    private LocalTime horaFim;

    /**
     * Nome/descrição do evento.
     */
    private String nomeEvento;

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
     * Retorna o horário inicial do evento.
     *
     * @return Horário inicial do evento.
     */
    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    /**
     * Retorna o texto (hh:mm) do horário inicial deste evento.
     *
     * @return String com o horário inicial deste evento, no formato hh:mm.
     */
    public String getHoraInicioString() {
        return this.horaInicio.toString();
    }

    /**
     * Define um novo horário inicial para este evento.
     *
     * @param horaInicio Novo horário, no formato hh:mm
     * @throws DateTimeParseException Quando o horário inserido não seguir o
     * formato ou os limites numéricos
     */
    public void setHoraInicio(String horaInicio) throws DateTimeParseException {
        this.horaInicio = LocalTime.parse(horaInicio);
    }

    /**
     * Retorna o horário final do evento.
     *
     * @return Horário final deste evento.
     */
    public LocalTime getHoraFim() {
        return horaFim;
    }

    /**
     * Retorna o texto (hh:mm) do horário final deste evento.
     *
     * @return String com o horário final deste evento, no formato hh:mm.
     */
    public String getHoraFimString() {
        return this.horaFim.toString();
    }

    /**
     * Define um novo horário final para este evento. Caso o evento possua data
     * inicial e final iguais, o horário final não pode ser anterior ao horário
     * inicial.
     *
     * @param horaFim Novo horário, no formato hh:mm
     * @throws br.ufg.inf.mds.grupo.kappa.excecao.EntradaInvalida Quando a hora
     * for anterior ao horário inicial (para eventos de dia único).
     * @throws DateTimeParseException Quando o horário inserido não seguir o
     * formato ou os limites numéricos
     */
    public void setHoraFim(String horaFim) throws EntradaInvalida,
            DateTimeParseException {

        LocalTime novaHoraFim = LocalTime.parse(horaFim);
        if (this.horaInicio.isAfter(novaHoraFim)
                && (this.dataInicio.equals(this.dataFim))) {
            throw new EntradaInvalida("A hora final deve ser maior ou igual à "
                    + "hora de início, para data unica.");
        } else {
            this.horaFim = novaHoraFim;
        }
    }

    /**
     * Retorna em texto a data inicial deste evento.
     *
     * @return String com a data inicial deste evento, no formato dd/mm/aaaa.
     */
    public String getDataInicioString() {
        SimpleDateFormat viraString = new SimpleDateFormat("dd/MM/yyyy");
        String dataString = viraString.format(this.dataInicio);
        return dataString;
    }

    /**
     * Retorna em texto a data final deste evento.
     *
     * @return String com a data final deste evento, no formato dd/mm/aaaa.
     */
    public String getDataFimString() {
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
        if (nomeEvento != null && !"".equals(nomeEvento)) {
            this.nomeEvento = nomeEvento;
        } else {
            throw new NomeInvalido("O nome de um evento não pode ser "
                    + "vazio.");
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date d;

        if (data == null || data.isEmpty()) {
            throw new EntradaInvalida("Data vazia.");
        }

        if (data.length() != 10) {
            throw new EntradaInvalida("Data mal formatada.");
        }

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
     * @throws NomeInvalido Quando o nome for apenas espaços.
     */
    public float nomeValido(String nomeEvento) throws NomeInvalido {

        if (nomeEvento.replace(" ", "").trim().isEmpty()) {
            throw new NomeInvalido("O nome de um evento não pode ser vazio.");
        }

        float teste = Float.parseFloat(nomeEvento);
        return teste;
    }

}
