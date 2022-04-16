package com.pris.cinema.entities;

import com.fasterxml.jackson.annotation.*;
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
    protected Long id;

    @Email(message = "Username needs to be an email")
    @NotBlank(message = "Username field is required")
    protected String username;

    @NotBlank(message = "Please enter your first name")
    protected String firstName;

    @NotBlank(message = "Please enter your last name")
    protected String lastName;

    @JsonBackReference("role_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId")
    private Role role;

    @NotBlank(message = "Password field is required")
    protected String password;

    @Transient
    protected String confirmPassword;

    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-mm-dd")
    protected Date createdAt;

    @JsonFormat(pattern = "yyyy-mm-dd")
    protected Date updatedAt;

    @JsonManagedReference("tickets")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
    protected List<Ticket> tickets = new LinkedList<>();

    @PrePersist protected void onCreate()   { this.createdAt = new Date(); }
    @PreUpdate protected void onUpdate()    { this.updatedAt = new Date(); }

    public ERole roleAsEnum() {
        return ERole.values()[(int) (role.id - 1)];
    }

    public String toJson() {

        JSONObject obj = new JSONObject();

        Field[] fields = this.getClass().getDeclaredFields();

        try {
            for (Field field : fields)
                if (field.getName().contains("word"))
                    continue;
                else
                    obj.put(field.getName(), field.get(this));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return obj.toString();
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
