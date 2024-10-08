package net.hypixel.skyblock.npc.hub.merchants;

import net.hypixel.skyblock.features.merchant.FishMerchantGUI;
import net.hypixel.skyblock.npc.impl.NPCParameters;
import net.hypixel.skyblock.npc.impl.SkyblockNPC;
import net.hypixel.skyblock.npc.impl.enums.NPCType;
import org.bukkit.entity.Player;

public class FishMerchant extends SkyblockNPC {
    public FishMerchant() {
        super(new NPCParameters() {

            @Override
            public String name() {
                return "FishMerchant";
            }

            @Override
            public String[] messages() {
                return new String[]{
                        "Fishing is my trade. I buy and sell any fish, rod, or treasure you can find!",
                        "Click me again to open the Fisherman Shop!"
                };
            }

            @Override
            public String[] holograms() {
                return new String[]{
                        "&fFisherman",
                        "&e&lCLICK",
                };
            }

            @Override
            public NPCType type() {
                return NPCType.PLAYER;
            }

            @Override
            public String world() {
                return "world";
            }

            @Override
            public double x() {
                return 52;
            }

            @Override
            public double y() {
                return 68;
            }

            @Override
            public double z() {
                return -82;
            }

            @Override
            public boolean looking() {
                return true;
            }

            @Override
            public void onInteract(Player player, SkyblockNPC npc) {
                FishMerchantGUI gui = new FishMerchantGUI();
                gui.open(player);
            }
        });
    }
}
