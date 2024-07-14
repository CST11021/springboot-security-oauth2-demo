package com.whz.spring.security.oauth2.demo.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author ChengJianSheng
 * @date 2019-02-12
 */
@Data
@Entity
@Table(schema = "auth", name = "sys_user_role")
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = -1810195806444298544L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "role_id")
    private Integer roleId;
}
