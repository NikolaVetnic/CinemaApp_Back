package com.pris.cinema.h2Classes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Accessors(chain = true) //setters return 'this' instead of void
@Getter
@Setter
@Entity
@Table(name = "projection")
public class ProjectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "projectionId")
    private Integer projectionId;

    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "hall")
    @Column(name = "hall", nullable = false)
   HallEntity hall;

    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "movie")
    @Column(name = "movie", nullable = false)
    MovieEntity movie;

    @Column(name = "date", nullable = false)
    Date date;

    @JsonBackReference
    @OneToMany(mappedBy = "projection", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
    protected List<TicketEntity> tickets = new ArrayList<>();

}
