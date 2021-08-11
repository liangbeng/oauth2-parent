package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission implements Serializable {
    private static final long serialVersionUID = -109732883519751381L;
    private Integer id;

    private Integer parentid;

    private String name;

    private String css;

    private String href;

    private Boolean type;

    private String permission;

    private Integer sort;
}