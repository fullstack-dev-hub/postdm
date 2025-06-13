package com.postdm.backend.domain.estimate.service.policy;

import com.postdm.backend.domain.estimate.entity.Estimate;
import com.postdm.backend.domain.estimate.entity.EstimateRepository;
import com.postdm.backend.domain.member.domain.entity.Member;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface EstimatePolicy {
    Page<Estimate> getEstimates(Member member, EstimateRepository repository, Pageable pageable);
}