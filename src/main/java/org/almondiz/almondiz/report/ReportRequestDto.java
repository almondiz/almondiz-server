package org.almondiz.almondiz.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequestDto {

    private Long postId;

    private String text;

    private String type;

}
