package net.hypixel.skyblock.nms.nmsutil.packetlistener.channel;

import net.hypixel.skyblock.nms.nmsutil.packetlistener.Cancellable;
import net.hypixel.skyblock.nms.nmsutil.packetlistener.IPacketListener;
import net.hypixel.skyblock.nms.nmsutil.reflection.minecraft.Minecraft;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.net.SocketAddress;
import java.util.ArrayList;

public class INCChannel extends ChannelAbstract {
    private static final Field channelField;

    public INCChannel(final IPacketListener iPacketListener) {
        super(iPacketListener);
    }

    @Override
    public void addChannel(final Player player) {
        try {
            final Channel channel = this.getChannel(player);
            this.addChannelExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        channel.pipeline().addBefore("packet_handler", "packet_listener_player", new ChannelHandler(player));
                    } catch (final Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (final ReflectiveOperationException e) {
            throw new RuntimeException("Failed to add channel for " + player, e);
        }
    }

    @Override
    public void removeChannel(final Player player) {
        try {
            final Channel channel = this.getChannel(player);
            this.removeChannelExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (channel.pipeline().get("packet_listener_player") != null) {
                            channel.pipeline().remove("packet_listener_player");
                        }
                    } catch (final Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (final ReflectiveOperationException e) {
            throw new RuntimeException("Failed to remove channel for " + player, e);
        }
    }

    Channel getChannel(final Player player) throws ReflectiveOperationException {
        final Object handle = Minecraft.getHandle(player);
        final Object connection = INCChannel.playerConnection.get(handle);
        return (Channel) INCChannel.channelField.get(INCChannel.networkManager.get(connection));
    }

    @Override
    public IListenerList newListenerList() {
        return new ListenerList();
    }

    static {
        channelField = INCChannel.networkManagerFieldResolver.resolveByFirstTypeSilent(Channel.class);
    }

    class ListenerList<E> extends ArrayList<E> implements IListenerList<E> {
        @Override
        public boolean add(final E paramE) {
            try {
                final E a = paramE;
                INCChannel.this.addChannelExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Channel channel;
                            for (channel = null; channel == null; channel = (Channel) INCChannel.channelField.get(a)) {
                            }
                            if (channel.pipeline().get("packet_listener_server") == null) {
                                channel.pipeline().addBefore("packet_handler", "packet_listener_server", new ChannelHandler(new INCChannelWrapper(channel)));
                            }
                        } catch (final Exception ex) {
                        }
                    }
                });
            } catch (final Exception ex) {
            }
            return super.add(paramE);
        }

        @Override
        public boolean remove(final Object arg0) {
            try {
                final Object a = arg0;
                INCChannel.this.removeChannelExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Channel channel;
                            for (channel = null; channel == null; channel = (Channel) INCChannel.channelField.get(a)) {
                            }
                            channel.pipeline().remove("packet_listener_server");
                        } catch (final Exception ex) {
                        }
                    }
                });
            } catch (final Exception ex) {
            }
            return super.remove(arg0);
        }
    }

    class ChannelHandler extends ChannelDuplexHandler implements IChannelHandler {
        private final Object owner;

        public ChannelHandler(final Player player) {
            this.owner = player;
        }

        public ChannelHandler(final ChannelWrapper channelWrapper) {
            this.owner = channelWrapper;
        }

        public void write(final ChannelHandlerContext ctx, final Object msg, final ChannelPromise promise) throws Exception {
            final Cancellable cancellable = new Cancellable();
            Object pckt = msg;
            if (ChannelAbstract.Packet.isAssignableFrom(msg.getClass())) {
                pckt = INCChannel.this.onPacketSend(this.owner, msg, cancellable);
            }
            if (cancellable.isCancelled()) {
                return;
            }
            super.write(ctx, pckt, promise);
        }

        public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
            final Cancellable cancellable = new Cancellable();
            Object pckt = msg;
            if (ChannelAbstract.Packet.isAssignableFrom(msg.getClass())) {
                pckt = INCChannel.this.onPacketReceive(this.owner, msg, cancellable);
            }
            if (cancellable.isCancelled()) {
                return;
            }
            super.channelRead(ctx, pckt);
        }

        public String toString() {
            return "INCChannel$ChannelHandler@" + this.hashCode() + " (" + this.owner + ")";
        }
    }

    class INCChannelWrapper extends ChannelWrapper<Channel> implements IChannelWrapper {
        public INCChannelWrapper(final Channel channel) {
            super(channel);
        }

        @Override
        public SocketAddress getRemoteAddress() {
            return this.channel().remoteAddress();
        }

        @Override
        public SocketAddress getLocalAddress() {
            return this.channel().localAddress();
        }
    }
}
