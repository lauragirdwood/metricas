////package estudo.metricas.metrics;
////
////public class fef {
////    package estudo.metricas.metrics;
////
////import io.micrometer.core.instrument.MeterRegistry;
////import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
////import io.micrometer.core.instrument.Tags;
////import org.junit.jupiter.api.BeforeEach;
////import org.junit.jupiter.api.Test;
////
////import static org.mockito.Mockito.*;
////
////    class MetricUtilsTest {
////
////        private MetricUtils metricUtils;
////
////        private MeterRegistry meterRegistry;
////
////        @BeforeEach
////        void setUp() {
////            meterRegistry = mock(SimpleMeterRegistry.class);
////            metricUtils = new MetricUtils(meterRegistry);
////        }
////
////        @Test
////        void incrementCounter_shouldIncrementCounter() {
////            String metricName = "test_metric";
////            Object object = new Object();
////            Tags tags = Tags.of("test_tag", "test_value");
////
////            metricUtils.incrementCounter(metricName, object, tags);
////
////            verify(meterRegistry, times(1)).counter(metricName, MetricUtils.getCommonTags(object).and(tags)).increment();
////        }
////
////        @Test
////        void financeiroSummary_shouldRecordSummary() {
////            String metricName = "test_metric";
////            Object object = new Object();
////            double valor = 10.0;
////
////            metricUtils.financeiroSummary(metricName, object, valor);
////
////            verify(meterRegistry, times(1)).summary(metricName, MetricUtils.getCommonTags(object)).record(valor);
////        }
////    }
////
////}
//public class MetricUtilsTest {
//
//    @Test
//    public void testGetCommonTags() {
//        // define os valores de teste para as tags comuns
//        String nomeHeader = "João";
//        String idadeHeader = "35";
//        String nomePayload = "Maria";
//        String idadePayload = "28";
//
//        // cria objetos de teste Header e Payload
//        Header header = new Header(nomeHeader, idadeHeader);
//        Payload payload = new Payload(nomePayload, idadePayload);
//
//        // obtém as tags comuns dos objetos de teste usando o método getCommonTags da classe MetricUtils
//        Tags tagsHeader = MetricUtils.getCommonTags(header);
//        Tags tagsPayload = MetricUtils.getCommonTags(payload);
//
//        // define as tags comuns válidas esperadas
//        List<Tag> expectedTags = Arrays.asList(
//                Tag.of("name", nomeHeader),
//                Tag.of("idade", idadeHeader)
//        );
//
//        // verifica se as tags comuns esperadas estão presentes nas tags retornadas pela chamada do método getCommonTags para o objeto Header
//        List<Tag> headerTagsList = tagsHeader.stream().collect(Collectors.toList());
//        Assertions.assertTrue(headerTagsList.containsAll(expectedTags));
//
//        // verifica se as tags comuns esperadas estão presentes nas tags retornadas pela chamada do método getCommonTags para o objeto Payload
//        List<Tag> payloadTagsList = tagsPayload.stream().collect(Collectors.toList());
//        Assertions.assertTrue(payloadTagsList.containsAll(expectedTags));
//
//        // verifica se todas as tags comuns foram preenchidas com valores válidos
//        Assertions.assertTrue(headerTagsList.stream().allMatch(tag -> isValidCommonTagValue(tag.getKey(), tag.getValue())));
//        Assertions.assertTrue(payloadTagsList.stream().allMatch(tag -> isValidCommonTagValue(tag.getKey(), tag.getValue())));
//    }
//
//    private boolean isValidCommonTagValue(String tagName, String

//@Test
//public void testCommonTags() {
//        Header header = new Header("John", "30");
//        Payload payload = new Payload("Jane", "25");
//
//        Tags headerTags = MetricUtils.getCommonTags(header);
//        assertTrue(headerTags.contains(Tag.of(MetricTagsConstants.NAME, header.getNome())));
//        assertTrue(headerTags.contains(Tag.of(MetricTagsConstants.IDADE, header.getIdade())));
//
//        Tags payloadTags = MetricUtils.getCommonTags(payload);
//        assertTrue(payloadTags.contains(Tag.of(MetricTagsConstants.NAME, payload.getNome())));
//        assertTrue(payloadTags.contains(Tag.of(MetricTagsConstants.IDADE, payload.getIdade())));
//        }