//? if <26.4 {
package cc.cassian.effects.mixin.backport;

import net.minecraft.world.entity.monster.zombie.Zombie;
import org.spongepowered.asm.mixin.Mixin;
//? if >=26 {
import net.minecraft.world.entity.EntitySpawnReason;
import org.spongepowered.asm.mixin.gen.Invoker;
//?}

@Mixin(Zombie.class)
public interface ZombieInvoker {
	//? if >=26 {
	@Invoker("handleAttributes")
	void embers$handleAttributes(float difficultyModifier, EntitySpawnReason spawnReason);
	//?}
}
//?}
