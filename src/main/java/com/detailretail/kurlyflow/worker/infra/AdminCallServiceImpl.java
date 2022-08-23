package com.detailretail.kurlyflow.worker.infra;

import com.detailretail.kurlyflow.worker.command.application.AdminCallService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminCallServiceImpl implements AdminCallService {

  private final String API_URL = "https://fcm.googleapis.com/v1/projects/kurly-flow-call/messages:send";
  private final String FIREBASE_CONFIG_PATH = "kurly-flow-call-fcm.json";
  private final ObjectMapper objectMapper;

  public void sendMessageTo(String targetToken, String title) throws IOException {
    String message = makeMessage(targetToken, title, "작업자 호출");

    OkHttpClient client = new OkHttpClient();
    RequestBody requestBody = RequestBody.create(message,
        MediaType.get("application/json; charset=utf-8"));
    Request request = new Request.Builder()
        .url(API_URL)
        .post(requestBody)
        .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
        .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
        .build();
    Response response = client.newCall(request).execute();
  }

  private String makeMessage(String targetToken, String title, String body) throws JsonParseException, JsonProcessingException {
    FcmMessage fcmMessage = FcmMessage.builder()
        .message(FcmMessage.Message.builder()
            .token(targetToken)
            .notification(FcmMessage.Notification.builder()
                .title(title)
                .body(body)
                .image(null)
                .build()
            ).build()).validateOnly(false).build();

    return objectMapper.writeValueAsString(fcmMessage);
  }

  private String getAccessToken() throws IOException {
    GoogleCredentials googleCredentials = GoogleCredentials
        .fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream())
        .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

    googleCredentials.refreshIfExpired();
    return googleCredentials.getAccessToken().getTokenValue();
  }
}