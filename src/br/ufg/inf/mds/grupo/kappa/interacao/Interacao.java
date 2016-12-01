package br.ufg.inf.mds.grupo.kappa.interacao;

import br.ufg.inf.mds.grupo.kappa.calendario.Calendario;
import br.ufg.inf.mds.grupo.kappa.calendario.Calendario.Regional;
import br.ufg.inf.mds.grupo.kappa.evento.Evento;
import br.ufg.inf.mds.grupo.kappa.excecao.CalendarioInvalido;
import br.ufg.inf.mds.grupo.kappa.excecao.CalendarioVazio;
import br.ufg.inf.mds.grupo.kappa.excecao.EntradaInvalida;
import br.ufg.inf.mds.grupo.kappa.excecao.EventoInvalido;
import br.ufg.inf.mds.grupo.kappa.excecao.RegionalInvalida;
import br.ufg.inf.mds.grupo.kappa.excecao.NomeInvalido;
import br.ufg.inf.mds.grupo.kappa.listaCalendario.ListaCalendario;
import br.ufg.inf.mds.grupo.kappa.login.Login;
import java.text.ParseException;
import java.util.Locale;
import java.util.Scanner;

/**
 * Essa classe é onde acontece a interação do usuário com o sistema (entradas e
 * saídas).
 *
 * @author Grupo Kappa
 */
public class Interacao {

    /**
     * Neste método se encontra o menu de operações para o usuário do sistema e
     * para o administrador.
     *
     * @param args Parâmetros opcionais na utilização do sistema não são
     * suportados.
     */
    public static void main(String[] args) {

        //Declaração dos objetos que serão manipulados na execução do sistema.
        ListaCalendario listaCalendario = new ListaCalendario();
        Scanner leitor = new Scanner(System.in, "UTF-8");
        leitor.useLocale(new Locale("pt", "BR"));
        int opcao = -1;

        //Exibição do menu principal até que o usuário saia
        do {

            Regional regional;

            //Exibe o menu
            exibirMenuPrincipal();

            //Processa a opção inserida
            try {
                opcao = inputOpcao();

                switch (opcao) {

                    //Acessar todas regionais
                    case 1:
                        menuTodasRegionais(leitor, listaCalendario);
                        break;

                    //Acessar Catalão
                    case 2:
                        pula();
                        regional = Regional.Catalao;
                        menuRegionalUnica(leitor, listaCalendario, regional);
                        break;

                    //Acessar Goiânia
                    case 3:
                        pula();
                        regional = Regional.Goiania;
                        menuRegionalUnica(leitor, listaCalendario, regional);
                        break;

                    //Acessar Goiás
                    case 4:
                        pula();
                        regional = Regional.Goias;
                        menuRegionalUnica(leitor, listaCalendario, regional);
                        break;

                    //Acessar Jataí
                    case 5:
                        pula();
                        regional = Regional.Jatai;
                        menuRegionalUnica(leitor, listaCalendario, regional);
                        break;

                    //Acesso do administrador
                    case 6:
                        loginAdministrador(leitor, listaCalendario);
                        break;

                    //Finalizar o sistema
                    case 0:
                        System.out.println("Finalizando...");
                        break;

                    //Opção inválida
                    default:
                        msgOpcaoNaoExistente();
                        pula();
                        break;
                }

            } catch (ParseException e) {
                msgOpcaoInvalida();
                pula();

            } catch (CalendarioVazio e) {
                msgCalendarioVazio();
                pula();
            }

        } while (opcao != 0);

        System.exit(0);

    }

    /**
     * Verifica se o resultado de uma consulta indica que ela foi vazia.
     *
     * @param resultConsulta Resultado da consulta.
     * @return Verdadeiro se a consulta foi vazia.
     */
    private static boolean verificaConsultaVazia(StringBuilder resultConsulta) {
        return (resultConsulta.toString().equals(ListaCalendario
                .cabecalhoConsulta()));
    }

