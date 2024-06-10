package com.project.domain.entity;

import com.project.domain.model.SignDomainForm;
import com.project.domain.type.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "USER_INFO")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {

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
    public static User register(SignDomainForm.SignUpForm form) {
        return User.builder()
                .email(form.getEmail())
                .password(form.getPassword())
                .username(form.getUsername())
                .phone(form.getPhone())
                .userType(UserType.CUSTOMER)
                .registeredDt(LocalDateTime.now())
                .build();
    }

    public void encryptPassword(String encryptedPwd) {
        this.password = encryptedPwd;
    }

    public void enrollPartner() {
        this.userType = UserType.PARTNER;
    }


/*    @Override
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
    }*/
}
