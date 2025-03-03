package com.postdm.backend.domain.estimate.service.policy;

import com.postdm.backend.domain.estimate.entity.Estimate;
import com.postdm.backend.domain.estimate.entity.EstimateRepository;
import com.postdm.backend.domain.member.domain.entity.Member;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserEstimatePolicy implements EstimatePolicy {
    @Override
    public List<Estimate> getEstimates(Member member, EstimateRepository repository) {
        return repository.findByMemberId(member.getId()); // 일반 유저: 본인만 조회
    }
}