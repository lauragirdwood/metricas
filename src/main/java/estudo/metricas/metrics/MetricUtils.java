package estudo.metricas.metrics;

import estudo.metricas.domain.Header;
import estudo.metricas.domain.Payload;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static estudo.metricas.metrics.MetricNamesConstants.ERRO;
import static estudo.metricas.metrics.MetricTagsConstants.IDADE;
import static estudo.metricas.metrics.MetricTagsConstants.NAME;

@Component
public class MetricUtils {

    private static MeterRegistry registry;
    public static final String VALOR_NAO_DEFINIDO = "valor_undefined";
    private static final Logger LOGGER = LogManager.getLogger(MetricUtils.class);

    @Autowired
    public MetricUtils (MeterRegistry registry) {
        MetricUtils.registry = registry;
    }

    public static void incrementCounter(String name, Object object, Tags specificTags) {
        try {
            registry.counter(name, getCommonTags(object).and(specificTags)).increment();
        } catch (Exception e) {
            LOGGER.warn("erro ao registrar metrica", e);
        }
    }

    public static void financeiroSummary(String name, Object object, double valor) {
        try {
            registry.summary(name, getCommonTags(object)).record(valor);
        } catch (Exception e) {
            LOGGER.warn("erro ao registrar metrica", e);
        }
    }

    public static Tags getCommonTags(Object object) {
        return getTagsFromObject(object);
    }

    public static Tags getTagsFromObject(Object object) {

        if (object instanceof Header) {
            Header header = (Header) object;
            return Tags.of(
                    NAME, getValid(header.getNome()),
                    IDADE, getValid(header.getIdade())
            );
        } else if (object instanceof Payload) {
            Payload payload = (Payload) object;
            return Tags.of(
                    NAME, getValid(payload.getNome()),
                    IDADE, getValid(payload.getIdade())
            );
        } else {
            return Tags.of(ERRO, VALOR_NAO_DEFINIDO);
        }
    }

    private static String getValid(String metricValue) {
        return StringUtils.hasText(metricValue) ? metricValue : VALOR_NAO_DEFINIDO;
    }

}

/*
* //    private static final String VALOR_ALTA_CARDINALIDADE = "valor_com_alta_cardinalidade";
//    private static final long MAX_LIMIT_ALTA_CARDINALIDADE = 100;
//    private static Tag getValidMetricValue(String strTag, String strValue) {
//        return (strValue != null && !strValue.isEmpty()) &&
//                isValidCardinalityTagValue(strTag) ?
//                Tag.of(strTag,strValue) : Tag.of(strTag,VALOR_NAO_DEFINIDO);
//    }
//
//    public static boolean isValidCardinalityTagValue(String tagName) {
//        Instant fiveMinutesAgo = Instant.now().minus(Duration.ofMinutes(5));
//        long tagValuesCount = registry
//                .getMeters()
//                .stream()
//                .flatMap(meter -> meter.getId().getTags().stream())
//                .filter(tag -> tag.getKey().equals(tagName))
//                .map(Tag::getValue)
//                .distinct()
//                .count();
//
//        return tagValuesCount < MAX_LIMIT_ALTA_CARDINALIDADE;
//    }
* */