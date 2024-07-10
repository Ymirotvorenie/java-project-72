package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repositories.UrlRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static hexlet.code.App.getApp;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AppTest {
    private Javalin app;

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
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://ru.hexlet.io");
            assertThat(UrlRepository.findByName("https://ru.hexlet.io")).isPresent();
        });
    }

    @Test
    public void testCreateInvalidUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=fffffff";
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).doesNotContain("fffffff");
            assertThat(UrlRepository.findByName("fffffff")).isNotPresent();
            //assertThat(response.body().string()).contains("Некорректный URL");
        });
    }

//    @Test
//    public void testAlreadyExistUrl() throws SQLException {
//        var url = new Url("https://ru.hexlet.io/my", Timestamp.valueOf(LocalDateTime.now()));
//        UrlRepository.save(url);
//
//        JavalinTest.test(app, (server, client) -> {
//            var requestBody = "url=https://ru.hexlet.io/my";
//            var response = client.post("/urls", requestBody);
//            assertThat(response.code()).isEqualTo(302);
//
//            //assertThat(response.body().string()).contains("Страница уже существует");
//            //assertThat(UrlRepository.findByName("https://ru.hexlet.io")).isNotPresent();
//
//
//        });
//    }

    @Test
    public void testUrlPage() throws SQLException {
        var url = new Url("https://ru.hexlet.io/my", Timestamp.valueOf(LocalDateTime.now()));
        UrlRepository.save(url);
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
        });
    }
}
