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
import cc.cassian.effects.entity.Frostbite;
import cc.cassian.effects.registry.ModEntityTypes;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
//?}
//? if <26.3 {
/^import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
^///?}
//? if <26 {
/^import net.neoforged.neoforge.event.village.WandererTradesEvent;
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
	public static void createAttributes(EntityAttributeCreationEvent event) {
		event.put(ModEntityTypes.FROSTBITE, Frostbite.createAttributes().build());
	}

	@SubscribeEvent
	public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
		event.register(ModEntityTypes.FROSTBITE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				ModEntityTypes::checkFrostbiteSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
	}

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

	//? if <26 {
	/^@SubscribeEvent
	public static void addWanderingTrades(WandererTradesEvent event) {
		event.getGenericTrades().addAll(FrozenCaves.wanderingTraderTrades());
	}
	^///?}
	//?}
}
*///?}
