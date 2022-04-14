package com.pris.cinema.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pris.cinema.security.SecurityConstants;
import org.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
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

    @Min(value = 1, message = "Role ID must be equal to or greater than {value}.")
    @Max(value = 3, message = "Role ID must be equal to or lesser than {value}.")
    protected Integer roleId;

    @NotBlank(message = "Password field is required")
    protected String password;

    @Transient
    protected String confirmPassword;

    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-mm-dd")
    protected Date createdAt;

    @JsonFormat(pattern = "yyyy-mm-dd")
    protected Date updatedAt;

    public User() { }

    @PrePersist protected void onCreate()   { this.createdAt = new Date(); }
    @PreUpdate protected void onUpdate()    { this.updatedAt = new Date(); }

    public Long getId()                 { return id;                            }
    public String getUsername()         { return username;                      }
    public String getFirstName()        { return firstName;                     }
    public String getLastName()         { return lastName;                      }
    public Integer getRoleId()          { return roleId;                        }
    public String getPassword()         { return password;                      }
    public String getConfirmPassword()  { return confirmPassword;               }
    public Date getCreatedAt()          { return createdAt;                     }
    public Date getUpdatedAt()          { return updatedAt;                     }

    public ERole roleAsEnum() {
        return ERole.values()[roleId - 1];
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

    public void setUsername(String username)                { this.username = username;                 }
    public void setFirstName(String firstName)              { this.firstName = firstName;               }
    public void setLastName(String lastName)                { this.lastName = lastName;                 }
    public void setRoleId(Integer roleId)                   { this.roleId = roleId;                     }
    public void setPassword(String password)                { this.password = password;                 }
    public void setConfirmPassword(String confirmPassword)  { this.confirmPassword = confirmPassword;   }

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
