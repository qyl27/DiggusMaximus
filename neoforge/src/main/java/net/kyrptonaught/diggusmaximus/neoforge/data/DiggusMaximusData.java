package net.kyrptonaught.diggusmaximus.neoforge.data;

import net.kyrptonaught.diggusmaximus.ModConstants;
import net.kyrptonaught.diggusmaximus.neoforge.data.lang.EnUsProvider;
import net.kyrptonaught.diggusmaximus.neoforge.data.lang.ZhCnProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = ModConstants.MOD_ID)
public class DiggusMaximusData {
    @SubscribeEvent
    public void onInitializeDataGenerator(GatherDataEvent.Client event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        generator.addProvider(true, new ZhCnProvider(output));
        generator.addProvider(true, new EnUsProvider(output));
    }
}
