package com.jeespb.databaseservice.model;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "user_id", nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_type")
    private String customerType;

    @Column(name = "last_login_date")
    private Date lastLoginDate;

    @Column(name = "customer_session_id", unique = true)
    private String customerSessionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "session_status")
    private SessionStatus sessionStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getCustomerSessionId() {
        return customerSessionId;
    }

    public void setCustomerSessionId(String customerSessionId) {
        this.customerSessionId = customerSessionId;
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(SessionStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
    }
}
