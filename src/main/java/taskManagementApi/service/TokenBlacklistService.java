package taskManagementApi.service;

import taskManagementApi.model.TokenBlacklist;
import taskManagementApi.repository.TokenBlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenBlacklistService {

    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;

    public void addTokenToBlacklist(String token, LocalDateTime expiryDate) {
        TokenBlacklist tokenBlacklist = new TokenBlacklist(token, expiryDate);
        tokenBlacklistRepository.save(tokenBlacklist);
    }

    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklistRepository.findByToken(token).isPresent();
    }
}
