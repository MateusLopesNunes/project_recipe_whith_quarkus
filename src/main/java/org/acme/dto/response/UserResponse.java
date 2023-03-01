package org.acme.dto.response;

import java.time.LocalDateTime;

public class UserResponse {

    private Long id;
    private String userName;
    private String email;
    private LocalDateTime birthDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String tel;
    private String perfilImage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPerfilImage() {
        return perfilImage;
    }

    public void setPerfilImage(String perfilImage) {
        this.perfilImage = perfilImage;
    }
}
