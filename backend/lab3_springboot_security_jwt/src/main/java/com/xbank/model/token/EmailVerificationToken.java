package com.xbank.model.token;

import com.xbank.model.User;
import com.xbank.model.audit.DateAudit;
import com.xbank.model.enums.TokenStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity(name = "email_verification_token")
public class EmailVerificationToken extends DateAudit {

    @Id
    @Column(name = "token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(name = "token_status")
    @Enumerated(EnumType.STRING)
    private TokenStatus tokenStatus;

    @Column(name = "expiry_dt", nullable = false)
    private Instant expiryDate;

    public void setConfirmedStatus() {
        setTokenStatus(TokenStatus.STATUS_CONFIRMED);
    }
}
