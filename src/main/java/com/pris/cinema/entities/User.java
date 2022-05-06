package com.pris.cinema.entities;

import com.fasterxml.jackson.annotation.*;
import com.pris.cinema.entities.dto.UserDisplayDto;
import com.pris.cinema.entities.dto.UserRegisterDto;
import com.pris.cinema.entities.e.ERole;
import com.pris.cinema.security.SecurityConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.lang.reflect.Field;
import java.util.*;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Long id;

    @Email(message = "Username needs to be an email")
    @NotBlank(message = "Username field is required")
    @Column(name = "username", nullable = false)
    protected String username;

    @NotBlank(message = "Please enter your first name")
    @Column(name = "first_name", nullable = false)
    protected String firstName;

    @NotBlank(message = "Please enter your last name")
    @Column(name = "last_name", nullable = false)
    protected String lastName;

    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @NotBlank(message = "Password field is required")
    @Column(name = "password", nullable = false)
    protected String password;

    @Transient
    protected String confirmPassword;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
    protected List<Ticket> tickets = new LinkedList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
    protected List<Comment> comments = new LinkedList<>();

    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-mm-dd")
    protected Date createdAt;

    @JsonFormat(pattern = "yyyy-mm-dd")
    protected Date updatedAt;

    @PrePersist protected void onCreate()   { this.createdAt = new Date(); }
    @PreUpdate protected void onUpdate()    { this.updatedAt = new Date(); }

    public UserDisplayDto getDisplayDto() {
        return new UserDisplayDto(this);
    }

    public ERole roleAsEnum() {
        return ERole.values()[(int) (role.id - 1)];
    }

    @Override @JsonIgnore public Collection<? extends GrantedAuthority> getAuthorities()    {

        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority(SecurityConstants.ROLE_PREFIX + roleAsEnum()));

        return list;
    }

    @Override @JsonIgnore public boolean isAccountNonExpired()                              { return true; }
    @Override @JsonIgnore public boolean isAccountNonLocked()                               { return true; }
    @Override @JsonIgnore public boolean isCredentialsNonExpired()                          { return true; }
    @Override @JsonIgnore public boolean isEnabled()                                        { return true; }
}
