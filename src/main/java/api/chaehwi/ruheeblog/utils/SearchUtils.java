package api.chaehwi.ruheeblog.utils;

import api.chaehwi.ruheeblog.search.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

public class SearchUtils {
    public static Page<Document> changePagination(List<Document> blogList, int page, int size) {
        if (blogList == null) {
            blogList = new ArrayList<Document>();
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Document> documentPage = new PageImpl<>(blogList, pageRequest, blogList.size());

        return documentPage;
    }
}
