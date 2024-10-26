package net.kyrptonaught.diggusmaximus.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.kyrptonaught.diggusmaximus.DiggusMaximusMod;
import net.kyrptonaught.kyrptconfig.keybinding.CustomKeyBinding;

public class DiggusKeyBinding extends CustomKeyBinding {
    public boolean respectsInvert;

    public DiggusKeyBinding(boolean respectsInvert, boolean unknownIsActivated, String defaultKey) {
        super(DiggusMaximusMod.MOD_ID, unknownIsActivated);
        this.respectsInvert = respectsInvert;
        this.defaultKey = defaultKey;
        this.rawKey = defaultKey;
    }

    private DiggusKeyBinding(String defaultKey) {
        this(false, false, defaultKey);
    }

    public DiggusKeyBinding copyKeyFrom(CustomKeyBinding other) {
        setRaw(other.rawKey);
        return this;
    }

    public boolean isKeybindPressed() {
        boolean pressed = super.isKeybindPressed();
        if (parsedKey == null) {
            return false;    // Invalid key
        }
        if (parsedKey == InputConstants.UNKNOWN) {
            return unknownIsActivated; // Always pressed for empty or explicitly "key.keyboard.unknown"
        }
        if (respectsInvert && DiggusMaximusMod.getOptions().invertActivation) {
            return !pressed;
        }
        return pressed;
    }
}