    /**
     * Exibe e processa o menu para regionais específicas.
     *
     * @param entrada Origem da entrada de opções.
     * @param lista Lista de calendários contendo eventos.
     * @param regional Regional a ser manipulada neste menu.
     * @throws CalendarioVazio Quando o calendário não possui nenhum evento.
     * @throws ParseException Quando a opção inserida não for um número válido.
     */
    public static void menuRegionalUnica(Scanner entrada, ListaCalendario lista,
            Regional regional) throws CalendarioVazio, ParseException {

        exibirMenuRegionalUnica();

        StringBuilder resultadoConsulta;
        int opcao = inputOpcao();
        switch (opcao) {

            //Exibir todos eventos dessa regional
            case 1:
                resultadoConsulta = lista.exibirPorRegional(regional);
                if (verificaConsultaVazia(resultadoConsulta)) {
                    msgEventoNaoLocalizado();
                } else {
                    System.out.println(resultadoConsulta);
                }
                pula();
                break;

            //Exibir eventos dentro de um período
            case 2:

                System.out.println("+=========================================="
                        + "===================================================="
                        + "===================================================="
                        + "=====+");
                System.out.print("| * Digite a data inicial do período de busca"
                        + " (dd/mm/aaaa) :");
                String inicio = entrada.nextLine();

                System.out.println("|------------------------------------------"
                        + "----------------------------------------------------"
                        + "----------------------------------------------------"
                        + "-----|");
                System.out.print("| * Digite a data final do período de busca"
                        + "(dd/mm/aaaa) :");
                String fim = entrada.nextLine();

                System.out.println("+=========================================="
                        + "===================================================="
                        + "===================================================="
                        + "=====+");

                resultadoConsulta = lista.exibirPorPeriodoReg(inicio, fim,
                        regional);
                if (verificaConsultaVazia(resultadoConsulta)) {
                    msgEventoNaoLocalizado();
                } else {
                    System.out.println(resultadoConsulta);
                }
                pula();
                break;

            //Exibir eventos por palavra no nome
            case 3:
                System.out.println("+=========================================="
                        + "===================================================="
                        + "===================================================="
                        + "=====+");
                System.out.print("| * Digite a palavra que deseja buscar: ");
                System.out.println("+=========================================="
                        + "===================================================="
                        + "===================================================="
                        + "=====+");

                resultadoConsulta = lista
                        .exibirPorPalavraChaveReg(entrada.nextLine(), regional);

                if (verificaConsultaVazia(resultadoConsulta)) {
                    msgEventoNaoLocalizado();
                } else {
                    System.out.println(resultadoConsulta);
                }
                pula();
                break;

            //Exibir eventos por data específica
            case 4:
                System.out.println("+=========================================="
                        + "===================================================="
                        + "===================================================="
                        + "=====+");
                System.out.print("| * Digite a data que deseja buscar (dd/mm/aa"
                        + "aa): ");
                String data = entrada.nextLine();
                System.out.println("+=========================================="
                        + "===================================================="
                        + "===================================================="
                        + "=====+");

                resultadoConsulta = lista.exibirPorData(data, regional);
                if (verificaConsultaVazia(resultadoConsulta)) {
                    msgEventoNaoLocalizado();
                } else {
                    System.out.println(resultadoConsulta);
                }
                pula();
                break;

            //Voltar
            case 0:
                break;

            //Opção inválida
            default:
                msgOpcaoNaoExistente();
                pula();
                menuRegionalUnica(entrada, lista, regional);
                break;
        }
    }

    /**
     * Exibe e processa o menu para todas as regionais.
     *
     * @param leitor Leitor da entrada de dados.
     * @param lista Lista de eventos.
     * @throws CalendarioVazio Quando a lista de eventos estiver vazia.
     * @throws ParseException Quando não puder parsear um número.
     */
    public static void menuTodasRegionais(Scanner leitor,
            ListaCalendario lista) throws CalendarioVazio, ParseException {

        exibirMenuConsultasTodasRegionais();

        StringBuilder resultadoConsulta;
        int opcao = inputOpcao();

        switch (opcao) {

            //Exibir todos os eventos
            case 1:
                resultadoConsulta = lista.exibirTodosEventos();
                if (verificaConsultaVazia(resultadoConsulta)) {
                    msgEventoNaoLocalizado();
                } else {
                    System.out.println(resultadoConsulta);
                }
                pula();
                break;

            //Exibir todos os eventos num período
            case 2:
                System.out.println("+=========================================="
                        + "===================================================="
                        + "===================================================="
                        + "=====+");
                System.out.print("| * Digite a data inicial do período de busca"
                        + " (dd/mm/aaaa) :");
                String inicio = leitor.nextLine();

                System.out.println("|------------------------------------------"
                        + "----------------------------------------------------"
                        + "----------------------------------------------------"
                        + "-----|");
                System.out.print("| * Digite a data final do período de busca"
                        + "(dd/mm/aaaa) :");
                String fim = leitor.nextLine();

                System.out.println("+=========================================="
                        + "===================================================="
                        + "===================================================="
                        + "=====+");

                resultadoConsulta = lista.exibirPorPeriodo(inicio, fim);
                if (verificaConsultaVazia(resultadoConsulta)) {
                    msgEventoNaoLocalizado();
                } else {
                    System.out.println(resultadoConsulta);
                }
                pula();
                break;

            //Exibir eventos por palavra no nome
            case 3:
                System.out.println("+=========================================="
                        + "===================================================="
                        + "===================================================="
                        + "=====+");
                System.out.print("| * Digite a palavra que deseja buscar: ");

                System.out.println("+=========================================="
                        + "===================================================="
                        + "===================================================="
                        + "=====+");

                resultadoConsulta = lista
                        .exibirPorPalavraChave(leitor.nextLine());

                if (verificaConsultaVazia(resultadoConsulta)) {
                    msgEventoNaoLocalizado();
                } else {
                    System.out.println(resultadoConsulta);
                }
                pula();
                break;

            //Exibir eventos em data específica
            case 4:
                System.out.println("+=========================================="
                        + "===================================================="
                        + "===================================================="
                        + "=====+");
                System.out.print("| * Digite a data que deseja buscar (dd/mm/aa"
                        + "aa): ");
                String data = leitor.nextLine();
                System.out.println("+=========================================="
                        + "===================================================="
                        + "===================================================="
                        + "=====+");

                resultadoConsulta = lista.exibirPorData(data);
                if (verificaConsultaVazia(resultadoConsulta)) {
                    msgEventoNaoLocalizado();
                } else {
                    System.out.println(resultadoConsulta);
                }
                pula();
                break;

            //Voltar
            case 0:
                break;

            //Opção inexistente
            default:
                msgOpcaoNaoExistente();
                pula();
                menuTodasRegionais(leitor, lista);
                break;
        }
    }

