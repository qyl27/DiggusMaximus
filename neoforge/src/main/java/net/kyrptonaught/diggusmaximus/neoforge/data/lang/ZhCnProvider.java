package net.kyrptonaught.diggusmaximus.neoforge.data.lang;

import net.kyrptonaught.diggusmaximus.ModConstants;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import static net.kyrptonaught.diggusmaximus.neoforge.data.lang.LangProviderHelper.*;

public class ZhCnProvider extends LanguageProvider {
    public ZhCnProvider(PackOutput output) {
        super(output, ModConstants.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add(ModConstants.KEY_CATEGORY, "连锁挖掘 §7(Diggus Maximus)");
        add(ModConstants.KEY_EXCAVATE, "激活连锁挖掘");
        add(ModConstants.KEY_SHAPED, "形状连锁挖掘");
        add(ModConstants.KEY_CYCLE_SHAPE, "改变形状");

        add(buildAutoConfigTitle(), "连锁挖掘选项");
        add(buildAutoConfigCategory("config"), "选项");
        add(buildAutoConfigKey("config", "enabled"), "启用连锁挖掘");
        add(buildAutoConfigKey("config", "invertActivation"), "反向激活");
        add(buildAutoConfigKey("config", "sneakToExcavate"), "潜行时触发（服务端）");
        add(buildAutoConfigKey("config", "mineDiag"), "对角挖掘（挖掘形状变为更规整的立方体）");
        add(buildAutoConfigKey("config", "maxMinedBlocks"), "单次最大挖掘方块数");
        add(buildAutoConfigKey("config", "maxMineDistance"), "最远连锁距离");
        add(buildAutoConfigKey("config", "autoPickup"), "自动拾取");
        add(buildAutoConfigKey("config", "requiresTool"), "需要工具");
        add(buildAutoConfigKey("config", "dontBreakTool"), "为工具保留一格耐久");
        add(buildAutoConfigKey("config", "stopOnToolBreak"), "在工具损坏后停止挖掘");
        add(buildAutoConfigKey("config", "toolDurability"), "减少工具耐久度");
        add(buildAutoConfigKey("config", "playerExhaustion"), "增加玩家饥饿等级（加速消耗饱食度与饱和度）");
        add(buildAutoConfigKey("config", "exhaustionMultiplier"), "饥饿等级倍率");
        add(buildAutoConfigKey("config", "tools"), "额外工具列表（支持Tag和ID）");

        add(buildAutoConfigCategory("blacklist"), "方块黑名单");
        add(buildAutoConfigKey("blockList", "isWhitelist"), "换用白名单模式");
        add(buildAutoConfigKey("blockList", "blacklistedBlocks"), "方块名单（支持Tag和ID）");

        add(buildAutoConfigCategory("grouping"), "自定义方块组别");
        add(buildAutoConfigKey("grouping", "customGrouping"), "启用自定义方块组别");
        add(buildAutoConfigKey("grouping", "groups"), "方块组（一行一组，使用英文逗号分隔，支持Tag和ID）");

        add(buildAutoConfigCategory("excavatingshapes"), "形状挖掘");
        add(buildAutoConfigKey("shapes", "enableShapes"), "启用形状挖掘");
        add(buildAutoConfigKey("shapes", "includeDifBlocks"), "包含不同的方块");
        add(buildAutoConfigKey("shapes", "selectedShape"), "当前选择的形状");

        add(ModConstants.SHAPE_NONE, "无");

        add(ModConstants.SHAPE_HORIZONTAL_LAYER, "水平层");
        add(ModConstants.SHAPE_LAYER, "层");
        add(ModConstants.SHAPE_HOLE, "洞");
        add(ModConstants.SHAPE_ONE_BY_TWO, "1x2");
        add(ModConstants.SHAPE_ONE_BY_TWO_TUNNEL, "1x2 隧道");
        add(ModConstants.SHAPE_THREE_BY_THREE, "3x3");
        add(ModConstants.SHAPE_THREE_BY_THREE_TUNNEL, "3x3 隧道");
    }
}
