package br.ufg.inf.mds.grupo.kappa.listaCalendario;

import br.ufg.inf.mds.grupo.kappa.calendario.Calendario;
import br.ufg.inf.mds.grupo.kappa.divisoes.Categoria;
import br.ufg.inf.mds.grupo.kappa.divisoes.Regional;
import br.ufg.inf.mds.grupo.kappa.excecao.CalendarioInvalido;
import br.ufg.inf.mds.grupo.kappa.excecao.CalendarioVazio;
import br.ufg.inf.mds.grupo.kappa.excecao.EventoInvalido;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
                Date c1Value = c1.getEvento().getInicio();
                Date c2Value = c2.getEvento().getInicio();
                return c1Value.compareTo(c2Value);
            });

        } else {
            throw new CalendarioInvalido("O evento fornecido não pode ser"
                    + " vazio.");
        }
    }

    /**
     * Permite remover um evento do calendário, com base em seu index.
     *
     * @param index Index do evento na lista calendário.
     * @throws ArrayIndexOutOfBoundsException Quando o index não existir na
     * lista.
     */
    public void removerCalendarioUfg(int index)
            throws ArrayIndexOutOfBoundsException {
        if (this.calendarioUfg.get(index) != null) {
            this.calendarioUfg.remove(index);
        } else {
            throw new ArrayIndexOutOfBoundsException();
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
                    && calendario.getEvento().getDataInicioString()
                    .equals(data)) {

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
     * @param index
     * @param novoEvento
     * @throws EventoInvalido Quando os dados informados não são validados.
     * @throws CalendarioInvalido Quando não é possível atualizar o evento
     * devido a erros de parâmetros.
     */
    public void atualizarCalendario(int index, Calendario novoEvento)
            throws EventoInvalido, CalendarioInvalido {

        if (calendarioUfg.get(index) != null) {
            calendarioUfg.remove(index);
            calendarioUfg.add(novoEvento);

            //Organiza a lista em ordem crescente pelas datas iniciais.
            Collections.sort(calendarioUfg, (Calendario c1, Calendario c2)
                    -> {
                Date c1Value = c1.getEvento().getInicio();
                Date c2Value = c2.getEvento().getInicio();
                return c1Value.compareTo(c2Value);
            });
        } else {
            throw new CalendarioInvalido("Evento não encontrado para "
                    + "atualização.");
        }
    }

    /**
     * Obtém os calendários desta lista.
     *
     * @return Lista de calendários contidos nesta lista calendário.
     */
    public List<Calendario> getCalendarioUfg() {
        return this.calendarioUfg;
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
            for (Calendario calendario : this.calendarioUfg) {
                if (calendario.getEvento().getNomeEvento().equals(nome)
                        && calendario.getEvento().getDataInicioString()
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
            for (Calendario calendario : this.calendarioUfg) {
                if (calendario.getEvento().getNomeEvento().equals(nome)) {
                    retorno.append(calendario.getTexto());
                }
            }
        }

        return retorno;
    }

    /**
     * Retorna todos os calendários desta lista que contêm um determinado nome.
     *
     * @param nome Nome a ser buscado.
     * @return Lista com os calendários cujo evento possui o nome buscado.
     * @throws CalendarioVazio Quando a lista calendário estiver vazia.
     */
    public List<Calendario> consultaNumerada(String nome)
            throws CalendarioVazio {
        List<Calendario> retorno = new ArrayList<>();

        if (verificarVazio() == true) {
            throw new CalendarioVazio("O sistema não possui eventos "
                    + "cadastrados.");

        } else {
            for (Calendario calendario : this.calendarioUfg) {
                if (calendario.getEvento().getNomeEvento().toLowerCase()
                        .contains(nome.toLowerCase())) {
                    retorno.add(calendario);
                }
            }
        }

        return retorno;
    }

    /**
     * Retorna o texto de consulta para uma consulta numerada, exibindo o index
     * de cada evento da consulta, com base na lista calendario total.
     *
     * @param result Resultado da consulta numerada cujo texto busca-se.
     * @return exto da consulta numerada fornecida.
     */
    public StringBuilder textoConsultaNumerada(List<Calendario> result) {
        StringBuilder retorno = new StringBuilder();
        for (Calendario calendario : result) {
            retorno.append("             | ");
            retorno.append(String.format("%04d",
                    calendarioUfg.indexOf(calendario)));
            retorno.append(") ");
            retorno.append(calendario.getEvento().getNomeEvento());
            retorno.append("\n");
            retorno.append("	     |       De  ");
            retorno.append(calendario.getEvento().getDataInicioString());
            retorno.append(" às ");
            retorno.append(calendario.getEvento().getHoraInicioString());
            retorno.append("\n");
            retorno.append("	     |       Até ");
            retorno.append(calendario.getEvento().getDataFimString());
            retorno.append(" às ");
            retorno.append(calendario.getEvento().getHoraFimString());
            retorno.append("\n	     |       ");
            retorno.append(Regional
                    .getNomesListados(calendario.getRegionais()));
            retorno.append("\n	     |       ");
            retorno.append(Categoria
                    .getNomesListados(calendario.getCategorias()));
            retorno.append("\n             |\n");
        }
        retorno = retorno.replace(retorno.length() - ("             |\\n")
                .length(), retorno.length(), "");
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

            for (Calendario calendario : this.calendarioUfg) {
                if (calendario.getEvento().getDataInicioString().equals(data)) {
                    retorno.append(calendario.getTexto());
                }
            }
        }

        return retorno;
    }

    /**
     * Retorna um StringBuilder contendo os resultados de uma pesquisa por
     * eventos com base na data inicial.
     *
     * @param data Data (dd/mm/aaaa) a ser pesquisada.
     * @param regionais Regionais que podem aparecer no resultado.
     * @return StringBuilder contendo o resultado da pesquisa exata.
     * @throws CalendarioVazio Quando o calendário não conter nenhum evento.
     */
    public StringBuilder exibirPorDataReg(String data, List<Regional> regionais)
            throws CalendarioVazio {

        StringBuilder retorno = new StringBuilder();

        if (verificarVazio() == true) {
            throw new CalendarioVazio("O sistema não possui eventos"
                    + " cadastrados.");

        } else {
            for (Calendario calendario : this.calendarioUfg) {
                boolean containsAny = false;
                for (Regional reg : regionais) {
                    if (calendario.getRegionais().contains(reg)) {
                        containsAny = true;
                        break;
                    }
                }
                if (containsAny && calendario.getEvento()
                        .getDataInicioString().equals(data)) {
                    retorno.append(calendario.getTexto());
                }
            }

            if (retorno.length() > 0) {
                //Remove primeira quebra de linha
                retorno.replace(0, 1, "");

                //Remove última linha de divisão
                retorno.replace(retorno.length() - 49, retorno.length(), "");
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
            for (Calendario calendario : this.calendarioUfg) {
                if (calendario.getEvento().getDataInicioString().equals(data)
                        && calendario.getRegionais().contains(regional)) {
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
     * @param regionais Regionais que podem aparecer no resultado.
     * @return StringBuilder contendo o resultado da pesquisa exata.
     * @throws CalendarioVazio Quando o calendário não conter nenhum evento.
     */
    public StringBuilder exibirPorPalavraChaveReg(String nome,
            List<Regional> regionais) throws CalendarioVazio {

        StringBuilder retorno = new StringBuilder();

        if (verificarVazio() == true) {
            throw new CalendarioVazio("O sistema não possui eventos "
                    + "cadastrados.");

        } else {
            for (Calendario calendario : this.calendarioUfg) {
                boolean containsAny = false;
                for (Regional reg : regionais) {
                    if (calendario.getRegionais().contains(reg)) {
                        containsAny = true;
                        break;
                    }
                }
                if (containsAny && calendario.getEvento().getNomeEvento()
                        .toLowerCase().contains(nome.toLowerCase())) {
                    retorno.append(calendario.getTexto());
                }
            }

            if (retorno.length() > 0) {
                //Remove primeira quebra de linha
                retorno.replace(0, 1, "");

                //Remove última linha de divisão
                retorno.replace(retorno.length() - 49, retorno.length(), "");
            }
        }
        return retorno;
    }

    /**
     * Retorna um StringBuilder contendo os resultados de uma pesquisa por todos
     * os eventos de uma regional.
     *
     * @param regionais Regional a ser pesquisada.
     * @return StringBuilder contendo o resultado da pesquisa exata.
     * @throws CalendarioVazio Quando o calendário não conter nenhum evento.
     */
    public StringBuilder exibirPorRegionais(List<Regional> regionais) throws
            CalendarioVazio {

        StringBuilder retorno = new StringBuilder();

        if (verificarVazio() == true) {
            throw new CalendarioVazio("O sistema não possui eventos "
                    + "cadastrados.");

        } else {
            for (Calendario calendario : this.calendarioUfg) {
                boolean containsAny = false;
                for (Regional reg : regionais) {
                    if (calendario.getRegionais().contains(reg)) {
                        containsAny = true;
                        break;
                    }
                }
                if (containsAny) {
                    retorno.append(calendario.getTexto());
                }
            }

            if (retorno.length() > 0) {
                //Remove primeira quebra de linha
                retorno.replace(0, 1, "");

                //Remove última linha de divisão
                retorno.replace(retorno.length() - 49, retorno.length(), "");
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
            for (Calendario calendario : this.calendarioUfg) {
                if (calendario.getRegionais().contains(regional)) {
                    retorno.append(calendario.getTexto());
                }
            }
        }
        return retorno;
    }

    /**
     * Retorna o texto de resultado de uma consulta de todos os eventos em
     * determinada(s) categorias. Limitado a regionais especificas.
     *
     * @param categorias Categorias a buscar.
     * @param regionais Regionais a buscar.
     * @return Texto de resultado com os eventos que possuem as categorias e
     * regionais listadas.
     * @throws CalendarioVazio Quando o calendário for vazio.
     */
    public StringBuilder exibirPorCategoriasReg(List<Categoria> categorias,
            List<Regional> regionais) throws
            CalendarioVazio {

        StringBuilder retorno = new StringBuilder();

        if (verificarVazio() == true) {
            throw new CalendarioVazio("O sistema não possui eventos "
                    + "cadastrados.");

        } else {
            for (Calendario calendario : this.calendarioUfg) {
                boolean containsCat = false;
                boolean containsReg = false;
                for (Categoria cat : categorias) {
                    if (calendario.getCategorias().contains(cat)) {
                        containsCat = true;
                        break;
                    }
                }
                for (Regional reg : regionais) {
                    if (calendario.getRegionais().contains(reg)) {
                        containsReg = true;
                        break;
                    }
                }
                if (containsCat && containsReg) {
                    retorno.append(calendario.getTexto());
                }
            }

            if (retorno.length() > 0) {
                //Remove primeira quebra de linha
                retorno.replace(0, 1, "");

                //Remove última linha de divisão
                retorno.replace(retorno.length() - 49, retorno.length(), "");
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
            for (Calendario calendario : this.calendarioUfg) {
                if (calendario.getEvento().getInicio().before(dataFim)
                        && calendario.getEvento().getInicio()
                        .after(dataInicio)) {
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
     * @param regionais Regional a ser pesquisada.
     * @return StringBuilder contendo o resultado da pesquisa exata.
     * @throws CalendarioVazio Quando o calendário não conter nenhum evento.
     * @throws java.text.ParseException Quando não for possível transformar as
     * datas fornecidas em Date.
     */
    public StringBuilder exibirPorPeriodoReg(String inicio, String fim,
            List<Regional> regionais) throws CalendarioVazio, ParseException {

        Date dataInicio, dataFim;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        dataInicio = formatter.parse(inicio);
        dataFim = formatter.parse(fim);

        StringBuilder retorno = new StringBuilder();

        if (verificarVazio() == true) {
            throw new CalendarioVazio("O sistema não possui eventos "
                    + "cadastrados.");

        } else {
            for (Calendario calendario : this.calendarioUfg) {
                boolean containsAny = false;
                for (Regional reg : regionais) {
                    if (calendario.getRegionais().contains(reg)) {
                        containsAny = true;
                        break;
                    }
                }
                if (containsAny && (((calendario.getEvento().getInicio()
                        .before(dataFim)
                        || calendario.getEvento().getInicio().equals(dataFim)))
                        && (calendario.getEvento().getInicio().
                        after(dataInicio))
                        || calendario.getEvento().getInicio()
                        .equals(dataInicio))) {
                    retorno.append(calendario.getTexto());
                }
            }

            if (retorno.length() > 0) {
                //Remove primeira quebra de linha
                retorno.replace(0, 1, "");

                //Remove última linha de divisão
                retorno.replace(retorno.length() - 49, retorno.length(), "");
            }
        }

        return retorno;
    }

}
