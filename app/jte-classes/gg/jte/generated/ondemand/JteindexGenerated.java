package gg.jte.generated.ondemand;
import hexlet.code.dto.BasePage;
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,35,35,35,36,36,36,36,37,37,37,40,40,79,79,79,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, BasePage page) {
		jteOutput.writeContent("\n<!DOCTYPE html>\n<html>\n\n<head>\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n    <title>Анализатор страниц</title>\n    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\"\n          integrity=\"sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65\" crossorigin=\"anonymous\">\n    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js\"\n            integrity=\"sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4\"\n            crossorigin=\"anonymous\"></script>\n</head>\n\n<body class=\"d-flex flex-column min-vh-100\">\n<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">\n    <div class=\"container-fluid\">\n        <a class=\"navbar-brand\" href=\"/\">Анализатор страниц</a>\n        <button class=\"navbar-toggler\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#navbarNav\"\n                aria-controls=\"navbarNav\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n            <span class=\"navbar-toggler-icon\"></span>\n        </button>\n        <div class=\"collapse navbar-collapse\" id=\"navbarNav\">\n            <div class=\"navbar-nav\">\n                <a class=\"nav-link\" href=\"/\">Главная</a>\n                <a class=\"nav-link\" href=\"/urls\">Сайты</a>\n            </div>\n        </div>\n    </div>\n</nav>\n\n<main class=\"flex-grow-1\">\n    ");
		if (page != null && page.getFlash() != null) {
			jteOutput.writeContent("\n        <div class=\"rounded-0 m-0 alert alert-dismissible fade show alert-");
			jteOutput.setContext("div", "class");
			jteOutput.writeUserContent(page.getVariant());
			jteOutput.setContext("div", null);
			jteOutput.writeContent("\" role=\"alert\">\n            <p class=\"m-0\">");
			jteOutput.setContext("p", null);
			jteOutput.writeUserContent(page.getFlash());
			jteOutput.writeContent("</p>\n            <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\n        </div>\n    ");
		}
		jteOutput.writeContent("\n    <section>\n\n        <div class=\"container-fluid bg-dark p-5\">\n            <div class=\"row\">\n                <div class=\"col-md-10 col-lg-8 mx-auto text-white\">\n                    <h1 class=\"display-3 mb-0\">Анализатор страниц</h1>\n                    <p class=\"lead\">Бесплатно проверяйте сайты на SEO пригодность</p>\n                    <form action=\"/urls\" method=\"post\" class=\"rss-form text-body\">\n                        <div class=\"row\">\n                            <div class=\"col\">\n                                <div class=\"form-floating\">\n                                    <input id=\"url-input\" autofocus type=\"text\" required name=\"url\" aria-label=\"url\"\n                                           class=\"form-control w-100\" placeholder=\"ссылка\" autocomplete=\"off\">\n                                    <label for=\"url-input\">Ссылка</label>\n                                </div>\n                            </div>\n                            <div class=\"col-auto\">\n                                <button type=\"submit\" class=\"h-100 btn btn-lg btn-primary px-sm-5\">Проверить</button>\n                            </div>\n                        </div>\n                    </form>\n                    <p class=\"mt-2 mb-0 text-muted\">Пример: https://www.example.com</p>\n                </div>\n            </div>\n        </div>\n\n    </section>\n</main>\n\n<footer class=\"footer border-top py-3 mt-5 bg-light\">\n    <div class=\"container-xl\">\n        <div class=\"text-center\">\n            created by\n            <a href=\"https://ru.hexlet.io\" target=\"_blank\">Hexlet</a>\n        </div>\n    </div>\n</footer>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		BasePage page = (BasePage)params.getOrDefault("page", null);
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
