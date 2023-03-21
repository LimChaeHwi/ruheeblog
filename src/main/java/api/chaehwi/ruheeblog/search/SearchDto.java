package api.chaehwi.ruheeblog.search;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
public class SearchDto {
    private Meta meta;
    private List<Document> documents;
}
