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

    public void inserir(Registro reg) {
        String codigo = reg.getCodigo();
        int chave = 0;
        for (int i = 0; i < codigo.length(); i++) {
            char c = codigo.charAt(i);
            chave = chave * 10 + (c - '0');
        }

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
            int valor = 0;
            for (int i = 0; i < codigo.length(); i++) {
                valor = valor * 10 + (codigo.charAt(i) - '0');
            }

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
}
