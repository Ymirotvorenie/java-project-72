@import hexlet.code.dto.UrlsPage
@param UrlsPage page = null

@template.layout.page(
    page = page,
    content = @`
    <section>

        <div class="container-lg mt-5">
            <h1>Сайты</h1>

            <table class="table table-bordered table-hover mt-3">
                <thead>
                <tr>
                    <th class="col-1">ID</th>
                    <th>Имя</th>
                    <th class="col-2">Последняя проверка</th>
                    <th class="col-1">Код ответа</th>
                </tr>
                </thead>
                <tbody>
                @if (page != null)
                    @for (var url : page.getUrls())
                        <tr>
                            <td>
                                ${url.getId()}
                            </td>
                            <td>
                                <a href="/urls/${url.getId()}">${url.getName()}</a>
                            </td>
                            @if (page.getLastChecks().get(url.getId()) != null)
                            <td>
                                ${page.getLastChecks().get(url.getId()).getCreatedAt().toString()}
                            </td>
                            <td>
                                ${page.getLastChecks().get(url.getId()).getStatusCode()}
                            </td>
                            @else
                            <td></td>
                            <td></td>
                            @endif
                        </tr>
                    @endfor
                @endif
                </tbody>
            </table>
        </div>

    </section>
    `
)
