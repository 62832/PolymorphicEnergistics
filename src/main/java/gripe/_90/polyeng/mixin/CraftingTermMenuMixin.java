package gripe._90.polyeng.mixin;

import appeng.api.storage.ITerminalHost;
import appeng.menu.me.common.MEStorageMenu;
import appeng.menu.me.items.CraftingTermMenu;
import com.illusivesoulworks.polymorph.api.PolymorphApi;
import gripe._90.polyeng.PolymorphicEnergistics;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.Optional;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingTermMenu.class)
public abstract class CraftingTermMenuMixin extends MEStorageMenu {
    public CraftingTermMenuMixin(MenuType<?> menuType, int id, Inventory ip, ITerminalHost host) {
        super(menuType, id, ip, host);
    }

    @Shadow(remap = false)
    protected abstract void updateCurrentRecipeAndOutput(boolean forceUpdate);

    // spotless:off
    @Inject(
            method = "<init>(Lnet/minecraft/world/inventory/MenuType;ILnet/minecraft/world/entity/player/Inventory;Lappeng/api/storage/ITerminalHost;Z)V",
            at = @At("RETURN"))
    // spotless:on
    private void registerAction(
            MenuType<?> menuType, int id, Inventory ip, ITerminalHost host, boolean bindInventory, CallbackInfo ci) {
        registerClientAction(PolymorphicEnergistics.ACTION, () -> updateCurrentRecipeAndOutput(true));
    }

    // spotless:off
    @Redirect(
            method = "updateCurrentRecipeAndOutput",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/crafting/RecipeManager;getRecipeFor(Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/world/item/crafting/RecipeInput;Lnet/minecraft/world/level/Level;)Ljava/util/Optional;"))
    // spotless:on
    private <I extends RecipeInput, R extends Recipe<I>> Optional<RecipeHolder<R>> getRecipe(
            RecipeManager manager, RecipeType<R> type, I input, Level level) {
        return PolymorphApi.getInstance().getRecipeManager().getPlayerRecipe(this, type, input, level, getPlayer());
    }

    @Override
    public void onServerDataSync(ShortSet updatedFields) {
        super.onServerDataSync(updatedFields);
        updateCurrentRecipeAndOutput(true);
    }
}
