package hexlet.code.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
public class Url {
    @Setter
    private Long id;
    private String name;
    @Setter
    private Timestamp createdAt;

    public Url(String name) {
        this.name = name;
    }
}
