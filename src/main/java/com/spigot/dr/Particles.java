package com.spigot.dr;

import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Particles {

    private PacketPlayOutWorldParticles packet;

    public Particles(EnumParticle particle, Location location, float xOffset, float yOffset, float zOffset, float speed, int count) {

        float x = (float) location.getX();
        float y = (float) location.getY();
        float z = (float) location.getZ();

        PacketPlayOutWorldParticles packet;
        packet = new PacketPlayOutWorldParticles(particle, true, x, y, z, xOffset, yOffset, zOffset, speed, count);

        this.packet = packet;

    }

    public void sendToPlayer(Player p)  {
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
    }

}
