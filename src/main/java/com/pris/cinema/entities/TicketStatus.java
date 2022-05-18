package com.pris.cinema.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@Entity
@Table(name = "ticket_status")
public class TicketStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Long id;

    @NotBlank(message = "Please enter ticket status")
    @Column(name = "status", nullable = false)
    protected String status;

    @JsonManagedReference
    @OneToMany(mappedBy = "ticketStatus", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
    protected List<Ticket> tickets = new LinkedList<>();
}
