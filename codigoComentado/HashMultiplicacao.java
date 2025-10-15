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
        // Caso a lista já exista, insere o registro e o hash na posição.
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
             return tabelaHash[hash].buscar(codigo);
        }
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

                // Compara o tamanho da posição t
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
        // Retorna uma matriz com o primeiro valor sendo o indice e o segundo valor sendo o tamanho da lista encadeada.
        return new int [][] {indiceMaiores, maioresListas};
    }

}
