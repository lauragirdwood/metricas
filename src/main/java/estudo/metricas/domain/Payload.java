package estudo.metricas.domain;

import java.math.BigDecimal;

public class Payload {

    String nome;
    String idade;
    double valor;

    public Payload(){};
    public Payload(String nome, String idade) {
        this.nome = nome;
        this.idade = idade;
        valor = 0.01;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor += 0.01;
    }
}
