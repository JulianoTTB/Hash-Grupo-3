public class HashLinear {
    private Registro[] tabela;
    private int tamanho;
    private int colisoes;

    private int menorGap;
    private int maiorGap;
    private double mediaGap;

    public HashLinear(int tamanho) {
        this.tamanho = tamanho;
        this.tabela = new Registro[tamanho];
        this.colisoes = 0;

        this.menorGap = Integer.MAX_VALUE;
        this.maiorGap = -1;
        this.mediaGap = 0;
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

            if (tentativa >= tamanho) {
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
            int valor = stringParaInt(tabela[posicao].getCodigo());
            if (valor == chave) {
                return colisoesBusca;
            }

            tentativa++;
            colisoesBusca++;
            posicao = proximaPosicao(posicao, chave, tentativa);

            if (tentativa >= tamanho) {
                return -1;
            }
        }

        return -1;
    }

    public int getColisoes() {
        return colisoes;
    }

    public void testarBuscaEGap(Registro[] vetor) {
        for (int i = 0; i < vetor.length; i++) {
            int chave = stringParaInt(vetor[i].getCodigo());
            buscar(chave);
        }
        calcularGaps(vetor);
    }

    private void calcularGaps(Registro[] vetor) {
        if (vetor.length < 2) {
            menorGap = 0;
            maiorGap = 0;
            mediaGap = 0;
            return;
        }

        menorGap = Integer.MAX_VALUE;
        maiorGap = -1;
        int somaGap = 0;
        int cont = 0;

        for (int i = 1; i < vetor.length; i++) {
            int anterior = stringParaInt(vetor[i - 1].getCodigo());
            int atual = stringParaInt(vetor[i].getCodigo());
            int gap = Math.abs(atual - anterior);

            if (gap < menorGap) menorGap = gap;
            if (gap > maiorGap) maiorGap = gap;

            somaGap += gap;
            cont++;
        }

        mediaGap = (cont > 0) ? (double) somaGap / cont : 0;
    }

    public int getMenorGap() {
        return menorGap;
    }

    public int getMaiorGap() {
        return maiorGap;
    }

    public double getMediaGap() {
        return mediaGap;
    }
}
