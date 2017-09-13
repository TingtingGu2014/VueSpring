/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.service;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoPool;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.DefaultDeserializer;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * refer to : http://blog.csdn.net/skmbw/article/details/12905939
 * @author zhao0677
 */
public class KryoRedisSerializer<T> implements RedisSerializer<T>, BeanClassLoaderAware {  
    
    static KryoPool kryoPool = new KryoPool.Builder(Kryo::new).build();  
    private Converter<Object, byte[]> serializer = new SerializingConverter();
    private Converter<byte[], Object> deserializer;
    
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.deserializer = new DeserializingConverter(new DefaultDeserializer(classLoader));
    }
      
    @Override  
    public byte[] serialize(Object t) throws SerializationException {  
        Kryo kryo = kryoPool.borrow();
        byte[] buffer = new byte[2048];  
        Output output = new Output(buffer);  
        kryo.writeClassAndObject(output, t);  
        return output.toBytes();  
    }  
  
    @Override  
    public T deserialize(byte[] bytes) throws SerializationException {  
        if(null == bytes || bytes.length <= 0){
            try {
                throw new Exception("The deserialization input is empty!");
            } catch (Exception ex) {
                Logger.getLogger(KryoRedisSerializer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Kryo kryo = kryoPool.borrow();
        Input input = new Input(bytes);  
        @SuppressWarnings("unchecked")  
        T t = (T) kryo.readClassAndObject(input);  
        return t;  
    }  
  
}