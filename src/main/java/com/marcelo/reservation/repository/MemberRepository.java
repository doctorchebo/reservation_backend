package com.marcelo.reservation.repository;

import com.marcelo.reservation.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByBusinessId(Long businessId);

    List<Member> findByBusinessIdAndIsActiveTrue(Long businessId);
}
