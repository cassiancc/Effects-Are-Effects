//? neoforge {
/*package cc.cassian.effects.neoforge;

import cc.cassian.effects.EmbersAndIcicles;
import cc.cassian.effects.registry.ModItems;
import cc.cassian.effects.registry.ModPotions;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;
//? if <26.4 {
import cc.cassian.effects.backport.FrozenCaves;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
//?}
//? if <26.3 {
/^import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
^///?}

@EventBusSubscriber
public class NeoForgeEntrypoint {
	@SubscribeEvent
	public static void register(RegisterEvent event) {
		if (event.getRegistryKey().equals(Registries.BLOCK)) {
			EmbersAndIcicles.onInitialize();
		}
	}

	//? if <26.3 {
	/^@SubscribeEvent
	public static void register(RegisterBrewingRecipesEvent event) {
		event.getBuilder().addStartMix(
				// Ingredient
				ModItems.ICE_BALL,
				// Output potion.
				ModPotions.FREEZING
		);
	}
	^///?}

	//? if <26.4 {
	@SubscribeEvent
	public static void addToCreativeTabs(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.COMBAT) {
			insertAfter(event, Items.SNOWBALL, ModItems.ICE_BALL);
		}

		for (FrozenCaves.TabInsertion insertion : FrozenCaves.tabInsertions()) {
			if (event.getTabKey() == insertion.tab()) {
				insertAfter(event, insertion.after(), insertion.item());
			}
		}
	}

	private static void insertAfter(BuildCreativeModeTabContentsEvent event, Item after, Item item) {
		ItemStack afterStack = new ItemStack(after);
		ItemStack stack = new ItemStack(item);
		if (event.getParentEntries().contains(afterStack) && event.getSearchEntries().contains(afterStack)) {
			event.insertAfter(afterStack, stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		} else {
			event.accept(stack);
		}
	}
	//?}
}
*///?}
