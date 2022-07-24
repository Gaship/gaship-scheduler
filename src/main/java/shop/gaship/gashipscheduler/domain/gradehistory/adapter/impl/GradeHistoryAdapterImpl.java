package shop.gaship.gashipscheduler.domain.gradehistory.adapter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipscheduler.config.ServerConfig;
import shop.gaship.gashipscheduler.domain.gradehistory.adapter.GradeHistoryAdapter;
import shop.gaship.gashipscheduler.domain.gradehistory.dto.request.GradeHistoryAddRequestDto;
import shop.gaship.gashipscheduler.util.ExceptionUtil;

/**
 * GradeHistoryAdapter interface 구현체.
 *
 * @author : 김세미
 * @since 1.0
 * @see GradeHistoryAdapter
 */
@Component
@RequiredArgsConstructor
public class GradeHistoryAdapterImpl implements GradeHistoryAdapter {
    private static final String GRADE_HISTORY_URL = "/api/grade-histories";
    private final ServerConfig serverConfig;

    private final WebClient webClient = WebClient.builder()
            .baseUrl(serverConfig.getShoppingMallUrl())
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @Override
    public boolean addGradeHistory(GradeHistoryAddRequestDto requestDto) {
        webClient.post()
                .uri(GRADE_HISTORY_URL)
                .bodyValue(requestDto)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono);

        return true;
    }
}
