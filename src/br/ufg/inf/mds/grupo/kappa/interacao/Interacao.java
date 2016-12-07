package br.ufg.inf.mds.grupo.kappa.interacao;

import br.ufg.inf.mds.grupo.kappa.calendario.Calendario;
import br.ufg.inf.mds.grupo.kappa.divisoes.Categoria;
import br.ufg.inf.mds.grupo.kappa.divisoes.Regional;
import br.ufg.inf.mds.grupo.kappa.evento.Evento;
import br.ufg.inf.mds.grupo.kappa.excecao.CalendarioInvalido;
import br.ufg.inf.mds.grupo.kappa.excecao.CalendarioVazio;
import br.ufg.inf.mds.grupo.kappa.excecao.EntradaInvalida;
import br.ufg.inf.mds.grupo.kappa.excecao.EventoInvalido;
import br.ufg.inf.mds.grupo.kappa.excecao.RegionalInvalida;
import br.ufg.inf.mds.grupo.kappa.excecao.NomeInvalido;
import br.ufg.inf.mds.grupo.kappa.listaCalendario.ListaCalendario;
import br.ufg.inf.mds.grupo.kappa.login.Admin;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        Admin admin = new Admin();
        System.out.println("Iniciando...");
        int opcao = -1;

        //PRESET EVENTOS PARA TESTES
        //< R E M O V E R>
        Evento newEvento = new Evento();
        try {
            newEvento.setDataInicio("02/02/2000");
            newEvento.setDataFim("03/02/2000");
            newEvento.setHoraInicio("06:00");
            newEvento.setHoraFim("09:00");
            newEvento.setNomeEvento("Evento A");
        } catch (ParseException | EntradaInvalida | NomeInvalido ex) {
            Logger.getLogger(Interacao.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        Calendario newCalendairo = new Calendario();
        try {
            newCalendairo.addCategoria(Categoria.DOCENTE);
            newCalendairo.addCategoria(Categoria.SERVIDOR);
            newCalendairo.addRegional(Regional.GOIAS);
            newCalendairo.addRegional(Regional.GOIANIA);
            newCalendairo.setEvento(newEvento);
        } catch (EntradaInvalida | EventoInvalido ex) {
            Logger.getLogger(Interacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            listaCalendario.adicionarCalendarioUfg(newCalendairo);
        } catch (CalendarioInvalido ex) {
            Logger.getLogger(Interacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        newEvento = new Evento();
        try {
            newEvento.setDataInicio("02/03/2000");
            newEvento.setDataFim("03/04/2000");
            newEvento.setHoraInicio("08:00");
            newEvento.setHoraFim("07:00");
            newEvento.setNomeEvento("Evento B");
        } catch (ParseException | EntradaInvalida | NomeInvalido ex) {
            Logger.getLogger(Interacao.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        newCalendairo = new Calendario();
        try {
            newCalendairo.addCategoria(Categoria.DISCENTE);
            newCalendairo.addCategoria(Categoria.COMUNIDADE);
            newCalendairo.addRegional(Regional.JATAI);
            newCalendairo.addRegional(Regional.GOIANIA);
            newCalendairo.setEvento(newEvento);
        } catch (EntradaInvalida | EventoInvalido ex) {
            Logger.getLogger(Interacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            listaCalendario.adicionarCalendarioUfg(newCalendairo);
        } catch (CalendarioInvalido ex) {
            Logger.getLogger(Interacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</ R E M O V E R>

        //Exibição do menu principal até que o usuário saia
        do {

            List<Regional> regionais;

            //Exibe o menu
            pula();
            exibirMenuPrincipal();

            //Processa a opção inserida
            try {
                opcao = inputOpcao();

                switch (opcao) {

                    //Acessar todas regionais
                    case 1:
                        regionais = new ArrayList<>();
                        regionais.add(Regional.GOIAS);
                        regionais.add(Regional.CATALAO);
                        regionais.add(Regional.GOIANIA);
                        regionais.add(Regional.JATAI);
                        pula();
                        menuRegionaisEspecificas(leitor, listaCalendario,
                                regionais);
                        break;

                    //Acessar regional especifica
                    case 2:
                        regionais = inputRegional(0);
                        if (regionais != null) {
                            pula();
                            menuRegionaisEspecificas(leitor, listaCalendario,
                                    regionais);
                        }
                        break;

                    //Acesso do administrador
                    case 3:
                        loginAdministrador(leitor, listaCalendario, admin);
                        break;

                    //Finalizar o sistema
                    case 0:
                        System.out.println("\nFinalizando...");
                        break;

                    //Opção inválida
                    default:
                        msgOpcaoNaoExistente();
                        pula();
                        break;
                }

            } catch (ParseException e) {
                msgOpcaoInvalida();

            } catch (CalendarioVazio e) {
                msgCalendarioVazio();
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
        return (resultConsulta.toString().equals(""));
    }

    /**
     * Exibe e processa o menu para regionais específicas.
     *
     * @param entrada Origem da entrada de opções.
     * @param lista Lista de calendários contendo eventos.
     * @param regionais Regional a ser manipulada neste menu.
     * @throws CalendarioVazio Quando o calendário não possui nenhum evento.
     * @throws ParseException Quando a opção inserida não for um número válido.
     */
    public static void menuRegionaisEspecificas(Scanner entrada,
            ListaCalendario lista, List<Regional> regionais)
            throws CalendarioVazio, ParseException {

        StringBuilder resultadoConsulta;
        int opcao = -1;

        while (opcao != 0) {
            exibirMenuRegionaisEspecificas(regionais);

            opcao = inputOpcao(1);

            try {
                switch (opcao) {
                    //Exibir todos eventos dessa regional
                    case 1:
                        resultadoConsulta = lista.exibirPorRegionais(regionais);
                        if (verificaConsultaVazia(resultadoConsulta)) {
                            System.out.println("vazio");
                            //throw new CalendarioVazio("");
                        } else {
                            pula();
                            System.out.println("          +===================="
                                    + "=======================+\n"
                                    + "          |   EVENTOS ENCONTRADOS       "
                                    + "              |\n"
                                    + "          +============================="
                                    + "==============+");
                            System.out.println(resultadoConsulta);
                            System.out.println("	  +--------------------"
                                    + "-----------------------+");
                            pula();
                        }
                        break;

                    //Exibir eventos por palavra no nome
                    case 2:
                        System.out.print("     | Informe a palavra a buscar.\n"
                                + "     | PALAVRA: ");

                        resultadoConsulta = lista
                                .exibirPorPalavraChaveReg(entrada.nextLine(),
                                        regionais);

                        if (verificaConsultaVazia(resultadoConsulta)) {
                            linhaNivelada(1);
                            msgEventoNaoLocalizado();
                            pula();

                        } else {
                            linhaNivelada(1);
                            pula();
                            System.out.println("          +===================="
                                    + "=======================+\n"
                                    + "          |   EVENTOS ENCONTRADOS       "
                                    + "              |\n"
                                    + "          +============================="
                                    + "==============+");
                            System.out.println(resultadoConsulta);
                            System.out.println("	  +--------------------"
                                    + "-----------------------+");
                            pula();
                        }
                        break;

                    //Exibir eventos por data específica
                    case 3:
                        System.out.print("     | Informe a data a buscar.\n");

                        Evento evento2 = new Evento();
                        boolean set2 = false;

                        while (!set2) {
                            System.out.print("     | DATA (dd/mm/aaaa): ");
                            try {
                                evento2.setDataInicio(entrada.nextLine());
                                set2 = true;
                            } catch (EntradaInvalida ni) {
                                System.out.println("     | (!) Data inválida.");
                            } catch (ParseException pe) {
                                System.out.println("     | (!) Formato de data "
                                        + "inválido.");
                            }
                        }

                        resultadoConsulta = lista.exibirPorDataReg(evento2
                                .getDataInicioString(), regionais);

                        if (verificaConsultaVazia(resultadoConsulta)) {
                            linhaNivelada(1);
                            msgEventoNaoLocalizado();
                            pula();

                        } else {
                            linhaNivelada(1);
                            pula();
                            System.out.println("          +===================="
                                    + "=======================+\n"
                                    + "          |   EVENTOS ENCONTRADOS       "
                                    + "              |\n"
                                    + "          +============================="
                                    + "==============+");
                            System.out.println(resultadoConsulta);
                            System.out.println("	  +--------------------"
                                    + "-----------------------+");
                            pula();
                        }
                        break;

                    //Exibir por periodo
                    case 4:
                        Evento evento = new Evento();
                        boolean set = false;

                        while (!set) {
                            System.out.print("     | DATA MÍNIMA (dd/mm/aaaa):"
                                    + " ");
                            try {
                                evento.setDataInicio(entrada.nextLine());
                                set = true;
                            } catch (EntradaInvalida ni) {
                                System.out.println("     | (!) Data inválida.");
                            } catch (ParseException pe) {
                                System.out.println("     | (!) Formato de data "
                                        + "inválido.");
                            }
                        }
                        set = false;

                        while (!set) {
                            System.out.print("     | DATA MÁXIMA (dd/mm/aaaa): "
                                    + "");
                            try {
                                evento.setDataFim(entrada.nextLine());
                                set = true;
                            } catch (EntradaInvalida ni) {
                                System.out.println("     | (!) Data inválida.");
                            } catch (ParseException pe) {
                                System.out.println("     | (!) Formato de data "
                                        + "inválido.");
                            }
                        }

                        String inicio = evento.getDataInicioString();
                        String fim = evento.getDataFimString();

                        resultadoConsulta = lista.exibirPorPeriodoReg(inicio,
                                fim, regionais);

                        if (verificaConsultaVazia(resultadoConsulta)) {
                            linhaNivelada(1);
                            msgEventoNaoLocalizado();
                            pula();

                        } else {
                            linhaNivelada(1);
                            pula();
                            System.out.println("          +===================="
                                    + "=======================+\n"
                                    + "          |   EVENTOS ENCONTRADOS       "
                                    + "              |\n"
                                    + "          +============================="
                                    + "==============+");
                            System.out.println(resultadoConsulta);
                            System.out.println("	  +--------------------"
                                    + "-----------------------+");
                            pula();
                        }
                        break;

                    //Por categorias
                    case 5:
                        Calendario calend = new Calendario();
                        boolean set3 = false;
                        List<Categoria> categorias = new ArrayList<>();

                        while (!set3) {
                            System.out.print("     | Informe os números das cat"
                                    + "egorias a "
                                    + "buscar.\n"
                                    + "     | Se mais de uma, separe-os por vír"
                                    + "gulas.\n"
                                    + "     | 1) Discente    2) Docente\n"
                                    + "     | 3) Servidor    4) Comunidade Acad"
                                    + "êmica\n"
                                    + "     |\n"
                                    + "     | CATEGORIAS: ");

                            categorias = new ArrayList<>();
                            String input = entrada.nextLine().replace(" ", "")
                                    .trim();

                            if (input.replace(",", "").isEmpty()) {
                                System.out.println("     | (!) Categorias invál"
                                        + "idas.");
                                System.out.println("     |");
                                categorias = null;
                            } else {
                                String[] partes = input.split(",");
                                for (String parte : partes) {
                                    try {
                                        int opt = Integer.parseInt(parte);
                                        if (opt < 1 || opt > 4) {
                                            IllegalArgumentException i;
                                            i = new IllegalArgumentException();
                                            throw i;
                                        } else {
                                            Categoria disc = Categoria.DISCENTE;
                                            Categoria docen = Categoria.DOCENTE;
                                            Categoria serv = Categoria.SERVIDOR;
                                            Categoria co = Categoria.COMUNIDADE;

                                            switch (opt) {
                                                case 1:
                                                    if (!categorias
                                                            .contains(disc)) {
                                                        categorias.add(disc);
                                                    }
                                                    break;
                                                case 2:
                                                    if (!categorias
                                                            .contains(docen)) {
                                                        categorias.add(docen);
                                                    }
                                                    break;
                                                case 3:
                                                    if (!categorias
                                                            .contains(serv)) {
                                                        categorias.add(serv);
                                                    }
                                                    break;
                                                case 4:
                                                    if (!categorias.
                                                            contains(co)) {
                                                        categorias.add(co);
                                                    }
                                                    break;
                                                default:
                                                    msgOpcaoNaoExistente();
                                                    break;
                                            }
                                        }
                                    } catch (IllegalArgumentException e) {
                                        System.out.println("     | (!) Categori"
                                                + "as inválidas.");
                                        System.out.println("     |");
                                        categorias = null;
                                        break;
                                    }
                                }
                            }

                            if (categorias != null) {
                                for (Categoria cat : categorias) {
                                    try {
                                        calend.addCategoria(cat);
                                        System.out.println("     | " + Categoria
                                                .getNomesListados(categorias));
                                        set3 = true;
                                    } catch (EntradaInvalida ex) {
                                        System.out.println("     | (!) Categori"
                                                + "as inválidas.");
                                    }
                                }
                            }
                        }

                        resultadoConsulta = lista
                                .exibirPorCategoriasReg(categorias, regionais);

                        if (verificaConsultaVazia(resultadoConsulta)) {
                            linhaNivelada(1);
                            msgEventoNaoLocalizado();
                            pula();

                        } else {
                            linhaNivelada(1);
                            pula();
                            System.out.println("          +===================="
                                    + "=======================+\n"
                                    + "          |   EVENTOS ENCONTRADOS       "
                                    + "              |\n"
                                    + "          +============================="
                                    + "==============+");
                            System.out.println(resultadoConsulta);
                            System.out.println("	  +--------------------"
                                    + "-----------------------+");
                            pula();
                        }

                        break;

                    //Voltar
                    case 0:
                        break;

                    //Opção inválida
                    default:
                        msgOpcaoNaoExistente();
                        pula();
                        opcao = 0;
                        menuRegionaisEspecificas(entrada, lista, regionais);
                        break;
                }
            } catch (CalendarioVazio c) {
                linhaNivelada(1);
                msgCalendarioVazio();
                pula();
            }
        }
    }

    /**
     * Exibe e processa o login do administrador e seu menu.
     *
     * @param leitor Leitor de entrada de dados.
     * @param lista Lista de eventos do sistema.
     */
    private static void loginAdministrador(Scanner leitor,
            ListaCalendario lista, Admin admin) {

        System.out.print("| LOGIN: ");
        String login = leitor.nextLine();

        System.out.print("| SENHA: ");
        String password = leitor.nextLine();

        linhaNivelada(0);

        //Checa usuário e senhas fornecidos
        if (!admin.validarSenha(login, password)) {

            //Usuário e senha inválidos
            msgUsuarioSenhaInvalidos();

        } else {
            //Exibe o menu de administrador até o usuário sair
            int opcao = -1;
            do {
                pula();
                exibirMenuAdministrador();

                try {
                    opcao = inputOpcao(1);

                    switch (opcao) {
                        //Adição de novo evento
                        case 1:
                            adicionar(leitor, lista);
                            break;

                        //Remover evento
                        case 3:
                            remover(leitor, lista);
                            break;

                        //Atualizar evento
                        case 2:
                            atualizar(leitor, lista);
                            break;

                        //Pesquisar evento
                        case 4:
                            List<Regional> regionais = inputRegional(1);
                            if (regionais != null) {
                                pula();
                                menuRegionaisEspecificas(leitor, lista,
                                        regionais);
                            }
                            //pesquisar(leitor, lista);
                            break;

                        //Alterar credenciais
                        case 5:
                            alterarCredenciais(leitor, admin);
                            break;

                        //Voltar
                        case 0:
                            break;

                        //Opção inválida
                        default:
                            msgOpcaoNaoExistente();
                            break;
                    }

                } catch (CalendarioVazio | CalendarioInvalido |
                        NomeInvalido | EventoInvalido |
                        EntradaInvalida e) {
                    System.out.println(e.getMessage());
                } catch (ParseException | IndexOutOfBoundsException e) {
                } catch (NumberFormatException e) {
                    msgOpcaoInvalida();
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

        System.out.print("\n          +========================================"
                + "===+\n"
                + "          |   REMOVER EVENTO                          |\n"
                + "          +===========================================+\n"
                + "	  | Informe o nome do evento a remover.\n"
                + "	  | NOME: ");
        String nome = leitor.nextLine();

        if (lista.verificarVazio()) {
            linhaNivelada(2);
            msgCalendarioVazio();

        } else {
            List<Calendario> resultadoConsulta = lista.consultaNumerada(nome);
            if (resultadoConsulta.isEmpty()) {
                linhaNivelada(2);
                msgEventoNaoLocalizado();

            } else {
                linhaNivelada(2);
                System.out.println(lista
                        .textoConsultaNumerada(resultadoConsulta));

                linhaNivelada(2);

                System.out.println("          | Informe o número de um "
                        + "evento encontrado\n"
                        + "          | para removê-lo definitivamente.");
                System.out.print("          | NÚMERO: ");
                int numParaDeletar = Integer.parseInt(leitor.nextLine());

                try {
                    lista.removerCalendarioUfg(numParaDeletar);
                    System.out.println("          +----------------------------"
                            + "---"
                            + "------------+  \n"
                            + "	  | (*) Evento removido com sucesso.\n"
                            + "          +-------------------------------------"
                            + "----"
                            + "--+   ");
                } catch (IndexOutOfBoundsException aioufbe) {
                    linhaNivelada(2);

                    msgEventoNaoLocalizado();
                }
            }
        }
    }

    /**
     * Permite atualizar um evento já cadastrado.
     *
     * @param leitor Scanner da entrada de dados.
     * @param lista Lista com os eventos cadastrados.
     * @throws CalendarioVazio Quando a lista de eventos for vazia.
     * @throws CalendarioInvalido Quando não achar o evento escolhido.
     * @throws ParseException Quando não for possível obter números de texto.
     * @throws NomeInvalido Quando o nome inserido for inválido.
     * @throws EventoInvalido Quando o evento não existir.
     * @throws EntradaInvalida Quando houver inconsistência nos dados inseridos.
     */
    private static void atualizar(Scanner leitor, ListaCalendario lista)
            throws CalendarioVazio, CalendarioInvalido,
            ParseException, NomeInvalido, EventoInvalido, EntradaInvalida {

        Evento evento = new Evento();

        System.out.print("\n          +========================================"
                + "===+\n"
                + "          |   ATUALIZAR EVENTO                        |\n"
                + "          +===========================================+\n"
                + "	  | Informe o nome do evento a atualizar.\n"
                + "	  | NOME: ");
        String nome = leitor.nextLine();

        if (lista.verificarVazio()) {
            linhaNivelada(2);
            msgCalendarioVazio();

        } else {
            List<Calendario> resultadoConsulta = lista.consultaNumerada(nome);
            if (resultadoConsulta.isEmpty()) {
                linhaNivelada(2);
                msgEventoNaoLocalizado();

            } else {
                linhaNivelada(2);
                System.out.println(lista
                        .textoConsultaNumerada(resultadoConsulta));

                linhaNivelada(2);

                System.out.println("          | Informe o número de um "
                        + "evento encontrado\n"
                        + "          | para atualizá-lo.");
                System.out.print("          | NÚMERO: ");
                int numParaAtualizar = Integer.parseInt(leitor.nextLine());

                try {
                    lista.getCalendarioUfg().get(numParaAtualizar);
                    try {
                        lerEAtualizar(leitor, lista, numParaAtualizar);
                        System.out.println("          +------------------------"
                                + "-------"
                                + "------------+  \n	  | (*) Evento atualiza"
                                + "do com sucesso.\n"
                                + "          +---------------------------------"
                                + "--------"
                                + "--+   ");
                    } catch (IndexOutOfBoundsException aioufbe) {
                        linhaNivelada(2);

                        msgEventoNaoLocalizado();
                    }
                } catch (IndexOutOfBoundsException c) {
                    linhaNivelada(2);

                    msgEventoNaoLocalizado();
                }

            }
        }
    }

    /**
     * Permite selecionar um evento da lista e reinserir suas informações.
     *
     * @param leitor Entrada dos dados.
     * @param lista Lista calendario dos eventos diponiveis.
     * @param indexPraAtualizar Index do evento a atualizar (da lista inserida).
     * @throws br.ufg.inf.mds.grupo.kappa.excecao.EntradaInvalida Quando a
     * entrada for inválida.
     * @throws br.ufg.inf.mds.grupo.kappa.excecao.EventoInvalido Quando o evento
     * escolhido for inválido.
     * @throws br.ufg.inf.mds.grupo.kappa.excecao.CalendarioInvalido Quando o
     * calendário for inválido.
     */
    public static void lerEAtualizar(Scanner leitor, ListaCalendario lista,
            int indexPraAtualizar) throws EventoInvalido,
            CalendarioInvalido,
            EntradaInvalida {

        Evento evento = new Evento();
        Calendario calendario = new Calendario();
        boolean set = false;

        System.out.println("          +======================================="
                + "====+\n"
                + "          |   ATUALIZAR EVENTO                        |\n"
                + "          +===========================================+\n"
                + "	  | Preencha os novos dados do evento.\n"
                + "	  |");

        //LEITURA DO NOME
        while (!set) {
            System.out.print("	  | NOME: ");
            try {
                evento.setNomeEvento(leitor.nextLine());
                set = true;
            } catch (NomeInvalido ni) {
                System.out.println("	  | (!) Nome de evento inválido.");
            }
        }
        set = false;

        System.out.println("	  | - - - - - - - - - - - - - - - - - - - - - "
                + "+");

        //LEITURA DA DATA INICIAL
        while (!set) {
            System.out.print("	  | DATA DE INÍCIO (dd/mm/aaaa): ");
            try {
                evento.setDataInicio(leitor.nextLine());
                set = true;
            } catch (EntradaInvalida ni) {
                System.out.println("	  | (!) Data inválida.");
            } catch (ParseException pe) {
                System.out.println("	  | (!) Formato de data inválido.");
            }
        }
        set = false;

        //LEITURA DA HORA INICIAL
        while (!set) {
            System.out.print("	  | HORA DE INÍCIO (hh:mm): ");
            try {
                evento.setHoraInicio(leitor.nextLine());
                set = true;
            } catch (DateTimeParseException ni) {
                System.out.println("	  | (!) Horário inválido.");
            }
        }
        set = false;

        System.out.println("	  | - - - - - - - - - - - - - - - - - - - - - "
                + "+");

        //LEITURA DA DATA FINAL
        while (!set) {
            System.out.print("	  | DATA DE TÉRMINO (dd/mm/aaaa): ");
            try {
                evento.setDataFim(leitor.nextLine());
                set = true;
            } catch (EntradaInvalida ni) {
                System.out.println("	  | (!) Data inválida.");
            } catch (ParseException pe) {
                System.out.println("	  | (!) Formato de data inválido.");
            }
        }
        set = false;

        //LEITURA DA HORA FINAL
        while (!set) {
            System.out.print("	  | HORA DE TÉRMINO (hh:mm): ");
            try {
                evento.setHoraFim(leitor.nextLine());
                set = true;
            } catch (DateTimeParseException ni) {
                System.out.println("	  | (!) Horário inválido.");
            } catch (EntradaInvalida ei) {
                System.out.println("	  | (!) Para eventos de dia único, a "
                        + "hora de\n 	  | término deve ser posterior ou\n"
                        + " 	  | igual ao horário de início.");
            }
        }
        set = false;

        System.out.println("	  | - - - - - - - - - - - - - - - - - - - - - "
                + "+");

        //LEITURA DAS REGIONAIS
        while (!set) {
            System.out.print("          | Informe os números das regionais do "
                    + "evento.\n"
                    + "          | Se mais de uma, separe-os por vírgulas.\n"
                    + "          | 1) Catalão     2) Goiânia\n"
                    + "          | 3) Goiás       4) Jataí\n"
                    + "          |\n"
                    + "          | REGIONAIS: ");

            List<Regional> regionais = new ArrayList<>();
            String input = leitor.nextLine().replace(" ", "").trim();
            if (input.replace(",", "").isEmpty()) {
                System.out.println("	  | (!) Regionais inválidas.");
                System.out.println("	  |");
                regionais = null;
            } else {
                String[] partes = input.split(",");
                for (String parte : partes) {
                    try {
                        int opt = Integer.parseInt(parte);
                        if (opt < 1 || opt > 4) {
                            throw new IllegalArgumentException();
                        } else {
                            switch (opt) {
                                case 1:
                                    if (!regionais.contains(Regional.CATALAO)) {
                                        regionais.add(Regional.CATALAO);
                                    }
                                    break;
                                case 2:
                                    if (!regionais.contains(Regional.GOIANIA)) {
                                        regionais.add(Regional.GOIANIA);
                                    }
                                    break;
                                case 3:
                                    if (!regionais.contains(Regional.GOIAS)) {
                                        regionais.add(Regional.GOIAS);
                                    }
                                    break;
                                case 4:
                                    if (!regionais.contains(Regional.JATAI)) {
                                        regionais.add(Regional.JATAI);
                                    }
                                    break;
                                default:
                                    msgOpcaoNaoExistente();
                                    break;
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("	  | (!) Regionais inválidas.");
                        System.out.println("	  |");
                        regionais = null;
                        break;
                    }
                }
            }

            if (regionais != null) {
                for (Regional reg : regionais) {
                    calendario.addRegional(reg);
                }

                System.out.println("          | " + Regional
                        .getNomesListados(regionais));

                set = true;
            }
        }
        set = false;

        System.out.println("	  | - - - - - - - - - - - - - - - - - - - - - "
                + "+");

        //LEITURA DAS CATEGORIAS
        while (!set) {
            System.out.print("          | Informe os números das categorias do "
                    + "evento.\n"
                    + "          | Se mais de uma, separe-os por vírgulas.\n"
                    + "          | 1) Discente    2) Docente\n"
                    + "          | 3) Servidor    4) Comunidade Acadêmica\n"
                    + "          |\n"
                    + "          | CATEGORIAS: ");

            List<Categoria> categorias = new ArrayList<>();
            String input = leitor.nextLine().replace(" ", "").trim();
            if (input.replace(",", "").isEmpty()) {
                System.out.println("	  | (!) Categorias inválidas.");
                System.out.println("	  |");
                categorias = null;
            } else {
                String[] partes = input.split(",");
                for (String parte : partes) {
                    try {
                        int opt = Integer.parseInt(parte);
                        if (opt < 1 || opt > 4) {
                            throw new IllegalArgumentException();
                        } else {
                            switch (opt) {
                                case 1:
                                    if (!categorias
                                            .contains(Categoria.DISCENTE)) {
                                        categorias
                                                .add(Categoria.DISCENTE);
                                    }
                                    break;
                                case 2:
                                    if (!categorias
                                            .contains(Categoria.DOCENTE)) {
                                        categorias
                                                .add(Categoria.DOCENTE);
                                    }
                                    break;
                                case 3:
                                    if (!categorias
                                            .contains(Categoria.SERVIDOR)) {
                                        categorias.
                                                add(Categoria.SERVIDOR);
                                    }
                                    break;
                                case 4:
                                    if (!categorias.
                                            contains(Categoria.COMUNIDADE)) {
                                        categorias
                                                .add(Categoria.COMUNIDADE);
                                    }
                                    break;
                                default:
                                    msgOpcaoNaoExistente();
                                    break;
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("	  | (!) Categorias inválidas.");
                        System.out.println("	  |");
                        categorias = null;
                        break;
                    }
                }
            }

            if (categorias != null) {
                for (Categoria cat : categorias) {
                    calendario.addCategoria(cat);
                }

                System.out.println("          | " + Categoria
                        .getNomesListados(categorias));

                set = true;
            }
        }

        //ATUALIZAR O EVENTO
        calendario.setEvento(evento);
        lista.atualizarCalendario(indexPraAtualizar, calendario);
    }

    /**
     * Exibe e processa o menu de adição de eventos do administrador.
     *
     * @param leitor Scanner de entrada de dados.
     * @param lista Lista de eventos do sistema.
     */
    private static void adicionar(Scanner leitor, ListaCalendario lista) {

        try {
            pula();
            lerEAdicionar(leitor, lista);

        } catch (NomeInvalido | EventoInvalido |
                CalendarioInvalido | RegionalInvalida |
                ParseException | EntradaInvalida e) {

        }
    }

    /**
     * Permite ler um novo evento e adicioná-lo à lista de eventos.
     *
     * @param leitor Scanner da entrada de dados.
     * @param lista Lista de evento do sistema.
     * @param regionais Regional sendo manipulada no momento.
     * @throws ParseException Quando for incapaz de parser alguma data/horário.
     * @throws NomeInvalido Quando o nome inserido for inválido.
     * @throws EventoInvalido Quando o evento for inválido.
     * @throws CalendarioInvalido Quando o calendário for inválido.
     * @throws EntradaInvalida Quando algum dado inválido for inserido.
     * @throws RegionalInvalida Quando a regional inserida for inválida.
     */
    private static void lerEAdicionar(Scanner leitor, ListaCalendario lista)
            throws ParseException, NomeInvalido,
            EventoInvalido, CalendarioInvalido, EntradaInvalida,
            RegionalInvalida {

        Evento evento = new Evento();
        Calendario calendario = new Calendario();
        boolean set = false;

        System.out.println("          +======================================="
                + "====+\n"
                + "          |   ADICIONAR EVENTO                        |\n"
                + "          +===========================================+\n"
                + "	  | Preencha os dados do novo evento.\n"
                + "	  |");

        //LEITURA DO NOME
        while (!set) {
            System.out.print("	  | NOME: ");
            try {
                evento.setNomeEvento(leitor.nextLine());
                set = true;
            } catch (NomeInvalido ni) {
                System.out.println("	  | (!) Nome de evento inválido.");
            }
        }
        set = false;

        System.out.println("	  | - - - - - - - - - - - - - - - - - - - - - "
                + "+");

        //LEITURA DA DATA INICIAL
        while (!set) {
            System.out.print("	  | DATA DE INÍCIO (dd/mm/aaaa): ");
            try {
                evento.setDataInicio(leitor.nextLine());
                set = true;
            } catch (EntradaInvalida ni) {
                System.out.println("	  | (!) Data inválida.");
            } catch (ParseException pe) {
                System.out.println("	  | (!) Formato de data inválido.");
            }
        }
        set = false;

        //LEITURA DA HORA INICIAL
        while (!set) {
            System.out.print("	  | HORA DE INÍCIO (hh:mm): ");
            try {
                evento.setHoraInicio(leitor.nextLine());
                set = true;
            } catch (DateTimeParseException ni) {
                System.out.println("	  | (!) Horário inválido.");
            }
        }
        set = false;

        System.out.println("	  | - - - - - - - - - - - - - - - - - - - - - "
                + "+");

        //LEITURA DA DATA FINAL
        while (!set) {
            System.out.print("	  | DATA DE TÉRMINO (dd/mm/aaaa): ");
            try {
                evento.setDataFim(leitor.nextLine());
                set = true;
            } catch (EntradaInvalida ni) {
                System.out.println("	  | (!) Data inválida.");
            } catch (ParseException pe) {
                System.out.println("	  | (!) Formato de data inválido.");
            }
        }
        set = false;

        //LEITURA DA HORA FINAL
        while (!set) {
            System.out.print("	  | HORA DE TÉRMINO (hh:mm): ");
            try {
                evento.setHoraFim(leitor.nextLine());
                set = true;
            } catch (DateTimeParseException ni) {
                System.out.println("	  | (!) Horário inválido.");
            } catch (EntradaInvalida ei) {
                System.out.println("	  | (!) Para eventos de dia único, a "
                        + "hora de\n 	  | término deve ser posterior ou\n"
                        + " 	  | igual ao horário de início.");
            }
        }
        set = false;

        System.out.println("	  | - - - - - - - - - - - - - - - - - - - - - "
                + "+");

        //LEITURA DAS REGIONAIS
        while (!set) {
            System.out.print("          | Informe os números das regionais do "
                    + "evento.\n"
                    + "          | Se mais de uma, separe-os por vírgulas.\n"
                    + "          | 1) Catalão     2) Goiânia\n"
                    + "          | 3) Goiás       4) Jataí\n"
                    + "          |\n"
                    + "          | REGIONAIS: ");

            List<Regional> regionais = new ArrayList<>();
            String input = leitor.nextLine().replace(" ", "").trim();
            if (input.replace(",", "").isEmpty()) {
                System.out.println("	  | (!) Regionais inválidas.");
                System.out.println("	  |");
                regionais = null;
            } else {
                String[] partes = input.split(",");
                for (String parte : partes) {
                    try {
                        int opt = Integer.parseInt(parte);
                        if (opt < 1 || opt > 4) {
                            throw new IllegalArgumentException();
                        } else {
                            switch (opt) {
                                case 1:
                                    if (!regionais.contains(Regional.CATALAO)) {
                                        regionais.add(Regional.CATALAO);
                                    }
                                    break;
                                case 2:
                                    if (!regionais.contains(Regional.GOIANIA)) {
                                        regionais.add(Regional.GOIANIA);
                                    }
                                    break;
                                case 3:
                                    if (!regionais.contains(Regional.GOIAS)) {
                                        regionais.add(Regional.GOIAS);
                                    }
                                    break;
                                case 4:
                                    if (!regionais.contains(Regional.JATAI)) {
                                        regionais.add(Regional.JATAI);
                                    }
                                    break;
                                default:
                                    msgOpcaoNaoExistente();
                                    break;
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("	  | (!) Regionais inválidas.");
                        System.out.println("	  |");
                        regionais = null;
                        break;
                    }
                }
            }

            if (regionais != null) {
                for (Regional reg : regionais) {
                    calendario.addRegional(reg);
                }

                System.out.println("          | " + Regional
                        .getNomesListados(regionais));

                set = true;
            }
        }
        set = false;

        System.out.println("	  | - - - - - - - - - - - - - - - - - - - - - "
                + "+");

        //LEITURA DAS CATEGORIAS
        while (!set) {
            System.out.print("          | Informe os números das categorias do "
                    + "evento.\n"
                    + "          | Se mais de uma, separe-os por vírgulas.\n"
                    + "          | 1) Discente    2) Docente\n"
                    + "          | 3) Servidor    4) Comunidade Acadêmica\n"
                    + "          |\n"
                    + "          | CATEGORIAS: ");

            List<Categoria> categorias = new ArrayList<>();
            String input = leitor.nextLine().replace(" ", "").trim();
            if (input.replace(",", "").isEmpty()) {
                System.out.println("	  | (!) Categorias inválidas.");
                System.out.println("	  |");
                categorias = null;
            } else {
                String[] partes = input.split(",");
                for (String parte : partes) {
                    try {
                        int opt = Integer.parseInt(parte);
                        if (opt < 1 || opt > 4) {
                            throw new IllegalArgumentException();
                        } else {
                            switch (opt) {
                                case 1:
                                    if (!categorias
                                            .contains(Categoria.DISCENTE)) {
                                        categorias
                                                .add(Categoria.DISCENTE);
                                    }
                                    break;
                                case 2:
                                    if (!categorias
                                            .contains(Categoria.DOCENTE)) {
                                        categorias
                                                .add(Categoria.DOCENTE);
                                    }
                                    break;
                                case 3:
                                    if (!categorias
                                            .contains(Categoria.SERVIDOR)) {
                                        categorias
                                                .add(Categoria.SERVIDOR);
                                    }
                                    break;
                                case 4:
                                    if (!categorias
                                            .contains(Categoria.COMUNIDADE)) {
                                        categorias
                                                .add(Categoria.COMUNIDADE);
                                    }
                                    break;
                                default:
                                    msgOpcaoNaoExistente();
                                    break;
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("	  | (!) Categorias inválidas.");
                        System.out.println("	  |");
                        categorias = null;
                        break;
                    }
                }
            }

            if (categorias != null) {
                for (Categoria cat : categorias) {
                    calendario.addCategoria(cat);
                }

                System.out.println("          | " + Categoria
                        .getNomesListados(categorias));

                set = true;
            }
        }

        System.out.println("	  +-------------------------------------------"
                + "+");

        //ADICIONAR O EVENTO
        calendario.setEvento(evento);
        lista.adicionarCalendarioUfg(calendario);
        System.out.print("	  | (*) Evento adicionado com sucesso.\n"
                + "	  +-------------------------------------------+\n");
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
                + "===+\n"
                + "|      SISTEMA DE CALENDÁRIO ACADÊMICO - SCA UFG      |\n"
                + "+=====================================================+\n"
                + "|   1 - Consultar todas regonais                      |\n"
                + "|   2 - Consultar regionais específicas               |\n"
                + "|   3 - Acessar como administrador                    |\n"
                + "|   0 - Sair                                          |\n"
                + "+=====================================================+");
    }

    /**
     * Exibe o menu de opções para todas as regionais.
     */
    public static void exibirMenuConsultasTodasRegionais() {
        System.out.println("     +============================================="
                + "===+\n"
                + "     |   ACESSO GLOBAL                                |\n"
                + "     +================================================+\n"
                + "     |  1 - Consultar todos eventos                   |\n"
                + "     |  2 - Consultar evento(s) por palavra           |\n"
                + "     |  3 - Consultar evento(s) por data              |\n"
                + "     |  4 - Consultar evento(s) por período           |\n"
                + "     |  5 - Consultar evento(s) por categoria(s)      |\n"
                + "     |  0 - Voltar                                    |\n"
                + "     +================================================+");
    }

    /**
     * Exibe o menu de opções para regionais específicas.
     *
     * @param regionais Regionais deste menu.
     */
    public static void exibirMenuRegionaisEspecificas(List<Regional> regionais) {

        System.out.print(""
                + "     +================================================+"
                + "\n     |   CONSULTAR " + Regional
                .getNomesListados(regionais)
                .toString().toUpperCase() + "\n");

        System.out.println("     +========================================="
                + "=======+\n"
                + "     |  1 - Consultar todos eventos                   |"
                + "\n"
                + "     |  2 - Consultar evento(s) por palavra           |"
                + "\n"
                + "     |  3 - Consultar evento(s) por data              |"
                + "\n"
                + "     |  4 - Consultar evento(s) por período           |"
                + "\n"
                + "     |  5 - Consultar evento(s) por categoria(s)      |"
                + "\n"
                + "     |  0 - Voltar                                    |"
                + "\n"
                + "     +================================================"
                + "+");
    }

    /**
     * Exibe e processa a atualização de credenciais do administrador.
     *
     * @param leitor Entrada de dados.
     * @param admin Usuário do administrador a ser modificado.
     */
    public static void alterarCredenciais(Scanner leitor, Admin admin) {

        pula();
        System.out.print("          +=========================================="
                + "=+\n"
                + "          |   ATUALIZAR CREDENCIAIS                   |\n"
                + "          +===========================================+\n"
                + "	  | Digite as credenciais atuais:\n"
                + "	  | LOGIN: ");
        String login = leitor.nextLine();

        System.out.print("	  | SENHA: ");
        String password = leitor.nextLine();

        //Checa usuário e senhas fornecidos
        if (!admin.validarSenha(login, password)) {

            //Usuário e senha inválidos
            linhaNivelada(2);
            msgUsuarioSenhaInvalidos();

        } else {

            boolean set = false;

            System.out.println("          | - - - - - - - - - - - - - - - - - -"
                    + " - - - +");
            System.out.print("          | Digite as novas credenciais:\n");

            String oldUsuario = admin.getUsuario();

            while (!set) {
                try {
                    System.out.print("	  | LOGIN: ");
                    String novoUsuario = leitor.nextLine();
                    admin.alterarUsuario(novoUsuario);
                    set = true;
                } catch (EntradaInvalida ex) {
                    System.out.println("	  | (!) Login inválido! O login"
                            + " não pode conter\n"
                            + "          | espaços ou menos de 3 caracteres.");
                }
            }
            set = false;

            String oldSenha = admin.getSenha();

            while (!set) {
                try {
                    System.out.print("	  | SENHA: ");
                    String novaSenha = leitor.nextLine();
                    admin.alterarSenha(novaSenha);
                    set = true;
                } catch (EntradaInvalida ex) {
                    System.out.println("	  | (!) Senha inválida! A senha"
                            + " não pode conter\n"
                            + "          | menos de 3 caracteres.");
                }
            }
            set = false;

            while (!set) {
                try {
                    System.out.print("	  | Confirme a SENHA: ");
                    String novaSenha = leitor.nextLine();
                    if (!novaSenha.equals(admin.getSenha())) {
                        admin.alterarSenha(oldSenha);
                        admin.alterarUsuario(oldUsuario);
                        throw new IllegalArgumentException("Senhas não são igua"
                                + "is.");
                    } else {
                        set = true;
                    }
                } catch (EntradaInvalida ex) {
                } catch (IllegalArgumentException ex) {
                    System.out.println("	  | (!) As senhas não são iguai"
                            + "s.");
                    System.out.println("          +---------------------------"
                            + "----------------+\n"
                            + "          | (!) Credenciais não alteradas.\n"
                            + "          +-------------------------------------"
                            + "------+");
                    return;
                }
            }

            System.out.println("	  +-----------------------------------"
                    + "--------+\n"
                    + "	  | (*) Credenciais alteradas com sucesso.\n"
                    + "	  +-------------------------------------------+  ");

        }
    }

    /**
     * Exibe o menu do administrador.
     */
    public static void exibirMenuAdministrador() {
        System.out.println("     +============================================="
                + "===+\n"
                + "     |   ADMINISTRADOR                                |\n"
                + "     +================================================+\n"
                + "     |  1 - Adicionar evento                          |\n"
                + "     |  2 - Atualizar evento                          |\n"
                + "     |  3 - Remover evento                            |\n"
                + "     |  4 - Consultar eventos                         |\n"
                + "     |  5 - Alterar credenciais                       |\n"
                + "     |  0 - Voltar                                    |\n"
                + "     +================================================+");
    }

    /**
     * Exibe a mensagem de opção inexistente.
     */
    public static void msgOpcaoNaoExistente() {
        pula();
        System.out.println("[              (!) OPÇÃO INEXISTENTE (!)"
                + "              ]");
    }

    /**
     * Exibe a mensagem de opção inválida.
     */
    public static void msgOpcaoInvalida() {
        pula();
        System.out.println("[                (!) OPÇÃO INVÁLIDA (!)           "
                + "    ]");
    }

    /**
     * Exibe a mensagem de evento não localizado.
     */
    public static void msgEventoNaoLocalizado() {
        pula();
        System.out.println("[            (!) EVENTO NÃO ENCONTRADO (!)         "
                + "   ]");
    }

    /**
     * Exibe a mensagem de usuário e senha incorretos.
     */
    public static void msgUsuarioSenhaInvalidos() {
        pula();
        System.out.println("[         (!) USUÁRIO OU SENHA INVÁLIDOS (!)       "
                + "   ]");
    }

    /**
     * Sobrecarga do inputOpcao para nivel de tabulação nenhum.
     *
     * @return Opção fornecida pelo usuário.
     */
    public static int inputOpcao() {
        return inputOpcao(0);
    }

    /**
     * Exibe e processa uma opção de menu.
     *
     * @param nivel Nível de tabulação.
     * @return A opção fornecida pelo usuário.
     */
    public static int inputOpcao(int nivel) {
        Scanner entrada = new Scanner(System.in, "UTF-8");

        nivela(nivel);
        System.out.print("| OPÇÃO: ");
        int opcao = -1;
        try {
            opcao = Integer.parseInt(entrada.nextLine());
            linhaNivelada(nivel);
        } catch (NumberFormatException e) {
            linhaNivelada(nivel);
        }

        return opcao;
    }

    /**
     * Imprime um nível de tabulação.
     *
     * @param nivel Nivel de tabulação a imprimir.
     */
    public static void nivela(int nivel) {
        for (int i = 0; i < nivel; i++) {
            System.out.print("     ");
        }
    }

    /**
     * Imprime uma linha com nivel de tabulação determinado.
     *
     * @param nivel Nível de tabulação.
     */
    public static void linhaNivelada(int nivel) {
        int menos = 53;

        for (int i = 0; i < nivel; i++) {
            System.out.print("     ");
            menos -= 5;
        }
        System.out.print("+");
        for (int i = 0; i < menos; i++) {
            System.out.print("-");
        }
        System.out.print("+\n");
    }

    /**
     * Permite inserir uma lista de regionais em um menu intuitivo.
     *
     * @param nivel Nível de tabulação do menu.
     * @return Lista de regionais selecionadas pelo usuário.
     */
    public static List<Regional> inputRegional(int nivel) {
        Scanner entrada = new Scanner(System.in, "UTF-8");

        nivela(nivel);
        System.out.print("| Informe os números das regionais a consular.\n");
        nivela(nivel);
        System.out.print("| Se mais de uma, separe-os por vírgulas.\n");
        nivela(nivel);
        System.out.print("| 1) Catalão     2) Goiânia\n");
        nivela(nivel);
        System.out.print("| 3) Goiás       4) Jataí\n");
        nivela(nivel);
        System.out.print("|\n");
        nivela(nivel);
        System.out.print("| REGIONAIS: ");

        List<Regional> regionais = new ArrayList<>();
        String input = entrada.nextLine().replace(" ", "").trim();
        if (input.replace(",", "").isEmpty()) {
            linhaNivelada(nivel);
            msgOpcaoInvalida();
            regionais = null;
            return regionais;
        }
        String[] partes = input.split(",");

        for (String parte : partes) {
            try {
                int opt = Integer.parseInt(parte);
                if (opt < 1 || opt > 4) {
                    throw new IllegalArgumentException();
                } else {
                    switch (opt) {
                        case 1:
                            if (!regionais.contains(Regional.CATALAO)) {
                                regionais.add(Regional.CATALAO);
                            }
                            break;
                        case 2:
                            if (!regionais.contains(Regional.GOIANIA)) {
                                regionais.add(Regional.GOIANIA);
                            }
                            break;
                        case 3:
                            if (!regionais.contains(Regional.GOIAS)) {
                                regionais.add(Regional.GOIAS);
                            }
                            break;
                        case 4:
                            if (!regionais.contains(Regional.JATAI)) {
                                regionais.add(Regional.JATAI);
                            }
                            break;
                        default:
                            msgOpcaoNaoExistente();
                            break;
                    }
                }
            } catch (IllegalArgumentException e) {
                linhaNivelada(nivel);
                msgOpcaoInvalida();
                regionais = null;
                break;
            }
        }

        if (regionais != null) {
            nivela(nivel);
            System.out.println("| " + Regional
                    .getNomesListados(regionais));
            linhaNivelada(nivel);
            return regionais;
        }

        return regionais;
    }

    /**
     * Exibe a mensagem de calendário vazio.
     */
    public static void msgCalendarioVazio() {
        pula();
        System.out.println("[               (!) CALENDÁRIO VAZIO (!)           "
                + "   ]");
    }
}
