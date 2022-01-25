package com.sc.backend.interfaceService;

import java.util.List;
import java.util.Optional;

import com.sc.backend.entity.UserEntity;

public interface InterfaceUserService {
	public List<UserEntity> list();
	public Optional<UserEntity> listOneUserId(Long id);
	public UserEntity add(UserEntity userEntity);
	public UserEntity edit(UserEntity userEntity);
	public void delete(Long id);
}