    /**
     * Exibe e processa o login do administrador e seu menu.
     *
     * @param leitor Leitor de entrada de dados.
     * @param lista Lista de eventos do sistema.
     */
    private static void loginAdministrador(Scanner leitor,
            ListaCalendario lista) {

        Login senha = new Login();

        System.out.println("+=================================================="
                + "===================================+");
        System.out.println("|                   - Sistema de Calendario "
                + "Academico - SCA UFG -                     |");
        System.out.println("+=================================================="
                + "===================================+");

        System.out.print("| * LOGIN: ");
        String login = leitor.nextLine();

        System.out.print("| * SENHA: ");
        String password = leitor.nextLine();

        System.out.println("+================================================"
                + "=====================================+");

        //Checa usuário e senhas fornecidos
        if (!senha.validarSenha(login, password)) {

            //Usuário e senha inválidos
            msgUsuarioSenhaInvalidos();
            pula();

        } else {
            //Exibe o menu de administrador até o usuário sair
            int opcao = -1;
            do {
                pula();
                exibirMenuAdministrador();

                try {
                    opcao = inputOpcao();

                    switch (opcao) {
                        //Adição de novo evento
                        case 1:
                            adicionar(leitor, lista);
                            pula();
                            break;

                        //Remover evento
                        case 2:
                            remover(leitor, lista);
                            pula();
                            break;

                        //Atualizar evento
                        case 3:
                            atualizar(leitor, lista);
                            pula();
                            break;

                        //Pesquisar evento
                        case 4:
                            pesquisar(leitor, lista);
                            pula();
                            break;

                        //Voltar
                        case 0:
                            break;

                        //Opção inválida
                        default:
                            msgOpcaoNaoExistente();
                            pula();
                            exibirMenuAdministrador();
                            break;
                    }

                } catch (CalendarioVazio | CalendarioInvalido |
                        RegionalInvalida | NomeInvalido | EventoInvalido |
                        EntradaInvalida e) {
                    System.out.println(e.getMessage());
                    pula();
                } catch (ParseException e) {
                    msgDataInvalida();
                    pula();
                } catch (IndexOutOfBoundsException e) {
                    msgSucesso();
                    pula();
                } catch (NumberFormatException e) {
                    msgOpcaoInvalida();
                    pula();
                }

            } while (opcao != 0);
        }
    }

    /**
     * Exibe a remoção de um evento já cadastrado.
     *
     * @param leitor Scanner da entrada de dados.
     * @param lista Lista com os eventos do sistema.
     * @throws CalendarioVazio Quando o calendário estiver vazio.
     * @throws CalendarioInvalido Quando não for possível remover evento.
     */
    private static void remover(Scanner leitor, ListaCalendario lista)
            throws CalendarioVazio, CalendarioInvalido {

        System.out.println("+=================================================="
                + "============================================================"
                + "=========================================+");
        System.out.println("|                                                  "
                + "            - REMOVER EVENTO -                              "
                + "                                         |");
        System.out.println("+=================================================="
                + "============================================================"
                + "=========================================+");
        System.out.print("| * Digite o nome exato do evento a remover: ");
        String nome = leitor.nextLine();

        System.out.println("|--------------------------------------------------"
                + "------------------------------------------------------------"
                + "-----------------------------------------|");

        StringBuilder resultadoConsulta = lista.consultaExata(nome);
        if (verificaConsultaVazia(resultadoConsulta)) {
            msgEventoNaoLocalizado();

        } else {
            pula();
            System.out.println(resultadoConsulta);
            pula();

            System.out.println("|----------------------------------------------"
                    + "----"
                    + "-------------------------------------------------------"
                    + "-----"
                    + "-----------------------------------------|");

            System.out.print("| * Para confirmar, digite a data de início do "
                    + "evento a remover: ");
            String data = leitor.nextLine();

            System.out.println("|---------------------------------------------"
                    + "-----"
                    + "------------------------------------------------------"
                    + "------"
                    + "-----------------------------------------|");

            lista.removerCalendarioUfg(nome, data);
            msgRemocaoRealizada();

        }
    }

