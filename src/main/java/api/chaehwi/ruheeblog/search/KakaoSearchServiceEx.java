package api.chaehwi.ruheeblog.search;

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
public class KakaoSearchServiceEx implements SearchService {

    @Override
    public Page<Document> search(QueryDto queryDto) {
        throw new RestClientException("(강제) 카카오 API 서비스 불가 예외처리");
    }

    @Override
    public String getQueryString(QueryDto queryDto) {
        return "";
    }

}
