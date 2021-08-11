package com.example.demo.vo;

import com.example.demo.domain.Permission;
import com.example.demo.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser extends User implements UserDetails{
    private static final long serialVersionUID = -5911645384918125716L;

    private Integer id;

    private String username;

    private String password;

    private String phone;

    private Integer sex;

    private List<Permission> permissions;

    private String token;
    /**登陆时间戳（毫秒）**/
    private Long loginTime;

    /** 过期时间戳 **/
    private Long expireTime;



    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (!CollectionUtils.isEmpty(permissions)){
            Set<SimpleGrantedAuthority> collect = permissions.parallelStream().filter(p -> !StringUtils.isEmpty(p.getPermission()))
                    .map(p -> new SimpleGrantedAuthority(p.getPermission())).collect(Collectors.toSet());
            log.info("collect:"+collect);
            return collect;
        }
        log.warn("{} does not have any permission",username);
        return new ArrayList<>();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return getStatus() != Status.LOCKED;
//        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
