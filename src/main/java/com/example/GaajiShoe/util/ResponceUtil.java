package com.example.GaajiShoe.util;/*  gaajiCode
    99
    04/10/2024
    */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponceUtil {
    private int code;
    private String massage;

    private Object data;
}
