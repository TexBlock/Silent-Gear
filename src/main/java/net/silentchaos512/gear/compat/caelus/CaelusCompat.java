package net.silentchaos512.gear.compat.caelus;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public final class CaelusCompat {
    private CaelusCompat() {}

    public static void tryAddFlightAttribute(Multimap<Attribute, AttributeModifier> map) {
        /*if (ModList.get().isLoaded(Const.CAELUS)) {
            CaelusCompatProxy.addFlightAttribute(map);
        }*/
    }
}
