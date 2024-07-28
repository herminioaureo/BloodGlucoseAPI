package com.bloodglucose.api.adapter.out;

import com.bloodglucose.api.core.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositoryAdapterOut extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findByEmail(String email);

}
