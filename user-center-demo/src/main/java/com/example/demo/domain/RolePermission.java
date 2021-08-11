package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission implements Serializable {
    private static final long serialVersionUID = 838806703606481891L;
    private Integer roleid;

    private Integer permissionid;
}