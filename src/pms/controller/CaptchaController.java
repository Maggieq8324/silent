package pms.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("captcha")
public class CaptchaController {

	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	public void getCaptcha(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		// 禁止图像缓存
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		int width = 80;// 定义图片的width
		int height = 20;// 定义图片的height
		int codeCount = 4;// 定义图片上显示验证码的个数
		int fontSize = 18;// 定义字体大小
		int codeX = 5;// 定义最左侧字符的X坐标
		int codeY = 16;// 定义最左侧字符的Y坐标
		int codeStep = 18;// 定义验证码间距
		char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
				'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		// 定义图像buffer
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 获取图像
		Graphics graphics = bufferedImage.getGraphics();
		// 创建一个随机数生成器类
		Random random = new Random();
		// 填充图像颜色
		graphics.setColor(this.getRandomColor(200, 250));
		graphics.fillRect(0, 0, width, height);

		// 创建字体，字体的大小应该根据图片的高度来定
		Font font = new Font("Times New Roman", Font.BOLD, fontSize);
		// 设置字体
		graphics.setFont(font);

		// 画边框
		graphics.setColor(this.getRandomColor(160, 200));
		graphics.drawRect(0, 0, width - 1, height - 1);

		// 随机产生150条干扰线，使图象中的认证码不易被其它程序探测到
		graphics.setColor(this.getRandomColor(160, 200));
		for (int i = 0; i < 150; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			graphics.drawLine(x, y, x + xl, y + yl);
		}

		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证
		StringBuffer randomCode = new StringBuffer();
		int red = 0, green = 0, blue = 0;

		// 随机产生codeCount数字的验证码
		for (int i = 0; i < codeCount; i++) {
			// 得到随机产生的验证码数字
			String code = String.valueOf(codeSequence[random.nextInt(36)]);
			// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同
			red = 20 + random.nextInt(110);
			green = 20 + random.nextInt(110);
			blue = 20 + random.nextInt(110);

			// 用随机产生的颜色将验证码绘制到图像中
			graphics.setColor(new Color(red, green, blue));
			if (i == 0) {
				graphics.drawString(code, codeX, codeY);
			} else {
				graphics.drawString(code, codeX + i * codeStep, codeY);
			}

			// 将产生的四个随机数组合在一起
			randomCode.append(code);
		}
		// 将四位数字的验证码保存到Session中
		model.addAttribute("captcha", randomCode.toString());

		graphics.dispose();

		// 将图像输出到输出流
		ServletOutputStream outputStream = response.getOutputStream();
		ImageIO.write(bufferedImage, "jpeg", outputStream);
		outputStream.flush();
		outputStream.close();
	}

	// 给定范围获得随机颜色
	private Color getRandomColor(int fColor, int bColor) {
		Random random = new Random();
		if (fColor > 255)
			fColor = 255;
		if (bColor > 255)
			bColor = 255;
		int r = fColor + random.nextInt(bColor - fColor);
		int g = fColor + random.nextInt(bColor - fColor);
		int b = fColor + random.nextInt(bColor - fColor);
		return new Color(r, g, b);
	}

}
