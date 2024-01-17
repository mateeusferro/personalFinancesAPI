package com.mateeusferro.backend.models;

import com.mateeusferro.backend.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 45, message = "Name must be between 2 and 255 characters")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Size(max = 255, message = "Email must be the max of 255 characters")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Column(name = "password")
    private String password;

    @Column(name = "is_admin")
    private String isAdmin;

    @OneToMany(mappedBy = "usersId", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<BankAccount> bankAccounts;

    @OneToMany(mappedBy = "usersId", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Expenses> expenses;

    @OneToMany(mappedBy = "usersId", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<FinancialGoal> financialGoals;

    @OneToMany(mappedBy = "usersId", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Investments> investments;

    @OneToMany(mappedBy = "usersId", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Salary> salary;

    public Users(String name, String email, String password, String isAdmin) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.isAdmin == "user") return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));

    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
