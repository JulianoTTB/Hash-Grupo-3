public class HashDuplo {
    private Registro[] tabela;
    private int tamanho;
    private int colisao;

    public HashDuplo(int tamanho){
        this.tamanho = tamanho;
        this.tabela = new Registro[tamanho];
        this.colisao = 0;
    }

    private int hashPrincipal(int chave){
        return chave % tamanho;
    }

    private int segundaFuncaoHash(int chave){
        return 1 + (chave % (tamanho - 1));
    }

    private int hash(int chave, int i){
        return (hashPrincipal(chave) + i * segundaFuncaoHash(chave) % tamanho);
    }

    private void inserirElemento(Registro r){
        int chave = Integer.parseInt(r.getCodigo());
        for (int i = 0; i < tamanho; i++){
            int posicao = hash(chave, i);
            if(tabela[posicao] == null){
                tabela[posicao] = r;
                return;
            }
            else{
                colisao++;

            }

        }
        System.out.println("Não foi possivel inserir pois a tabela está cheia. " + r.getCodigo());

    }

    public boolean buscarElemento(int chave){
        for (int i = 0; i < tamanho; i++){
            int posicao = hash(chave, i);
            if(tabela[posicao] == null){
                return false;
            }
            if(Integer.parseInt(tabela[posicao].getCodigo()) == chave){
                return true;
            }
        }
        return false;
    }

    public int getColisao(){
        return colisao;
    }
}
