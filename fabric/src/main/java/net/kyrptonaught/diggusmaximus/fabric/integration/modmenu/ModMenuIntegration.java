package net.kyrptonaught.diggusmaximus.fabric.integration.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kyrptonaught.diggusmaximus.client.ConfigClientHelper;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ConfigClientHelper::getConfigScreen;
    }
}
