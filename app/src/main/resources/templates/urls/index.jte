@import hexlet.code.dto.UrlsPage
@import hexlet.code.repositories.UrlCheckRepository
@import java.time.format.DateTimeFormatter
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
                            <td>
                                @if(UrlCheckRepository.getUrlLastCheck(url.getId()).isPresent())
                                    ${UrlCheckRepository.getUrlLastCheck(url.getId()).get()
                                        .getCreatedAt().toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))}
                                @endif
                            </td>
                            <td>
                                @if(UrlCheckRepository.getUrlLastCheck(url.getId()).isPresent())
                                    ${UrlCheckRepository.getUrlLastCheck(url.getId()).get().getStatusCode()}
                                @endif
                            </td>
                        </tr>
                    @endfor
                @endif
                </tbody>
            </table>
        </div>

    </section>
    `
)
