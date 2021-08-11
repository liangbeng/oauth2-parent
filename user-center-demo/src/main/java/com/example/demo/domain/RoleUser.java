package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleUser implements Serializable {
    private static final long serialVersionUID = -6756723367883575079L;
    private Integer userid;

    private Integer roleid;
}