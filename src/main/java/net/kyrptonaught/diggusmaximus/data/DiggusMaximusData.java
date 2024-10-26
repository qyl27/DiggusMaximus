package net.kyrptonaught.diggusmaximus.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.kyrptonaught.diggusmaximus.data.lang.EnUsProvider;
import net.kyrptonaught.diggusmaximus.data.lang.ZhCnProvider;

public class DiggusMaximusData implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        var pack = generator.createPack();
        pack.addProvider(ZhCnProvider::new);
        pack.addProvider(EnUsProvider::new);
    }
}
