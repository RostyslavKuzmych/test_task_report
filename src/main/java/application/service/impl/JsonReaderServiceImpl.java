package application.service.impl;

import application.model.Report;
import application.service.JsonReaderService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class JsonReaderServiceImpl implements JsonReaderService {
    private static final String TEST_REPORT_JSON = "test_report.json";
    private static final String READ_EXCEPTION = "Can't read test_report.json file ";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Cacheable(value = "allResponses", unless = "#result == null")
    public Report readFile() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.registerModule(new JavaTimeModule());
        try {
            String content = new String(Files.readAllBytes(Paths.get(TEST_REPORT_JSON)));
            return objectMapper.readValue(content, Report.class);
        } catch (IOException e) {
            throw new RuntimeException(READ_EXCEPTION + e);
        }
    }
}
