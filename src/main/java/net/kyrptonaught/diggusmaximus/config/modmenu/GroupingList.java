package net.kyrptonaught.diggusmaximus.config.modmenu;

import net.kyrptonaught.kyrptconfig.config.screen.NotSuckyButton;
import net.kyrptonaught.kyrptconfig.config.screen.items.lists.BlockIconList;
import net.kyrptonaught.kyrptconfig.config.screen.items.lists.StringList;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GroupingList extends StringList {

    public GroupingList(Component name, List<String> value, List<String> defaultValue) {
        super(name, value, defaultValue);
        this.configs = new CopyOnWriteArrayList<>();
        setValue(value);
        this.addButton = new NotSuckyButton(0, 0, 35, 20, Component.translatable("key.kyrptconfig.config.add"), widget -> {
            addConfigItem(createListEntry(""));
        });
        setToolTip();
    }

    public void setToolTip() {
        setToolTip(Component.translatable("key.kyrptconfig.config.hastags"),
                Component.translatable("key.kyrptconfig.config.tagsdisplay"));
    }

    @Override
    public void setValue(List<String> value) {
        configs.clear();
        super.setValue(value);
    }

    public void tick() {
        super.tick();
        for (int i = configs.size() - 1; i >= 0; i--) {
            if (configs.get(i) instanceof BlockIconListWDel
                    && ((BlockIconListWDel) configs.get(i)).scheduleToRemove) {
                configs.remove(i);
            }
        }
    }

    public void populateFromList() {
        for (String s : value) {
            addConfigItem(createListEntry(s));
        }
    }

    public StringList createListEntry(String string) {
        String[] blocks = string.split(",");
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = blocks[i].trim();
        }

        return new BlockIconListWDel(Component.translatable("key.diggusmaximus.config.customgroupinggroup"), List.of(blocks), new ArrayList<>());
    }

    public List<String> getNewValues() {
        List<String> newValues = new ArrayList<>();
        configs.forEach(configItem -> {
            if (configItem instanceof StringList stringListEntry) {
                List<String> result = stringListEntry.getNewValues();
                if (result != null && !result.isEmpty()) {
                    newValues.add(String.join(",", result));
                }
            }
        });
        return newValues;
    }

    public static class BlockIconListWDel extends BlockIconList {
        protected NotSuckyButton delButton;
        protected boolean scheduleToRemove = false;

        public BlockIconListWDel(Component name, List<String> value, List<String> defaultValue) {
            super(name, value, defaultValue, true);
            this.delButton = new NotSuckyButton(0, 0, 35, 20, Component.translatable("key.kyrptconfig.config.delete"), widget -> {
                this.scheduleToRemove = true;
            });
        }

        @Override
        public void mouseClicked(double mouseX, double mouseY, int button) {
            super.mouseClicked(mouseX, mouseY, button);
            delButton.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        public void render(GuiGraphics context, int x, int y, int mouseX, int mouseY, float delta) {
            super.render(context, x, y, mouseX, mouseY, delta);
            if (expanded) {
                this.delButton.setY(y);
                this.delButton.setX(addButton.getX() - (delButton.getWidth() / 2) - 20);
                this.delButton.render(context, mouseX, mouseY, delta);
            }
        }
    }
}
