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
        add(ModConstants.KEY_CATEGORY, "Diggus Maximus Reborn");
        add(ModConstants.KEY_EXCAVATE, "Excavate");
        add(ModConstants.KEY_SHAPED, "Shape Excavate");
        add(ModConstants.KEY_CYCLE_SHAPE, "Cycle Shape");

        add(buildAutoConfigTitle(), "Diggus Maximus Options");
        add(buildAutoConfigCategory("common"), "Common");
        add(buildAutoConfigKey("common", "enabled"), "Enabled");
        add(buildAutoConfigKey("common", "sneakToExcavate"), "Sneak to Excavate (Server Side)");
        add(buildAutoConfigKeyTooltip("common", "sneakToExcavate"), "For clients without the mod installed");
        add(buildAutoConfigKey("common", "diagonallyMine"), "Mine blocks diagonally");
        add(buildAutoConfigKeyTooltip("common", "diagonallyMine"), "Excavated blocks take on a more regular cubic form.");
        add(buildAutoConfigKey("common", "maxMinedBlocks"), "Max Mined Blocks per Excavation");
        add(buildAutoConfigKey("common", "maxMineDistance"), "Max Excavation Distance");
        add(buildAutoConfigKeyTooltip("common", "maxMineDistance"), "Distance from player to the block being excavated");
        add(buildAutoConfigKey("common", "autoPickup"), "Auto Pickup");
        add(buildAutoConfigKey("common", "requiresTool"), "Requires Tool");
        add(buildAutoConfigKey("common", "stopBeforeToolBroken"), "Stop Before Tool Breaks");
        add(buildAutoConfigKeyTooltip("common", "stopBeforeToolBroken"), "Leaves 1 durability for your tool");
        add(buildAutoConfigKey("common", "stopAfterToolBroken"), "Stop After Tool Breaks");
        add(buildAutoConfigKeyTooltip("common", "stopAfterToolBroken"), "Prevent lost any drops");
        add(buildAutoConfigKey("common", "causeToolDamage"), "Cause Tool Damage");
        add(buildAutoConfigKeyTooltip("common", "causeToolDamage"), "Decreases tool durability");
        add(buildAutoConfigKey("common", "causePlayerExhaustion"), "Cause Player Exhaustion");
        add(buildAutoConfigKeyTooltip("common", "causePlayerExhaustion"), "Increases hunger when excavating");
        add(buildAutoConfigKey("common", "exhaustionMultiplier"), "Exhaustion Multiplier");
        add(buildAutoConfigKey("common", "tools"), "Additional Tool List (items in list are treated as tools)");
        add(buildAutoConfigKeyTooltip("common", "tools"), "Supports IDs and Tags (starting with #), e.g. \"minecraft:stick\"");
        add(buildAutoConfigKey("common", "groups"), "Custom Block Groups (blocks in same group are treated as a same block)");
        add(buildAutoConfigKeyTooltip("common", "groups"), "One group per line, use commas to separate. Supports IDs and Tags");
        add(buildAutoConfigKey("common", "enableShapes"), "Enable Shape Excavation");
        add(buildAutoConfigKey("common", "shapeIgnoreIdMismatch"), "Allow Different Block in Shape Mining");
        add(buildAutoConfigKeyTooltip("common", "shapeIgnoreIdMismatch"), "Allows excavation even if block IDs don't match");
        add(buildAutoConfigKey("common", "blocklistedBlocks"), "Blocklisted blocks");
        add(buildAutoConfigKeyTooltip("common", "blocklistedBlocks"), "Supports IDs and Tags");
        add(buildAutoConfigKey("common", "asAllowlist"), "Use Allowlist Mode");
        add(buildAutoConfigKeyTooltip("common", "asAllowlist"), "Changes to an ‘allowed blocks’ list");

        add(buildAutoConfigCategory("client"), "Client");
        add(buildAutoConfigKey("client", "invertActivation"), "Invert Activation");
        add(buildAutoConfigKeyTooltip("client", "invertActivation"), "Change activation to ‘not excavate while key is held’");
        add(buildAutoConfigKey("client", "selectedShape"), "Selected Shape");
        add(buildAutoConfigKeyTooltip("client", "selectedShape"), "Can also be switched in-game via hotkey");
        add(buildAutoConfigKey("client", "stopBeforeToolBroken"), "Stop Before Tool Breaks");
        add(buildAutoConfigKeyTooltip("client", "stopBeforeToolBroken"), "Leaves 1 durability for your tool (client setting overrides common setting)");
        add(buildAutoConfigKey("client", "stopAfterToolBroken"), "Stop After Tool Breaks");
        add(buildAutoConfigKeyTooltip("client", "stopAfterToolBroken"), "Prevent lost any drops (client setting overrides common setting)");

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
