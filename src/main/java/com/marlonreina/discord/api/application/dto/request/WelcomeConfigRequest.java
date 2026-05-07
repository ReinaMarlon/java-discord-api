package com.marlonreina.discord.api.application.dto.request;

import tools.jackson.databind.JsonNode;

public class WelcomeConfigRequest {

    private Integer version;
    private String module;
    private Boolean enabled;
    private String guildId;
    private String channelId;
    private String renderMode;
    private Boolean premiumMedia;
    private JsonNode template;
    private JsonNode design;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getRenderMode() {
        return renderMode;
    }

    public void setRenderMode(String renderMode) {
        this.renderMode = renderMode;
    }

    public Boolean getPremiumMedia() {
        return premiumMedia;
    }

    public void setPremiumMedia(Boolean premiumMedia) {
        this.premiumMedia = premiumMedia;
    }

    public JsonNode getTemplate() {
        return template;
    }

    public void setTemplate(JsonNode template) {
        this.template = template;
    }

    public JsonNode getDesign() {
        return design;
    }

    public void setDesign(JsonNode design) {
        this.design = design;
    }
}
