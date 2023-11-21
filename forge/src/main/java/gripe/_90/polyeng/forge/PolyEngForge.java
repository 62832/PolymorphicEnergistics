package gripe._90.polyeng.forge;

import gripe._90.polyeng.PolymorphicEnergistics;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(PolymorphicEnergistics.MODID)
public class PolyEngForge {
    public PolyEngForge() {
        if (FMLEnvironment.dist.isClient()) {
            PolymorphicEnergistics.register();
        }
    }
}
