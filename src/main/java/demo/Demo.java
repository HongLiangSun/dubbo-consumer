package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dubbo.service.api.IDemoApi;

@Controller
public class Demo {
	@Autowired
	private IDemoApi iDemoApi;
	@Autowired
	private RedisTemplate<String, String> redisDao;
	
	@RequestMapping("/demo")
	@ResponseBody
	public String demo(String username) {
		String sayHello = iDemoApi.sayHello(username);
		Boolean result = redisDao.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> stringRedisSerializer = redisDao.getStringSerializer();
				byte[] key = stringRedisSerializer.serialize(username);
				byte[] value = stringRedisSerializer.serialize(username+"123");
				return connection.setNX(key, value);
			}
		});
		System.out.println(result);
		return sayHello;
	}
}
