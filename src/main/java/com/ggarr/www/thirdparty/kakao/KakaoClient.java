package com.ggarr.www.thirdparty.kakao;

import com.ggarr.www.thirdparty.kakao.data.DetectThumbnailResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class KakaoClient {

    private static final String DOMAIN = "https://kapi.kakao.com";

    @Value("${kakao.restKey}")
    private String REST_KEY;

    private RestTemplate template;
    private HttpHeaders headers;

    KakaoClient() {
        template = new RestTemplate();
        headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + REST_KEY);
    }

    public String detectThumbnail(String image_url, int width, int height) {
        String url = DOMAIN + "/v1/vision/thumbnail/crop";
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("image_url", image_url);
        form.add("width", width);
        form.add("height", height);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(form, headers);
        ParameterizedTypeReference<DetectThumbnailResponse> reference = new ParameterizedTypeReference<DetectThumbnailResponse>() {};
        ResponseEntity<DetectThumbnailResponse> result = template.exchange(url, HttpMethod.POST, httpEntity,  reference);

        if (!result.getStatusCode().is2xxSuccessful()) {
            throw new KakaoClientException("이미지 썸네일 생성 실패 response code [" + result.getStatusCodeValue() + "]");
        }

        if (result.getBody() == null || result.getBody().getThumbnail_image_url() == null) {
            throw new KakaoClientException("이미 썸네일 생성 실패. 데이터가 잘못 반환되었거나, 없습니다.");
        }
        return result.getBody().getThumbnail_image_url();
    }
}
