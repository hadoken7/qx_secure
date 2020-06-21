package com.happypeng.qx.secure;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * @author Hadoken
 * @date 2020/6/18
 */

@Slf4j
@Service
public class SecureService implements Encrypt {

    private QxConfig qxConfig;
    @Value("${work.config}")
    private String workConfig;

    @Autowired
    private RestTemplate restTemplate;


    public Result encryptedUrlContent(int index) {
        log.info("Get Url Index:{}", index);
        if (updateConfig() && qxConfig.getUrls().size() > index) {
            String url = qxConfig.getUrls().get(index);
            String content = getContent(url);
            return paddingResult(encrypt(content, qxConfig.getKey()));
        } else {
            return configError();
        }
    }

    public Result encryptedPlainContent(int index) {
        log.info("Get Plain Index:{}", index);
        if (updateConfig() && qxConfig.getPlains().size() > index) {
            String filePath = qxConfig.getPlains().get(index);
            String content = getContentByFile(filePath);
            return paddingResult(encrypt(content, qxConfig.getKey()));
        } else {
            return configError();
        }
    }

    private Result configError() {
        return new Result(-1, "无此配置或配置错误");
    }

    private Result paddingResult(String content) {
        if (StringUtils.isEmpty(content)) {
            return new Result(-2, "获取配置失败");
        } else {
            return new Result(content);
        }
    }

    private String getContent(String url) {
        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(null, null),
                        String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }

    private String getContentByFile(String filePath) {
        try {
            return Files.lines(Paths.get(filePath)).collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean updateConfig() {
        File file = new File(workConfig);
        if (file.exists()) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                qxConfig = objectMapper.readValue(new FileInputStream(workConfig), QxConfig.class);
                log.info("qxConfig:{}", qxConfig);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

}
