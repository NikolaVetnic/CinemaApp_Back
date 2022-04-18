package com.pris.cinema.entities.dto;

import com.pris.cinema.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class UserInfoDto {

    protected Long id;
    protected String username;
    protected String firstName;
    protected String lastName;
    private String role;
    protected Date createdAt;
    protected Date updatedAt;

    public UserInfoDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole().getRole();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}
