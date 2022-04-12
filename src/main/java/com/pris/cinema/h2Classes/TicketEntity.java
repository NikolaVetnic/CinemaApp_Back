package com.pris.cinema.h2Classes;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@NoArgsConstructor
@Accessors(chain = true) //setters return 'this' instead of void
@Getter
@Setter
@Entity
@Table(name = "ticket")
public class TicketEntity {

    //rezervacija ce biti karta+registrovan korisnik

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ticketId")
    private Integer ticketId;

    @Column(name = "holderName", nullable = false)
    String holderName;

    @Column(name = "holderSurame", nullable = false)
    String holderSurname;

    @Column(name = "price", nullable = false)
    Double price;

    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "projection")
    @Column(name = "projection", nullable = false)
    ProjectionEntity projection;

}
