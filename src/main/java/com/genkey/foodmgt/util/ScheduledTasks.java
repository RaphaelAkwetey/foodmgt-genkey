package com.genkey.foodmgt.util;

import com.genkey.foodmgt.model.impl.Menu;
import com.genkey.foodmgt.repository.dao.api.MenuDAO;
import com.genkey.foodmgt.services.impl.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ScheduledTasks {

    @Autowired
    MenuDAO menuRepository;

    private final PasswordResetTokenService passwordResetTokenService;

    public ScheduledTasks(PasswordResetTokenService passwordResetTokenService) {
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @Scheduled(fixedRate = 60000) // runs every 1 minute
    public void deleteExpiredTokens() {
        passwordResetTokenService.deleteExpiredTokens();
    }

    @Scheduled(fixedRate = 60000) // Runs every hour
    public void updateMenuStatus() {
        LocalDate currentDate = LocalDate.now();
        List<Menu> expiredMenus = menuRepository.findByExpiryDateLessThanAndStatus(currentDate,"Available");

        for (Menu menu : expiredMenus) {
            menu.setStatus("Unavailable");
            menuRepository.save(menu);
        }
    }
}
