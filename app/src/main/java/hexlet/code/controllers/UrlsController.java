package hexlet.code.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import hexlet.code.dto.BasePage;
import hexlet.code.dto.UrlsPage;
import hexlet.code.dto.UrlPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repositories.UrlCheckRepository;
import hexlet.code.repositories.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.jsoup.Jsoup;

import java.net.MalformedURLException;
import java.net.URI;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {

    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        var page = new UrlsPage(urls);
        pageSetFlash(ctx, page);
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.findById(id).orElseThrow(() -> new NotFoundResponse("Url not found"));
        var checks = UrlCheckRepository.getUrlChecks(id);
        var page = new UrlPage(url, checks);
        pageSetFlash(ctx, page);
        ctx.render("urls/show.jte", model("page", page));
    }

    public static void create(Context ctx) throws SQLException {
        try {
            var input = ctx.formParam("url");
            var inputUrl = URI.create(input).toURL();
            var checkedUrl = inputUrl.getProtocol() + "://" + inputUrl.getAuthority();

            var url = new Url(checkedUrl, new Timestamp(new Date().getTime()));
            if (UrlRepository.findByName(checkedUrl).isEmpty()) {
                UrlRepository.save(url);
                setFlash(ctx, "Страница успешно добавлена", "success");
            } else {
                setFlash(ctx, "Страница уже существует", "info");
            }
            ctx.redirect(NamedRoutes.urlsPath());

        } catch (IllegalArgumentException | MalformedURLException e) {
            setFlash(ctx, "Некорректный URL", "danger");
            ctx.redirect(NamedRoutes.rootPath());
        }
    }

    public static void check(Context ctx) throws SQLException, UnirestException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.findById(id).orElseThrow(() -> new NotFoundResponse("Url not found"));

        HttpResponse<String> response = Unirest.get(url.getName()).asString();

        var body = Jsoup.parse(response.getBody());

        var status = response.getStatus();

        var title = body.title();

        var h1Tag = body.selectFirst("h1");
        var h1 = h1Tag  != null ? h1Tag.text() : "";

        var descriptionTag = body.selectFirst("meta[name=\"description\"]");
        var description = descriptionTag != null ? descriptionTag.attr("content") : "";

        var check = new UrlCheck(status, title, h1, description, id, new Timestamp(new Date().getTime()));

        UrlCheckRepository.save(check);

        ctx.sessionAttribute("flash", "Страница успешно проверена");
        ctx.sessionAttribute("flashVariant", "success");

        ctx.redirect(NamedRoutes.urlPath(id));
    }

    private static void setFlash(Context ctx, String flash, String flashVariant) {
        ctx.sessionAttribute("flash", flash);
        ctx.sessionAttribute("flashVariant", flashVariant);
    }

    private static void pageSetFlash(Context ctx, BasePage page) {
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setVariant(ctx.consumeSessionAttribute("flashVariant"));
    }
}
