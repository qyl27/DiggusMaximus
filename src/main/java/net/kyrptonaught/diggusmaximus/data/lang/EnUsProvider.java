package net.kyrptonaught.diggusmaximus.data.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.kyrptonaught.diggusmaximus.ModConstants;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class EnUsProvider extends FabricLanguageProvider {
    public EnUsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(dataOutput, "en_us", registries);
    }

    private static String buildAutoConfigTitle() {
        return "text.autoconfig." + ModConstants.MOD_ID + ".title";
    }

    private static String buildAutoConfigCategory(String cateName) {
        return "text.autoconfig." + ModConstants.MOD_ID + ".category." + cateName;
    }

    private static String buildAutoConfigKey(String cateName, String fieldName) {
        return "text.autoconfig." + ModConstants.MOD_ID + ".option." + cateName + "." + fieldName;
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder builder) {
        builder.add(ModConstants.KEY_CATEGORY, "Diggus Maximus");
        builder.add(ModConstants.KEY_EXCAVATE, "Excavate");
        builder.add(ModConstants.KEY_SHAPED, "Shape Excavate");
        builder.add(ModConstants.KEY_CYCLE_SHAPE, "Cycle Shape");

        builder.add(buildAutoConfigTitle(), "Diggus Maximus Settings");
        builder.add(buildAutoConfigCategory("config"), "Options");
        builder.add(buildAutoConfigKey("config", "enabled"), "Enabled");
        builder.add(buildAutoConfigKey("config", "invertActivation"), "Invert Activation");
        builder.add(buildAutoConfigKey("config", "sneakToExcavate"), "Sneaking triggers excavating (serverside)");
        builder.add(buildAutoConfigKey("config", "mineDiag"), "Mine blocks diagonally, excludes shapes");
        builder.add(buildAutoConfigKey("config", "maxMinedBlocks"), "Max mined blocks");
        builder.add(buildAutoConfigKey("config", "maxMineDistance"), "Max distance");
        builder.add(buildAutoConfigKey("config", "autoPickup"), "Auto Pickup");
        builder.add(buildAutoConfigKey("config", "requiresTool"), "Requires tool");
        builder.add(buildAutoConfigKey("config", "dontBreakTool"), "Save the last durability for tool");
        builder.add(buildAutoConfigKey("config", "stopOnToolBreak"), "Stop on tool breakage");
        builder.add(buildAutoConfigKey("config", "toolDurability"), "Tool durability");
        builder.add(buildAutoConfigKey("config", "playerExhaustion"), "Player Exhaustion");
        builder.add(buildAutoConfigKey("config", "exhaustionMultiplier"), "Exhaustion Multiplier");
        builder.add(buildAutoConfigKey("config", "tools"), "List of other tools (both ID and Tag supported)");

        builder.add(buildAutoConfigCategory("blacklist"), "Blocklist");
        builder.add(buildAutoConfigKey("blockList", "isWhitelist"), "Use as Whitelist");
        builder.add(buildAutoConfigKey("blockList", "blacklistedBlocks"), "Listed Blocks (both ID and Tag supported)");

        builder.add(buildAutoConfigCategory("grouping"), "Block Grouping");
        builder.add(buildAutoConfigKey("grouping", "customGrouping"), "Enable custom block grouping");
        builder.add(buildAutoConfigKey("grouping", "groups"), "Blocks groups (a group per line, separated by comma, both ID and Tag supported)");

        builder.add(buildAutoConfigCategory("excavatingshapes"), "Shape Excavating");
        builder.add(buildAutoConfigKey("shapes", "enableShapes"), "Enable Shape Excavating");
        builder.add(buildAutoConfigKey("shapes", "includeDifBlocks"), "Include Different Blocks");
        builder.add(buildAutoConfigKey("shapes", "selectedShape"), "Currently Selected Shape");

        builder.add("diggusmaximus.shape.horizontal_layer", "Horizontal Layer");
        builder.add("diggusmaximus.shape.layer", "Layer");
        builder.add("diggusmaximus.shape.hole", "Hole");
        builder.add("diggusmaximus.shape.one_by_two", "1x2");
        builder.add("diggusmaximus.shape.one_by_two_tunnel", "1x2 Tunnel");
        builder.add("diggusmaximus.shape.three_by_three", "3x3");
        builder.add("diggusmaximus.shape.three_by_three_tunnel", "3x3 Tunnel");
    }
}
