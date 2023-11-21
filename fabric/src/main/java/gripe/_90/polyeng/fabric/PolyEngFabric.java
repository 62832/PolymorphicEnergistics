package gripe._90.polyeng.fabric;

import gripe._90.polyeng.PolymorphicEnergistics;
import net.fabricmc.api.ClientModInitializer;

public class PolyEngFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PolymorphicEnergistics.register();
    }
}
