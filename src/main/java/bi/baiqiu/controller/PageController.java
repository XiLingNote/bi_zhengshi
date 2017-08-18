package bi.baiqiu.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("page")
public class PageController {

	@RequestMapping("{value}")
	public String page(@PathVariable String value) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("dailySales");
		list.add("monthlySales");
		list.add("salesTrend");
		list.add("topBrand");
		list.add("topStore");
		list.add("index");
		list.add("inputExcel");
		list.add("showlogin");
		list.add("pcFlowStructure");
		list.add("wirelessFlowStructure");
		list.add("storeSalesDemonstrate");
		boolean b = false;
		for (String string : list) {
			if (string.equals(value)) {
				b = true;
			}
		}

		if (b) {
			return "/" + value;
		} else {
			return "/" + value;
			// return "/index";
		}
	}

	@RequestMapping("left")
	public String left() {
		return "/left";
	}

	@RequestMapping("top")
	public String top() {
		return "/top";
	}

	@RequestMapping("login")
	public String dologin(Model model, String username, String password) {
		Subject sub = SecurityUtils.getSubject();
		String pass = DigestUtils.md5Hex(password);
		UsernamePasswordToken token = new UsernamePasswordToken(username, pass);
		System.out.println(username);
		System.out.println(password);
		sub.login(token);
		return "redirect:/page/index.do";
	}

	@RequestMapping("logout")
	public String logout(Model model, HttpServletRequest request) {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		return "redirect:/page/showlogin.do";
	}
}
