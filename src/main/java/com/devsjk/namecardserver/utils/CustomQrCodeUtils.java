package com.devsjk.namecardserver.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Auther: zjp
 * @Date: 2020/12/8 18:38
 * @Description:
 */
public class CustomQrCodeUtils {

    /**
     * 用户方形头像换成圆形头像
     * @param headImage 用户方形头像
     * @return
     * @throws MalformedURLException
     */
    public static BufferedImage circularHeadImage(String headImage) throws MalformedURLException {
        BufferedImage avatarImage;
        try {
            avatarImage = ImageIO.read(new URL(headImage));
            int width = 400;
            BufferedImage formatAvatarImage = new BufferedImage(width, width, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D graphics = formatAvatarImage.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int border = 1;
            Ellipse2D.Double shape = new Ellipse2D.Double(border, border, width - border * 2, width - border * 2);
            graphics.setClip(shape);
            graphics.drawImage(avatarImage, border, border, width - border * 2, width - border * 2, null);
            graphics.dispose();
            graphics = formatAvatarImage.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int border1 = 3;
            Stroke s = new BasicStroke(4.5F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            graphics.setStroke(s);
            graphics.setColor(Color.WHITE);
            graphics.drawOval(border1, border1, width - border1 * 2, width - border1 * 2);
            graphics.dispose();
            /*String pathFileStr = File.separator + model + File.separator  + field + File.separator + DateUtils.getCurrentDate();
            IPUtils.createFile(new File(UPLOAD_PATH + pathFileStr));
            String imageId = UuidUtils.randomUUID();
            String fileName = imageId + ".png";
            String destPath = UPLOAD_PATH + pathFileStr + File.separator + fileName;
            ImageIO.write(formatAvatarImage, "png", new FileOutputStream(destPath));*/
            //LOGGER.info("调用用户方形头像换成圆形头像接口==="+domainName + "/img"+pathFileStr + File.separator + fileName);
            return formatAvatarImage;
        } catch (Exception e) {
            //LOGGER.error("调用用户方形头像换成圆形头像接口异常", e);
        }
        return null;
    }

    /**
     * 生成小程序码
     * @param wxQcode
     * @return
     */
    public static BufferedImage qrCode(String wxQcode,String avatarUrl) {
        try{
            BufferedImage circularHeadImage = circularHeadImage(avatarUrl);
            BufferedImage b1 = ImageIO.read(new URL(wxQcode));
            //将用户头像覆盖小程序码中间的logo
            //BufferedImage b2 = circularHeadImage;
            if (b1 != null) {
                //Graphics2D绘图
                Graphics2D g = b1.createGraphics();
                g.drawImage(circularHeadImage, 115, 117, 200, 195, null);
                g.dispose();
                /*String pathFileStr2 = File.separator + vo.getModel() + File.separator  + vo.getField() + File.separator + DateUtils.getCurrentDate();
                IPUtils.createFile(new File(CustomQrCodeUtil.UPLOAD_PATH + pathFileStr2));
                String uuId = UuidUtils.randomUUID();
                String fileName2 = uuId + ".png";
                String destPath2 = CustomQrCodeUtil.UPLOAD_PATH + pathFileStr2 + File.separator + fileName2;
                ImageIO.write(b1, "png", new File(destPath2));
                qrCodeUrl = pathFileStr2 + File.separator + fileName2;*/
                return b1;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
