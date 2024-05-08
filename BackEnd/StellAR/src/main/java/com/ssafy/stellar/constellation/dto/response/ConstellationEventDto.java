package com.ssafy.stellar.constellation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
public class ConstellationEventDto {

    private String astroEvent;
    private String astroTime;
    private LocalDate localDate;

}
