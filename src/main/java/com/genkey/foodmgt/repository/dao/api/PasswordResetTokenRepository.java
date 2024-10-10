package com.genkey.foodmgt.repository.dao.api;

import com.genkey.foodmgt.model.impl.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    //Optional<PasswordResetToken> findByToken(String token);

    @Query(value = "SELECT * FROM password_reset_token WHERE token = :token", nativeQuery = true)
    Optional<PasswordResetToken> RetrieveToken(String token);

    List<PasswordResetToken> findAllByExpiryDateBefore(LocalDateTime expiryDate);

}
