package com.project.reservation.auth.entity;

import com.project.reservation.auth.model.SignForm;
import com.project.reservation.common.entity.BaseEntity;
import com.project.reservation.reservation.entity.Reservation;
import com.project.reservation.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "USER_INFO")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email", unique = true)
    private String email;

    @Column(name = "user_pw")
    private String password;

    @Column(name = "user_name")
    private String username;

    @Column(name = "user_phone")
    private String phone;

    @Column(name = "user_type")
    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    @Column(name = "user_reg_dt")
    private LocalDateTime registeredDt;

    // 예약 리스트
    @OneToMany(mappedBy = "customer")
    private List<Reservation> reservationList = new ArrayList<>();


    // 파트너기능
    @OneToMany(mappedBy = "owner")
    private List<Store> storeList = new ArrayList<>();



    // signUpForm 으로부터 User 객체 생성 (비밀번호 암호화 위해 passwordEncoder 넘겨줌)
    public static User register(SignForm.SignUpForm form, PasswordEncoder encoder) {
        return User.builder()
                .email(form.getEmail())
                .password(encoder.encode(form.getPassword()))
                .username(form.getUsername())
                .phone(form.getPhone())
                .userType(UserType.CUSTOMER)
                .registeredDt(LocalDateTime.now())
                .build();
    }

    public void enrollPartner() {
        this.userType = UserType.PARTNER;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(userType.name()));
        return list;
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
