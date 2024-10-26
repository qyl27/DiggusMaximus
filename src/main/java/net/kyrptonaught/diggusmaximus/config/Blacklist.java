package net.kyrptonaught.diggusmaximus.config;

import blue.endless.jankson.Comment;
import net.kyrptonaught.kyrptconfig.TagHelper;
import net.kyrptonaught.kyrptconfig.config.AbstractConfigFile;
import net.minecraft.resources.ResourceLocation;
import java.util.HashSet;

public class Blacklist implements AbstractConfigFile {
    @Comment("Function as whitelist instead")
    public boolean isWhitelist = false;

    @Comment("Block IDs to blacklist from being mined")
    public HashSet<String> blacklistedBlocks = new HashSet<>();

    public transient HashSet<String> lookup = new HashSet<>();

    public void generateLookup() {
        lookup.clear();
        blacklistedBlocks.forEach(entry -> {
            if (entry.startsWith("#")) {
                entry = entry.replaceAll("#", "");

                TagHelper.getBlockIDsInTag(ResourceLocation.parse(entry)).forEach(identifier ->
                        lookup.add(identifier.toString()));

            } else {
                lookup.add(ResourceLocation.parse(entry).toString());
            }
        });
    }
}