    /**
     * Permite atualizar um evento já cadastrado.
     *
     * @param entrada Scanner da entrada de dados.
     * @param lista Lista com os eventos cadastrados.
     * @throws CalendarioVazio Quando a lista de eventos for vazia.
     * @throws CalendarioInvalido Quando não achar o evento escolhido.
     * @throws ParseException Quando não for possível obter números de texto.
     * @throws NomeInvalido Quando o nome inserido for inválido.
     * @throws EventoInvalido Quando o evento não existir.
     * @throws EntradaInvalida Quando houver inconsistência nos dados inseridos.
     */
    private static void atualizar(Scanner entrada, ListaCalendario lista)
            throws CalendarioVazio, CalendarioInvalido,
            ParseException, NomeInvalido, EventoInvalido, EntradaInvalida {

        Evento evento = new Evento();

        System.out.println("+=================================================="
                + "============================================================"
                + "=========================================+");
        System.out.println("|                                                  "
                + "           - ATUALIZAR EVENTO -                             "
                + "                                         |");
        System.out.println("+=================================================="
                + "============================================================"
                + "=========================================+");

        System.out.print("| * Digite o nome do evento que deseja atualizar: ");
        String nome = entrada.nextLine();
        System.out.println("|--------------------------------------------------"
                + "------------------------------------------------------------"
                + "-----------------------------------------|");

        StringBuilder resultadoConsulta = lista.consultaExata(nome);
        if (verificaConsultaVazia(resultadoConsulta)) {
            msgEventoNaoLocalizado();

        } else {
            pula();
            System.out.println(resultadoConsulta);
            pula();

            System.out.println("|----------------------------------------------"
                    + "----"
                    + "--------------------------------------------------------"
                    + "----"
                    + "-----------------------------------------|");

            System.out.print("| * Para confirmar, digite a data de início do "
                    + "evento a atualizar: ");
            String data = entrada.nextLine();

            System.out.println("|----------------------------------------------"
                    + "----"
                    + "--------------------------------------------------------"
                    + "----"
                    + "-----------------------------------------|");

            System.out.print("| * Digite o novo nome do evento: ");
            evento.setNomeEvento(entrada.nextLine());
            System.out.println("|------------------------------------------"
                    + "----------------------------------------------------"
                    + "----------------------------------------------------"
                    + "-----|");

            System.out.print("| * Digite a nova data de início: ");
            evento.setDataInicio(entrada.nextLine());
            System.out.println("|------------------------------------------"
                    + "----------------------------------------------------"
                    + "----------------------------------------------------"
                    + "-----|");

            System.out.print("| * Digite a nova data de término: ");
            evento.setDataFim(entrada.nextLine());
            System.out.println("|------------------------------------------"
                    + "----------------------------------------------------"
                    + "----------------------------------------------------"
                    + "-----|");

            lista.atualizarCalendario(nome, data, evento);
            msgAtualizacaoRealizada();
        }
    }

