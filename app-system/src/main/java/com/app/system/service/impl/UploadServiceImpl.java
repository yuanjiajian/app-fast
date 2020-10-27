package com.app.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.app.system.config.UploadConfig;
import com.app.system.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.util.Date;

@Service
public class UploadServiceImpl implements UploadService {
    @Autowired
    private UploadConfig uploadConfig;

    @Override
    public String uploadFile(byte[] bytes, String originalFilename) {
        String extName = FileUtil.extName(originalFilename);
        String fileName = DateUtil.format(new Date(), uploadConfig.getPattern()) + IdUtil.simpleUUID() + "." + extName;
        this.byte2image(bytes, uploadConfig.getPath() + fileName);
        return uploadConfig.getUrl() + fileName;
    }


    public void byte2image(byte[] data, String path) {
        if (data.length < 3 || path.equals("")) return;
        try {
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            FileImageOutputStream imageOutput = new FileImageOutputStream(file);
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out.println("Make Picture success,Please find image in " + path);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }
}
