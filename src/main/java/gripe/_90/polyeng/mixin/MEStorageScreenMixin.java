package gripe._90.polyeng.mixin;

import appeng.client.gui.me.common.MEStorageScreen;
import com.illusivesoulworks.polymorph.api.client.PolymorphWidgets;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MEStorageScreen.class)
public abstract class MEStorageScreenMixin {
    @Inject(method = "toggleTerminalStyle", at = @At("RETURN"))
    private void moveWidget(CallbackInfo ci) {
        if (PolymorphWidgets.getInstance().getCurrentWidget() instanceof AbstractRecipesWidgetAccessor widget) {
            widget.callResetWidgetOffsets();
        }
    }
}
