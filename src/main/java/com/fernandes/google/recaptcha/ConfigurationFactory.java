package com.fernandes.google.recaptcha;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

/**
 * Use this class to create the configuration.
 */
public class ConfigurationFactory {

    public static final String DEFAULT_PROPERTIES = "recaptcha.properties";

    public static Configuration createDefault() {
        return createFromFile(DEFAULT_PROPERTIES);
    }

    public static Configuration createFromFile(String classpathLocation) {
        try(InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(classpathLocation)) {
            Properties properties = new Properties();
            properties.load(in);
            final Configuration.Builder builder = new Configuration.Builder(properties.getProperty("site.key"),
                    properties.getProperty("secret.key"))
                    .setUserAgent(properties.getProperty("user.agent"));
            final String verifySite = properties.getProperty("recaptcha.verify.site");
            if(verifySite != null) {
                builder.setVerifySite(URI.create(verifySite));
            }
            return builder
                    .build();
        } catch(IOException e) {
            throw new RuntimeException(
                    String.format("Could not load configuration. Please make sure that %s exists.", classpathLocation), e);
        }
    }
}
