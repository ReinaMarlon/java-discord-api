package com.marlonreina.discord.api.domain.model;

public class DiscordChannel {

    private final String id;
    private final String name;
    private final int type;

    public DiscordChannel(String id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }
}
