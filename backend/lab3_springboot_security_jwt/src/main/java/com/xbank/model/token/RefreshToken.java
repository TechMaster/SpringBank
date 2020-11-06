package com.xbank.model.token;

import com.xbank.model.UserDevice;
import com.xbank.model.audit.DateAudit;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity(name = "refresh_token")
public class RefreshToken extends DateAudit {

    @Id
    @Column(name = "token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false, unique = true)
    @NaturalId(mutable = true)
    private String token;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_device_id", unique = true)
    private UserDevice userDevice;

    @Column(name = "refresh_count")
    private Long refreshCount;

    @Column(name = "expiry_dt", nullable = false)
    private Instant expiryDate;

    public void incrementRefreshCount() {
        refreshCount = refreshCount + 1;
    }
}
