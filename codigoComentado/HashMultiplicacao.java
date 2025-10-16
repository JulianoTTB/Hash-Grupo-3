public class HashMultiplicacao {
    private ListaEncadeada [] tabelaHash; // Atributo que armazenará o vetor de listas encadeadas para cada posição da tabela hash.
    private int tamanho;// Atributo para definir o tamanho de cada lista encadeada.

    public HashMultiplicacao(int tamanho) {
        this.tamanho = tamanho;
        this.tabelaHash = new ListaEncadeada [this.tamanho]; // Cria um vetor de referência para listas encadeadas.
    }

    public int funcaoHashMultiplicacao(String codigo) {
        // A variável soma armazena a soma do valor de cada caractere do código.
        int soma = 0;
        for (int i = 0; i < codigo.length(); i++) {
            soma += codigo.charAt(i);
        }
        double constante = 0.6180339887; // Constante de Knutch.
        double valor = soma * constante;

        int inteiro = (int) valor; // Corta a parte decimal da variável valor.
        double decimal = valor - inteiro; // Remove a parte inteira do valor e armazena a parte decimal na variável decimal.

        // Multiplica a parte fracionada pelo tamanho da tabela hash.
        int hash = (int) (tamanho * decimal);
        return hash;
    }


    public int inserir(Registro registro) {
        int hash = funcaoHashMultiplicacao(registro.getCodigo());
        // Cria uma lista encadeada na posição tabelaHash[hash] caso ela não exista.
        if (tabelaHash[hash] == null) {
            tabelaHash[hash] = new ListaEncadeada();
        }
        // Caso a lista já exista, insere o registro e o hash na posição e retorna o número de colisões.
        return tabelaHash[hash].inserir(registro);
    }


    public void remover(String codigo) {
        int hash = funcaoHashMultiplicacao(codigo);

        // Verifica se existe algum valor armazenado na posição.
        if (tabelaHash[hash] != null) {
            tabelaHash[hash].remover(codigo);
        }
    }

    public int buscar(String codigo) {
        int hash = funcaoHashMultiplicacao(codigo);

        // Verifica se existe algum valor armazenado na posição.
        if (tabelaHash[hash] != null) {
            // Retorna o número de colisões.
             return tabelaHash[hash].buscar(codigo);
        }
        // Caso não exista na tabela hash, retorna -1.
        return -1;
    }

    public int [][] maioresListas() {
        int [] indiceMaiores = {-1, -1, -1};
        int [] maioresListas = {0, 0, 0};

        // Percorre todas as posições da tabela hash e verifica qual delas possuem uma lista encadeada diferente de null para verificar o tamanho.
        for(int i = 0; i < this.tamanho; i++) {
            // Verifica se existe uma lista encadeada que contenha nós na posição i.
            if(tabelaHash[i] != null) {
                int tamanho = tabelaHash[i].getTamanho();

                // Compara o tamanho da lista na posiçao i da tabela hash com a maior lista do vetor maioresListas.
                if(tamanho > maioresListas[0]) {
                    // Verdadeiro caso o tamanho da lista encadeada i na tabela hash seja maior que a maior lista do vetor listaMaiores.
                    maioresListas[2] = maioresListas[1]; // Coloca a segunda maior lista na terceira posição.
                    indiceMaiores[2] = indiceMaiores[1]; // Altera o indice da segunda maior lista para a terceira posição.

                    maioresListas[1] = maioresListas[0]; // Coloca a maior lista na segunda posição.
                    indiceMaiores[1] = indiceMaiores[0]; // Altera o indice da maior lista para a segunda posição.

                    maioresListas[0] = tamanho; // Insere o tamanho da lista encadeada i da tabela hash na primeiro posição do vetor.
                    indiceMaiores[0] = i; // Insere o índice da posição da lista encadeada na primeira primeira posição do vetor.

                // Verifica o tamanho da lista na posiçao i da tabela hash com a segunda maior lista do vetor maioresListas.
                } else if(tamanho > maioresListas[1]) {

                    maioresListas[2] = maioresListas[1]; // Coloca a segunda maior lista na terceira posição.
                    indiceMaiores[2] = indiceMaiores[1]; // Altera o indice da segunda maior lista para a terceira posição.

                    maioresListas[1] = tamanho; // Insere o tamanho da lista encadeada i da tabela hash na segunda posição do vetor.
                    indiceMaiores[1] = i; // Insere o índice da posição da lista encadeada na segunda posição do vetor.

                    // Verifica o tamanho da lista na posiçao i da tabela hash com a terceira maior lista do vetor maioresListas.
                } else if(tamanho > maioresListas[2]) {

                    maioresListas[2] = tamanho; // Insere o tamanho da lista encadeada i da tabela hash na terceira posição do vetor maioresListas.
                    indiceMaiores[2] = i; // Insere o indice da posição da lista encadeada na terceira posiçao do vetor.
                }
            }
        }
        // Retorna uma matriz com o primeiro valor sendo o indice e o segundo valor sendo o tamanho da lista encadeada.
        return new int [][] {indiceMaiores, maioresListas};
    }

    public double[] numeroGaps() {
        int ultimoIndice = -1;
        int menorGap = tamanho; // Menor espaço entre 2 listas
        int maiorGap = 0; // Maior espaço entre 2 listas
        int somaGaps = 0; // Soma do número de gaps para calcular média
        int numGaps = 0; // Contagem de gaps encontrados

        // Percorre todas as posições da tabela hash.
        for(int i = 0; i < tabelaHash.length; i++) {
            if(tabelaHash[i] != null) { // Verifica se existe alguma lista encadeada na posição i.
                if(ultimoIndice != -1) { // Verifica se não está no primeiro elemento da tabela.
                    int gap = i - ultimoIndice - 1; // Calcula o espaço entre o elemento atual e a ultima posição onde existia um elemento.

                    // Atualiza o menor gap se o gap atual for menor.
                    if(gap < menorGap) {
                        menorGap = gap;
                    }

                    // Atualiza o maior gap se o gap atual for maior.
                    if(gap > maiorGap) {
                        maiorGap = gap;
                    }

                    somaGaps += gap; // Soma a variável somaGaps com o gap atual.
                    numGaps++; // Aumenta a contagem do número de gaps.
                }
                ultimoIndice = i; // Atualiza o indice do último elemento encontrado.
            }
        }

        double mediaGap = 0; // Média dos gaps.
        if(numGaps > 0) {
            mediaGap = (double)somaGaps / numGaps; // Calcula a média dos gaps.
        }

        return new double[] {menorGap, maiorGap, mediaGap}; // retorna um vetor com menor, maior e a média de gaps.
    }

}
