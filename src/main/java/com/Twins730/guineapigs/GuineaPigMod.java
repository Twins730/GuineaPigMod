package com.Twins730.guineapigs;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(GuineaPigMod.MOD_ID)
public class GuineaPigMod {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "guineapigs";

    public GuineaPigMod() {
        GuineaPigMod.LOGGER.info("Setting up mod:" + MOD_ID + " starting!");
        MinecraftForge.EVENT_BUS.register(this);
        GuineaPigMod.LOGGER.info("Finished up mod:" + MOD_ID);
    }

}
