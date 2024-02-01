package com.ymmihw.events.externalization;

import com.ymmihw.Application;
import com.ymmihw.events.externalization.listener.TestKafkaListenerConfig;
import com.ymmihw.events.externalization.listener.TestListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest(classes = {Application.class, TestKafkaListenerConfig.class})
class EventsExternalizationLiveTest {

    @Autowired
    private Baeldung baeldung;
    @Autowired
    private TestListener listener;
    @Autowired
    private ArticleRepository repository;


    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", () -> "172.20.72.207:9094");
    }


    @BeforeEach
    void beforeEach() {
        listener.reset();
        repository.deleteAll();
    }

    @Test
    void whenArticleIsSavedToDB_thenItIsAlsoPublishedToKafka() {
        var article = new Article("introduction-to-spring-boot", "Introduction to Spring Boot", "John Doe", "<p> Spring Boot is [...] </p>");

        baeldung.createArticle(article);

        await().untilAsserted(() ->
                assertThat(listener.getEvents())
                        .hasSize(1)
                        .first().asString()
                        .contains("\"slug\":\"introduction-to-spring-boot\"")
                        .contains("\"title\":\"Introduction to Spring Boot\""));

        assertThat(repository.findAll())
                .hasSize(1)
                .first()
                .extracting(Article::getSlug, Article::getTitle)
                .containsExactly("introduction-to-spring-boot", "Introduction to Spring Boot");
    }

    @Test
    void whenPublishingMessageFails_thenArticleIsStillSavedToDB() throws InterruptedException {
        var article = new Article(null, "Introduction to Spring Boot", "John Doe", "<p> Spring Boot is [...] </p>");

        baeldung.createArticle(article);

        TimeUnit.SECONDS.sleep(10);

        assertThat(listener.getEvents()).isEmpty();

        assertThat(repository.findAll())
                .hasSize(1)
                .first()
                .extracting(Article::getTitle, Article::getAuthor)
                .containsExactly("Introduction to Spring Boot", "John Doe");
    }
}