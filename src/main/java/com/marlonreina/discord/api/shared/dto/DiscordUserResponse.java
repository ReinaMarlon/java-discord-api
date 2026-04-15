package com.marlonreina.discord.api.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscordUserResponse {

    @JsonProperty("id")
    private String id;

    private String username;
    private String avatar;
    private String email;

    @JsonProperty("global_name")
    private String globalName;
}