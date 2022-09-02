package org.almondiz.almondiz.post.dto;

import java.sql.Clob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {
    private Long storeId;
    private Clob title;
    private Clob content;
}
