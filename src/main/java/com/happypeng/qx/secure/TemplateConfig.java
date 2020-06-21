package com.happypeng.qx.secure;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * @author Hadoken
 * @date 2019/7/11
 */
@Configuration
public class TemplateConfig {
    @Bean
    public RestTemplate restTemplate() {

        RequestConfig config = RequestConfig.custom().setConnectTimeout(5 * 1000)
                .setSocketTimeout(20 * 1000).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();


        RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_PLAIN));
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));

        template.getMessageConverters().add(new FormHttpMessageConverter());
        template.getMessageConverters().add(converter);

        return template;
    }


}
