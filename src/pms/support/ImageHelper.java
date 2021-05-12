package pms.support;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageHelper {
    /**
     * 指定图片宽度和高度和压缩比例对图片进行压缩
     * 
     * @param imgsrc
     *            源图片地址
     * @param imgdist
     *            目标图片地址
     * @param widthdist
     *            压缩后图片的宽度
     * @param heightdist
     *            压缩后图片的高度
     * @param rate
     *            压缩的比例
     */
    public static void reduceImg(File srcfile, String imgdist, int widthdist, int heightdist, Float rate) {
        try {
            //File srcfile = new File(imgsrc);
            // 检查图片文件是否存在
            if (!srcfile.exists()) {
                System.out.println("文件不存在");
            }
            // 如果比例不为空则说明是按比例压缩
            if (rate != null && rate > 0) {
                //获得源图片的宽高存入数组中
                int[] results = getImgWidthHeight(srcfile);
                if (results == null || results[0] == 0 || results[1] == 0) {
                    return;
                } else {
                    //按比例缩放或扩大图片大小，将浮点型转为整型
                    widthdist = (int) (results[0] * rate);
                    heightdist = (int) (results[1] * rate);
                }
            }
            // 开始读取文件并进行压缩
            Image src = ImageIO.read(srcfile);

            // 构造一个类型为预定义图像类型之一的 BufferedImage
            BufferedImage tag = new BufferedImage((int) widthdist, (int) heightdist, BufferedImage.TYPE_INT_RGB);

            //绘制图像  getScaledInstance表示创建此图像的缩放版本，返回一个新的缩放版本Image,按指定的width,height呈现图像
            //Image.SCALE_SMOOTH,选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
            tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist, Image.SCALE_SMOOTH), 0, 0, null);

            //创建文件输出流
            FileOutputStream out = new FileOutputStream(imgdist);
            //将图片按JPEG压缩，保存到out中
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(tag);
            //关闭文件输出流
            out.close();
        } catch (Exception ef) {
            ef.printStackTrace();
        }
    }

    /**
     * 获取图片宽度和高度
     * 
     * @param 图片路径
     * @return 返回图片的宽度
     */
    public static int[] getImgWidthHeight(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int result[] = { 0, 0 };
        try {
            // 获得文件输入流
            is = new FileInputStream(file);
            // 从流里将图片写入缓冲图片区
            src = ImageIO.read(is);
            result[0] =src.getWidth(null); // 得到源图片宽
            result[1] =src.getHeight(null);// 得到源图片高
            is.close();  //关闭输入流
        } catch (Exception ef) {
            ef.printStackTrace();
        }

        return result;
    }
    
 
    /**
     * 判断高度和宽度，如果高度大于宽度顺时针旋转90度（通过交换图像的整数像素RGB 值）
     * 
     * @param 图片对象
     * @return 新的图片对象
     */
    public static BufferedImage rotateClockwise90(BufferedImage bi) {  
        int width = bi.getWidth();  
        int height = bi.getHeight();  
        BufferedImage bufferedImage = new BufferedImage(height, width, bi.getType());         
        //bufferedImage.createGraphics().rotate(theta);
        

        	for (int i = 0; i < width; i++)  
                for (int j = 0; j < height; j++)  
                    bufferedImage.setRGB(height - 1 - j, width - 1 - i, bi.getRGB(i, j));



        return bufferedImage;  
    }

    public static void main(String[] args) throws IOException {
        
        /*File srcfile = new File("E:\\apache-tomcat-8.5.4\\webapps\\upload\\yy\\2018\\04\\23\\fd7b9aad-44d5-49cf-94ee-e23699b3be2e.png"); 
        File distfile = new File("E:\\apache-tomcat-8.5.4\\webapps\\upload\\yy\\2018\\04\\23\\fd7b9aad-44d5-49cf-94ee-e23699b3be2e_small.png");
        
        System.out.println("压缩前图片大小：" + srcfile.length());
        reduceImg(srcfile, "E:\\apache-tomcat-8.5.4\\webapps\\upload\\yy\\2018\\04\\23\\fd7b9aad-44d5-49cf-94ee-e23699b3be2e_small.png", 30, 30, 0.2f);
        System.out.println("压缩后图片大小：" + distfile.length());*/
    	
    	 // 测试来源图片    
        String pathname = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "photo.jpg";    
        File file = new File(pathname);    
        // 测试生成图片    
        String testPath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "test.png";    
        File outFile = new File(testPath);    
          
        //顺时针旋转90度测试  
        BufferedImage image = ImageIO.read(file);    
        image=rotateClockwise90(image);  
        ImageIO.write(image, "png", outFile);
        System.out.println("完成了!");
    }
    
}
