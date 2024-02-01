package com.ymmihw.events.externalization;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.modulith.events.EventExternalizationConfiguration;
import org.springframework.modulith.events.RoutingTarget;
import org.springframework.util.Assert;

@Configuration
class EventExternalizationConfig {

    @Bean
    EventExternalizationConfiguration eventExternalizationConfiguration() {
        return EventExternalizationConfiguration.externalizing()
          .select(EventExternalizationConfiguration.annotatedAsExternalized())
          .route(
            ArticlePublishedEvent.class,
                  it -> {
                      Assert.notNull(it.slug(), "Article Slug must not be null!");
                      return RoutingTarget.forTarget("baeldung.articles.published").andKey(it.slug());
                  }
          )
          .mapping(
            ArticlePublishedEvent.class,
            it -> new ArticlePublishedKafkaEvent(it.slug(), it.title())
          )
          .build();
    }

    record ArticlePublishedKafkaEvent(String slug, String title) {
    }
}

