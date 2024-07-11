package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repositories.UrlCheckRepository;
import hexlet.code.repositories.UrlRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static hexlet.code.App.getApp;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AppTest {
    private Javalin app;
    private static MockWebServer testServer;
    private static String testUrl;

    @BeforeAll
    public static void setUpMockServer() throws IOException {
        testServer = new MockWebServer();
        testServer.enqueue(new MockResponse().setBody(getFileContent(getFixturePath("testPage.html"))));
        testServer.enqueue(new MockResponse().setStatus("200"));
        testServer.start();
        testUrl = testServer.url("/test").toString();
    }

    @AfterAll
    public static void shutDown() throws IOException {
        testServer.shutdown();
    }

    @BeforeEach
    public final void setUp() throws SQLException {
        app = getApp();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://ru.hexlet.io/my";
            try (var response = client.post("/urls", requestBody)) {
                assertEquals(response.code(), 200);
                assertThat(response.body().string()).contains("https://ru.hexlet.io");
                assertThat(UrlRepository.findByName("https://ru.hexlet.io")).isPresent();
            }
        });
    }

    @Test
    public void testCreateInvalidUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=fffffff";
            try (var response = client.post("/urls", requestBody);) {
                assertThat(response.code()).isEqualTo(200);
                assertThat(response.body().string()).doesNotContain("fffffff");
                assertThat(UrlRepository.findByName("fffffff")).isNotPresent();
            }
        });
    }

    @Test
    public void testUrlPage() throws SQLException {
        var url = new Url("https://ru.hexlet.io/my", Timestamp.valueOf(LocalDateTime.now()));
        UrlRepository.save(url);
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUrlCheck() throws SQLException {
        var url = new Url(testUrl, Timestamp.valueOf(LocalDateTime.now()));
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            var postUrl = "/urls/" + url.getId() + "/checks";
            try (var response = client.post(postUrl)) {
                assertThat(response.code()).isEqualTo(200);

                var checks = UrlCheckRepository.getUrlChecks(url.getId());
                assertFalse(checks.isEmpty());
                assertEquals(checks.getFirst().getUrlId(), url.getId());
            }
        });
    }

    public static String getFileContent(String path) throws IOException {
        return Files.asCharSource(new File(path), Charsets.UTF_8).read();
    }

    public static String getFixturePath(String fileName) {
        return "./src/test/resources/fixtures/" + fileName;
    }
}
