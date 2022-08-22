package com.detailretail.kurlyflow.worker.infra;

import com.detailretail.kurlyflow.worker.command.application.AdminCallRequest;
import com.detailretail.kurlyflow.worker.command.application.AdminCallService;
import com.detailretail.kurlyflow.worker.command.domain.Worker;
import com.detailretail.kurlyflow.worker.command.domain.WorkerRepository;
import com.detailretail.kurlyflow.worker.exception.EntityNotFoundException;
import com.detailretail.kurlyflow.worker.util.WorkerConverter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminCallServiceImpl implements AdminCallService {

  private final WorkerRepository workerRepository;
  @Value("${fcm.key.path}")
  private String FCM_PRIVATE_KEY_PATH;
  @Value("${fcm.key.scope}")
  private String FIRE_BASE_SCOPE;

  @PostConstruct
  public void init() {
    try {
      FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(
          GoogleCredentials.fromStream(new ClassPathResource(FCM_PRIVATE_KEY_PATH).getInputStream())
              .createScoped(List.of(FIRE_BASE_SCOPE))).build();
      if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(options);
      }
    } catch (IOException e) {
      throw new FirebaseServerErrorException();
    }
  }

  @Override
  public void callAdmin(Long workerId) {
    Worker worker = workerRepository.findById(workerId).orElseThrow(EntityNotFoundException::new);
    AdminCallRequest adminCallRequest = WorkerConverter.toCall(worker);
//    BatchResponse response;
//    try {
//      response = FirebaseMessaging.getInstance().sendAsync(
//          Message.builder().putData("createdAt", LocalDateTime.now().toString())
//              .setNotification().setToken().build());
//    } catch (FirebaseMessagingException e) {
//      throw new FirebaseServerErrorException();
//    }
  }
}
