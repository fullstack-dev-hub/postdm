package com.postdm.backend.domain.estimate.service.policy;

import com.postdm.backend.domain.estimate.entity.Estimate;
import com.postdm.backend.domain.estimate.entity.EstimateRepository;
import com.postdm.backend.domain.member.domain.entity.Member;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminEstimatePolicy implements EstimatePolicy {
    @Override
    public List<Estimate> getEstimates(Member member, EstimateRepository repository) {
        return repository.findAll(); // 관리자: 전체 조회
    }
}