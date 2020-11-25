package com.xbank.service;

import com.xbank.model.Investigate;
import com.xbank.model.dto.InvestigateRequest;
import com.xbank.repository.InvestigateRepository;
import com.xbank.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvestigateService {

    private final InvestigateRepository investigateRepository;

    public Optional<Investigate> createInvestigate(InvestigateRequest investigateRequest) {
        Investigate investigate = new Investigate();
        if (Objects.nonNull(investigateRequest)) {
            investigate.setId(UuidUtil.generateRandomUuid());
            investigate.setAccount(investigateRequest.getAccount());
            investigate.setCreatedAt(LocalDateTime.now());
        }
        return Optional.of(investigateRepository.save(investigate));
    }

    public Optional<Investigate> getById(String id) {
        return investigateRepository.findById(id);
    }

    public Page<Investigate> pagination(Pageable pageable) {
        return investigateRepository.findAll(pageable);
    }
}
