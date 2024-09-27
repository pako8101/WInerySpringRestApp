package com.kamenov.wineryspringrestapp.service;

import com.kamenov.wineryspringrestapp.models.dto.ReCaptchaResponseDTO;

import java.util.Optional;

public interface RecaptchaService {
    Optional<ReCaptchaResponseDTO> verify(String token);
}
