package estudo.metricas.beans;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicrometerConfig {
    @Bean

    public MeterRegistry meterRegistry() {
        return new CompositeMeterRegistry();
    }

}
