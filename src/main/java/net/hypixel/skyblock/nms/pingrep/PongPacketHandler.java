package net.hypixel.skyblock.nms.pingrep;

import net.minecraft.server.v1_8_R3.PacketStatusOutPong;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PongPacketHandler extends PongPacket {
    public PongPacketHandler(final PingEvent reply) {
        super(reply);
    }

    @Override
    public void send() {
        try {
            final PingReply reply = this.getEvent().getReply();
            final PacketStatusOutPong packet = new PacketStatusOutPong();
            final Field field = this.getEvent().getReply().getClass().getDeclaredField("ctx");
            field.setAccessible(true);
            final Object ctx = field.get(reply);
            final Method writeAndFlush = ctx.getClass().getMethod("writeAndFlush", Object.class);
            writeAndFlush.setAccessible(true);
            writeAndFlush.invoke(ctx, packet);
        } catch (final NoSuchFieldException | IllegalAccessException | IllegalArgumentException |
                       InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
    }
}
