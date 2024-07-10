package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.UrlsPage;
public final class JteindexGenerated {
	public static final String JTE_NAME = "urls/index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,3,3,6,6,22,22,23,23,26,26,26,29,29,29,29,29,29,29,32,32,32,37,37,38,38,44,44,44,45,45,45,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlsPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <section>\n\n        <div class=\"container-lg mt-5\">\n            <h1>Сайты</h1>\n\n            <table class=\"table table-bordered table-hover mt-3\">\n                <thead>\n                <tr>\n                    <th class=\"col-1\">ID</th>\n                    <th>Имя</th>\n                    <th class=\"col-2\">Последняя проверка</th>\n                    <th class=\"col-1\">Код ответа</th>\n                </tr>\n                </thead>\n                <tbody>\n                ");
				if (page != null) {
					jteOutput.writeContent("\n                    ");
					for (var url : page.getUrls()) {
						jteOutput.writeContent("\n                        <tr>\n                            <td>\n                                ");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(url.getId());
						jteOutput.writeContent("\n                            </td>\n                            <td>\n                                <a href=\"/urls/");
						jteOutput.setContext("a", "href");
						jteOutput.writeUserContent(url.getId());
						jteOutput.setContext("a", null);
						jteOutput.writeContent("\">");
						jteOutput.setContext("a", null);
						jteOutput.writeUserContent(url.getName());
						jteOutput.writeContent("</a>\n                            </td>\n                            <td>\n                                ");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(url.getCreatedAt().toString());
						jteOutput.writeContent("\n                            </td>\n                            <td>\n                            </td>\n                        </tr>\n                    ");
					}
					jteOutput.writeContent("\n                ");
				}
				jteOutput.writeContent("\n                </tbody>\n            </table>\n        </div>\n\n    </section>\n    ");
			}
		}, page);
		jteOutput.writeContent("\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlsPage page = (UrlsPage)params.getOrDefault("page", null);
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
