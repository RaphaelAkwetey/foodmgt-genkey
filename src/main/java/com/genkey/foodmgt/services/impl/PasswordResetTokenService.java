package com.genkey.foodmgt.services.impl;

import com.genkey.foodmgt.model.impl.PasswordResetToken;
import com.genkey.foodmgt.model.impl.Users;
import com.genkey.foodmgt.repository.dao.api.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public PasswordResetTokenService(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    public PasswordResetToken createPasswordResetToken(Users user) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken(user,token);
        return passwordResetTokenRepository.save(passwordResetToken);
    }

    public Optional<PasswordResetToken> getPasswordResetToken(String token) {
        return passwordResetTokenRepository.RetrieveToken(token);
    }

    public void deletePasswordResetToken(PasswordResetToken passwordResetToken) {
        passwordResetTokenRepository.delete(passwordResetToken);
    }

    public void deleteExpiredTokens() {
        List<PasswordResetToken> expiredTokens = passwordResetTokenRepository.findAllByExpiryDateBefore(LocalDateTime.now());
        //System.out.println("Found " + expiredTokens.size() + " expired tokens.");
        passwordResetTokenRepository.deleteAll(expiredTokens);
    }

}

