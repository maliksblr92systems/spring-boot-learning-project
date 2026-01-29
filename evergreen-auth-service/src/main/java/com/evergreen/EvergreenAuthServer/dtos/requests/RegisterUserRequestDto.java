    package com.evergreen.EvergreenAuthServer.dtos.requests;

    import jakarta.validation.constraints.Email;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.NotNull;
    import lombok.*;


    @Data
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class RegisterUserRequestDto {

        @NotNull
        @Email
        private String email;
        @NotBlank
        private String password;
        @NotBlank
        private String confirmPassword;

    }
