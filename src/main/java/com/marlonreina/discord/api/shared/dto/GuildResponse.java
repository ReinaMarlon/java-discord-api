package com.marlonreina.discord.api.shared.dto;

public class GuildResponse {

    private final String id;
    private final String name;
    private final String icon;
    private final String banner;

    public GuildResponse(String id, String name, String icon, String banner) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.banner = banner;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getBanner() {
        return banner;
    }
}
