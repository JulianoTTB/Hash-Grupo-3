public class HashMultiplicacao {
    private ListaEncadeada [] tabelaHash;
    private int tamanho;

    public HashMultiplicacao(int tamanho) {
        this.tamanho = tamanho;
        this.tabelaHash = new ListaEncadeada [this.tamanho];
    }

    public int funcaoHashMultiplicacao(String codigo) {
        int soma = 0;
        for (int i = 0; i < codigo.length(); i++) {
            soma += codigo.charAt(i);
        }
        double constante = 0.6180339887;
        double valor = soma * constante;

        int inteiro = (int) valor;
        double decimal = valor - inteiro;

        int hash = (int) (tamanho * decimal);
        return hash;
    }


    public int inserir(Registro registro) {
        int hash = funcaoHashMultiplicacao(registro.getCodigo());
        if (tabelaHash[hash] == null) {
            tabelaHash[hash] = new ListaEncadeada();
        }
        return tabelaHash[hash].inserir(registro);
    }


    public void remover(String codigo) {
        int hash = funcaoHashMultiplicacao(codigo);

        if (tabelaHash[hash] != null) {
            tabelaHash[hash].remover(codigo);
        }
    }

    public int buscar(String codigo) {
        int hash = funcaoHashMultiplicacao(codigo);

        if (tabelaHash[hash] != null) {
             return tabelaHash[hash].buscar(codigo);
        }
        return -1;
    }

    public int [][] maioresListas() {
        int [] indiceMaiores = {-1, -1, -1};
        int [] maioresListas = {0, 0, 0};

        for(int i = 0; i < this.tamanho; i++) {
            if(tabelaHash[i] != null) {
                int tamanho = tabelaHash[i].getTamanho();

                if(tamanho > maioresListas[0]) {
                    maioresListas[2] = maioresListas[1];
                    indiceMaiores[2] = indiceMaiores[1];

                    maioresListas[1] = maioresListas[0];
                    indiceMaiores[1] = indiceMaiores[0];

                    maioresListas[0] = tamanho;
                    indiceMaiores[0] = i;

                } else if(tamanho > maioresListas[1]) {

                    maioresListas[2] = maioresListas[1];
                    indiceMaiores[2] = indiceMaiores[1];

                    maioresListas[1] = tamanho;
                    indiceMaiores[1] = i;

                } else if(tamanho > maioresListas[2]) {

                    maioresListas[2] = tamanho;
                    indiceMaiores[2] = i;
                }
            }
        }
        return new int [][] {indiceMaiores, maioresListas};
    }

    public double[] numeroGaps() {
        int ultimoIndice = -1;
        int menorGap = tamanho;
        int maiorGap = 0;
        int somaGaps = 0;
        int numGaps = 0;

        for(int i = 0; i < tabelaHash.length; i++) {
            if(tabelaHash[i] != null) {
                if(ultimoIndice != -1) {
                    int gap = i - ultimoIndice - 1;

                    if(gap < menorGap) {
                        menorGap = gap;
                    }

                    if(gap > maiorGap) {
                        maiorGap = gap;
                    }

                    somaGaps += gap;
                    numGaps++;
                }
                ultimoIndice = i;
            }
        }

        double mediaGap = 0;
        if(numGaps > 0) {
            mediaGap = (double)somaGaps / numGaps;
        }

        return new double[] {menorGap, maiorGap, mediaGap};
    }

}
