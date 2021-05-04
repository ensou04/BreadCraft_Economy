package com.github.ensou04.breadcrafteconomy.items;

import com.github.ensou04.breadcrafteconomy.BreadCraftEconomy;
import net.minecraft.client.audio.SoundSource;
import net.minecraft.command.impl.LocateCommand;
import net.minecraft.command.impl.PlaySoundCommand;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EyeOfEnderEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EnderWayfinderItem extends Item {
    public EnderWayfinderItem() {
        super(new Item.Properties().tab(BreadCraftEconomy.BREADCRAFT_GROUP).fireResistant());
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        if(!playerEntity.getCooldowns().isOnCooldown(this)){

            playerEntity.getCooldowns().addCooldown(this, 200);

            if(world instanceof ServerWorld){
                BlockPos playerPos = playerEntity.getEntity().blockPosition();
                BlockPos featurePos = ((ServerWorld)world).getChunkSource().getGenerator().findNearestMapFeature(
                        (ServerWorld)world,
                        Structure.END_CITY,
                        playerEntity.blockPosition(),
                        1,
                        false
                );

                if(!playerEntity.getCommandSenderWorld().isClientSide()){

                    StringTextComponent message = new StringTextComponent("The Elders failed to find a City");

                    if(featurePos != null){
                        BlockPos distance = new BlockPos(featurePos.getX() - playerPos.getX(), 0, featurePos.getZ() - playerPos.getZ());
                        message = new StringTextComponent("The Elders shows you the way: " + Direction.getNearest(distance.getX(),0,distance.getZ()));
                    }

                    message.withStyle(TextFormatting.DARK_PURPLE);
                    playerEntity.sendMessage(message, playerEntity.getUUID());
                }
            }

            return ActionResult.success(playerEntity.getItemInHand(hand));
        }
        return ActionResult.fail(playerEntity.getItemInHand(hand));
    }
}
