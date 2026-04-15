package com.marlonreina.discord.api.domain.model;

public class DiscordGuild {

    private String id;
    private String name;
    private String icon;
    private String banner;
    private boolean owner;
    private long permissions;

    public DiscordGuild(String id, String name, String icon, String banner, boolean owner, long permissions) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.banner = banner;
        this.owner = owner;
        this.permissions = permissions;
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

    public boolean isOwner() {
        return owner;
    }

    public long getPermissions() {
        return permissions;
    }
}