package com.example.DemoSecurity.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Component
@Entity
@Table(name = "user")
public class User extends BaseEntity
        implements UserDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "User_Id")
    private Long userId;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "Full_Name")
    private String fullName;

    @Column(name = "Email")
    private String email;

    @Column(name = "User_Name", unique = true, nullable = false)
    private String userName;

    @Column(name = "Password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonManagedReference
    private Set<Role> roles = new HashSet<>();

    public User() {
        this.uuid = UUID.randomUUID().toString();
    }

    public User (String userName , String password , HashSet<Role> roles) {
        this.userName = userName ;
        this.password = password ;
        this.roles = roles ;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        this.roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }


    /**
     * TK còn hạn hay ko
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * TK có bị lock hay ko
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Dùng để kiểm tra hạn sử dụng của 1 tài khoản
     * (ví dụ: một tài khoản có thời hạn sử dụng)
     * và trả về true hoặc false để cho phép
     * tk đó đc vào hệ thống hay ko
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Dùng để kích hoạt tài khoản người dùng
     * có thể đăng nhập và sử dụng hệ thống hay ko
     * Ex: Kiểm tra xem người dùng đã xác nhận email
     * hay chưa mà set thành True or false
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
