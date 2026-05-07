package com.marlonreina.discord.api.shared.dto;

public class GuildResponse {

    private final String id;
    private final String name;
    private final String icon;
    private final String banner;
    private final boolean botPresent;

    public GuildResponse(String id, String name, String icon, String banner, boolean botPresent) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.banner = banner;
        this.botPresent = botPresent;
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

    public boolean isBotPresent() {
        return botPresent;
    }
}
