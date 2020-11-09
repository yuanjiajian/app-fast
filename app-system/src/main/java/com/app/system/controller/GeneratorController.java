package com.app.system.controller;

import com.app.system.entity.GeneratorParam;
import com.app.system.entity.Result;
import com.app.system.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/generator")
public class GeneratorController {

    @Autowired
    private GeneratorService generatorService;

    @GetMapping("/index.html")
    public String index(){
        return "generator/index";
    }

    @PostMapping("/create")
    @ResponseBody
    public Result run(@Validated GeneratorParam generatorParam){
        generatorService.create(generatorParam);
        return Result.success();
    }
}
