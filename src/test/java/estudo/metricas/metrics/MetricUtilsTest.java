package estudo.metricas.metrics;

import estudo.metricas.domain.Header;
import estudo.metricas.domain.Payload;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static estudo.metricas.metrics.MetricTagsConstants.IDADE;
import static estudo.metricas.metrics.MetricTagsConstants.NAME;
import static estudo.metricas.metrics.MetricUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {MetricUtils.class})
public class MetricUtilsTest{

    @Autowired
    private MetricUtils metricUtils;

    private MeterRegistry registry = null;
    private Counter counter;
    private DistributionSummary summary;
    private final Header header = new Header("Teste", "25");
    private final Payload payload = new Payload("Jane", "30");

    @BeforeEach
    public void setup() {
        registry = new SimpleMeterRegistry();
        counter = registry.counter("test.counter");
        summary = registry.summary("test.summary");
    }

    @Test
    void incrementCounterTest() {
        Tags specificTags = Tags.of("status", "success");
        Tags commonTags = Tags.of(NAME, "Jane", IDADE, "30");

        incrementCounter("test.counter", payload, specificTags);

        verify(registry).counter("test.counter", commonTags.and(specificTags));
        assertEquals(1, counter.count());
    }

    @Test
    void recordSummaryTest() {
        Tags commonTags = Tags.of("name", "Teste");
        Payload payload = new Payload("Jane", "25");
        payload.setValor(10.0);
        double valor = payload.getValor();

        financeiroSummary("test.summary", new Header(), valor);

        verify(registry).summary("test.summary", commonTags);
        assertEquals(1, summary.count());
    }
//    private Tags commonTags;
//    @MockBean
//    private Counter counter;
//    @MockBean
//    private MeterRegistry registry;
//    @MockBean
//    private DistributionSummary summary;
//    private final Header header = new Header("Teste", "25");
//    private final Payload payload = new Payload("Jane", "30");
//
//    @Test
//    void incrementCounterTest() {
//
//        Tags specificTags = Tags.of("status", "success");
//        commonTags = Tags.of(NAME, "Jane", IDADE, "30");
//
//        incrementCounter("test.counter", payload, specificTags);
//
//        verify(registry).counter("test.counter", commonTags.and(specificTags));
//        verify(counter).increment();
//        assertEquals(1,counter.count());
//    }
//
//    @Test
//    void recordSummaryTest() {
//
//        Tags commonTags = Tags.of("name", "Teste");
//        Payload payload = new Payload("Jane", "25");
//        payload.setValor(10.0);
//        double valor = payload.getValor();
//
//        financeiroSummary("test.summary", new Header(), valor);
//
//        verify(registry).summary("test.summary", commonTags);
//        verify(summary).record(valor);
//
//    }
//
//    @Test
//    public void testCommonTags() {
//        Header header = new Header("John", "30");
//        Payload payload = new Payload("Jane", "25");
//
//        Tags headerTags = getCommonTags(header);
//        assertEquals(headerTags, getTagsFromObject(header));
//
//        Tags payloadTags = getCommonTags(payload);
//        assertEquals(payloadTags, getTagsFromObject(payload));
//    }
//

}
