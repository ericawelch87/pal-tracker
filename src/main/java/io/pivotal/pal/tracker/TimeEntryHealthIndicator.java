package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.boot.actuate.metrics.reader.MetricReader;
import org.springframework.stereotype.Component;

@Component
public class TimeEntryHealthIndicator implements HealthIndicator {


    private final MetricReader metricReader;

    public TimeEntryHealthIndicator(MetricReader metricReader) {
        this.metricReader = metricReader;
    }


    @Override
    public Health health() {
        Metric metric =  metricReader.findOne("gauge.timeEntries.count");
        Health.Builder healthBuilder = new Health.Builder();

        if(metric != null && metric.getValue().intValue() > 5) {
            healthBuilder.down();
        } else {
            healthBuilder.up();
        }

        return healthBuilder.build();
    }
}
