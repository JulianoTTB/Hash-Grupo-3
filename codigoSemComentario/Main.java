import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        funcaoHash1();
        funcaoHashMultiplicacaoTest();
        funcaoHashLinearTest();
    }
    public static void funcaoHash1() {
        File aqHashModular = new File("ResultadoHash1.txt");

        try {
            boolean novo = aqHashModular.createNewFile();
            if (novo) {
                try (FileWriter fw = new FileWriter(aqHashModular, true)) {
                    fw.write("tamanho_tabela,arquivo,num_registros,tempo_insercao_ms,total_colisoes_insercao,avg_colisoes_insercao,tempo_busca_ms,total_colisoes_busca,avg_colisoes_busca,gap_menor,gap_maior,gap_media,fator_carga\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo de saída: " + e.getMessage());
            System.exit(-1);
        }

        String[] arquivos = {"teste1.txt", "teste2.txt", "teste3.txt"};
        long[] registrosEsperados = {100_000L, 1_000_000L, 10_000_000L};
        int[] tamanhosTabela = {1_000, 10_000, 100_000};

        for (int t = 0; t < tamanhosTabela.length; t++) {
            for (int a = 0; a < arquivos.length; a++) {
                int tamanhoTabela = tamanhosTabela[t];
                String arquivo = arquivos[a];
                long numRegistrosEstimados = registrosEsperados[a];

                System.out.println("Executando: tabela=" + tamanhoTabela + ", arquivo=" + arquivo);

                HashModular hashModular = new HashModular(tamanhoTabela);

                long totalColisoesInsercao = 0;
                long totalRegistros = 0;

                long tempoInsercaoInicio = System.nanoTime();

                try (Scanner scanner = new Scanner(new File(arquivo))) {
                    while (scanner.hasNextLine()) {
                        String codigo = scanner.nextLine().trim();
                        if (codigo.isEmpty()) continue;

                        Registro r = new Registro(codigo);
                        int colisoes = hashModular.inserir(r);
                        if (colisoes >= 0)
                            totalColisoesInsercao += colisoes;
                        totalRegistros++;
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("Arquivo não encontrado: " + arquivo);
                    continue;
                }

                long tempoInsercaoFim = System.nanoTime();
                double tempoInsercaoMs = (tempoInsercaoFim - tempoInsercaoInicio) / 1_000_000.0;
                double avgColisoesInsercao = totalRegistros > 0 ? (double) totalColisoesInsercao / totalRegistros : 0.0;

                hashModular.encontrarGap();
                int gapMenor = hashModular.getGapMenor();
                int gapMaior = hashModular.getGapMaior();
                double gapMedia = hashModular.getGapMedia();

                long totalColisoesBusca = 0;
                long totalBuscas = 0;
                long tempoBuscaInicio = System.nanoTime();

                try (Scanner scanner = new Scanner(new File(arquivo))) {
                    while (scanner.hasNextLine()) {
                        String codigo = scanner.nextLine().trim();
                        if (codigo.isEmpty()) continue;

                        int colisoesBusca = hashModular.buscar(codigo);
                        if (colisoesBusca >= 0)
                            totalColisoesBusca += colisoesBusca;
                        totalBuscas++;
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("Erro ao abrir arquivo para busca: " + arquivo);
                    continue;
                }

                long tempoBuscaFim = System.nanoTime();
                double tempoBuscaMs = (tempoBuscaFim - tempoBuscaInicio) / 1_000_000.0;
                double avgColisoesBusca = totalBuscas > 0 ? (double) totalColisoesBusca / totalBuscas : 0.0;
                double fatorCarga = (double) totalRegistros / tamanhoTabela;

                try (FileWriter fw = new FileWriter(aqHashModular, true)) {
                    String linha = String.format(Locale.US, "%d,%s,%d,%.3f,%d,%.2f,%.3f,%d,%.4f,%d,%d,%.2f,%.1f\n",
                            tamanhoTabela,
                            arquivo,
                            totalRegistros,
                            tempoInsercaoMs,
                            totalColisoesInsercao,
                            avgColisoesInsercao,
                            tempoBuscaMs,
                            totalColisoesBusca,
                            avgColisoesBusca,
                            gapMenor,
                            gapMaior,
                            gapMedia,
                            fatorCarga
                    );
                    fw.write(linha);
                } catch (IOException e) {
                    System.out.println("Erro ao escrever no arquivo de saída: " + e.getMessage());
                }

                System.out.println("→ Inserção: " + totalRegistros + " registros | " + tempoInsercaoMs + " ms | colisões méd. " + avgColisoesInsercao);
                System.out.println("→ Busca: " + totalBuscas + " registros | " + tempoBuscaMs + " ms | colisões méd. " + avgColisoesBusca);
                System.out.println("→ Gaps: menor=" + gapMenor + " maior=" + gapMaior + " média=" + gapMedia);
                System.out.println("→ Fator de carga: " + fatorCarga);
                System.out.println("----------------------------------------------------");
            }
        }
    }


    public static void funcaoHashMultiplicacaoTest() {
        File arquivoCSV = new File("ResultadoHashMultiplicacao.csv");

        try {
            boolean novo = arquivoCSV.createNewFile();
            if (novo) {
                try (FileWriter fw = new FileWriter(arquivoCSV, true)) {
                    fw.write("tamanho_tabela,arquivo,num_registros,tempo_insercao_ms,total_colisoes_insercao,avg_colisoes_insercao,tempo_busca_ms,total_colisoes_busca,avg_colisoes_busca,menor_gap,maior_gap,media_gap\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao criar CSV: " + e.getMessage());
            System.exit(-1);
        }

        String[] arquivos = {"teste1.txt", "teste2.txt", "teste3.txt"};
        int[] tamanhosTabela = {1000, 10000, 100000};

        for (int tamanhoTabela : tamanhosTabela) {
            for (String arquivo : arquivos) {
                System.out.println("Processando: tabela=" + tamanhoTabela + ", arquivo=" + arquivo);

                Registro[] registros;
                long totalRegistros = 0;
                try (Scanner scanner = new Scanner(new File(arquivo))) {
                    int linhas = 0;
                    while (scanner.hasNextLine()) {
                        String linha = scanner.nextLine().trim();
                        if (!linha.isEmpty()) linhas++;
                    }
                    registros = new Registro[linhas];
                } catch (IOException e) {
                    System.out.println("Erro ao contar linhas: " + arquivo);
                    continue;
                }

                try (Scanner scanner = new Scanner(new File(arquivo))) {
                    int idx = 0;
                    while (scanner.hasNextLine()) {
                        String codigo = scanner.nextLine().trim();
                        if (!codigo.isEmpty()) {
                            registros[idx++] = new Registro(codigo);
                            totalRegistros++;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Erro ao ler arquivo: " + arquivo);
                    continue;
                }

                HashMultiplicacao hash = new HashMultiplicacao(tamanhoTabela);

                long colisoesTotaisInsercao = 0;
                long tempoInicioInsercao = System.nanoTime();
                for (Registro r : registros) {
                    int col = hash.inserir(r);
                    if (col >= 0) colisoesTotaisInsercao += col;
                }
                long tempoFimInsercao = System.nanoTime();
                double tempoInsercaoMs = (tempoFimInsercao - tempoInicioInsercao) / 1_000_000.0;
                double avgColisoesInsercao = totalRegistros > 0 ? (double) colisoesTotaisInsercao / totalRegistros : 0;

                long colisoesTotaisBusca = 0;
                long tempoInicioBusca = System.nanoTime();
                for (Registro r : registros) {
                    int col = hash.buscar(r.getCodigo());
                    if (col >= 0) colisoesTotaisBusca += col;
                }
                long tempoFimBusca = System.nanoTime();
                double tempoBuscaMs = (tempoFimBusca - tempoInicioBusca) / 1_000_000.0;
                double avgColisoesBusca = totalRegistros > 0 ? (double) colisoesTotaisBusca / totalRegistros : 0;

                double[] gaps = hash.numeroGaps();
                double menorGap = gaps[0];
                double maiorGap = gaps[1];
                double mediaGap = gaps[2];

                try (FileWriter fw = new FileWriter(arquivoCSV, true)) {
                    String linha = String.format(Locale.US, "%d,%s,%d,%.3f,%d,%.3f,%.3f,%d,%.3f,%.0f,%.0f,%.3f\n",
                            tamanhoTabela,
                            arquivo,
                            totalRegistros,
                            tempoInsercaoMs,
                            colisoesTotaisInsercao,
                            avgColisoesInsercao,
                            tempoBuscaMs,
                            colisoesTotaisBusca,
                            avgColisoesBusca,
                            menorGap,
                            maiorGap,
                            mediaGap
                    );
                    fw.write(linha);
                } catch (IOException e) {
                    System.out.println("Erro ao escrever CSV: " + e.getMessage());
                }

                System.out.println("→ Inserção: " + totalRegistros + " registros | " + tempoInsercaoMs + " ms | colisões méd. " + avgColisoesInsercao);
                System.out.println("→ Busca: " + totalRegistros + " registros | " + tempoBuscaMs + " ms | colisões méd. " + avgColisoesBusca);
                System.out.println("→ Gaps: menor=" + menorGap + ", maior=" + maiorGap + ", média=" + mediaGap);
                System.out.println("----------------------------------------------------");
            }
        }
    }

    public static void funcaoHashLinearTest() {
        File arquivoCSV = new File("ResultadoHashLinear.csv");

        try {
            boolean novo = arquivoCSV.createNewFile();
            if (novo) {
                try (FileWriter fw = new FileWriter(arquivoCSV, true)) {
                    fw.write("tamanho_tabela,arquivo,num_registros,tempo_insercao_ms,total_colisoes_insercao,avg_colisoes_insercao,tempo_busca_ms,total_colisoes_busca,avg_colisoes_busca,menor_gap,maior_gap,media_gap\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao criar CSV: " + e.getMessage());
            System.exit(-1);
        }

        String[] arquivos = {"teste1.txt", "teste2.txt", "teste3.txt"};
        int[] tamanhosTabela = {1000, 10000, 100000};

        for (int tamanhoTabela : tamanhosTabela) {
            for (String nomeArquivo : arquivos) {
                System.out.println("Processando: tabela=" + tamanhoTabela + ", arquivo=" + nomeArquivo);

                // Lê registros
                Registro[] registros;
                long totalRegistros = 0;

                try (Scanner scanner = new Scanner(new File(nomeArquivo))) {
                    int linhas = 0;
                    while (scanner.hasNextLine()) {
                        String linha = scanner.nextLine().trim();
                        if (!linha.isEmpty()) linhas++;
                    }
                    registros = new Registro[linhas];
                } catch (IOException e) {
                    System.out.println("Erro ao contar linhas do arquivo: " + nomeArquivo);
                    continue;
                }

                try (Scanner scanner = new Scanner(new File(nomeArquivo))) {
                    int idx = 0;
                    while (scanner.hasNextLine()) {
                        String codigo = scanner.nextLine().trim();
                        if (!codigo.isEmpty()) {
                            registros[idx++] = new Registro(codigo);
                            totalRegistros++;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Erro ao ler arquivo: " + nomeArquivo);
                    continue;
                }

                HashLinear hash = new HashLinear(tamanhoTabela);

                long totalColisoesInsercao = 0;
                long inicioInsercao = System.nanoTime();
                for (Registro r : registros) {
                    int col = hash.inserir(r);
                    if (col >= 0) totalColisoesInsercao += col;
                }
                long fimInsercao = System.nanoTime();
                double tempoInsercaoMs = (fimInsercao - inicioInsercao) / 1_000_000.0;
                double avgColisoesInsercao = totalRegistros > 0 ? (double) totalColisoesInsercao / totalRegistros : 0;

                long totalColisoesBusca = 0;
                long inicioBusca = System.nanoTime();
                for (Registro r : registros) {
                    int chave = stringParaInt(r.getCodigo());
                    int col = hash.buscar(chave);
                    if (col >= 0) totalColisoesBusca += col;
                }
                long fimBusca = System.nanoTime();
                double tempoBuscaMs = (fimBusca - inicioBusca) / 1_000_000.0;
                double avgColisoesBusca = totalRegistros > 0 ? (double) totalColisoesBusca / totalRegistros : 0;

                hash.testarBuscaEGap(registros);

                int menorGap = hash.getMenorGap();
                int maiorGap = hash.getMaiorGap();
                double mediaGap = hash.getMediaGap();

                try (FileWriter fw = new FileWriter(arquivoCSV, true)) {
                    String linha = String.format(Locale.US,
                            "%d,%s,%d,%.3f,%d,%.3f,%.3f,%d,%.3f,%d,%d,%.3f\n",
                            tamanhoTabela,
                            nomeArquivo,
                            totalRegistros,
                            tempoInsercaoMs,
                            totalColisoesInsercao,
                            avgColisoesInsercao,
                            tempoBuscaMs,
                            totalColisoesBusca,
                            avgColisoesBusca,
                            menorGap,
                            maiorGap,
                            mediaGap
                    );
                    fw.write(linha);
                } catch (IOException e) {
                    System.out.println("Erro ao escrever no CSV: " + e.getMessage());
                }

                System.out.println("→ Inserção: " + totalRegistros + " registros | " + tempoInsercaoMs + " ms | col. média " + avgColisoesInsercao);
                System.out.println("→ Busca: " + tempoBuscaMs + " ms | col. média " + avgColisoesBusca);
                System.out.println("→ Gaps: menor=" + menorGap + ", maior=" + maiorGap + ", média=" + mediaGap);
                System.out.println("----------------------------------------------------");
            }
        }
    }

    private static int stringParaInt(String codigo) {
        int valor = 0;
        for (int i = 0; i < codigo.length(); i++) {
            char c = codigo.charAt(i);
            valor = valor * 10 + (c - '0');
        }
        return valor;
    }
}
