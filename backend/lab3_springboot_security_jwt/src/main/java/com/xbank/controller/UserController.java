package com.xbank.controller;

import com.xbank.annotation.CurrentUser;
import com.xbank.event.OnUserAccountChangeEvent;
import com.xbank.event.OnUserLogoutSuccessEvent;
import com.xbank.exception.UpdatePasswordException;
import com.xbank.model.CustomUserDetails;
import com.xbank.model.User;
import com.xbank.model.dto.ApiResponse;
import com.xbank.model.dto.LogOutRequest;
import com.xbank.model.dto.UpdatePasswordRequest;
import com.xbank.repository.UserRepositoryCustomR2dpc;
import com.xbank.service.AuthService;
import com.xbank.service.FileService;
import com.xbank.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@Api(value = "User Rest API")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    private final AuthService authService;

    private final UserService userService;

    private final FileService fileService;

    private final UserRepositoryCustomR2dpc userRepositoryCustom;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public UserController(AuthService authService, UserService userService,
                          ApplicationEventPublisher applicationEventPublisher, FileService fileService, UserRepositoryCustomR2dpc userRepositoryCustom) {
        this.authService = authService;
        this.userService = userService;
        this.fileService = fileService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.userRepositoryCustom = userRepositoryCustom;
    }

    @GetMapping("/all")
    @ApiOperation(value = "Returns the current user profile")
    public ResponseEntity getAll(@CurrentUser CustomUserDetails currentUser) {
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * Gets the current user profile of the logged in user
     */
    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @ApiOperation(value = "Returns the current user profile")
    public ResponseEntity getUserProfile(@CurrentUser CustomUserDetails currentUser) {
        Mono<User> userMono = userRepositoryCustom.findOneByUsername("hoaronal");
        logger.info(currentUser.getEmail() + " has role: " + currentUser.getRoles());
        return ResponseEntity.ok("Hello. This is about me");
    }

    /**
     * Returns all admins in the system. Requires Admin access
     */
    @GetMapping("/admins")
    @PreAuthorize("hasRole('ROLE_BANK_OPERATOR')")
    @ApiOperation(value = "Returns the list of configured admins. Requires ADMIN Access")
    public ResponseEntity getAllAdmins() {
        logger.info("Inside secured resource with admin");
        return ResponseEntity.ok("Hello. This is about admins");
    }

    /**
     * Updates the password of the current logged in user
     */
    @PostMapping("/password/update")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @ApiOperation(value = "Allows the user to change his password once logged in by supplying the correct current " +
            "password")
    public ResponseEntity updateUserPassword(@CurrentUser CustomUserDetails customUserDetails,
                                             @ApiParam(value = "The UpdatePasswordRequest payload") @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {

        return authService.updatePassword(customUserDetails, updatePasswordRequest)
                .map(updatedUser -> {
                    OnUserAccountChangeEvent onUserPasswordChangeEvent = new OnUserAccountChangeEvent(updatedUser, "Update Password", "Change successful");
                    applicationEventPublisher.publishEvent(onUserPasswordChangeEvent);
                    return ResponseEntity.ok(new ApiResponse(true, "Password changed successfully"));
                })
                .orElseThrow(() -> new UpdatePasswordException("--Empty--", "No such user present."));
    }

    /**
     * Log the user out from the app/device. Release the refresh token associated with the
     * user device.
     */
    @PostMapping("/logout")
    @ApiOperation(value = "Logs the specified user device and clears the refresh tokens associated with it")
    public ResponseEntity logoutUser(@CurrentUser CustomUserDetails customUserDetails,
                                     @ApiParam(value = "The LogOutRequest payload") @Valid @RequestBody LogOutRequest logOutRequest) {
        userService.logoutUser(customUserDetails, logOutRequest);
        Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();

        OnUserLogoutSuccessEvent logoutSuccessEvent = new OnUserLogoutSuccessEvent(customUserDetails.getEmail(), credentials.toString(), logOutRequest);
        applicationEventPublisher.publishEvent(logoutSuccessEvent);
        return ResponseEntity.ok(new ApiResponse(true, "Log out successful"));
    }

    @PostMapping("/avatar")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @ApiOperation(value = "Allows the user to change his avatar")
    public ResponseEntity changeAvatar(@CurrentUser CustomUserDetails customUser,
                                     @ApiParam(value = "The Avatar image") MultipartFile fileAvatar) {
        logger.info(customUser.getEmail() + " has role: " + customUser.getRoles());
        userService.updateAvatar(customUser, fileAvatar);
        return ResponseEntity.ok(new ApiResponse(true, "Update avatar success"));
    }
}
