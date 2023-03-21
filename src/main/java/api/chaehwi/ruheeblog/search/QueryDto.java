package api.chaehwi.ruheeblog.search;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class QueryDto {
    private String query;
    private String sort;
    private Integer page;
    private Integer size;
}
