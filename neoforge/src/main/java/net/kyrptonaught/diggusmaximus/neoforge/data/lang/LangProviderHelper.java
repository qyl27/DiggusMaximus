package net.kyrptonaught.diggusmaximus.neoforge.data.lang;

import net.kyrptonaught.diggusmaximus.ModConstants;

public class LangProviderHelper {
    public static String buildAutoConfigTitle() {
        return "text.autoconfig." + ModConstants.MOD_ID + ".title";
    }

    public static String buildAutoConfigCategory(String cateName) {
        return "text.autoconfig." + ModConstants.MOD_ID + ".category." + cateName;
    }

    public static String buildAutoConfigKey(String cateName, String fieldName) {
        return "text.autoconfig." + ModConstants.MOD_ID + ".option." + cateName + "." + fieldName;
    }

    public static String buildAutoConfigKeyTooltip(String cateName, String fieldName) {
        return "text.autoconfig." + ModConstants.MOD_ID + ".option." + cateName + "." + fieldName + ".@Tooltip";
    }

    public static String buildAutoConfigKeyTooltip(String cateName, String fieldName, int id) {
        return "text.autoconfig." + ModConstants.MOD_ID + ".option." + cateName + "." + fieldName + ".@Tooltip[" + id + "]";
    }
}
