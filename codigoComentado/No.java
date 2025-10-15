public class No {
    private Registro dado; // Atributo armazena o registro do código
    private No prox; // Atributo armazena a referência para o próximo nó da lista

    // Construtor define o dado com base no argumento passado
    public No(Registro dado){
        this.dado = dado;
        this.prox = null;
    }

    // Retorna o registro armazenado
    public Registro getDado() {
        return dado;
    }

    // Modifica o registro armanzenado no nó
    public void setDado(Registro dado) {
        this.dado = dado;
    }

    // Retrona a referência para o próximo nó da lista
    public No getProx() {
        return prox;
    }

    // Modifica a referência do próximo nó da lista
    public void setProx(No prox) {
        this.prox = prox;
    }
}
