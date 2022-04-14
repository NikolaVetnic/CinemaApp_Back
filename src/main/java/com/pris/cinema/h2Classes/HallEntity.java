//package com.pris.cinema.h2Classes;
//
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.experimental.Accessors;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import java.util.ArrayList;
//import java.util.List;
//
//@NoArgsConstructor
//@Accessors(chain = true) //setters return 'this' instead of void
//@Getter
//@Setter
//@Entity
//@Table(name = "hall")
////hala kao sala, nisam mogao da smislim bolji prevod
//public class HallEntity {
//
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "hallId")
//    private Integer hallId;
//
//    @Column(name = "hallName", nullable = false)
//    @NotNull(message = "Name must be provided.")
//    String hallName;
//
//    @Column(name = "seats", nullable = false)
//    @NotNull(message = "Seats must be provided.")
//    int seats;
//
//    @JsonBackReference
//    @OneToMany(mappedBy = "hall", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
//    @Column(name = "projectionsHall", nullable = false)
//    protected List<TicketEntity> projectionsHall = new ArrayList<>();
//
//
//}
