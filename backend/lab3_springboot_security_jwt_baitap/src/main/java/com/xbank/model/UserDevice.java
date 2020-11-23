package com.xbank.model;

import com.xbank.model.audit.DateAudit;
import com.xbank.model.enums.DeviceType;
import com.xbank.model.token.RefreshToken;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "user_device")
public class UserDevice extends DateAudit {

    @Id
    @Column(name = "user_device_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "device_type")
    @Enumerated(value = EnumType.STRING)
    private DeviceType deviceType;

    @Column(name = "notification_token")
    private String notificationToken;

    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @OneToOne(optional = false, mappedBy = "userDevice")
    private RefreshToken refreshToken;

    @Column(name = "is_refresh_active")
    private Boolean isRefreshActive;

    public Boolean getRefreshActive() {
        return isRefreshActive;
    }

    public void setRefreshActive(Boolean refreshActive) {
        isRefreshActive = refreshActive;
    }
}
