package com.sc.backend.interfaceService;

import java.util.List;

import com.sc.backend.entity.User;

public interface InterfaceUserService {
	public List<User> list();
	public User listOneUserId(Long id);
	public User add(User user);
	public User edit(User user);
	public void delete(Long id);
}
