package shop.gaship.scheduler.dataprotection.dto;

import lombok.Getter;

/**
 * NHN Secure Key Manager 의 Secure Key 응답 메시지 타입.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
public class SecureKeyResponse {
    private Header header;
    private Body body;


    /**
     * The type Header.
     */
    @Getter
    public static class Header {
        private Integer resultCode;
        private String resultMessage;
        private Boolean isSuccessful;
    }

    /**
     * The type Body.
     */
    @Getter
    public static class Body {
        private String secret;
    }
}