    /**
     * Exibe e processa o menu de pesquisa de eventos do administrador.
     *
     * @param entrada Scanner da entrada de dados.
     * @param lista Lista de eventos do sistema.
     * @throws CalendarioVazio Quando o calendário estiver vazio.
     * @throws ParseException Quando não for possível parsar data.
     * @throws RegionalInvalida Quando for inserida regional inválida.
     */
    public static void pesquisar(Scanner entrada, ListaCalendario lista)
            throws CalendarioVazio, ParseException, RegionalInvalida {

        exibirMenuPesquisarEvento();

        StringBuilder resultadoConsulta;
        int opcao = inputOpcao();

        switch (opcao) {

            //Por regional
            case 1:
                System.out.println("+=========================================="
                        + "===================================================="
                        + "===================================================="
                        + "=====+");
                System.out.print("| * Digite o nome da regional: ");
                resultadoConsulta = lista.exibirPorRegional(Regional
                        .getRegionalFromNome(entrada.nextLine()));
                System.out.println("+=========================================="
                        + "===================================================="
                        + "===================================================="
                        + "=====+");

                if (verificaConsultaVazia(resultadoConsulta)) {
                    msgEventoNaoLocalizado();
                } else {
                    System.out.println(resultadoConsulta);
                }
                pula();
                break;

            //Por período
            case 2:
                System.out.println("+=========================================="
                        + "===================================================="
                        + "===================================================="
                        + "=====+");

                System.out.print("| * Digite a data inicial do período de busca"
                        + " (dd/mm/aaaa): ");
                String inicio = entrada.nextLine();

                System.out.println("|------------------------------------------"
                        + "----------------------------------------------------"
                        + "----------------------------------------------------"
                        + "-----|");

                System.out.print("| * Digite a data de término do período de "
                        + "busca (dd/mm/aaaa): ");
                String fim = entrada.nextLine();
                System.out.println("+=========================================="
                        + "===================================================="
                        + "===================================================="
                        + "=====+");

                resultadoConsulta = lista.exibirPorPeriodo(inicio, fim);
                if (verificaConsultaVazia(resultadoConsulta)) {
                    msgEventoNaoLocalizado();
                } else {
                    System.out.println(resultadoConsulta);
                }
                pula();
                break;

            //Por palavra-chave
            case 3:
                System.out.println("+=========================================="
                        + "===================================================="
                        + "===================================================="
                        + "=====+");
                System.out.print("| * Digite a palavra que deseja buscar: ");
                resultadoConsulta = lista.exibirPorPalavraChave(entrada
                        .nextLine());
                System.out.println("+=========================================="
                        + "===================================================="
                        + "===================================================="
                        + "=====+");

                if (verificaConsultaVazia(resultadoConsulta)) {
                    msgEventoNaoLocalizado();
                } else {
                    System.out.println(resultadoConsulta);
                }
                pula();
                break;

            //Todos
            case 4:
                resultadoConsulta = lista.exibirTodosEventos();
                if (verificaConsultaVazia(resultadoConsulta)) {
                    msgEventoNaoLocalizado();
                } else {
                    System.out.println(resultadoConsulta);
                }
                pula();
                break;

            //Por data
            case 5:
                System.out.println("+=========================================="
                        + "===================================================="
                        + "===================================================="
                        + "=====+");

                System.out.print("| * Digite a data para busca (dd/mm/aaaa): ");
                String inicioData = entrada.nextLine();

                System.out.println("+=========================================="
                        + "===================================================="
                        + "===================================================="
                        + "=====+");

                resultadoConsulta = lista.exibirPorData(inicioData);
                if (verificaConsultaVazia(resultadoConsulta)) {
                    msgEventoNaoLocalizado();
                } else {
                    System.out.println(resultadoConsulta);
                }
                pula();
                break;

            //Voltar
            case 0:
                break;

            //Opcao inexistente
            default:
                msgOpcaoNaoExistente();
                pula();
                pesquisar(entrada, lista);
                break;
        }

    }

    /**
     * Exibe e processa o menu de adição de eventos do administrador.
     *
     * @param leitor Scanner de entrada de dados.
     * @param lista Lista de eventos do sistema.
     */
    private static void adicionar(Scanner leitor, ListaCalendario lista) {

        try {
            exibirMenuAdicionarEvento();
            int opcao = inputOpcao();

            Regional regional;

            switch (opcao) {
                case 1:
                    pula();
                    regional = Regional.Todas;
                    lerEAdicionar(leitor, lista, regional);
                    pula();
                    break;
                case 2:
                    pula();
                    regional = Regional.Catalao;
                    lerEAdicionar(leitor, lista, regional);
                    pula();
                    break;
                case 3:
                    pula();
                    regional = Regional.Goiania;
                    lerEAdicionar(leitor, lista, regional);
                    pula();
                    break;
                case 4:
                    pula();
                    regional = Regional.Goias;
                    lerEAdicionar(leitor, lista, regional);
                    pula();
                    break;
                case 5:
                    pula();
                    regional = Regional.Jatai;
                    lerEAdicionar(leitor, lista, regional);
                    pula();
                    break;
                case 0:
                    break;
                default:
                    msgOpcaoNaoExistente();
                    pula();
                    adicionar(leitor, lista);
                    break;
            }

        } catch (NomeInvalido | EventoInvalido |
                CalendarioInvalido | RegionalInvalida e) {
            System.out.println(e.getMessage());
            msgOperacaoNaoRealizada();
            pula();
        } catch (ParseException | EntradaInvalida e) {
            msgDataInvalida();
            pula();
        } catch (NullPointerException e) {
            msgCaracteresEspeciais();
            pula();
        }
    }

    /**
     * Permite ler um novo evento e adicioná-lo à lista de eventos.
     *
     * @param leitor Scanner da entrada de dados.
     * @param lista Lista de evento do sistema.
     * @param regional Regional sendo manipulada no momento.
     * @throws ParseException
     * @throws NomeInvalido
     * @throws EventoInvalido
     * @throws CalendarioInvalido
     * @throws EntradaInvalida
     * @throws RegionalInvalida
     */
    private static void lerEAdicionar(Scanner leitor, ListaCalendario lista,
            Regional regional) throws ParseException, NomeInvalido,
            EventoInvalido, CalendarioInvalido, EntradaInvalida,
            RegionalInvalida {

        Evento evento = new Evento();
        Calendario calendario = new Calendario();

        System.out.println("+=================================================="
                + "============================================================"
                + "=========================================+");
        System.out.println("|                                                  "
                + "             - INSERIR EVENTO -                             "
                + "                                         |");
        System.out.println("+=================================================="
                + "============================================================"
                + "=========================================+");

        System.out.print("| * Insira o nome do evento: ");
        evento.setNomeEvento(leitor.nextLine());

        System.out.println("|------------------------------------------"
                + "----------------------------------------------------"
                + "----------------------------------------------------"
                + "-----|");

        System.out.print("| * Insira a data de início do evento (dd/mm/aaaa)"
                + ": ");
        evento.setDataInicio(leitor.nextLine());

        System.out.println("|------------------------------------------"
                + "----------------------------------------------------"
                + "----------------------------------------------------"
                + "-----|");

        System.out.print("| * Insira a data de término do evento (dd/mm/aaaa)"
                + ": ");
        evento.setDataFim(leitor.nextLine());

        System.out.println("+=================================================="
                + "============================================================"
                + "=========================================+");

        calendario.setRegional(regional);
        calendario.setEvento(evento);
        lista.adicionarCalendarioUfg(calendario);
        msgSucesso();
        pula();
    }

