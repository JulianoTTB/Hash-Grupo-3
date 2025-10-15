public class HashMultiplicacao {
    private ListaEncadeada [] tabelaHash; // Atributo que armazenará o vetor de listas encadeadas para cada posição da tabela hash.
    private int tamanho; // Atributo para definir o tamanho de cada lista encadeada.

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
}
