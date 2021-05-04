package com.github.ensou04.breadcrafteconomy.items;

import com.github.ensou04.breadcrafteconomy.BreadCraftEconomy;
import net.minecraft.client.audio.SoundSource;
import net.minecraft.command.impl.LocateCommand;
import net.minecraft.command.impl.PlaySoundCommand;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EnderWayfinderItem extends Item {
    public EnderWayfinderItem() {
        super(new Item.Properties().tab(BreadCraftEconomy.BREADCRAFT_GROUP).fireResistant());
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        if(!playerEntity.getCooldowns().isOnCooldown(this)){


            if(!playerEntity.getCommandSenderWorld().isClientSide()){
                StringTextComponent message = new StringTextComponent("The Elders show you the way.");
                message.withStyle(TextFormatting.DARK_PURPLE);
                playerEntity.sendMessage(message, playerEntity.getUUID());
            }

            playerEntity.getCooldowns().addCooldown(this, 1000);
            return ActionResult.success(playerEntity.getItemInHand(hand));
        }
        return ActionResult.fail(playerEntity.getItemInHand(hand));
    }
}
