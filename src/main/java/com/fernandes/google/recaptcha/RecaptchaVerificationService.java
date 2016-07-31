package com.fernandes.google.recaptcha;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Google Recaptcha based implementation to be used on the server side.
 * See https://www.google.com/recaptcha/intro/index.html
 */
public class RecaptchaVerificationService implements VerificationService {

    private ObjectMapper mapper = new ObjectMapper();

    private Configuration configuration;

    public RecaptchaVerificationService() {
        this(ConfigurationFactory.createDefault());
    }

    public RecaptchaVerificationService(Configuration configuration) {
        this.configuration = configuration;
    }

    public boolean isValid(String captchaResponse) {
        if (captchaResponse == null || captchaResponse.trim().isEmpty()) {
            return false;
        }
        try {
            URL obj = configuration.getVerifySite().toURL();
            HttpsURLConnection con = prepareConnection(obj);

            String postParams = String.format("secret=%s&response=%s",
                    configuration.getSecretKey(), captchaResponse);

            con.setDoOutput(true);
            try(DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(postParams);
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            if(responseCode >= 200 && responseCode < 300) {

                try (BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()))) {
                    return parseResult(in);
                }
            }
            else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean parseResult(BufferedReader in) throws IOException {
        JsonNode jsonNode = mapper.readTree(in);
        return jsonNode.get("success").asBoolean();
    }

    private HttpsURLConnection prepareConnection(URL obj) throws IOException {
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        if(configuration.getUserAgent() != null) {
            con.setRequestProperty("User-Agent", configuration.getUserAgent());
        }
        con.setRequestProperty("Accept-Language", configuration.getAcceptLanguage());
        return con;
    }
}
