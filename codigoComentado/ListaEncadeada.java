public class ListaEncadeada {
    private No raiz; // Atributo que armazena o nó raiz da lista encadeada
    private int tamanho; // Atributo que armazena o tamanho da lista encadeada

    // Construtor atribui valor nulo a raiz e 0 no tamanho, pois a lista acabou de ser construída
    public ListaEncadeada(){
        this.raiz = null;
        this.tamanho = 0;
    }

    // Retorna verdadeiro se a lista estiver vazia ou falso se não estiver
    public boolean vazia(){
        return tamanho == 0;
    }

    // Retorna a raiz da lista
    public No getRaiz() {
        return raiz;
    }

    // Retorna o tamanho da lista
    public int getTamanho() {
        return tamanho;
    }

    public int inserir(Registro registro){
        // Cria nó com base no que foi passado
        No novo_no = new No(registro);
        // Variável para contar colisões
        int colisoes = 0;
        // Se estiver vazia, então o nó será a raiz da lista
        if(vazia()){
            this.raiz = novo_no;
            this.tamanho++;
            return colisoes;
        }

        // Ponteiro para a raiz da lista
        No atual = this.raiz;

        // Percorre a lista em busca do último nó
        for(; atual.getProx() != null; atual = atual.getProx()) {
            colisoes++;
        }
        // Atribui à referência do próximo nó do último nó, o novo nó
        atual.setProx(novo_no);
        this.tamanho++; // Incrementa o tamanho da lista
        return colisoes;
    }

    public int buscar(String codigo){
        // Se a lista estiver vazia, então o código não está nela
        if (vazia()) return -1; // lista vazia retorna -1

        // Ponteiro para a raiz da lista
        No atual = this.raiz;
        int colisoes = 0;

        // Percorre a lista até chegar no final
        for(; atual != null; atual = atual.getProx()) {
            if (atual.getDado().getCodigo().equals(codigo)) { // Verifica se o nó atual contém o objeto com o código procurado
                return colisoes;
            }
            colisoes++;
        }

        return -1;
    }

    public void remover(String codigo){
        // Se estiver vazia, não tem nó para ser removido
        if (vazia()) return;


        // Ponteiro para a raiz da lista
        No atual = this.raiz;

        // Verifica se o nó procurado é a raiz
        if(atual.getDado().getCodigo().equals(codigo)){
            // Se for, define a nova raiz como o próximo da antiga raiz
            this.raiz = atual.getProx();
            this.tamanho--;
            return;
        }

        // Ponteiro para o próximo do atual
        No proximo = atual.getProx();

        // Percorre a lista até o final
        for(; proximo != null; atual = atual.getProx(), proximo = proximo.getProx())
            if (proximo.getDado().getCodigo().equals(codigo)) break; // Se o próximo conter o código procurado o loop será interrompido


        // Se o ponteiro do próximo não for nullo, será atribuido a referência do próximo do atual a referência do próximo do próximo
        if (proximo != null){
            atual.setProx(proximo.getProx());
            this.tamanho--; // Decrementa o tamanho da lista
        }
    }
}
