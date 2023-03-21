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
        Page<Document> result;
        try {
            result = this.searchService.search(queryDto);
            this.topsService.save(queryDto.getQuery());
        } catch (RestClientException e) {
            e.printStackTrace();
            this.searchService = new NaverSearchService();
            result = this.searchService.search(queryDto);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/searchEx")
    public ResponseEntity searchEx(QueryDto queryDto) {
        Page<Document> result;
        try {
            log.info("searchEx 테스트");
            log.info("{}",this.searchService);
            this.searchService = new KakaoSearchServiceEx();
            log.info("{}",this.searchService);
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
