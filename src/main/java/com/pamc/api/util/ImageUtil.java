package com.pamc.api.util;

import com.pamc.api.dto.BackImagePointDto;
import com.pamc.api.setting.Setting;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.util.ThumbnailatorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;

/**
 * @author zhenghongwei943
 * @date 2018/11/9
 * @description：图片处理工具类
 **/
public class ImageUtil {

    private static  final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    public final static byte[] pressText(String[] pressText,
                                         String srcImageFile,Font font1,Font font2
                                         , Color color,int x,
                                         int y, float alpha, String author, int startCharX, int startCharY) {
        try {
            Image src = ImageIO.read(ImageUtil.class.getClassLoader().getResourceAsStream(Setting.POETRY_BACKIMAG_PATH+srcImageFile));
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            //getCharPix(pressText,font);
            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(font1);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            // 在指定坐标绘制水印文字
            FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font1);
            //文字高度
            int charHeight = metrics.getHeight();
            //字符间x轴间隔
            int x_interval = 5;
            //字符间y轴间隔
            int y_interval = -5;
            //从右往左，从上到下填充诗句
            for (int m = 0; m < pressText.length; m++) {
                String poetryLine = pressText[m];
                if (m != 0) {
                    startCharX = startCharX - x_interval * m;
                }
                for (int j = 0; j < poetryLine.length(); j++) {
                    int charWidth = metrics.charWidth(poetryLine.charAt(j));
                    int nx = startCharX - charWidth * m;
                    int ny = startCharY + (charHeight + y_interval) * j;
                    if (j == 0) {
                        drawString(poetryLine.charAt(j),font1,font2, nx, startCharY,g);
                    } else {
                        drawString(poetryLine.charAt(j), font1,font2,nx, ny,g);
                    }
                    //当最后一个字填充完毕，则开始制作印章
                    if (m == pressText.length - 1 && j == poetryLine.length() - 1) {
                        g.drawImage(drawAuthor(author), nx, ny + 10, null);
                    }
                }
            }
            g.dispose();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "JPEG", byteArrayOutputStream);
            //ImageIO.write((BufferedImage) image, "JPEG", new File("C:\\Users\\JCN\\Desktop\\水墨图片\\dest\\"+srcImageFile));// 输出到文件流
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 设置作者印章
     *
     * @param author 作者名称
     * @return
     */
    public final static BufferedImage drawAuthor(String author) {
        if (author.length() == 2) {
            author = author + "之印";
        } else if (author.length() == 3) {
            author = author + "印";
        }
        try {
            //String iconPath = ImageUtil.class.getClassLoader().getResourceAsStream("static/img/poetry/shuiyin.png");//ApplicationUtil.contentPath + Setting.POETRY_BACKIMAG_PATH + "shuiyin.png";
            //File img = new File(iconPath);
            Font font1 = new Font("华文隶书", Font.BOLD, 27);
            Font font2 = new Font("隶书", Font.BOLD, 27);
            Image src = ImageIO.read(ImageUtil.class.getClassLoader().getResourceAsStream(Setting.POETRY_BACKIMAG_PATH+"shuiyin.png"));
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            //getCharPix(pressText,font);
            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(Color.WHITE);
            g.setFont(font1);
          /*  g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    0.5f));*/
            // 在指定坐标绘制水印文字
            FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font1);
            //文字基线坐标Y值
            int ascent = metrics.getAscent();
            drawString(author.charAt(2), font1, font2,1,2 + ascent,g);
            drawString(author.charAt(3), font1, font2,1,  ascent * 2,g);
            drawString(author.charAt(0), font1, font2,23, 2 + ascent,g);
            drawString(author.charAt(1), font1, font2,23, ascent * 2,g);
            g.dispose();
            //输入图片
           /* ImageIO.write(image, "PNG",
                    new File("C:\\Users\\JCN\\Desktop\\水墨图片\\dest\\shuiyin.jpg"));*/
            return image;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 单个汇字，
     * 如果字符在font1中不存在则使用第二种font2绘制
     * @param ch 字符
     * @param font1 字体1
     * @param font2 字体2
     * @param x 基线X坐标
     * @param y 基线Y坐标
     * @param graphics
     * @return
     */
    public static Graphics drawString(char ch,Font font1,Font font2,int x,int y,Graphics graphics) {
        graphics.setFont(font1);
        if(!font1.canDisplay(ch)){
            graphics.setFont(font2);
        }
        graphics.drawString(String.valueOf(ch), x, y);
        return graphics;
    }

    /**
     * 将诗句绘制在图片上并输入图片流
     *
     * @param strArray
     * @param name
     * @return
     */
    public static byte[] drawPoetry(String[] strArray, String name) {
        Random random = new Random();
        int i = random.nextInt(Setting.totalBackImageNum + 1);
        BackImagePointDto dto  = Setting.picDefaulPoint.get(i - 1);
        Font font1 = new Font("华文隶书", Font.BOLD, 30);
        Font font2 = new Font("隶书", Font.BOLD, 30);
        logger.info("诗词内容:{}  绘制的图片名称：{}",strArray,dto.getFileName());
        return pressText(strArray, dto.getFileName(),font1,font2 , Color.BLACK,
                 0, 0, 0.6f, name, dto.getX(), dto.getY());
    }

    public static void main(String[] args) throws Exception {
        String[] strArray = {"蔡襄不止工其法", "因事还思善纳忠", "锐龙千丈何蜿蜒", "苍鳞翠鬣翔江边"};
        String name = "蔡锐";
        /*for (int i = 1; i < Setting.totalBackImageNum+1; i++) {
            BackImagePointDto dto  = Setting.picDefaulPoint.get(i - 1);
            Font font1 = new Font("华文隶书", Font.BOLD, 30);
            Font font2 = new Font("隶书", Font.BOLD, 30);
            byte[] b= pressText(strArray, dto.getFileName(),font1,font2 , Color.BLACK,
                    0, 0, 0.6f, name, dto.getX(), dto.getY());

        }*/
        /*String path = "C:\\Users\\JCN\\Desktop\\水墨图片\\";
        Random random = new Random();
        int i = 19;//random.nextInt(20) ;
        String imgName = i + ".jpg";
        // 方法一：
        String[] strArray = {"蔡襄不止工其法", "因事还思善纳忠", "锐龙千丈何蜿蜒", "苍鳞翠鬣翔江边"};
        pressText(strArray, path + imgName,
                path + "dest\\" + imgName, "华文隶书", Font.BOLD, Color.BLACK,
                30, 0, 0, 0.6f, "蔡锐", Setting.picDefaulPoint[i - 1][0], Setting.picDefaulPoint[i - 1][1]);//测试OK
        System.out.println("OK");*/
        //drawAuthor("苍鳞翠鬣");
        //drawAuthor("明珰不学");
        System.out.println(11%3);
        // 方法二：
        //ImageUtils.pressText2("我也是水印文字", "e:/abc.jpg","e:/abc_pressText2.jpg", "黑体", 36, Color.white, 80, 0, 0, 0.5f);//测试OK
    }
}
