package com.bloodglucose.api.adapter.out;

import com.bloodglucose.api.core.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryPortOut extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

}
