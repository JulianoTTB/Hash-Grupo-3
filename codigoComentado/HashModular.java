public class HashModular {
    private final Registro[] tabelaHash; // Atributo que armazenará o vetor de registros
    private final int tamanho; // Atributo que armazenará o tamanho do vetor de registros

    public HashModular(int tamanho){
        this.tamanho = tamanho;
        this.tabelaHash = new Registro[this.tamanho]; // Cria um vetor de registros com o tamanho passado na criação do objeto
    }


    public int hashModular(String codigo){
        // O valor é os valores ASCII dos números do código + a sua posição no código
        int valor = 0;
        int position = 1;
        for(int i = 0; i < codigo.length(); i++)
            valor += ((int)codigo.charAt(i) * position++);


        // O hash é o resto de divisão entre (valor + 163) e o tamanho do vetor de registros
        return (valor + 163) % this.tamanho;
    }

    public int hashModular2(int hash){
        // Hash 2 é 997 menos o resto de divisão entre o (hash passado como argumento e 997)
        return 997 - (hash % 997);
    }

    public int reHashing(int hashPrimario, int colisoes){
        // Rehashing é o resto de divisão entre o (hash passado como argumento + o número de colisões * o hash secundário) e o tamanho do vetor de registros
        return (hashPrimario + colisoes * hashModular2(hashPrimario)) % this.tamanho;
    }


    public int buscar(String codigo){
        // O método ira retornar o número de colisões até encontrar o registro que contenha o código passado como argumento, se estiver no vetor de registros
        int hash = hashModular(codigo); // Faz o hashing do código

        int colisoes = 0;
        // Enquanto a posição na tabela não for nula, o loop continuará o processo de rehashing
        while(tabelaHash[hash] != null){
            // Se o item nessa posição do vetor for o procurado, então o loop será finalizado
            if(tabelaHash[hash].getCodigo().equals(codigo)) break;
            // Caso não for o procurado, será feito um rehasing do hash e aumentará o número de colisões
            hash = reHashing(hash, colisoes);
            colisoes++;

            // Se o número de colisões chegar ao número de posições validas no vetor, então o registro não está no vetor
            if(colisoes == this.tamanho - 1) return -1;
        }

        if(tabelaHash[hash] == null) return -1; // Se a posição estiver nula, significa que o registro não está no vetor
        return colisoes; // Se estiver, será retornado o número de colisões
    }

    public int inserir(Registro registro){
        // O método ira retornar o número de colisões até inserir o registro que contenha o código passado como argumento, se estiver no vetor de registros
        int hash = hashModular(registro.getCodigo());
        int colisoes = 0;
        // Enquanto o valor na posição do vetor não for nulo, ocorrerá um processo de rehashing até encontrar uma posição nula
        while(tabelaHash[hash] != null){
            hash = reHashing(hash, colisoes);
            colisoes++;

            // Se o número de colisões chegar ao número de posições validas no vetor, então não há posição válida no vetor
            if(colisoes == this.tamanho - 1) return -1;
        }

        // Posição nula no vetor no índice hash, com isso será atribuído a essa posição o novo registro
        tabelaHash[hash] = registro;

        return colisoes;// Retorna o número de colisões
    }


    public int remover(String codigo){
        // O método ira retornar o número de colisões até remover o registro que contenha o código passado como argumento, se estiver no vetor de registros
        int hash = hashModular(codigo);
        int colisoes = 0;
        // Enquanto a posição na tabela não for nula, o loop continuará o processo de rehashing
        while(tabelaHash[hash] != null){
            // Se o item nessa posição do vetor for o procurado, então o loop será finalizado
            if(tabelaHash[hash].getCodigo().equals(codigo)) break;
            // Caso não for o procurado, será feito um rehasing do hash e aumentará o número de colisões
            hash = reHashing(hash, colisoes);
            colisoes++;

            // Se o número de colisões chegar ao número de posições validas no vetor, então o registro não está no vetor
            if(colisoes == this.tamanho -1) return -1;
        }

        // Remove o registro do vetor, atribuindo um valor nulo a sua posição
        tabelaHash[hash] = null;

        return colisoes; // Retorna o número de colisões
    }
}
