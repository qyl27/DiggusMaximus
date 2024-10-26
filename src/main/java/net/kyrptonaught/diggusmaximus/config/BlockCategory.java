package net.kyrptonaught.diggusmaximus.config;

import blue.endless.jankson.Comment;
import net.kyrptonaught.kyrptconfig.TagHelper;
import net.kyrptonaught.kyrptconfig.config.AbstractConfigFile;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BlockCategory implements AbstractConfigFile {
    @Comment("Enable block custom grouping")
    public boolean customGrouping = false;
    @Comment("BlockID to be considered the same block when excavating (IDs separated by commas)")
    public List<String> groups = new ArrayList<>();

    public transient HashMap<ResourceLocation, ResourceLocation> lookup = new HashMap<>();

    public void generateLookup() {
        lookup.clear();
        groups.forEach(group -> {
            List<ResourceLocation> expanded = new ArrayList<>();
            for (String item : group.split(",")) {
                if (item.startsWith("#")) {
                    expanded.addAll(TagHelper.getBlockIDsInTag(ResourceLocation.parse(item.replaceAll("#", ""))));
                } else {
                    expanded.add(ResourceLocation.tryParse(item));
                }
            }

            for (int i = 1; i < expanded.size(); i++) {
                lookup.put(expanded.get(i), expanded.get(0));
            }
        });
    }
}
