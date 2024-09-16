package com.manuelberganza.xclone.models;

import java.util.List;

import com.manuelberganza.xclone.security.validation.IsExistsDb;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(groups = {UserValidationGroups.Registration.class, UserValidationGroups.UpdateProfile.class})
    private String name;

    @NotBlank(groups = {UserValidationGroups.Registration.class, UserValidationGroups.UpdateProfile.class})
    private String lastname;

    @NotBlank(groups = UserValidationGroups.Registration.class)
    @Size( groups = UserValidationGroups.Registration.class, min = 8, max = 20, message = "el usuario tiene que ser minimo de 8 caracteres y maximo 20")
    @IsExistsDb(groups = UserValidationGroups.Registration.class)
    private String username;

    @Email(groups = UserValidationGroups.Registration.class)
    @NotBlank(groups = UserValidationGroups.Registration.class)
    private String email;

    @NotEmpty(groups = UserValidationGroups.Registration.class)
    @Size(groups = UserValidationGroups.Registration.class, min = 8)
    private String password;

    private Boolean enabled;

    private String photo;

    @ManyToMany
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
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
    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
}
