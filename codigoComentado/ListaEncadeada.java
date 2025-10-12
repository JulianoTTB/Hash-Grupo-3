public class ListaEncadeada {
    private No raiz;
    private int tamanho;

    public ListaEncadeada(){
        this.raiz = null;
        this.tamanho = 0;
    }

    public boolean vazia(){
        return tamanho == 0;
    }

    public No getRaiz() {
        return raiz;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void inserir(Registro registro, int hash){
        No novo_no = new No(registro);
        novo_no.setHash(hash);
        if(vazia()){
            this.raiz = novo_no;
            this.tamanho++;
            return;
        }

        No atual = this.raiz;

        for(; atual.getProx() != null; atual = atual.getProx());

        atual.setProx(novo_no);
        this.tamanho++;
    }

    public Registro buscar(int hash){
        if (vazia()) return null;

        No atual = this.raiz;

        for(; atual != null; atual = atual.getProx())
            if (atual.getHash() == hash) return atual.getDado();

        return null;
    }

    public void remover(int hash){
        if (vazia()) return;


        No atual = this.raiz;

        if(atual.getHash() == hash){
            this.raiz = atual.getProx();
            this.tamanho--;
            return;
        }

        for(; atual.getProx().getHash() != hash; atual = atual.getProx());

        No proximo = atual.getProx();

        atual.setProx(proximo.getProx());
        this.tamanho--;
    }
}
