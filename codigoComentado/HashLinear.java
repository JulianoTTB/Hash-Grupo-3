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

    public void inserir(Registro reg) {
        int chave = stringParaInt(reg.getCodigo());
        int posicao = hashPrincipal(chave);
        int tentativa = 0;
        while (tabela[posicao] != null) {
            colisoes++;
            tentativa++;
            posicao = proximaPosicao(posicao, chave, tentativa);
        }
        tabela[posicao] = reg;
    }

    public Registro buscar(int chave) {
        if (chave < 0)
            chave = -chave;
        int posicao = hashPrincipal(chave);
        int tentativa = 0;
        while (tabela[posicao] != null) {
            String codigo = tabela[posicao].getCodigo();
            int valor = stringParaInt(codigo);
            if (valor == chave)
                return tabela[posicao];
            tentativa++;
            posicao = proximaPosicao(posicao, chave, tentativa);
        }
        return null;
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
        int menorGap = 999999999;
        int maiorGap = -1;
        int somaGap = 0;
        int cont = 0;
        for (int i = 1; i < vetor.length; i++) {
            int anterior = stringParaInt(vetor[i - 1].getCodigo());
            int atual = stringParaInt(vetor[i].getCodigo());
            int gap = atual - anterior;
            if (gap < 0)
                gap = -gap;
            if (gap < menorGap)
                menorGap = gap;
            if (gap > maiorGap)
                maiorGap = gap;
            somaGap += gap;
            cont++;
        }
        double mediaGap = 0;
        if (cont > 0)
            mediaGap = (double) somaGap / cont;
        System.out.println("Menor gap: " + menorGap);
        System.out.println("Maior gap: " + maiorGap);
        System.out.println("Média dos gaps: " + mediaGap);
    }
}
