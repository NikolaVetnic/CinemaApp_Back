package com.pris.cinema.entities.dto;

import com.pris.cinema.entities.Hall;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class HallDisplayDto {

    protected Long id;
    protected String name;
    protected Integer seatsGroundFloor;
    protected Integer seatsGalleryLeft;
    protected Integer seatsGalleryRight;

    public HallDisplayDto(Hall hall) {
        this.id = hall.getId();
        this.name = hall.getName();
        this.seatsGroundFloor = Math.toIntExact(
                hall.getSeats().stream().filter(seat -> seat.getSection().getSection().equals("GROUND_FLOOR")).count());
        this.seatsGalleryLeft = Math.toIntExact(
                hall.getSeats().stream().filter(seat -> seat.getSection().getSection().equals("GALLERY_LEFT")).count());
        this.seatsGalleryRight = Math.toIntExact(
                hall.getSeats().stream().filter(seat -> seat.getSection().getSection().equals("GALLERY_RIGHT")).count());
    }
}
