//? if <26.4 {
package cc.cassian.effects.mixin.backport;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockBehaviour.class)
public interface BlockBehaviourAccessor {
	@Mutable
	@Accessor("soundType")
	void embers$setSoundType(SoundType soundType);
}
//?}
