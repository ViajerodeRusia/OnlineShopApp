package ru.skypro.homework.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdDto {
    private Integer author;
    private String image;
    private Integer pk;
    private Integer price;
    private String title;
}
