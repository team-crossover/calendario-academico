package br.ufg.inf.mds.grupo.kappa.listaCalendario;

import br.ufg.inf.mds.grupo.kappa.calendario.Calendario;
import br.ufg.inf.mds.grupo.kappa.calendario.Calendario.Regional;
import br.ufg.inf.mds.grupo.kappa.evento.Evento;
import br.ufg.inf.mds.grupo.kappa.excecao.CalendarioInvalido;
import br.ufg.inf.mds.grupo.kappa.excecao.CalendarioVazio;
import br.ufg.inf.mds.grupo.kappa.excecao.EventoInvalido;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Classe responsável por armazenar todos os eventos cadastrados para todas as
 * regionais.
 *
 * @author Grupo kappa
 */
public class ListaCalendario {

    /**
     * Lista contendo todos os objetos "Calendario" (responsáveis por armazenar
     * eventos) registrados no calendário da UFG.
     */
    private final List<Calendario> calendarioUfg = new ArrayList<>();

    /**
     * Permite adicionar um novo evento à lista. Organiza os eventos em ordem
     * crescente com base na data inicial.
     *
     * @param novoCadastro Calendario contendo o novo evento a ser cadastrado.
     * @throws CalendarioInvalido Quando tenta-se adicionar um evento null.
     */
    public void adicionarCalendarioUfg(Calendario novoCadastro) throws
            CalendarioInvalido {

        if (novoCadastro != null) {
            this.calendarioUfg.add(novoCadastro);

            //Organiza a lista em ordem crescente com base nas datas de início.
            Collections.sort(calendarioUfg, (Calendario c1, Calendario c2) -> {
                String c1Value = c1.getEvento().getDataInicio();
                String c2Value = c2.getEvento().getDataInicio();
                return c1Value.compareTo(c2Value);
            });

        } else {
            throw new CalendarioInvalido("O evento fornecido não pode ser"
                    + " vazio.");
        }

    }

    /**
     * Permite remover um evento da lista.
     *
     * @param nome Nome do evento a ser removido.
     * @param data Data de início do evento a ser removido.
     * @throws CalendarioInvalido Quando o usuário informa algum dado inválido.
     */
    public void removerCalendarioUfg(String nome, String data) throws
            CalendarioInvalido {

        //Iteração por toda a lista
        for (Calendario calendario : this.calendarioUfg) {

            //Compara até localizar o evento a ser atualizado
            if (calendario.getEvento().getNomeEvento().equals(nome)
                    && calendario.getEvento().getDataInicio().equals(data)) {

                calendarioUfg.remove(calendario);

                return;
            }

        }

        throw new CalendarioInvalido("Evento não encontrado para atualização.");

    }

    /**
     * Permite atualizar um evento da lista. Reorganiza a lista em ordem
     * crescente após a atualização.
     *
     * @param nome Nome do evento a ser atualizado.
     * @param data Data inicial do evento a ser atualizado.
     * @param evento Evento a ser atualizado.
     * @throws EventoInvalido Quando os dados informados não são validados.
     * @throws CalendarioInvalido Quando não é possível atualizar o evento
     * devido a erros de parâmetros.
     */
    public void atualizarCalendario(String nome, String data, Evento evento)
            throws EventoInvalido, CalendarioInvalido {

        //Iteração por toda a lista
        for (Calendario calendario : this.calendarioUfg) {

            //Compara até localizar o evento a ser atualizado
            if (calendario.getEvento().getNomeEvento().equals(nome)
                    && calendario.getEvento().getDataInicio().equals(data)) {

                calendario.setEvento(evento);

                //Organiza a lista em ordem crescente pelas datas iniciais.
                Collections.sort(calendarioUfg, (Calendario c1, Calendario c2)
                        -> {
                    String c1Value = c1.getEvento().getDataInicio();
                    String c2Value = c2.getEvento().getDataInicio();
                    return c1Value.compareTo(c2Value);
                });

                return;
            }

        }

        throw new CalendarioInvalido("Evento não encontrado para atualização.");
    }

    /**
     * Linha de divisão exibida o início de consultas.
     *
     * @return A linha cabeçalho do início de consultas.
     */
    public static String cabecalhoConsulta() {
        return ("+==================== EVENTOS ======================+");
    }

    /**
     * Retorna um StringBuilder contendo os resultados de uma pesquisa por
     * eventos com base em nome e data de inicio.
     *
     * @param nome Nome do evento a ser resultado.
     * @param data Data inicial do evento a ser resultado.
     * @return StringBuilder contendo o resultado da pesquisa exata.
     * @throws CalendarioVazio Quando o calendário não conter nenhum evento.
     */
    public StringBuilder consultaExata(String nome, String data) throws
            CalendarioVazio {

        StringBuilder retorno = new StringBuilder();

        if (verificarVazio() == true) {
            throw new CalendarioVazio("O sistema não possui eventos"
                    + "cadastrados.");

        } else {
            retorno.append(cabecalhoConsulta());
            for (Calendario calendario : this.calendarioUfg) {
                if (calendario.getEvento().getNomeEvento().equals(nome)
                        && calendario.getEvento().getDataInicio()
                        .equals(data)) {
                    retorno.append(calendario.getTexto());
                }
            }
        }

        return retorno;
    }

