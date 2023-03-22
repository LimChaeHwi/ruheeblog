package api.chaehwi.ruheeblog.search;

import api.chaehwi.ruheeblog.tops.TopsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

@RestController
@Slf4j
public class SearchRestController {
    private SearchService searchService;
    private final TopsService topsService;

    public SearchRestController(TopsService topsService) {
        this.topsService = topsService;
        this.searchService = new KakaoSearchService();
    }

    @GetMapping("/search")
    public ResponseEntity search(QueryDto queryDto) {
        Page<?> result;
        try {
            result = this.searchService.search(queryDto);
            // 검색어 카운트
            this.topsService.save(queryDto.getQuery());
        } catch (RestClientException e) {
            e.printStackTrace();
            // 네이버 블로그 API
            this.searchService = new NaverSearchService();
            result = this.searchService.search(queryDto);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * 강제 예외 처리 (카카오 블로그 API 사용불가 상태 가정)
     * @param queryDto
     * @return
     */
    @GetMapping("/searchEx")
    public ResponseEntity searchEx(QueryDto queryDto) {
        Page<?> result;
        try {
            this.searchService = new KakaoSearchServiceEx();
            result = this.searchService.search(queryDto);
            this.topsService.save(queryDto.getQuery());
        } catch (RestClientException e) {
            e.printStackTrace();
            this.searchService = new NaverSearchService();
            result = this.searchService.search(queryDto);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
