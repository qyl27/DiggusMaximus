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
        add(ModConstants.KEY_CATEGORY, "连锁挖掘 §7(Diggus Maximus Reborn)");
        add(ModConstants.KEY_EXCAVATE, "激活连锁挖掘");
        add(ModConstants.KEY_SHAPED, "形状连锁挖掘");
        add(ModConstants.KEY_CYCLE_SHAPE, "改变形状");

        add(buildAutoConfigTitle(), "连锁挖掘选项");
        add(buildAutoConfigCategory("common"), "通用设置");
        add(buildAutoConfigKey("common", "enabled"), "启用连锁挖掘");
        add(buildAutoConfigKey("common", "sneakToExcavate"), "潜行时触发（服务端）");
        add(buildAutoConfigKeyTooltip("common", "sneakToExcavate"), "可以允许未安装模组的客户端使用");
        add(buildAutoConfigKey("common", "diagonallyMine"), "对角挖掘");
        add(buildAutoConfigKeyTooltip("common", "diagonallyMine"), "挖掘形状变为更规整的立方体");
        add(buildAutoConfigKey("common", "maxMinedBlocks"), "单次最大挖掘方块数");
        add(buildAutoConfigKey("common", "maxMineDistance"), "最远连锁距离");
        add(buildAutoConfigKeyTooltip("common", "maxMineDistance"), "从玩家当前位置到被挖掘的方块位置");
        add(buildAutoConfigKey("common", "autoPickup"), "自动拾取");
        add(buildAutoConfigKey("common", "requiresTool"), "需要工具");
        add(buildAutoConfigKey("common", "stopBeforeToolBroken"), "在工具损坏前停止挖掘");
        add(buildAutoConfigKeyTooltip("common", "stopBeforeToolBroken"), "为工具保留一格耐久");
        add(buildAutoConfigKey("common", "stopAfterToolBroken"), "在工具损坏后停止挖掘");
        add(buildAutoConfigKeyTooltip("common", "stopAfterToolBroken"), "防止挖坏方块");
        add(buildAutoConfigKey("common", "causeToolDamage"), "消耗工具");
        add(buildAutoConfigKeyTooltip("common", "causeToolDamage"), "会减少工具耐久度");
        add(buildAutoConfigKey("common", "causePlayerExhaustion"), "消耗玩家饱食度");
        add(buildAutoConfigKeyTooltip("common", "causePlayerExhaustion"), "增加玩家饥饿等级");
        add(buildAutoConfigKey("common", "exhaustionMultiplier"), "饱食度消耗倍率");
        add(buildAutoConfigKey("common", "tools"), "额外工具列表（在列表中的物品也被视为挖掘工具）");
        add(buildAutoConfigKeyTooltip("common", "tools"), "支持ID和Tag（以#开头的），例如：\"minecraft:stick\"");
        add(buildAutoConfigKey("common", "groups"), "自定义方块组别（在同一组中的视为同种方块）");
        add(buildAutoConfigKeyTooltip("common", "groups"), "一行一组，使用英文逗号\",\"分隔，支持ID和Tag");
        add(buildAutoConfigKey("common", "enableShapes"), "启用形状挖掘");
        add(buildAutoConfigKey("common", "shapeIgnoreIdMismatch"), "允许形状挖掘包含不同的方块");
        add(buildAutoConfigKeyTooltip("common", "shapeIgnoreIdMismatch"), "在方块ID不同的时候依然按形状挖掘");
        add(buildAutoConfigKey("common", "blocklistedBlocks"), "禁止连锁列表");
        add(buildAutoConfigKeyTooltip("common", "blocklistedBlocks"), "禁止列表中的方块被连锁，支持ID和Tag");
        add(buildAutoConfigKey("common", "asAllowlist"), "↑换用白名单模式");
        add(buildAutoConfigKeyTooltip("common", "asAllowlist"), "只有列表中的方块才能被连锁");

        add(buildAutoConfigCategory("client"), "客户端设置");
        add(buildAutoConfigKey("client", "invertActivation"), "反向激活");
        add(buildAutoConfigKeyTooltip("client", "invertActivation"), "由按下热键连锁变为按下热键不连锁");
        add(buildAutoConfigKey("client", "selectedShape"), "当前选择的形状");
        add(buildAutoConfigKeyTooltip("client", "selectedShape"), "也可以在游戏中按热键切换");
        add(buildAutoConfigKey("client", "stopBeforeToolBroken"), "在工具损坏前停止挖掘");
        add(buildAutoConfigKeyTooltip("client", "stopBeforeToolBroken"), "为工具保留一格耐久（客户端偏好设置优先于通用设置）");
        add(buildAutoConfigKey("client", "stopAfterToolBroken"), "在工具损坏后停止挖掘");
        add(buildAutoConfigKeyTooltip("client", "stopAfterToolBroken"), "防止挖坏方块（客户端偏好设置优先于通用设置）");

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