    /**
     * Retorna um StringBuilder contendo os resultados de uma pesquisa por
     * eventos com base no nome.
     *
     * @param nome Nome do evento a ser resultado.
     * @return StringBuilder contendo o resultado da pesquisa exata.
     * @throws CalendarioVazio Quando o calendário não conter nenhum evento.
     */
    public StringBuilder consultaExata(String nome) throws
            CalendarioVazio {

        StringBuilder retorno = new StringBuilder();

        if (verificarVazio() == true) {
            throw new CalendarioVazio("O sistema não possui eventos "
                    + "cadastrados.");

        } else {
            retorno.append(cabecalhoConsulta());
            for (Calendario calendario : this.calendarioUfg) {
                if (calendario.getEvento().getNomeEvento().equals(nome)) {
                    retorno.append(calendario.getTexto());
                }
            }
        }

        return retorno;
    }

    /**
     * Verifica se a lista de eventos do sistema está vazia.
     *
     * @return True se não houver eventos na lista CalendarioUFG.
     */
    public boolean verificarVazio() {
        return this.calendarioUfg.isEmpty() == true;
    }

    /**
     * Retorna um StringBuilder contendo os resultados de uma pesquisa por
     * eventos com base na data inicial.
     *
     * @param data Data (dd/mm/aaaa) a ser pesquisada.
     * @return StringBuilder contendo o resultado da pesquisa exata.
     * @throws CalendarioVazio Quando o calendário não conter nenhum evento.
     */
    public StringBuilder exibirPorData(String data) throws CalendarioVazio {

        StringBuilder retorno = new StringBuilder();

        if (verificarVazio() == true) {
            throw new CalendarioVazio("O sistema não possui eventos"
                    + " cadastrados.");

        } else {

            retorno.append(cabecalhoConsulta());
            for (Calendario calendario : this.calendarioUfg) {
                if (calendario.getEvento().getDataInicio().equals(data)) {
                    retorno.append(calendario.getTexto());
                }
            }
        }

        return retorno;
    }

    /**
     * Retorna um StringBuilder contendo os resultados de uma pesquisa por
     * eventos com base na data inicial e em uma regional específica.
     *
     * @param data Data (dd/mm/aaaa) a ser pesquisada.
     * @param regional Regional a ser pesquisada.
     * @return StringBuilder contendo o resultado da pesquisa exata.
     * @throws CalendarioVazio Quando o calendário não conter nenhum evento.
     */
    public StringBuilder exibirPorData(String data, Regional regional)
            throws CalendarioVazio {

        StringBuilder retorno = new StringBuilder();

        if (verificarVazio() == true) {
            throw new CalendarioVazio("O sistema não possui eventos "
                    + "cadastrados.");

        } else {
            retorno.append(cabecalhoConsulta());
            for (Calendario calendario : this.calendarioUfg) {
                if (calendario.getEvento().getDataInicio().equals(data)
                        && calendario.getRegional() == regional) {
                    retorno.append(calendario.getTexto());
                }
            }
        }

        return retorno;
    }

    /**
     * Retorna um StringBuilder contendo os resultados de uma pesquisa por todos
     * os eventos registrados.
     *
     * @return StringBuilder contendo o resultado da pesquisa exata.
     * @throws CalendarioVazio Quando o calendário não conter nenhum evento.
     */
    public StringBuilder exibirTodosEventos() throws CalendarioVazio {

        StringBuilder retorno = new StringBuilder();

        if (verificarVazio() == true) {
            throw new CalendarioVazio("O sistema não possui eventos "
                    + "cadastrados.");

        } else {
            retorno.append(cabecalhoConsulta());
            for (Calendario calendario : this.calendarioUfg) {
                retorno.append(calendario.getTexto());
            }
        }
        return retorno;
    }

    /**
     * Retorna um StringBuilder contendo os resultados de uma pesquisa por todos
     * os eventos contendo determinada palavra no nome.
     *
     * @param nome Nome a ser pesquisado.
     * @return StringBuilder contendo o resultado da pesquisa exata.
     * @throws CalendarioVazio Quando o calendário não conter nenhum evento.
     */
    public StringBuilder exibirPorPalavraChave(String nome) throws
            CalendarioVazio {

        StringBuilder retorno = new StringBuilder();

        if (verificarVazio() == true) {
            throw new CalendarioVazio("O sistema não possui eventos "
                    + "cadastrados.");

        } else {
            retorno.append(cabecalhoConsulta());
            for (Calendario calendario : this.calendarioUfg) {
                if (calendario.getEvento().getNomeEvento()
                        .toLowerCase().contains(nome.toLowerCase())) {
                    retorno.append(calendario.getTexto());
                }
            }
        }
        return retorno;
    }

