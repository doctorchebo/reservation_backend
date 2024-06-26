package com.marcelo.reservation.repository;

import com.marcelo.reservation.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByBusinessId(Long businessId);

    List<Member> findByBusinessIdAndIsActiveTrue(Long businessId);

    Optional<Member> findByUserId(Long userId);

    @Modifying
    @Query("DELETE FROM Member m WHERE m.id = :memberId")
    void deleteById(@Param("memberId") Long memberId);
}
