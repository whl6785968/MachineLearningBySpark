package RedisDemo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Demo1 {
    public static void main(String[] args) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(30);
        config.setMaxIdle(10);

        Jedis jedis = new Jedis("106.14.213.235",6379,10000);
        System.out.println(jedis.ping());
//        jedis.set("name","xiaowang");
//
//        String name = jedis.get("name");
//        System.out.println(name);

//        JedisPool jedisPool = new JedisPool(config,"212.64.72.172",6379);
//
//        Jedis jedis = null;
//
//        try{
//            jedis = jedisPool.getResource();
//            jedis.set("name","xiaozhang");
//            String name = jedis.get("name");
//
//            System.out.println(name);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        finally {
//            if(jedis!=null){
//                jedis.close();
//            }
//        }

    }
}
