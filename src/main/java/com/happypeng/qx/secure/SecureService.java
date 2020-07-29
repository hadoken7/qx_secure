package com.happypeng.qx.secure;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    public String getContent(String url) {
        return get(url);
    }

    public String get(String url) {
        String result = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(url);
            try (CloseableHttpResponse response = httpclient.execute(httpget)) {
                org.apache.http.HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity);
                }
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
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
