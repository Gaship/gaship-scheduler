package shop.gaship.gashipscheduler.domain.membergrade.adapter.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import shop.gaship.gashipscheduler.domain.membergrade.adapter.MemberGradeAdapter;
import shop.gaship.gashipscheduler.domain.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipscheduler.exception.RequestFailureException;


/**
 * MemberGradeAdapter interface 구현체.
 *
 * @author : 김세미
 * @see shop.gaship.gashipscheduler.domain.membergrade.adapter.MemberGradeAdapter
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class MemberGradeAdapterImpl implements MemberGradeAdapter {
    private static final Duration timeOut = Duration.of(3, ChronoUnit.SECONDS);
    private static final String ERROR_MESSAGE = "응답결과가 존재하지 않습니다.";
    private static final String MEMBER_GRADE_URL = "/api/member-grades";

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * {@inheritDoc}
     *
     * @throws RequestFailureException 서버로 보낸 요청이 실패하였습니다.
     */
    @Override
    public List<MemberGradeResponseDto> findMemberGrades() {
        UriBuilder uriBuilder = UriComponentsBuilder.newInstance();

        return mapper.convertValue(WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .build().get()
                .uri(uriBuilder.scheme("http")
                        .host("localhost")
                        .port(7072)
                        .path(MEMBER_GRADE_URL)
                        .build())
                .retrieve()
                .toEntity(List.class)
                .timeout(timeOut)
                .blockOptional()
                .orElseThrow(() -> new RequestFailureException(ERROR_MESSAGE)).getBody(),
                mapper.getTypeFactory()
                        .constructCollectionType(List.class, MemberGradeResponseDto.class));
    }
}