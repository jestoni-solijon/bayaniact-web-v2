package com.bayaniact.common.security;

import com.bayaniact.common.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
