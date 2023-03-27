package estudo.metricas.controller;

import estudo.metricas.domain.Header;
import estudo.metricas.domain.Payload;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Tags;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static estudo.metricas.metrics.MetricNamesConstants.*;
import static estudo.metricas.metrics.MetricTagsConstants.*;
import static estudo.metricas.metrics.MetricUtils.*;

@RestController
public class Controller {
    private static int contador = 0;
    private final Header header = new Header("laura", "33");
    private final Payload payload = new Payload("laurita", "06");

    @GetMapping
    @Timed(value = "print.hello.time", percentiles = {0.5, 0.9})
    public String estimulaContador() {

        try {
            int iterador = 10000;
            while (iterador > 0) {
                System.out.println("Hello world!");
                iterador--;
                incrementCounter(CLEARING_ENTRADA, payload, Tags.of(OPERATION, header.getNome()));
                incrementCounter(AUT_DIGITAIS_ENTRADA, header, Tags.of(OPERATION, header.getIdade()));
                incrementCounter(AUT_DIGITAIS_SAIDA_ERROR, payload, Tags.of(PAYMENT_NETWORK, header.getNome()));
                incrementCounter(AUT_PRESENTE_LISTENER_INICIO, header, Tags.of(OPERATION, header.getNome(), PAYMENT_NETWORK, "oi"));
                financeiroSummary(CLEARING_SAIDA_SUCCESS_VALOR, payload, payload.getValor());
            }
            return "foi! " + ++contador;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
