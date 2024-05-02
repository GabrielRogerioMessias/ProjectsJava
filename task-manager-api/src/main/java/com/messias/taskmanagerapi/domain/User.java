package com.messias.taskmanagerapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    @NotBlank(message = "Name may not blank")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Surname may not blank")
    private String surname;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username may not blank")
    private String username;


    @NotBlank(message = "Password may not blank")
    @Column(nullable = false)
    private String password;


    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;
    @Column(name = "account_non_expired")
    private Boolean accountNonExpired;
    @Column(name = "account_non_locked")
    private Boolean accountNonLocked;
    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired;
    private Boolean enabled;

    //quando o user for carregado, também carregara de forma automatica suas permissões
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_permission", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_permission"))
    private List<Permission> permissionList = new ArrayList<>();

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Task> taskList;

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Category> categoryList;


    public List<String> getRoles() {
        List<String> roles = new ArrayList<>();
        for (Permission permission : permissionList) {
            roles.add(permission.getDescription());
        }
        return roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissionList;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
