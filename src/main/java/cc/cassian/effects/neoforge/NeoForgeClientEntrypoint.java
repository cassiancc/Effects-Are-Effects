//? neoforge {
/*package cc.cassian.effects.neoforge;

import cc.cassian.effects.registry.ModEntityTypes;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(value = Dist.CLIENT)
public class NeoForgeClientEntrypoint {
	@SubscribeEvent
	public static void register(EntityRenderersEvent.RegisterRenderers event) {
		//? if <26.4
		event.registerEntityRenderer(ModEntityTypes.ICE_BALL, ThrownItemRenderer::new);
	}
}
*///?}
