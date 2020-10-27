package com.app.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.app.system.config.UploadConfig;
import com.app.system.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        File file = new File(uploadConfig.getPath() + fileName);
        return null;
    }
}
