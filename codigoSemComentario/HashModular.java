public class HashModular {
    private final Registro[] tabelaHash;
    private final int tamanho;
    private int gapMenor;
    private int gapMaior;
    private double gapMedia;

    public HashModular(int tamanho){
        this.tamanho = tamanho;
        this.tabelaHash = new Registro[this.tamanho];
        this.gapMenor = 1_000_000_000;
        this.gapMaior = 0;
        this.gapMedia = 0;

    }


    public int hashModular(String codigo){
        int valor = 0;
        int position = 1;
        for(int i = 0; i < codigo.length(); i++)
            valor += ((int)codigo.charAt(i) * position++);


        return (valor + 163) % this.tamanho;
    }

    public int hashModular2(int hash){
        return 997 - (hash % 997);
    }

    public int reHashing(int hashPrimario, int colisoes){
        return (hashPrimario + colisoes * hashModular2(hashPrimario)) % this.tamanho;
    }


    public int buscar(String codigo){
        int hash = hashModular(codigo);

        int colisoes = 0;
        while(tabelaHash[hash] != null){
            if(tabelaHash[hash].getCodigo().equals(codigo)) break;
            hash = reHashing(hash, colisoes);
            colisoes++;

            if(colisoes == this.tamanho - 1) return -1;
        }

        if(tabelaHash[hash] == null) return -1;
        return colisoes;
    }

    public int inserir(Registro registro){
        int hash = hashModular(registro.getCodigo());
        int colisoes = 0;
        while(tabelaHash[hash] != null){
            hash = reHashing(hash, colisoes);
            colisoes++;

            if(colisoes == this.tamanho - 1) return -1;
        }

        tabelaHash[hash] = registro;

        return colisoes;
    }


    public int remover(String codigo){
        int hash = hashModular(codigo);
        int colisoes = 0;
        while(tabelaHash[hash] != null){
            if(tabelaHash[hash].getCodigo().equals(codigo)) break;
            hash = reHashing(hash, colisoes);
            colisoes++;

            if(colisoes == this.tamanho -1) return -1;
        }

        tabelaHash[hash] = null;

        return colisoes;
    }


    public void encontrarGap(){
        int gap = 0;
        int totalGaps = 0;
        for(int i = 0; i < this.tamanho; i++){
            if(tabelaHash[i] == null){
                gap++;
            }
            else{
                if(gap <= 0)continue;
                totalGaps++;
                if (gap > this.gapMaior)
                    this.gapMaior = gap;
                if (gap < gapMenor)
                    this.gapMenor = gap;

                this.gapMedia += gap;

                gap = 0;
            }
        }

        if (gap > 0 && totalGaps > 0) {
            this.gapMedia += gap;
            totalGaps++;
            if (gap > this.gapMaior) this.gapMaior = gap;
            if (gap < this.gapMenor) this.gapMenor = gap;
        }
        if (totalGaps > 0)
            this.gapMedia = this.gapMedia / totalGaps;
        else
            this.gapMedia = 0;
    }

    public int getGapMenor() {
        return gapMenor;
    }

    public int getGapMaior() {
        return gapMaior;
    }

    public double getGapMedia() {
        return gapMedia;
    }
}
