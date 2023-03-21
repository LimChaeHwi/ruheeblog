package api.chaehwi.ruheeblog.search;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@AutoConfigureMockMvc
@NoArgsConstructor
@SpringBootTest
@Slf4j
public class RestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    @DisplayName("검색어 정렬 페이징 사이즈 값 존재 성공 테스트 (query=스프링부트,sort=recency,page=1,size=10) ")
    public void searchTest() {
        String url = "/search";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("query", "스프링부트");
        params.add("sort", "recency");
        params.add("page", "1");
        params.add("size", "10");

        try {
            mockMvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .params(params))
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    response.setCharacterEncoding("UTF-8");
                    log.info("[Response] searchTest => {}", response.getContentAsString());
                });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(2)
    @DisplayName("인기검색어 최대 10건 조회")
    public void findTop10() {
        String url = "/tops/ten";
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            )
                    .andExpect(result -> {
                        MockHttpServletResponse response = result.getResponse();
                        response.setCharacterEncoding("UTF-8");
                        log.info("[Response] findTop10 => {}", response.getContentAsString());
                    });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(3)
    @DisplayName("카카오 API 예외 테스트 (query=스프링부트,sort=recency,page=1,size=10) ")
    public void searchApiExceptionTest() {
        String url = "/searchEx";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("query", "리뷰");
        params.add("sort", "recency");
        params.add("page", "1");
        params.add("size", "10");

        try {
            mockMvc.perform(MockMvcRequestBuilders.get(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .params(params))
                    .andExpect(result -> {
                        MockHttpServletResponse response = result.getResponse();
                        response.setCharacterEncoding("UTF-8");
                        log.info("[Response] searchApiExceptionTest => {}", response.getContentAsString());
                    });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
