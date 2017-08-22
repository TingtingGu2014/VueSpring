/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.utils;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 *
 * @author zhao0677
 */
public class UtilsHelper {
    public static UUID getUUIDBasedOnTime(){
        NoArgGenerator timeBasedGenerator = Generators.timeBasedGenerator();
        return timeBasedGenerator.generate();
    }
    
    public static UUID getUUIDBasedOnEthernet() {
        NoArgGenerator timeBasedGenerator = Generators.timeBasedGenerator();
        timeBasedGenerator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
        return timeBasedGenerator.generate();
    }
    
    /**
     * From https://gist.github.com/jeffjohnson9046/c663dd22bbe6bb0b3f5e
     * @param uuid
     * @return 
     */
    public static byte[] getBytesFromUUID(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        return bb.array();
    }

    /**
     * From https://gist.github.com/jeffjohnson9046/c663dd22bbe6bb0b3f5e
     * @param bytes
     * @return 
     */
    public static UUID getUUIDFromBytes(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        Long high = byteBuffer.getLong();
        Long low = byteBuffer.getLong();

        return new UUID(high, low);
    }
}
