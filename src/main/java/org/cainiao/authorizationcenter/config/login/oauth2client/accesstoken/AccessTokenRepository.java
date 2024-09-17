package org.cainiao.authorizationcenter.config.login.oauth2client.accesstoken;

public interface AccessTokenRepository {

    String getAccessToken(String registrationId);
}
