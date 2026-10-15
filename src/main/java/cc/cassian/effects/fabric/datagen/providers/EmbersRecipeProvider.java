//? fabric {
//~ if <26 'FabricPackOutput'->'FabricDataOutput' {
package cc.cassian.effects.fabric.datagen.providers;

import cc.cassian.effects.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("all")
public class EmbersRecipeProvider extends FabricRecipeProvider {


	public EmbersRecipeProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(packOutput, registriesFuture);
	}

	//? if >26.2 {
	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> bootstrapContext, BootstrapContext<Advancement> bootstrapContext1) {
		return new RecipeProvider(bootstrapContext, bootstrapContext1) {
		@Override
			public void buildRecipes() {
			//?} else if >26 {

			/*@Override
			protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
				return new RecipeProvider(registries, output) {
				@Override
			public void buildRecipes() {
			*///?} else {
			/*@Override
			public void buildRecipes(RecipeOutput output) {
		*///?}

				twoByTwoPacker(
						//? if <26
						//output,
						RecipeCategory.MISC, Items.ICE, ModItems.ICE_BALL);
				//? if >26 {
			}
		};
//?}
	}

	@Override
	public String getName() {
		return "Recipes";
	}
}
//~}
//?}
