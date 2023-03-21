package api.chaehwi.ruheeblog.search;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchService {
    Page<Document> search(QueryDto queryDto);
    String getQueryString(QueryDto queryDto);
}
