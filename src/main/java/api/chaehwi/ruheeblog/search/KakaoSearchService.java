package api.chaehwi.ruheeblog.search;

import api.chaehwi.ruheeblog.utils.SearchUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
public class KakaoSearchService implements SearchService {

    @Override
    public Page<Document> search(QueryDto queryDto) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + ApiType.KAKAO_BLOG.getKey1());
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<SearchDto> response = null;
        try {
            response = restTemplate.exchange(ApiType.KAKAO_BLOG.getUrl() + "?" + this.getQueryString(queryDto), HttpMethod.GET, request, SearchDto.class);
        } catch (RestClientException e) {
            throw new RestClientException("카카오 API 에러", e);
        }
        List<Document> documents = response.getBody().getDocuments();

        return SearchUtils.changePagination(documents, queryDto.getPage(), queryDto.getSize());

    }

    @Override
    public String getQueryString(QueryDto queryDto) {
        String queryString = "";
        if (StringUtils.isNotBlank(queryDto.getQuery())) {
            queryString += "query=" + queryDto.getQuery();
        }
        if (StringUtils.isNotBlank(queryDto.getSort())) {
            queryString += "&sort=" + queryDto.getSort();
        }
        if (StringUtils.isNotBlank(String.valueOf(queryDto.getSize()))) {
            queryString += "&size=" + queryDto.getSize();
        }
        if (StringUtils.isNotBlank(String.valueOf(queryDto.getPage()))) {
            queryString += "&page=" + queryDto.getPage();
        }
        return queryString;
    }

}
