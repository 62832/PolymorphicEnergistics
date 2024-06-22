package gripe._90.polyeng.mixin;

import appeng.client.gui.me.common.MEStorageScreen;
import com.illusivesoulworks.polymorph.client.recipe.RecipesWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MEStorageScreen.class)
public abstract class MEStorageScreenMixin {
    @Inject(method = "toggleTerminalStyle", at = @At("RETURN"), remap = false)
    private void moveWidget(CallbackInfo ci) {
        RecipesWidget.get().ifPresent(widget -> ((AbstractRecipesWidgetAccessor) widget).callResetWidgetOffsets());
    }
}
