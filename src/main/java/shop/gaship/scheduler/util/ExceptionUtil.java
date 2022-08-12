package shop.gaship.scheduler.util;

import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;
import shop.gaship.gashipscheduler.exception.RequestFailureException;
import shop.gaship.gashipscheduler.message.ErrorResponse;

/**
 * WebClient error 전역 처리를 위한 util class.
 *
 * @author : 김민수, 김세미
 * @since 1.0
 */
public class ExceptionUtil {
    private ExceptionUtil() {}

    public static Mono<Throwable> createErrorMono(ClientResponse response) {
        return response.bodyToMono(ErrorResponse.class).flatMap(
                body -> Mono.error(new RequestFailureException(body.getMessage())));
    }
}
