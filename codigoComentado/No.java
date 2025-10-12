public class No {
    private Registro dado;
    private int hash;
    private No prox;

    public No(Registro dado){
        this.dado = dado;
        this.hash = -1;
        this.prox = null;
    }

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
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