    /**
     * Método que pula linhas após selecionar um evento.
     */
    public static void pula() {
        System.out.println();
    }

    /**
     * Exibe o menu principal.
     */
    public static void exibirMenuPrincipal() {
        System.out.println("+=================================================="
                + "============================================================"
                + "=========================================+");
        System.out.println("|                                                  "
                + " - Sistema de Calendario Academico - SCA UFG -              "
                + "                                         |");
        System.out.println("+=================================================="
                + "============================================================"
                + "=========================================+");
        System.out.println("| * Escolha um número correspondente a opção "
                + "desejada:                                                   "
                + "                                               |");
        System.out.println("|--------------------------------------------------"
                + "------------------------------------------------------------"
                + "-----------------------------------------|");
        System.out.println("|                                                  "
                + "                 OPÇOES DE ACESSO                           "
                + "                                         |");
        System.out.println("+--------------------------------------------------"
                + "------------------------+-----------------------------------"
                + "-----------------------------------------+");
        System.out.println("|            1 - Acessar todas as regionais        "
                + "         3 - Acessar regional Goiânia                     "
                + "5 - Acessar regional Jataí                 |");
        System.out.println("|            2 - Acessar regional Catalão          "
                + "         4 - Acessar regional Goiás                       "
                + "6 - Acessar como administrador             |");
        System.out.println("+--------------------------------------------------"
                + "------------------------------------------------------------"
                + "-----------------------------------------+");
        System.out.println("|                                                  "
                + "                     0 - SAIR                               "
                + "                                         |");
        System.out.println("+=================================================="
                + "==========================================================="
                + "==========================================+");
    }

    /**
     * Exibe o menu de opções para regionais.
     */
    public static void exibirMenuRegionalUnica() {
        System.out.println("+=================================================="
                + "============================================================"
                + "=========================================+");
        System.out.println("|                                                  "
                + " - Sistema de Calendario Academico - SCA UFG -              "
                + "                                         |");
        System.out.println("+=================================================="
                + "============================================================"
                + "=========================================+");
        System.out.println("| * Escolha um número correspondente a opção "
                + "desejada:                                                "
                + "                                                  |");
        System.out.println("|--------------------------------------------------"
                + "------------------------------------------------------------"
                + "-----------------------------------------|");
        System.out.println("|                                                  "
                + "                    CONSULTAS                               "
                + "                                         |");
        System.out.println("+--------------------------------------------------"
                + "------------------------+-----------------------------------"
                + "-----------------------------------------+");
        System.out.println("|                               1 - Consultar "
                + "todos eventos dessa regional      "
                + "   2 - Consultar por período de datas                       "
                + "            |");
        System.out.println("|                               "
                + "3 - Consultar por palavra                          "
                + "4 - Consultar por data específica                           "
                + "         |");
        System.out.println("+-------------------------------------------------"
                + "------------------------------------------------------------"
                + "------------------------------------------+");
        System.out.println("|                                                 "
                + "                      0 - VOLTAR                           "
                + "                                           |");
        System.out.println("+=================================================="
                + "============================================================"
                + "=========================================+");
    }

    /**
     * Exibe o menu de opções para todas as regionais.
     */
    public static void exibirMenuConsultasTodasRegionais() {
        System.out.println("+=================================================="
                + "============================================================"
                + "=========================================+");
        System.out.println("|                                                  "
                + " - Sistema de Calendario Academico - SCA UFG -              "
                + "                                         |");
        System.out.println("+=================================================="
                + "============================================================"
                + "=========================================+");
        System.out.println("| * Escolha um número correspondente a opção "
                + "desejada:                                                   "
                + "                                               |");
        System.out.println("|--------------------------------------------------"
                + "------------------------------------------------------------"
                + "-----------------------------------------|");
        System.out.println("|                                                 "
                + "                     CONSULTAS                              "
                + "                                          |");
        System.out.println("+-------------------------------------------------"
                + "-------------------------+---------------------------------"
                + "-------------------------------------------+");
        System.out.println("|                               1 - Consultar todos"
                + " os eventos                         3 - Consultar evento por"
                + " nome                                    |");
        System.out.println("|                               2 - Consultar "
                + "evento por período de datas              4 - Consultar "
                + "evento por data específica                         |");
        System.out.println("+--------------------------------------------------"
                + "------------------------------------------------------------"
                + "-----------------------------------------+");
        System.out.println("|                                                  "
                + "                     0 - VOLTAR                             "
                + "                                         |");
        System.out.println("+=================================================="
                + "============================================================"
                + "=========================================+");
    }

