package com.pris.cinema.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pris.cinema.security.SecurityConstants;
import com.pris.cinema.security.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Username needs to be an email")
    @NotBlank(message = "Username field is required")
    private String username;

    @NotBlank(message = "Please enter your first name")
    private String firstName;

    @NotBlank(message = "Please enter your last name")
    private String lastName;

    @NotNull(message = "Please enter your role")
    private ERole role;

    @NotBlank(message = "Password field is required")
    private String password;

    @Transient
    private String confirmPassword;

    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updatedAt;

    public User() { }

    @PrePersist protected void onCreate()   { this.createdAt = new Date(); }
    @PreUpdate protected void onUpdate()    { this.updatedAt = new Date(); }

    public Long getId()                 { return id;                }
    public String getUsername()         { return username;          }
    public String getFirstName()        { return firstName;         }
    public String getLastName()         { return lastName;          }
    public ERole getRole()              { return role;              }
    public String getPassword()         { return password;          }
    public String getConfirmPassword()  { return confirmPassword;   }
    public Date getCreatedAt()          { return createdAt;         }
    public Date getUpdatedAt()          { return updatedAt;         }

    public void setUsername(String username)                { this.username = username;                 }
    public void setFirstName(String firstName)              { this.firstName = firstName;               }
    public void setLastName(String lastName)                { this.lastName = lastName;                 }
    public void setRole(ERole role)                         { this.role = role;                         }
    public void setPassword(String password)                { this.password = password;                 }
    public void setConfirmPassword(String confirmPassword)  { this.confirmPassword = confirmPassword;   }

    @Override @JsonIgnore public Collection<? extends GrantedAuthority> getAuthorities()    {

        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority(SecurityConstants.ROLE_PREFIX + role));

        return list;
    }

    @Override @JsonIgnore public boolean isAccountNonExpired()                              { return true; }
    @Override @JsonIgnore public boolean isAccountNonLocked()                               { return true; }
    @Override @JsonIgnore public boolean isCredentialsNonExpired()                          { return true; }
    @Override @JsonIgnore public boolean isEnabled()                                        { return true; }
}
