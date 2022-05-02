package com.pris.cinema.entities.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pris.cinema.entities.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class UserRegisterDto {

    @Email(message = "Username needs to be an email")
    @NotBlank(message = "Username field is required")
    protected String username;

    @NotBlank(message = "Please enter your first name")
    protected String firstName;

    @NotBlank(message = "Please enter your last name")
    protected String lastName;

    private Long roleId;

    @NotBlank(message = "Password field is required")
    protected String password;

    @Transient
    protected String confirmPassword;

    @Override
    public String toString() {
        return "UserRegisterDto{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roleId=" + roleId +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
