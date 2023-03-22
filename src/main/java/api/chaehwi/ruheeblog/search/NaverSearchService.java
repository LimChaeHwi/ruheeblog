package api.chaehwi.ruheeblog.search;

import api.chaehwi.ruheeblog.utils.SearchUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Page;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class NaverSearchService implements SearchService {

    @Override
    public Page<Document> search(QueryDto queryDto) {
        List<Document> documents = new ArrayList<>();
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", ApiType.NAVER_BLOG.getKey1());
        requestHeaders.put("X-Naver-Client-Secret", ApiType.NAVER_BLOG.getKey2());

        String strResponse = get(ApiType.NAVER_BLOG.getUrl() + "?" + this.getQueryString(queryDto), requestHeaders);
        System.out.println("strResponse=>"+strResponse);
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(strResponse);
            JSONObject jsonObj = (JSONObject) obj;
            JSONArray items = (JSONArray) jsonObj.get("items");
            System.out.println("여기");
            for (int i = 0 ; i < items.size(); i++) {
                Document document = new Document();
                JSONObject jo = (JSONObject) items.get(i);
                document.setTitle((String) jo.get("title"));
                document.setContents((String) jo.get("description"));
                document.setBlogname((String) jo.get("bloggername"));
                document.setUrl((String) jo.get("link"));
                documents.add(document);
            }
        } catch (ParseException e) {
            throw new RuntimeException("Json Parsing 에러");
        }

        return SearchUtils.changePagination(documents, queryDto.getPage(), queryDto.getSize());

    }

    @Override
    public String getQueryString(QueryDto queryDto) {
        String queryString = "";
        if (StringUtils.isNotBlank(queryDto.getQuery())) {
            queryString += "query=" + URLEncoder.encode(queryDto.getQuery(), StandardCharsets.UTF_8);
        }
        if (StringUtils.isNotBlank(queryDto.getSort())) {
            String sort;
            switch(queryDto.getSort()) {
                case "recency": sort = "date"; break;
                default: sort = "sim";
            }
            queryString += "&sort=" + sort;
        }
        if (queryDto.getSize() != null) {
            queryString += "&display=" + queryDto.getSize();
        }
        if (queryDto.getPage() != null) {
            queryString += "&start=" + queryDto.getPage();
        }

        return queryString;
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }

}
