package org.cainiao.authorizationcenter.config.httpclient;

public interface AccessTokenRepository {

    String getAccessToken(String registrationId);
}
