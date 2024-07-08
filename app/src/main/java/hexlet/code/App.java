package hexlet.code;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

public class App {
    private final static int PORT = 7070;

    public static void main(String[] args) {
        var app = getApp();

        app.start(PORT);
    }
    public static Javalin getApp() {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.get("/", ctx -> ctx.result("Hello world"));

        return app;
    }
}
