package net.kyrptonaught.diggusmaximus.neoforge.data.lang;

import net.kyrptonaught.diggusmaximus.ModConstants;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import static net.kyrptonaught.diggusmaximus.neoforge.data.lang.LangProviderHelper.*;

public class EnUsProvider extends LanguageProvider {
    public EnUsProvider(PackOutput output) {
        super(output, ModConstants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ModConstants.KEY_CATEGORY, "Diggus Maximus");
        add(ModConstants.KEY_EXCAVATE, "Excavate");
        add(ModConstants.KEY_SHAPED, "Shape Excavate");
        add(ModConstants.KEY_CYCLE_SHAPE, "Cycle Shape");

        add(buildAutoConfigTitle(), "Diggus Maximus Settings");
        add(buildAutoConfigCategory("config"), "Options");
        add(buildAutoConfigKey("config", "enabled"), "Enabled");
        add(buildAutoConfigKey("config", "invertActivation"), "Invert Activation");
        add(buildAutoConfigKey("config", "sneakToExcavate"), "Sneaking triggers excavating (serverside)");
        add(buildAutoConfigKey("config", "mineDiag"), "Mine blocks diagonally, excludes shapes");
        add(buildAutoConfigKey("config", "maxMinedBlocks"), "Max mined blocks");
        add(buildAutoConfigKey("config", "maxMineDistance"), "Max distance");
        add(buildAutoConfigKey("config", "autoPickup"), "Auto Pickup");
        add(buildAutoConfigKey("config", "requiresTool"), "Requires tool");
        add(buildAutoConfigKey("config", "dontBreakTool"), "Save the last durability for tool");
        add(buildAutoConfigKey("config", "stopOnToolBreak"), "Stop on tool breakage");
        add(buildAutoConfigKey("config", "toolDurability"), "Tool durability");
        add(buildAutoConfigKey("config", "playerExhaustion"), "Player Exhaustion");
        add(buildAutoConfigKey("config", "exhaustionMultiplier"), "Exhaustion Multiplier");
        add(buildAutoConfigKey("config", "tools"), "List of other tools (both ID and Tag supported)");

        add(buildAutoConfigCategory("blacklist"), "Blocklist");
        add(buildAutoConfigKey("blockList", "isWhitelist"), "Use as Whitelist");
        add(buildAutoConfigKey("blockList", "blacklistedBlocks"), "Listed Blocks (both ID and Tag supported)");

        add(buildAutoConfigCategory("grouping"), "Block Grouping");
        add(buildAutoConfigKey("grouping", "customGrouping"), "Enable custom block grouping");
        add(buildAutoConfigKey("grouping", "groups"), "Blocks groups (a group per line, separated by comma, both ID and Tag supported)");

        add(buildAutoConfigCategory("excavatingshapes"), "Shape Excavating");
        add(buildAutoConfigKey("shapes", "enableShapes"), "Enable Shape Excavating");
        add(buildAutoConfigKey("shapes", "includeDifBlocks"), "Include Different Blocks");
        add(buildAutoConfigKey("shapes", "selectedShape"), "Currently Selected Shape");

        add(ModConstants.SHAPE_NONE, "None");

        add(ModConstants.SHAPE_HORIZONTAL_LAYER, "Horizontal Layer");
        add(ModConstants.SHAPE_LAYER, "Layer");
        add(ModConstants.SHAPE_HOLE, "Hole");
        add(ModConstants.SHAPE_ONE_BY_TWO, "1x2");
        add(ModConstants.SHAPE_ONE_BY_TWO_TUNNEL, "1x2 Tunnel");
        add(ModConstants.SHAPE_THREE_BY_THREE, "3x3");
        add(ModConstants.SHAPE_THREE_BY_THREE_TUNNEL, "3x3 Tunnel");
    }
}
