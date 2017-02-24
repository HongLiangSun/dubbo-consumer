package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dubbo.service.api.IDemoApi;

@Controller
public class Demo {
	@Autowired
	private IDemoApi iDemoApi;

	@RequestMapping("/demo")
	@ResponseBody
	public String demo(@RequestParam(required = false) String username) {
		String sayHello = iDemoApi.sayHello(username);
		return sayHello;
	}
}
