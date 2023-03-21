package api.chaehwi.ruheeblog.tops;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter @Getter
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Tops {
    @Id
    private String searchWord;
    private int searchCount;
}
