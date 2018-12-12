package com.github.kanon.auth.util;

import com.github.kanon.common.base.model.vo.RoleVo;
import com.github.kanon.common.base.model.vo.UserVo;
import com.github.kanon.common.constants.UserStatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author lengleng
 * @date 2017/10/29
 */
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Setter@Getter
    private String account;

    @Setter
    private String username;

    @Setter
    private String password;

    @Setter@Getter
    private Integer status;

    @Setter@Getter
    private List<RoleVo> roleVoList = new ArrayList<>();

    public UserDetailsImpl(UserVo userVo) {
        this.account = userVo.getUserAccount();
        this.username = userVo.getUserName();
        this.password = userVo.getUserPass();
        this.status = userVo.getStatus();
        roleVoList = userVo.getRoleList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (RoleVo roleVo : roleVoList) {
            authorityList.add(new SimpleGrantedAuthority(roleVo.getRoleId()));
        }
        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号是否未被锁定的
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return UserStatusEnum.BLOCK.getStatus().equals(this.status) ? false : true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账号是否启用的
     * @return
     */
    @Override
    public boolean isEnabled() {
        return  UserStatusEnum.COMMON.getStatus().equals(this.status) ? true : false;
    }
}
