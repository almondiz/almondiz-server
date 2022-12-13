package org.almondiz.almondiz.nut.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.almondiz.almondiz.nut.entity.Nut;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NutResponseDto {

    private Long nutId;

    private String nutName;

    public NutResponseDto(Nut nut) {
        this.nutId = nut.getNutId();
        this.nutName = nut.getNutName();
    }
}
