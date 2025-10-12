public class HashModular {
    private Registro[] tabelaHash;
    private int tamanho;

    public HashModular(int tamanho){
        this.tamanho = tamanho;
        this.tabelaHash = new Registro[this.tamanho];
    }


    public int hashModular(String codigo){
        int valor = 0;
        int position = 1;
        for(int i = 0; i < codigo.length(); i++)
            valor += ((int)codigo.charAt(i) * position++);


        return (valor + 163) % this.tamanho;
    }

    public int reHashing(int hashPrimario){
        return (hashPrimario * 23 + 1) % this.tamanho;
    }


    public Registro buscar(String codigo){
        int hash = hashModular(codigo);

        int colisoes = 0;
        while(tabelaHash[hash] != null){
            if(tabelaHash[hash].getCodigo() == codigo) break;
            hash = reHashing(hash);
            colisoes++;

            if(colisoes == 5) return null;
        }

        if(tabelaHash[hash] == null) return null;
        return tabelaHash[hash];
    }

    public boolean inserir(Registro registro){
        int hash = hashModular(registro.getCodigo());
        int colisoes = 0;
        while(tabelaHash[hash] != null){
            hash = reHashing(hash);
            colisoes++;

            if(colisoes == 5) return false;
        }

        tabelaHash[hash] = registro;

        return true;
    }


    public boolean remover(String codigo){
        int hash = hashModular(codigo);
        int colisoes = 0;
        while(tabelaHash[hash] != null){
            if(tabelaHash[hash].getCodigo() == codigo) break;
            hash = reHashing(hash);
            colisoes++;

            if(colisoes == 5) return false;
        }

        tabelaHash[hash] = null;

        return true;
    }
}
