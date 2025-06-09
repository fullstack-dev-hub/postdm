package com.postdm.backend.domain.estimate.service.policy;

import com.postdm.backend.domain.estimate.entity.Estimate;
import com.postdm.backend.domain.estimate.entity.EstimateRepository;
import com.postdm.backend.domain.member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.Pageable;

@Component
@RequiredArgsConstructor
public class AdminEstimatePolicy implements EstimatePolicy {
    @Override
    public Page<Estimate> getEstimates(Member member, EstimateRepository repository, Pageable pageable) {
        return repository.findAll(pageable);
    }
}