package com.budgetbuddy;

import com.budgetbuddy.service.AuthService;
import com.budgetbuddy.service.EmailService;
import com.budgetbuddy.shared.dto.UserDto;
import com.budgetbuddy.shared.utils.JwtUtils;
import com.budgetbuddy.ui.controller.AuthController;
import com.budgetbuddy.ui.modal.request.RequestPasswordResetModel;
import com.budgetbuddy.ui.modal.request.ResetPasswordModel;
import com.budgetbuddy.ui.modal.request.UserLogin;
import com.budgetbuddy.ui.modal.request.UserRequest;
import com.budgetbuddy.ui.modal.response.UserLoginResponse;
import com.budgetbuddy.ui.modal.response.UserResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthTests {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;
    @Mock
    private EmailService emailService;
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Test
    public void testCreateAuthenticationToken_Success() {
        // Mocking userDto
        UserDto userDto = new UserDto();
        userDto.setUserId("userId123");
        when(authService.getUserByEmail(anyString())).thenReturn(userDto);

        // Mocking authenticationManager.authenticate
        Authentication authentication = new UsernamePasswordAuthenticationToken("test@example.com", "password");
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        // Mocking jwtUtils.generateToken
        when(jwtUtils.generateToken(any())).thenReturn("mockedJwtToken");

        // Perform the API call
        UserLogin userLogin = new UserLogin("test@example.com", "password");
        ResponseEntity<?> response = authController.createAuthenticationToken(userLogin);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof UserLoginResponse);
        UserLoginResponse loginResponse = (UserLoginResponse) response.getBody();
        assertNotNull(loginResponse.getToken());
        verify(authService, times(1)).getUserByEmail(anyString());
        verify(authenticationManager, times(1)).authenticate(any());
        verify(jwtUtils, times(1)).generateToken(any());
    }
  @Test
    public void testCreateUser_Success() {
        // Mocking authService.createUser
        UserRequest userRequest = new UserRequest(" Doe","datta", "pranab.pronay@gmail.com", "password");
        UserDto userDto = new UserDto();
        userDto.setUserId("userId123");
        when(authService.createUser(any())).thenReturn(userDto);


        // Perform the API call
        ResponseEntity<UserResponse> responseEntity = authController.createUser(userRequest);

        // Assertions
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(userDto.getUserId(), responseEntity.getBody().getUserId());
        verify(authService, times(1)).createUser(any());
        //verify(emailService, times(1)).sendEmailVerificationEmail(any(), anyString());
    }

    @Test
    public void testRequestPasswordReset_Success() {
        // Mocking authService.getUserByEmail
        UserDto userDto = new UserDto();
        userDto.setUserId("userId123");
        when(authService.getUserByEmail(anyString())).thenReturn(userDto);

        // Mocking authService.getRequestPasswordToken
        String mockedToken = "mockedToken";
        when(authService.getRequestPasswordToken(any())).thenReturn(mockedToken);

        // Mocking authService.saveRequestPasswordToken
        doNothing().when(authService).saveRequestPasswordToken(any(), any());

        // Mocking emailService.sendPasswordResetEmail
        //doNothing().when(emailService).sendPasswordResetEmail(any(), any());

        // Perform the API call
        RequestPasswordResetModel requestModel = new RequestPasswordResetModel("pranab.pronay@gmail.com");
        ResponseEntity<Void> responseEntity = authController.requestPasswordReset(requestModel);

        // Assertions
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(authService, times(1)).getUserByEmail(anyString());
        verify(authService, times(1)).getRequestPasswordToken(any());
        verify(authService, times(1)).saveRequestPasswordToken(any(), any());
        verify(emailService, times(1)).sendPasswordResetEmail(any(), any());
    }
    @Test
    public void testResetPassword_Success() {
        // Mocking authService.resetPassword
        doNothing().when(authService).resetPassword(anyString(), anyString());

        // Perform the API call
        ResetPasswordModel resetModel = new ResetPasswordModel("mockedToken", "newPassword");
        ResponseEntity<Void> responseEntity = authController.resetPassword(resetModel);

        // Assertions
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(authService, times(1)).resetPassword(anyString(), anyString());
    }


}
