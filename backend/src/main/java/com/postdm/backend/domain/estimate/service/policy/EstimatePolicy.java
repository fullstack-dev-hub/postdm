package com.postdm.backend.domain.estimate.service.policy;

import com.postdm.backend.domain.estimate.entity.Estimate;
import com.postdm.backend.domain.estimate.entity.EstimateRepository;
import com.postdm.backend.domain.member.domain.entity.Member;

import java.util.List;

public interface EstimatePolicy {
    List<Estimate> getEstimates(Member member, EstimateRepository repository);
}