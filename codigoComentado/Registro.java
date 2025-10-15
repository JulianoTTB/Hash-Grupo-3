public class Registro {
    private String codigo; // Atributo armazena um código de 9 dígitos

    // Construtor atribui o argumento passado ao atributo codigo
    public Registro(String codigo){
        this.codigo = codigo;
    }

    // Retorna o codigo armazenado
    public String getCodigo() {
        return codigo;
    }

    // Modifica o atributo do código
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
