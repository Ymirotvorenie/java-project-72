package hexlet.code.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import hexlet.code.dto.UrlsPage;
import hexlet.code.dto.UrlPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repositories.UrlCheckRepository;
import hexlet.code.repositories.UrlRepository;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.net.MalformedURLException;
import java.net.URI;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {

    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();

        var page = new UrlsPage(urls);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setVariant(ctx.consumeSessionAttribute("flashVariant"));
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.findById(id).orElseThrow(() -> new NotFoundResponse("Url not found"));
        var checks = UrlCheckRepository.getUrlChecks(id);
        var page = new UrlPage(url, checks);
        ctx.render("urls/show.jte", model("page", page));
    }

    public static void create(Context ctx) throws SQLException {
        try {
            var input = ctx.formParam("url");
            var uri = URI.create(input);
            var checkedUrl = uri.toURL().getProtocol() + "://" + uri.toURL().getAuthority();

            var url = new Url(checkedUrl, Timestamp.valueOf(LocalDateTime.now()));
            if (UrlRepository.findByName(checkedUrl).isEmpty()) {
                UrlRepository.save(url);
                ctx.sessionAttribute("flash", "Страница успешно добавлена");
                ctx.sessionAttribute("flashVariant", "success");
            } else {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.sessionAttribute("flashVariant", "info");
            }
            ctx.redirect("/urls");

        } catch (IllegalArgumentException | MalformedURLException e) {
            //var invalidUrl = ctx.formParam("url");
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flashVariant", "danger");
            ctx.redirect("/");
        }
    }

    public static void check(Context ctx) throws SQLException, UnirestException {

        HttpResponse<String> response = Unirest.get("https://ru.hexlet.io").asString();
        var body = response.getBody();
        var status = response.getStatus();
//
//        System.out.println("HEADER = " + body);
//        ctx.redirect("/urls/1");

        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.findById(id).orElseThrow(() -> new NotFoundResponse("Url not found"));

        var check = new UrlCheck();
        check.setStatusCode(status);
        check.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        check.setUrlId(id);
        UrlCheckRepository.save(check);
        ctx.redirect("/urls/" + id);

    }
}
