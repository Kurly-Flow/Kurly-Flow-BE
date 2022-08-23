package com.detailretail.kurlyflow.tote.command.application;

import com.detailretail.kurlyflow.tote.command.domain.Tote;
import com.detailretail.kurlyflow.tote.command.domain.ToteRepository;
import com.detailretail.kurlyflow.worker.presentation.ToteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ToteCommandService {

  private final ToteRepository toteRepository;

  public void assignTote(ToteRequest toteRequest, Long workerId) {
    toteRepository.save(new Tote(toteRequest.getToteId()));
  }

}