    /**
     * Exibe o menu de opções para pesquisa de eventos do administrador.
     */
    public static void exibirMenuPesquisarEvento() {
        System.out.println("+=================================================="
                + "============================================================"
                + "=========================================+");
        System.out.println("|                                                  "
                + " - Sistema de Calendario Academico - SCA UFG -              "
                + "                                         |");
        System.out.println("+=================================================="
                + "============================================================"
                + "=========================================+");
        System.out.println("| * Escolha um número correspondente a opção "
                + "desejada:                                                  "
                + "                                                |");
        System.out.println("|--------------------------------------------------"
                + "------------------------------------------------------------"
                + "-----------------------------------------|");
        System.out.println("|                                                  "
                + "                    PESQUISAS                               "
                + "                                         |");
        System.out.println("+--------------------------------------------------"
                + "------------------------+-----------------------------------"
                + "-----------------------------------------+");
        System.out.println("|           1 - Consultar evento por regional      "
                + "       3 - Consultar evento por descrição               "
                + "5 - Consultar evento por data                |");
        System.out.println("|           2 - Consultar evento por periodo       "
                + "       4 - Consultar todos os eventos                       "
                + "                                         |");
        System.out.println("+-------------------------------------------------"
                + "-----------------------------------------------------------"
                + "-------------------------------------------+");
        System.out.println("|                                                 "
                + "                      0 - VOLTAR                           "
                + "                                           |");
        System.out.println("+================================================="
                + "==========================================================="
                + "===========================================+");
    }

    /**
     * Exibe o menu de adição de eventos do administrador.
     */
    public static void exibirMenuAdicionarEvento() {
        System.out.println("+=================================================="
                + "==========================================================="
                + "==========================================+");
        System.out.println("|                                                  "
                + " - Sistema de Calendario Academico - SCA UFG -              "
                + "                                         |");
        System.out.println("+=================================================="
                + "============================================================"
                + "=========================================+");
        System.out.println("| * Escolha um número correspondente a opção "
                + "desejada:                                                   "
                + "                                               |");
        System.out.println("|--------------------------------------------------"
                + "------------------------------------------------------------"
                + "-----------------------------------------|");
        System.out.println("|                                                  "
                + "                 OPÇOES DE ACESSO                           "
                + "                                         |");
        System.out.println("+--------------------------------------------------"
                + "------------------------+-----------------------------------"
                + "-----------------------------------------+");
        System.out.println("|           1 - Adicionar em todas as regionais    "
                + "           3 - Adicionar na regional Goiânia                "
                + " 5 - Adicionar na regional Jataí         |");
        System.out.println("|           2 - Adicionar na regional Catalão      "
                + "           4 - Adicionar na regional Goiás                  "
                + "                                         |");
        System.out.println("+--------------------------------------------------"
                + "------------------------------------------------------------"
                + "-----------------------------------------+");
        System.out.println("|                                                  "
                + "                     0 - VOLTAR                             "
                + "                                         |");
        System.out.println("+================================================="
                + "==========================================================="
                + "===========================================+");
    }

    /**
     * Exibe o menu do administrador.
     */
    public static void exibirMenuAdministrador() {
        System.out.println("+=================================================="
                + "============================================================"
                + "=========================================+");
        System.out.println("|                                                  "
                + " - Sistema de Calendario Academico - SCA UFG -              "
                + "                                         |");
        System.out.println("+=================================================="
                + "============================================================"
                + "=========================================+");
        System.out.println("| * Escolha um número correspondente a opção "
                + "desejada:                                                   "
                + "                                               |");
        System.out.println("|-------------------------------------------------"
                + "------------------------------------------------------------"
                + "------------------------------------------|");
        System.out.println("|                                                  "
                + "                    OPERACOES                               "
                + "                                         |");
        System.out.println("+--------------------------------------------------"
                + "------------------------+-----------------------------------"
                + "-----------------------------------------+");
        System.out.println("|                                  1 - Adicionar "
                + "evento                                           "
                + "3 - Atualizar evento                                  |");
        System.out.println("|                                  2 - Remover "
                + "evento                                             "
                + "4 - Pesquisar evento(s)                               |");
        System.out.println("+--------------------------------------------------"
                + "------------------------------------------------------------"
                + "-----------------------------------------+");
        System.out.println("|                                                  "
                + "                     0 - VOLTAR                             "
                + "                                         |");
        System.out.println("+================================================="
                + "============================================================"
                + "==========================================+");
    }

