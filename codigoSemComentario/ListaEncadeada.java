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

    public int inserir(Registro registro){
        No novo_no = new No(registro);
        int colisoes = 0;
        if(vazia()){
            this.raiz = novo_no;
            this.tamanho++;
            return colisoes;
        }

        No atual = this.raiz;

        for(; atual.getProx() != null; atual = atual.getProx()) {
            colisoes++;
        }
        atual.setProx(novo_no);
        this.tamanho++;
        return colisoes;
    }

    public int buscar(String codigo){
        if (vazia()) return -1;

        No atual = this.raiz;
        int colisoes = 0;

        for(; atual != null; atual = atual.getProx()) {
            if (atual.getDado().getCodigo().equals(codigo)) {
                return colisoes;
            }
            colisoes++;
        }

        return -1;
    }

    public void remover(String codigo){
        if (vazia()) return;


        No atual = this.raiz;

        if(atual.getDado().getCodigo().equals(codigo)){
            this.raiz = atual.getProx();
            this.tamanho--;
            return;
        }

        No proximo = atual.getProx();

        for(; proximo != null; atual = atual.getProx(), proximo = proximo.getProx())
            if (proximo.getDado().getCodigo().equals(codigo)) break;


        if (proximo != null){
            atual.setProx(proximo.getProx());
            this.tamanho--;
        }
    }
}
