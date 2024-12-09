package com.security.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    private String userId;

    private String name;

    @Column(unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @CreationTimestamp
    private LocalDateTime creationDate;
    
    private boolean isLoggedIn;
    private Long loggedInSession;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoginActivity> loginActivities;
    
    @PrePersist
    protected void generateUserId() {
		userId = UUID.randomUUID().toString();
    }

	public User(String name, String email, String password, String city) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.city = city;
		this.isLoggedIn = false;
		this.loggedInSession = Long.valueOf(0);
	}
    
}
