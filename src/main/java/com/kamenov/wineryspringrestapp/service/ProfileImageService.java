package com.kamenov.wineryspringrestapp.service;

import com.kamenov.wineryspringrestapp.models.entity.ProfileImage;
import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileImageService {
    ProfileImage saveProfileImage(MultipartFile multipartFile, UserEntity user);

    ProfileImage getDefaultProfileImage();

    ProfileImage save(ProfileImage profileImage);
}
