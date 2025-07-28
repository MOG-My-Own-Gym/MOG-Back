package com.project.mog.config.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();

        // 모든 컨버터를 가져옴
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();

        // JSON 메시지 컨버터를 찾음
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter jsonConverter) {
                // text/plain도 JSON처럼 파싱하도록 mediaType 추가
                List<MediaType> supportedMediaTypes = new ArrayList<MediaType>(jsonConverter.getSupportedMediaTypes());
                supportedMediaTypes.add(MediaType.TEXT_PLAIN); // text/plain 추가
                jsonConverter.setSupportedMediaTypes(supportedMediaTypes);
            }
        }

        return restTemplate;
    }
}
