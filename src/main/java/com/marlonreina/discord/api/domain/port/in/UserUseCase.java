package com.marlonreina.discord.api.domain.port.in;

import com.marlonreina.discord.api.shared.dto.UserResponse;

public interface UserUseCase {

    UserResponse getUserData(String discordId);

}