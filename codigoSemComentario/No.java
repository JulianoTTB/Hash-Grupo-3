public class No {
    private Registro dado;
    private No prox;

    public No(Registro dado){
        this.dado = dado;
        this.prox = null;
    }

    public Registro getDado() {
        return dado;
    }

    public void setDado(Registro dado) {
        this.dado = dado;
    }

    public No getProx() {
        return prox;
    }

    public void setProx(No prox) {
        this.prox = prox;
    }
}
