package api.chaehwi.ruheeblog.search;

enum ApiType {
    KAKAO_BLOG("https://dapi.kakao.com/v2/search/blog", "d94570c4026ce8515792257e81cbf52a", ""),
    NAVER_BLOG("https://openapi.naver.com/v1/search/blog", "raG9ybevh5zmjBmEejpL", "fBr09z4Woz");
    final private String url;
    final private String key1;
    final private String key2;

    public String getUrl() {
        return url;
    }
    public String getKey1() {
        return key1;
    }
    public String getKey2() {
        return key2;
    }
    private ApiType(String url, String key1, String key2) {
        this.url = url;
        this.key1 = key1;
        this.key2 = key2;
    }
}
