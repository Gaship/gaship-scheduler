package shop.gaship.gashipscheduler.domain.gradehistory.adapter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import shop.gaship.gashipscheduler.domain.gradehistory.adapter.GradeHistoryAdapter;
import shop.gaship.gashipscheduler.domain.gradehistory.dto.request.GradeHistoryAddRequestDto;
import shop.gaship.gashipscheduler.exception.RequestFailureException;
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

    /**
     * {@inheritDoc}
     *
     * @throws RequestFailureException 서버로 보낸 요청이 실패하였습니다.
     */
    @Override
    public void addGradeHistory(GradeHistoryAddRequestDto requestDto) {
        UriBuilder uriBuilder = UriComponentsBuilder.newInstance();

        WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build()
                .post()
                .uri(uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(7072)
                        .path(GRADE_HISTORY_URL)
                        .build())
                .bodyValue(requestDto)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono);
    }
}
