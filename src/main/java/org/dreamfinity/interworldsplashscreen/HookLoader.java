package org.dreamfinity.interworldsplashscreen;

import org.dreamfinity.hooklibultimate.minecraft.PrimaryClassTransformer;

public class HookLoader extends org.dreamfinity.hooklibultimate.minecraft.HookLoader {
    @Override
    protected void registerHooks() {
        registerHookContainer("org.dreamfinity.interworldsplashscreen.hooks.InterworldsSplashscreenHook");
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[] {(PrimaryClassTransformer.class.getName())};
    }
}
