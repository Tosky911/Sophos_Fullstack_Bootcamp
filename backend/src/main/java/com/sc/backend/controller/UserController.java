package com.sc.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sc.backend.entity.UserEntity;
import com.sc.backend.model.GeneralResponse;
import com.sc.backend.service.UserService;
//import com.sc.backend.util.JwtUtils;

@CrossOrigin(origins = "http:localhost:4200")
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
//	@Autowired
//	private AuthenticationManager authenticationManager;
//	
//	@Autowired
//	private JwtUtils jwtUtils;
	
	/**
	 * Login with userName and password.
	 * 
	 * @param user
	 * @return
	 */
	
//	@PostMapping("/auth")
//	public ResponseEntity<GeneralResponse<UserEntity>> login(@RequestBody UserEntity user) {
//		
//		GeneralResponse<UserEntity> response = new GeneralResponse<>();
//		HttpStatus status = null;
//
//		try {
//			
//			authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
//					);
//			user.setJwt(jwtUtils.generateToken(user.getUserName()));
//			
//			user.setPassword(null);
//			String msg = "Login successfull for user " + user.getUserName() + ".";
//			
//			response.setMessage(msg);
//			response.setSuccess(true);
//			response.setData(user);
//			status = HttpStatus.OK;
//			
//		} catch (AuthenticationException e) {
//			
//			String msg = "Usuario o clave incorrectos.";
//			status = HttpStatus.FORBIDDEN;
//			response.setMessage(msg);
//			response.setSuccess(false);
//		
//		} catch (Exception e) {
//			
//			String msg = "Something has failed. Please contact support.";
//			status = HttpStatus.INTERNAL_SERVER_ERROR;
//			response.setMessage(msg);
//			response.setSuccess(false);
//		
//		}
//
//		return new ResponseEntity<>(response, status);
//	}


	/**
	 * Listado de usuarios.
	 * 
	 * @return @List<UserVO>
	 */

	@GetMapping("")
	public ResponseEntity<GeneralResponse<List<UserEntity>>> get() {
		
		GeneralResponse<List<UserEntity>> response = new GeneralResponse<>();
		HttpStatus status = null;
		List<UserEntity> data; 

		try {
			data = userService.get();
			
			if (data == null || data.size() == 0) {
				response.setMessageResult( "0 users were found.");
				response.setCodeError(1);
				response.setData(null);
			}else {
				response.setMessageResult(data.size()+ " users were found.");
				response.setCodeError(0);

			}

			response.setMessage("Successful Query");
			response.setSuccess(true);
			response.setData(data);
			status = HttpStatus.OK;
			
		} catch (Exception e) {
			
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setMessage("Something has failed. Error: "+ e.getLocalizedMessage()  +". Please contact support.");
			response.setSuccess(false);
		}
		return new ResponseEntity<>(response, status);
	}
	
	/**
	 * Usuario por userName.
	 * 
	 * @return <UserVO>
	 */
	
	@GetMapping("/{userName}")
	public ResponseEntity<GeneralResponse<UserEntity>> findByUserName(@PathVariable("userName") String userName){
		
		GeneralResponse<UserEntity> response = new GeneralResponse<>();
		HttpStatus status = null;
		Optional<UserEntity> user; 

		try {
			user = userService.findByUserName(userName);
			
			if (user == null || user.get() == null) {
				response.setMessageResult( "User wasn't found.");
				response.setCodeError(1);
				response.setData(null);
			}else {
				response.setMessageResult("User " + userName +" was found.");
				response.setCodeError(0);

			}

			response.setMessage("Successful Query");
			response.setSuccess(true);
			response.setData(user.get());
			status = HttpStatus.OK;
			
		} catch (Exception e) {
			
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setMessage("Something has failed. Error: "+ e.getLocalizedMessage()  +". Please contact support.");
			response.setSuccess(false);
		}
		return new ResponseEntity<>(response, status);
	}
	
	
	/**
	 * Save/Guardar usuarios.
	 * 
	 * @return @List<UserVO>
	 */

	@PostMapping
	public ResponseEntity<GeneralResponse<List<UserEntity>>> save(@RequestBody List<UserEntity> users) {
		
		GeneralResponse<List<UserEntity>> response = new GeneralResponse<>();
		HttpStatus status = null;
		List<UserEntity> data = null; 

		try {
			data = userService.save(users);
			
			if(data == null || data.size() == 0) {
				response.setMessageResult( "0 users were saved.");
				response.setCodeError(1);
				response.setData(null);
			}else {
				response.setMessageResult(data.size()+ " users were saved.");
				response.setCodeError(0);
			}
			
			response.setMessage("Successful Save");
			response.setSuccess(true);
			response.setData(data);
			status = HttpStatus.OK;
		} catch (Exception e) {
			
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setMessage("Something has failed. Error: "+ e.getLocalizedMessage()  +" . Please contact support.");
			response.setSuccess(false);
		}
		return new ResponseEntity<>(response, status);
	}
	
	/**
	 * Update/Actualizar usuarios.
	 * 
	 * @return @List<UserVO>
	 */

	@PutMapping
	public ResponseEntity<GeneralResponse<List<UserEntity>>> update(@RequestBody List<UserEntity> users) {
		
		GeneralResponse<List<UserEntity>> response = new GeneralResponse<>();
		HttpStatus status = null;
		List<UserEntity> data = null; 

		try {

			data = userService.save(users);
			if (data == null || data.size() == 0) {
				response.setMessageResult( "0 users were updated.");
				response.setCodeError(1);
				response.setData(null);
			}else {
				response.setMessageResult(data.size() + " users were updated.");
				response.setCodeError(0);
			}
			
			response.setMessage("Successful Update");
			response.setSuccess(true);
			response.setData(data);
			status = HttpStatus.OK;
			
		} catch (Exception e) {
			
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setMessage("Something has failed. Error: "+ e.getLocalizedMessage()  +" . Please contact support.");
			response.setSuccess(false);
			
		}
		return new ResponseEntity<>(response, status);
	}
	
	/**
	 * Delete user.
	 * 
	 * @return @List<UserVO>
	 */

	@DeleteMapping("/{userName}")
	public ResponseEntity<GeneralResponse<String>> delete(@PathVariable String userName) {
		
		GeneralResponse<String> response = new GeneralResponse<>();
		HttpStatus status = null;
		
		try {
			userService.delete(userName);
			
			if(userName == null) {
				response.setCodeError(1);
				response.setMessageResult( "User wasn't deleted.");
				response.setData(null);
			}else {
				response.setCodeError(0);
				response.setMessageResult("User " + userName +" was deleted.");
			}
			
			response.setMessage("Successful Removal");
			response.setSuccess(true);
			response.setData(userName);
			status = HttpStatus.OK;
			
		} catch (Exception e) {
			
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setMessage("Something has failed. Error: "+ e.getLocalizedMessage()  +" . Please contact support.");
			response.setSuccess(false);
		}
		return new ResponseEntity<>(response, status);
	}
}
