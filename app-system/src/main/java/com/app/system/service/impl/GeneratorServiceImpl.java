package com.app.system.service.impl;

import com.app.generator.Generator;
import com.app.system.entity.GeneratorParam;
import com.app.system.service.GeneratorService;
import org.springframework.stereotype.Service;

@Service
public class GeneratorServiceImpl implements GeneratorService {

    @Override
    public void create(GeneratorParam generatorParam) {
        Generator.run(generatorParam.getProjectPath(), generatorParam.getPackageName(), generatorParam.getDbUrl(), generatorParam.getDbUsername(), generatorParam.getDbPassword(),generatorParam.getTables().toArray(new String[0]));
    }
}
