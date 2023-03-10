package org.acme.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class UserUpdateRequest {

    @NotBlank(message="Name may not be blank")
    private Long id;
    @NotBlank(message="Name may not be blank")
    private String userName;

    @NotBlank(message="Email may not be blank")
    private String email;

    @NotBlank(message="Password may not be blank")
    private String password;

    @NotNull
    private LocalDateTime birthDate;

    @NotBlank(message="Telephone may not be blank")
    private String tel;

    @NotBlank(message="Perfil image may not be blank")
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
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
