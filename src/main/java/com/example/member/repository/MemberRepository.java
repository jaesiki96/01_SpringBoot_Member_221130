package com.example.member.repository;

import com.example.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Member;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    //memberEmail 로 조회
    //select * from member_table where memberEmail=?
    //findBy = select * from member_table where
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
