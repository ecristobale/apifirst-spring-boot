package com.ecristobale.apifirst.apifirstspringboot.domain;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Dimensions {

    private Integer length;
    private Integer width;
    private Integer height;
}
