package gripe._90.polyeng.mixin;

import appeng.api.storage.ITerminalHost;
import appeng.menu.me.common.MEStorageMenu;
import appeng.menu.me.items.CraftingTermMenu;
import com.illusivesoulworks.polymorph.common.crafting.RecipeSelection;
import gripe._90.polyeng.PolymorphicEnergistics;
import java.util.Optional;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CraftingTermMenu.class, remap = false)
public abstract class CraftingTermMenuMixin extends MEStorageMenu {
    public CraftingTermMenuMixin(MenuType<?> menuType, int id, Inventory ip, ITerminalHost host) {
        super(menuType, id, ip, host);
    }

    @Shadow
    protected abstract void updateCurrentRecipeAndOutput(boolean forceUpdate);

    @Inject(
            method =
                    "<init>(Lnet/minecraft/world/inventory/MenuType;ILnet/minecraft/world/entity/player/Inventory;Lappeng/api/storage/ITerminalHost;Z)V",
            at = @At("RETURN"))
    private void registerAction(
            MenuType<?> menuType, int id, Inventory ip, ITerminalHost host, boolean bindInventory, CallbackInfo ci) {
        registerClientAction(PolymorphicEnergistics.ACTION, () -> updateCurrentRecipeAndOutput(true));
    }

    @Redirect(
            method = "updateCurrentRecipeAndOutput",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lnet/minecraft/world/item/crafting/RecipeManager;getRecipeFor(Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/world/Container;Lnet/minecraft/world/level/Level;)Ljava/util/Optional;"))
    private <C extends Container, R extends Recipe<C>> Optional<R> getRecipe(
            RecipeManager manager, RecipeType<R> type, C container, Level level) {
        var self = (CraftingTermMenu) (Object) this;
        return RecipeSelection.getPlayerRecipe(self, type, container, level, self.getPlayer());
    }
}
