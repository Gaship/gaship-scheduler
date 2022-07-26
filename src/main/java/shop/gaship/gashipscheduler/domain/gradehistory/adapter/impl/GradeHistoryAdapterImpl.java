package shop.gaship.gashipscheduler.domain.gradehistory.adapter.impl;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipscheduler.config.ServerConfig;
import shop.gaship.gashipscheduler.domain.gradehistory.adapter.GradeHistoryAdapter;
import shop.gaship.gashipscheduler.domain.gradehistory.dto.request.GradeHistoryAddRequestDto;
import shop.gaship.gashipscheduler.exception.RequestFailureException;

/**
 * GradeHistoryAdapter interface 구현체.
 *
 * @author : 김세미
 * @since 1.0
 * @see shop.gaship.gashipscheduler.domain.gradehistory.adapter.GradeHistoryAdapter
 */
@Component
@RequiredArgsConstructor
public class GradeHistoryAdapterImpl implements GradeHistoryAdapter {
    private static final Duration timeOut = Duration.of(3, ChronoUnit.SECONDS);
    private static final String ERROR_MESSAGE = "응답결과가 존재하지 않습니다.";
    private static final String GRADE_HISTORY_URL = "/api/grade-histories";
    private final ServerConfig serverConfig;

    /**
     * {@inheritDoc}
     *
     * @throws RequestFailureException 서버로 보낸 요청이 실패하였습니다.
     */
    @Override
    public void addGradeHistory(GradeHistoryAddRequestDto requestDto) {
        WebClient.builder()
                .baseUrl(serverConfig.getShoppingMallUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build()
                .post()
                .uri(serverConfig.getShoppingMallUrl() + GRADE_HISTORY_URL)
                .bodyValue(requestDto)
                .retrieve()
                .toEntity(Void.class)
                .timeout(timeOut)
                .blockOptional()
                .orElseThrow(() -> new RequestFailureException(ERROR_MESSAGE)).getBody();
    }
}
