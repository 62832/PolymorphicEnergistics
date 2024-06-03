package gripe._90.polyeng.mixin;

import appeng.menu.AEBaseMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AEBaseMenu.class)
public interface AEBaseMenuAccessor {
    @Invoker(remap = false)
    void invokeSendClientAction(String action);
}
