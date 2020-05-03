package com.arpita.security_first.securityBasic;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.arpita.security_first.securityBasic.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE,STUDENT_READ,STUDENT_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(COURSE_READ, STUDENT_READ));

    private final Set<ApplicationUserPermission> applicationUserPermissionSet;

    ApplicationUserRole(Set<ApplicationUserPermission> applicationUserPermissionSet) {
        this.applicationUserPermissionSet = applicationUserPermissionSet;
    }

    public Set<ApplicationUserPermission> getApplicationUserPermissionSet() {
        return applicationUserPermissionSet;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority(){
        Set<SimpleGrantedAuthority> permissions=getApplicationUserPermissionSet().stream()
                .map(applicationUserPermission ->new SimpleGrantedAuthority(applicationUserPermission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return permissions;
    }

}
