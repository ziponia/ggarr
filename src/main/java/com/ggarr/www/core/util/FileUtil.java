package com.ggarr.www.core.util;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class FileUtil {

    public static void resize(MultipartFile file) throws IOException {
        BufferedImage io = ImageIO.read(file.getInputStream());

        // 썸네일의 너비와 높이 입니다.
        int dw = 250, dh = 150;
        // 원본 이미지의 너비와 높이 입니다.
        int ow = io.getWidth();
        int oh = io.getHeight();
        // 원본 너비를 기준으로 하여 썸네일의 비율로 높이를 계산합니다.
        int nw = ow;
        int nh = (ow * dh) / dw;
        // 계산된 높이가 원본보다 높다면 crop이 안되므로
        // 원본 높이를 기준으로 썸네일의 비율로 너비를 계산합니다.
        if (nh > oh) {
            nw = (oh * dw) / dh;
            nh = oh;
        }

        BufferedImage cropImg = Scalr.crop(io, (ow-nw)/2, (oh-nh)/2, nw, nh);
        BufferedImage destImg = Scalr.resize(cropImg, dw, dh);
//        ImageIO.write(destImg, FilenameUtils.getExtension(file.getOriginalFilename()), null);
    }
}
