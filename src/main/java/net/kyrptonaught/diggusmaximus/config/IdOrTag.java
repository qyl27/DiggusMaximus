package net.kyrptonaught.diggusmaximus.config;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public abstract class IdOrTag<T> {
    private final ResourceKey<Registry<T>> registry;
    private final String value;

    public IdOrTag(ResourceKey<Registry<T>> registry, String value) {
        this.registry = registry;
        this.value = value;
    }

    public String getString() {
        return value;
    }

    public abstract boolean is(@NotNull Holder<T> obj);

    @Override
    public int hashCode() {
        return (registry.identifier() + getString()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj instanceof IdOrTag<?> other) {
            return registry.identifier().equals(other.registry.identifier()) && getString().equals(other.getString());
        }
        return false;
    }

    public static class Id<T> extends IdOrTag<T> {
        protected final ResourceKey<T> key;

        public Id(ResourceKey<Registry<T>> registry, String value, ResourceKey<T> key) {
            super(registry, value);
            this.key = key;
        }

        @Override
        public boolean is(@NotNull Holder<T> obj) {
            return obj.is(key);
        }
    }

    public static class Tag<T> extends IdOrTag<T> {
        protected final TagKey<T> key;

        public Tag(ResourceKey<Registry<T>> registry, String value, TagKey<T> key) {
            super(registry, value);
            this.key = key;
        }

        @Override
        public boolean is(@NotNull Holder<T> obj) {
            return obj.is(key);
        }
    }

    public static <T> IdOrTag<T> from(ResourceKey<Registry<T>> registry, String str) {
        if (str.startsWith("#")) {
            var rl = Identifier.parse(str.substring(1));
            var key = TagKey.create(registry, rl);
            return new Tag<>(registry, str, key);
        } else {
            var rl = Identifier.parse(str);
            var key = ResourceKey.create(registry, rl);
            return new Id<>(registry, str, key);
        }
    }

    public static IdOrTag<Block> blockFrom(String str) {
        return from(Registries.BLOCK, str);
    }

    public static IdOrTag<Item> itemFrom(String str) {
        return from(Registries.ITEM, str);
    }
}
