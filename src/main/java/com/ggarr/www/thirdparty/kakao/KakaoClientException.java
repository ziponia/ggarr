package com.ggarr.www.thirdparty.kakao;

public class KakaoClientException extends RuntimeException {
    public KakaoClientException() {
        super();
    }

    public KakaoClientException(String message) {
        super(message);
    }

    public KakaoClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public KakaoClientException(Throwable cause) {
        super(cause);
    }

    protected KakaoClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
