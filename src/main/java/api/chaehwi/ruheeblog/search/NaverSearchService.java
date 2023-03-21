package api.chaehwi.ruheeblog.search;

import api.chaehwi.ruheeblog.utils.SearchUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class NaverSearchService implements SearchService {

    @Override
    public Page<Document> search(QueryDto queryDto) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", ApiType.NAVER_BLOG.getKey1());
        headers.add("X-Naver-Client-Secret", ApiType.NAVER_BLOG.getKey2());
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<SearchDto> response
                = restTemplate.exchange(ApiType.NAVER_BLOG.getUrl() + "?" + this.getQueryString(queryDto), HttpMethod.GET, request, SearchDto.class);

        List<Document> documents = response.getBody().getDocuments();

        return SearchUtils.changePagination(documents, queryDto.getPage(), queryDto.getSize());

    }

    @Override
    public String getQueryString(QueryDto queryDto) {
        String queryString = "";
        if (StringUtils.isNotBlank(queryDto.getQuery())) {
            try {
                queryString += "query=" + URLEncoder.encode(queryDto.getQuery(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        if (StringUtils.isNotBlank(queryDto.getSort())) {
            String sort = "";
            switch(queryDto.getSort()) {
                case "recency": sort = "date"; break;
                default: sort = "sim";
            }
            queryString += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(String.valueOf(queryDto.getSize()))) {
            queryString += "&display=" + queryDto.getSize();
        }
        if (StringUtils.isNotBlank(String.valueOf(queryDto.getPage()))) {
            queryString += "&start=" + queryDto.getPage();
        }

        return queryString;
    }

}
