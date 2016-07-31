package com.fernandes.google.recaptcha;

import java.net.URI;

/**
 * Contains the configuration parameters of the Recaptcha.
 */
public class Configuration {

    private final String siteKey;

    private final String secretKey;

    private final String userAgent;

    private final URI verifySite;

    private final String acceptLanguage;

    private Configuration(String siteKey, String secretKey,
                          String userAgent, URI verifySite, String acceptLanguage) {
        this.siteKey = siteKey;
        this.secretKey = secretKey;
        this.userAgent = userAgent;
        this.verifySite = verifySite;
        this.acceptLanguage = acceptLanguage;
    }

    public String getSiteKey() {
        return siteKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public URI getVerifySite() {
        return verifySite;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public static class Builder {

        private final String siteKey;

        private final String secretKey;

        private String userAgent;

        private URI verifySite = URI.create("https://www.google.com/recaptcha/api/siteverify");

        private String acceptLanguage = "en-US,en;q=0.5";

        public Builder(String siteKey, String secretKey) {
            this.siteKey = siteKey;
            this.secretKey = secretKey;
        }

        public Builder setUserAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public Builder setVerifySite(URI verifySite) {
            this.verifySite = verifySite;
            return this;
        }

        public Builder setAcceptLanguage(String acceptLanguage) {
            this.acceptLanguage = acceptLanguage;
            return this;
        }

        public Configuration build() {
            return new Configuration(siteKey, secretKey, userAgent, verifySite, acceptLanguage);
        }
    }
}