    /**
     * Exibe a mensagem de opção inexistente.
     */
    public static void msgOpcaoNaoExistente() {
        System.out.println("+=========================================="
                + "===========================================+");
        System.out.println("|                                    OPÇÃO "
                + "NÃO EXISTENTE!                             |");
        System.out.println("+=========================================="
                + "===========================================+");
    }

    /**
     * Exibe a mensagem de opção inválida.
     */
    public static void msgOpcaoInvalida() {
        System.out.println("+=================================================="
                + "===================================+");
        System.out.println("|                                    OPÇÃO INVÁLIDA"
                + "!                                  |");
        System.out.println("+=========================================="
                + "===========================================+");
    }

    /**
     * Exibe a mensagem de data inválida.
     */
    public static void msgDataInvalida() {
        System.out.println("+=================================================="
                + "===================================+");
        System.out.println("|                                    DATA INVÁLIDA!"
                + "                                   |");
        System.out.println("+=========================================="
                + "===========================================+");
    }

    /**
     * Exibe a mensagem de operação não realizada.
     */
    public static void msgOperacaoNaoRealizada() {
        System.out.println("+=================================================="
                + "===================================+");
        System.out.println("|                 OPERACAO NAO REALIZADA! FAVOR "
                + "TENTE NOVAMENTE...                    |");
        System.out.println("+=================================================="
                + "===================================+");
    }

    /**
     * Exibe a mensagem de erro por caracteres especiais.
     */
    public static void msgCaracteresEspeciais() {
        System.out.println("+=================================================="
                + "===================================+");
        System.out.println("|       NAO É PERMITIDO O USO DE CARACTERES "
                + "ESPECIAIS! FAVOR TENTE NOVAMENTE...       |");
        System.out.println("+=========================================="
                + "===========================================+");
    }

    /**
     * Exibe a mensagem de sucesso.
     */
    public static void msgSucesso() {
        System.out.println("+=================================================="
                + "===================================+");
        System.out.println("|                           OPERAÇÃO REALIZADA COM "
                + "SUCESSO!                           |");
        System.out.println("+=========================================="
                + "===========================================+");
    }

    /**
     * Exibe a mensagem de remoção.
     */
    public static void msgRemocaoRealizada() {
        System.out.println("+=================================================="
                + "===================================+");
        System.out.println("|                         REMOCAO REALIZADA COM "
                + "SUCESSO!                              |");
        System.out.println("+=================================================="
                + "===================================+");
    }

    /**
     * Exibe a mensagem de atualização.
     */
    public static void msgAtualizacaoRealizada() {
        System.out.println("+=================================================="
                + "===================================+");
        System.out.println("|                       ATUALIZACAO REALIZADA COM "
                + "SUCESSO!                            |");
        System.out.println("+=================================================="
                + "===================================+");
    }

    /**
     * Exibe a mensagem de evento não localizado.
     */
    public static void msgEventoNaoLocalizado() {
        System.out.println("+=================================================="
                + "===================================+");
        System.out.println("|                            EVENTO NAO LOCALIZADO"
                + "                                    |");
        System.out.println("+=========================================="
                + "===========================================+");
        pula();
    }

    /**
     * Exibe a mensagem de usuário e senha incorretos.
     */
    public static void msgUsuarioSenhaInvalidos() {
        System.out.println("+=============================================="
                + "=======================================+");
        System.out.println("|                             USUARIO OU SENHA "
                + "INVALIDOS!                             |");
        System.out.println("+=============================================="
                + "=======================================+");
    }

    /**
     * Exibe e processa uma opção de menu.
     *
     * @return A opção fornecida pelo usuário.
     */
    public static int inputOpcao() {
        Scanner entrada = new Scanner(System.in, "UTF-8");

        System.out.print("| DIGITE A OPÇÃO DESEJADA: ");
        int opcao = -1;
        try {
            opcao = Integer.parseInt(entrada.nextLine());
            System.out.println("+========================================"
                    + "===================================================="
                    + "==================================================="
                    + "========+");
            pula();
        } catch (NumberFormatException e) {
            System.out.println("+========================================"
                    + "===================================================="
                    + "==================================================="
                    + "========+");
            pula();
        }

        return opcao;
    }

    /**
     * Exibe a mensagem de operação cancelada.
     */
    public static void msgOperacaoCancelada() {
        System.out.println("+=================================================="
                + "===================================+");
        System.out.println("|                         OPERACAO CANCELADA PELO "
                + "USUARIO                             |");
        System.out.println("+=========================================="
                + "===========================================+");
    }

    /**
     * Exibe a mensagem de calendário vazio.
     */
    public static void msgCalendarioVazio() {
        System.out.println("+=================================================="
                + "===================================+");
        System.out.println("|                                CALENDÁRIO VAZIO "
                + "                                    |");
        System.out.println("+=========================================="
                + "===========================================+");
    }
}
