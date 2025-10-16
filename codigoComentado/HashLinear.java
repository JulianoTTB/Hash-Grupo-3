public class HashLinear {
    private Registro[] tabela;
    private int tamanho;
    private int colisoes;

    public HashLinear(int tamanho) {
        this.tamanho = tamanho;
        this.tabela = new Registro[tamanho];
        this.colisoes = 0;
    }

    private int hashPrincipal(int chave) {
        if (chave < 0)
            chave = -chave;
        return chave % tamanho;
    }

    private int proximaPosicao(int posicao, int chave, int tentativa) {
        int digito = (chave / 10) % 9;
        if (digito == 0)
            digito = 3;
        int novo = posicao + digito * tentativa;
        while (novo >= tamanho)
            novo -= tamanho;
        return novo;
    }

    private int stringParaInt(String codigo) {
        int valor = 0;
        for (int i = 0; i < codigo.length(); i++) {
            char c = codigo.charAt(i);
            valor = valor * 10 + (c - '0');
        }
        return valor;
    }


    public int inserir(Registro reg) {
        int chave = stringParaInt(reg.getCodigo());
        int posicao = hashPrincipal(chave);
        int tentativa = 0;
        int colisoesInsercao = 0;

        while (tabela[posicao] != null) {
            colisoes++;
            colisoesInsercao++;
            tentativa++;
            posicao = proximaPosicao(posicao, chave, tentativa);

            // Caso rode toda a tabela sem achar espaço
            if (tentativa >= tamanho) {
                System.out.println("Tabela cheia, não foi possível inserir " + reg.getCodigo());
                return -1;
            }
        }

        tabela[posicao] = reg;
        return colisoesInsercao;
    }

    public int buscar(int chave) {
        if (chave < 0)
            chave = -chave;
        int posicao = hashPrincipal(chave);
        int tentativa = 0;
        int colisoesBusca = 0;

        while (tabela[posicao] != null) {
            String codigo = tabela[posicao].getCodigo();
            int valor = stringParaInt(codigo);

            if (valor == chave) {
                return colisoesBusca; // Retorna número de colisões da busca
            }

            tentativa++;
            colisoesBusca++;
            posicao = proximaPosicao(posicao, chave, tentativa);

            if (tentativa >= tamanho) {
                return -1; // Procurou em toda a tabela e não achou
            }
        }

        return -1; // Não encontrado
    }

    public int getColisoes() {
        return colisoes;
    }

    public void testarBuscaEGap(Registro[] vetor) {
        long inicio = System.nanoTime();
        for (int i = 0; i < vetor.length; i++) {
            int chave = stringParaInt(vetor[i].getCodigo());
            buscar(chave);
        }
        long fim = System.nanoTime();
        long tempoTotal = fim - inicio;
        System.out.println("Tempo total de busca (ns): " + tempoTotal);
        encontrarGaps(vetor);
    }

    private void encontrarGaps(Registro[] vetor) {
        if (vetor.length < 2) {
            System.out.println("Não há gaps suficientes para calcular.");
            return;
        }
        int menorGap = Integer.MAX_VALUE;
        int maiorGap = -1;
        int somaGap = 0;
        int cont = 0;

        for (int i = 1; i < vetor.length; i++) {
            int anterior = stringParaInt(vetor[i - 1].getCodigo());
            int atual = stringParaInt(vetor[i].getCodigo());
            int gap = Math.abs(atual - anterior);

            if (gap < menorGap)
                menorGap = gap;
            if (gap > maiorGap)
                maiorGap = gap;

            somaGap += gap;
            cont++;
        }

        double mediaGap = (cont > 0) ? (double) somaGap / cont : 0;
        System.out.println("Menor gap: " + menorGap);
        System.out.println("Maior gap: " + maiorGap);
        System.out.println("Média dos gaps: " + mediaGap);
    }
}
