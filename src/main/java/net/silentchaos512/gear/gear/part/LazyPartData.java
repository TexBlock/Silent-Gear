package net.silentchaos512.gear.gear.part;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.silentchaos512.gear.SilentGear;
import net.silentchaos512.gear.api.material.IMaterial;
import net.silentchaos512.gear.api.part.IGearPart;
import net.silentchaos512.gear.api.part.IPartData;
import net.silentchaos512.gear.api.part.PartType;
import net.silentchaos512.gear.gear.material.LazyMaterialInstance;
import net.silentchaos512.gear.item.CompoundPartItem;
import net.silentchaos512.gear.util.DataResource;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A "lazy" version of {@link PartData}. Since {@link IGearPart}s may not exist when certain things
 * like loot tables are loaded, {@code LazyPartData} can be used to represent a future part.
 */
public class LazyPartData implements IPartData {
    private final ResourceLocation partId;
    private final ItemStack craftingItem;

    public LazyPartData(ResourceLocation partId) {
        this(partId, ItemStack.EMPTY);
    }

    public LazyPartData(ResourceLocation partId, ItemStack craftingItem) {
        this.partId = partId;
        this.craftingItem = craftingItem;
    }

    public static LazyPartData of(ResourceLocation partId) {
        return new LazyPartData(partId);
    }

    public static LazyPartData of(ResourceLocation partId, ItemStack craftingItem) {
        return new LazyPartData(partId, craftingItem);
    }

    public static LazyPartData of(DataResource<IGearPart> part, ItemStack craftingItem) {
        return new LazyPartData(part.getId(), craftingItem);
    }

    public static LazyPartData of(DataResource<IGearPart> part, CompoundPartItem partItem, List<LazyMaterialInstance> materials) {
        return new LazyPartData(part.getId(), partItem.create(materials));
    }

    public static LazyPartData of(DataResource<IGearPart> part, CompoundPartItem partItem, LazyMaterialInstance material) {
        return of(part, partItem, Collections.singletonList(material));
    }

    public static LazyPartData of(DataResource<IGearPart> part, CompoundPartItem partItem, DataResource<IMaterial> material) {
        return of(part, partItem, LazyMaterialInstance.of(material));
    }

    @Override
    public ResourceLocation getId() {
        return partId;
    }

    @Nullable
    @Override
    public IGearPart get() {
        return PartManager.get(partId);
    }

    @Override
    public ItemStack getItem() {
        if (this.craftingItem.isEmpty()) {
            IGearPart part = get();
            if (part != null) {
                return PartData.of(part).getItem();
            }
        }
        return this.craftingItem;
    }

    @Override
    public Component getDisplayName(PartType type, ItemStack gear) {
        IGearPart part = get();
        return part != null ? part.getDisplayName(this, type, gear) : new TextComponent("INVALID");
    }

    @Override
    public CompoundTag write(CompoundTag tags) {
        tags.putString("ID", partId.toString());
        if (!this.craftingItem.isEmpty()) {
            tags.put("Item", this.craftingItem.save(new CompoundTag()));
        }
        return tags;
    }

    @Override
    public String getModelKey() {
        return SilentGear.shortenId(this.partId);
    }

    public boolean isValid() {
        return get() != null;
    }

    public static LazyPartData deserialize(JsonElement json) {
        if (json.isJsonPrimitive()) {
            String key = json.getAsString();
            return new LazyPartData(new ResourceLocation(key));
        }

        JsonObject jsonObject = json.getAsJsonObject();
        String key = GsonHelper.getAsString(jsonObject, "part");
        return new LazyPartData(new ResourceLocation(key));
    }

    public static List<PartData> createPartList(Collection<LazyPartData> parts) {
        return parts.stream()
                .filter(LazyPartData::isValid)
                .map(LazyPartData::get)
                .filter(Objects::nonNull)
                .map(PartData::of)
                .collect(Collectors.toList());
    }
}
