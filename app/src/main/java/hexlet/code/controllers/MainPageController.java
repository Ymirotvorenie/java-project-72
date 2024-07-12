package hexlet.code.controllers;

import hexlet.code.dto.BasePage;
import io.javalin.http.Context;

import static io.javalin.rendering.template.TemplateUtil.model;

public class MainPageController {
    public static void index(Context ctx) {
        var page = new BasePage();
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setVariant(ctx.consumeSessionAttribute("flashVariant"));
        ctx.render("index.jte", model("page", page));
    }
}
