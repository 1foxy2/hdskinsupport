package com.github.gravitlauncher.forge.hdskinsupport;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(HdSkinSupportMod.MODID)
public class HdSkinSupportMod {
    public static final String MODID = "hdskinsupport";
    public static final Logger LOGGER = LogUtils.getLogger();
}