    /**
     * Retorna um StringBuilder contendo os resultados de uma pesquisa por todos
     * os eventos contendo determinada palavra no nome apenas em uma regional.
     *
     * @param nome Nome a ser pesquisado.
     * @param regional Regional a ser pesquisada.
     * @return StringBuilder contendo o resultado da pesquisa exata.
     * @throws CalendarioVazio Quando o calendário não conter nenhum evento.
     */
    public StringBuilder exibirPorPalavraChaveReg(String nome,
            Regional regional) throws CalendarioVazio {

        StringBuilder retorno = new StringBuilder();

        if (verificarVazio() == true) {
            throw new CalendarioVazio("O sistema não possui eventos "
                    + "cadastrados.");

        } else {
            retorno.append(cabecalhoConsulta());
            for (Calendario calendario : this.calendarioUfg) {
                if (calendario.getEvento().getNomeEvento()
                        .toLowerCase().contains(nome.toLowerCase())
                        && calendario.getRegional().equals(regional)) {
                    retorno.append(calendario.getTexto());
                }
            }
        }
        return retorno;
    }

    /**
     * Retorna um StringBuilder contendo os resultados de uma pesquisa por todos
     * os eventos de uma regional.
     *
     * @param regional Regional a ser pesquisada.
     * @return StringBuilder contendo o resultado da pesquisa exata.
     * @throws CalendarioVazio Quando o calendário não conter nenhum evento.
     */
    public StringBuilder exibirPorRegional(Regional regional) throws
            CalendarioVazio {

        StringBuilder retorno = new StringBuilder();

        if (verificarVazio() == true) {
            throw new CalendarioVazio("O sistema não possui eventos "
                    + "cadastrados.");

        } else {
            retorno.append(cabecalhoConsulta());
            for (Calendario calendario : this.calendarioUfg) {
                if (calendario.getRegional() == regional) {
                    retorno.append(calendario.getTexto());
                }
            }
        }
        return retorno;
    }

    /**
     * Retorna um StringBuilder contendo os resultados de uma pesquisa por todos
     * os eventos dentro de um período de datas.
     *
     * @param inicio Data inicial do período.
     * @param fim Data final do período.
     * @return StringBuilder contendo o resultado da pesquisa exata.
     * @throws CalendarioVazio Quando o calendário não conter nenhum evento.
     * @throws java.text.ParseException Quando não for possível transformar as
     * datas fornecidas em Date.
     */
    public StringBuilder exibirPorPeriodo(String inicio, String fim) throws
            CalendarioVazio, ParseException {

        Date dataInicio, dataFim;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        dataInicio = formatter.parse(inicio);
        dataFim = formatter.parse(fim);

        StringBuilder retorno = new StringBuilder();

        if (verificarVazio() == true) {
            throw new CalendarioVazio("O sistema não possui eventos "
                    + "cadastrados.");

        } else {
            retorno.append(cabecalhoConsulta());
            for (Calendario calendario : this.calendarioUfg) {
                if (calendario.getEvento().getInicio().before(dataFim)
                        && calendario.getEvento().getInicio().after(dataInicio)) {
                    retorno.append(calendario.getTexto());
                }
            }
        }
        return retorno;
    }

    /**
     * Retorna um StringBuilder contendo os resultados de uma pesquisa por todos
     * os eventos dentro de um período de datas, em uma regional específica.
     *
     * @param inicio Data inicial do período.
     * @param fim Data final do período.
     * @param regional Regional a ser pesquisada.
     * @return StringBuilder contendo o resultado da pesquisa exata.
     * @throws CalendarioVazio Quando o calendário não conter nenhum evento.
     * @throws java.text.ParseException Quando não for possível transformar as
     * datas fornecidas em Date.
     */
    public StringBuilder exibirPorPeriodoReg(String inicio, String fim,
            Regional regional) throws CalendarioVazio, ParseException {

        Date dataInicio, dataFim;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        dataInicio = formatter.parse(inicio);
        dataFim = formatter.parse(fim);

        StringBuilder retorno = new StringBuilder();

        if (verificarVazio() == true) {
            throw new CalendarioVazio("O sistema não possui eventos "
                    + "cadastrados.");

        } else {
            retorno.append(cabecalhoConsulta());
            for (Calendario calendario : this.calendarioUfg) {
                if (calendario.getEvento().getInicio().before(dataFim)
                        && calendario.getEvento().getInicio().after(dataInicio)
                        && calendario.getRegional().equals(regional)) {
                    retorno.append(calendario.getTexto());
                }
            }
        }
        return retorno;
    }

}
