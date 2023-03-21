package api.chaehwi.ruheeblog.search;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Meta {
    private int total_count;
    private int pageable_count;
    private boolean is_end;
}
