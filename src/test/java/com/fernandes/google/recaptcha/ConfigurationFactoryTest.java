package com.fernandes.google.recaptcha;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ConfigurationFactoryTest {

    @Test
    public void whenCreateFromFile_ShouldGetAllProperties() throws Exception {
        Configuration configuration = ConfigurationFactory.createFromFile("recaptcha.properties");
        assertAll("configuration",
                () -> assertEquals("6LecCiYTAAAAAC32IAdaReCUIfH7FYIc7otgXKLw", configuration.getSiteKey()),
                () -> assertEquals("6LecCiYTAAAAABHouX2tlE0na48ObZDDxsT7VcL8", configuration.getSecretKey()),
                () -> assertEquals("Mozilla/5.0", configuration.getUserAgent()),
                () -> assertEquals("en-US,en;q=0.5", configuration.getAcceptLanguage()),
                () -> assertEquals(URI.create("https://www.google.com/recaptcha/api/siteverify"), configuration.getVerifySite())
        );
    }

    @Test
    public void whenCreateFromIncompleteFile_ShouldGetKeyProperties() throws Exception {
        Configuration configuration = ConfigurationFactory.createFromFile("recaptcha_incomplete.properties");
        assertAll("configuration",
                () -> assertEquals("6LecCiYTAAAAAC32IAdaReCUIfH7FYIc7otgXKLw", configuration.getSiteKey()),
                () -> assertEquals("6LecCiYTAAAAABHouX2tlE0na48ObZDDxsT7VcL8", configuration.getSecretKey()),
                () -> assertNull(configuration.getUserAgent()),
                () -> assertEquals("en-US,en;q=0.5", configuration.getAcceptLanguage()),
                () -> assertEquals(URI.create("https://www.google.com/recaptcha/api/siteverify"), configuration.getVerifySite())
        );
    }
}