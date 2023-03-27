package estudo.metricas.domain;

import io.micrometer.core.instrument.Tags;

import static estudo.metricas.metrics.MetricTagsConstants.IDADE;
import static estudo.metricas.metrics.MetricTagsConstants.NAME;

public class Header {

    String nome;
    String idade;

    public Header(){};
    public Header(String nome, String idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }
    public String getIdade() {
        return idade;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }
}
