package net.kyrptonaught.diggusmaximus.data.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.kyrptonaught.diggusmaximus.ModConstants;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ZhCnProvider extends FabricLanguageProvider {
    public ZhCnProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(dataOutput, "zh_cn", registries);
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
        builder.add(ModConstants.KEY_CATEGORY, "连锁挖掘 §7(Diggus Maximus)");
        builder.add(ModConstants.KEY_EXCAVATE, "激活连锁挖掘");
        builder.add(ModConstants.KEY_SHAPED, "形状连锁挖掘");
        builder.add(ModConstants.KEY_CYCLE_SHAPE, "改变形状");

        builder.add(buildAutoConfigTitle(), "连锁挖掘选项");
        builder.add(buildAutoConfigCategory("config"), "选项");
        builder.add(buildAutoConfigKey("config", "enabled"), "启用连锁挖掘");
        builder.add(buildAutoConfigKey("config", "invertActivation"), "反向激活");
        builder.add(buildAutoConfigKey("config", "sneakToExcavate"), "潜行时触发（服务端）");
        builder.add(buildAutoConfigKey("config", "mineDiag"), "对角挖掘（挖掘形状变为更规整的立方体）");
        builder.add(buildAutoConfigKey("config", "maxMinedBlocks"), "单次最大挖掘方块数");
        builder.add(buildAutoConfigKey("config", "maxMineDistance"), "最远连锁距离");
        builder.add(buildAutoConfigKey("config", "autoPickup"), "自动拾取");
        builder.add(buildAutoConfigKey("config", "requiresTool"), "需要工具");
        builder.add(buildAutoConfigKey("config", "dontBreakTool"), "为工具保留一格耐久");
        builder.add(buildAutoConfigKey("config", "stopOnToolBreak"), "在工具损坏后停止挖掘");
        builder.add(buildAutoConfigKey("config", "toolDurability"), "减少工具耐久度");
        builder.add(buildAutoConfigKey("config", "playerExhaustion"), "增加玩家饥饿等级（加速消耗饱食度与饱和度）");
        builder.add(buildAutoConfigKey("config", "exhaustionMultiplier"), "饥饿等级倍率");
        builder.add(buildAutoConfigKey("config", "tools"), "额外工具列表（支持Tag和ID）");

        builder.add(buildAutoConfigCategory("blacklist"), "方块黑名单");
        builder.add(buildAutoConfigKey("blockList", "isWhitelist"), "换用白名单模式");
        builder.add(buildAutoConfigKey("blockList", "blacklistedBlocks"), "方块名单（支持Tag和ID）");

        builder.add(buildAutoConfigCategory("grouping"), "自定义方块组别");
        builder.add(buildAutoConfigKey("grouping", "customGrouping"), "启用自定义方块组别");
        builder.add(buildAutoConfigKey("grouping", "groups"), "方块组（一行一组，使用英文逗号分隔，支持Tag和ID）");

        builder.add(buildAutoConfigCategory("excavatingshapes"), "形状挖掘");
        builder.add(buildAutoConfigKey("shapes", "enableShapes"), "启用形状挖掘");
        builder.add(buildAutoConfigKey("shapes", "includeDifBlocks"), "包含不同的方块");
        builder.add(buildAutoConfigKey("shapes", "selectedShape"), "当前选择的形状");

        builder.add("diggusmaximus.shape.horizontal_layer", "水平层");
        builder.add("diggusmaximus.shape.layer", "层");
        builder.add("diggusmaximus.shape.hole", "洞");
        builder.add("diggusmaximus.shape.one_by_two", "1x2");
        builder.add("diggusmaximus.shape.one_by_two_tunnel", "1x2 隧道");
        builder.add("diggusmaximus.shape.three_by_three", "3x3");
        builder.add("diggusmaximus.shape.three_by_three_tunnel", "3x3 隧道");
    }
}
