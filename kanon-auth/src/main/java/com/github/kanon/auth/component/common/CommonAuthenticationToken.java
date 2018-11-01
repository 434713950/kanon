package com.github.kanon.auth.component.common;

import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/9/7
 */
public class CommonAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    @Setter
    private Object principal;

    private Object credentials;

    public CommonAuthenticationToken(Object principal,Object credentials){
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    public CommonAuthenticationToken(Object principal,Object credentials,Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated){
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead"
            );
        }
        super.setAuthenticated(false);
    }
}
