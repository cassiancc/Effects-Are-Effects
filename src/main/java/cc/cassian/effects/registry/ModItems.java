package cc.cassian.effects.registry;

import cc.cassian.effects.references.ModItemIds;
import cc.cassian.effects.util.ModUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.function.Function;

public class ModItems {
	//? if <26.4 {
	public static final Item ICE_BALL = registerItem(ModItemIds.ICE_BALL, cc.cassian.effects.item.IceBallItem::new, new Item.Properties().stacksTo(16));
	//?} else {
	/*public static final Item ICE_BALL = Items.ICE_BALL;
	*///?}

	private static Item registerItem(final ResourceKey<Item> id, final Function<Item.Properties, Item> itemFactory, final Item.Properties properties) {
		Item item = itemFactory.apply(properties
				//? if >1.21.2
				.setId(id)
		);
		return Registry.register(BuiltInRegistries.ITEM, id, item);
	}

	public static void init() {
		//? if >26.3
		//ModUtil.addAlias(BuiltInRegistries.ITEM, "ice_ball");
	}
}
