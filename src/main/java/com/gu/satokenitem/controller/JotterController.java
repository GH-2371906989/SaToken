package com.gu.satokenitem.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gu.satokenitem.File.FileUtil;
import com.gu.satokenitem.common.json.BaseController;
import com.gu.satokenitem.common.response.Result;
import com.gu.satokenitem.mapper.JotterMapper;
import com.gu.satokenitem.pojo.FileData;
import com.gu.satokenitem.pojo.Jotter;
import com.gu.satokenitem.service.JotterService;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("jotter")
@Slf4j
public class JotterController extends BaseController {
    @Autowired
    private JotterService jotterService;
    @Autowired
    private JotterMapper jotterMapper;
    @Autowired
    private FileUtil fileUtil;

    @ApiOperation("获取笔记列表")
    @GetMapping("/getJotters")
    @SaCheckLogin
    public Result getJotterList(){
        return success().put("JotterList",getJotters());
    }

    public List<Jotter> getJotters(){
        QueryWrapper<Jotter> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("name");
        return jotterMapper.selectList(wrapper);
    }
    @ApiOperation("文件上传到服务器")
    @PostMapping("/uploadService")
    public Result uploadService(@RequestParam("file") MultipartFile file, FileData fileData ) throws IOException, JSchException {
        //上传文件的文件名
        String name = file.getOriginalFilename();
        String type = name.substring(name.lastIndexOf(".")+1);

        if (fileData.getFilename()==null || fileData.getFilename().trim().equals("")){
            log.info("数据库文件名为空");
            return error().put("JotterList",getJotters()).Message("保存文件名为null");
        }
        if(!type.trim().equals("pdf") && !type.trim().equals("html")){
            log.info("格式不是pdf或html");
            return error().put("JotterList",getJotters()).Message("文件格式不是PDF或者HTML");
        }

        //上传到那个文件夹下
        String filename = fileData.getFilename().trim();
        String URL = "http://152.136.163.72:8080/jotter/";
        //保存地址
        if (filename.equals("")){
            URL += name;
        }else {
            URL +=filename+"/"+name;
        }

        //登录sftp
        fileUtil.login();
        QueryWrapper<Jotter> wrapper = new QueryWrapper<>();
        wrapper.eq("name", fileData.getSavename());
        Jotter build = jotterMapper.selectOne(wrapper);
        //判断是添加还是修改
        String isInstert = "updata";
        if (build == null){
            isInstert = "insert";
            String flag = "false";
            build = Jotter.builder()
                    .name(fileData.getSavename())
                    .flag(flag)
                    .URL("")
                    .downloadURL("")
                    .build();
        }
        //上传只能是 pdf html
        if (type.trim().equals("pdf")){
            build.setDownloadURL(URL);
        }else if (type.trim().equals("html")){
            build.setURL(URL);
        }

        //保存到服务器
        String uploadpath = "/root/tomcat/jotter";
        try {
            byte[] bytes = IOUtils.toByteArray(file.getInputStream());
            log.info("length:{}",bytes.length);
            fileUtil.uploadSFTP(uploadpath,filename,name,bytes);
            if (isInstert.equals("insert")){
                jotterMapper.insert(build);
            }else {
                jotterMapper.updateById(build);
            }
        } catch (SftpException e) {
            e.printStackTrace();
        }
//        fileUtil.upload(uploadpath,fileData.getFilename().trim(),);
        fileUtil.closeFtp();
        return success().put("JotterList",getJotters()).Message("添加数据成功");
    }

    @ApiOperation("删除文件")
    @GetMapping("/delectJotters/{id}")
    @SaCheckPermission(value = {"admin"})
    public List<Jotter> delectfile(@PathVariable("id") int id) throws IOException, JSchException, SftpException {
        QueryWrapper<Jotter> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        Jotter jotter = jotterMapper.selectOne(wrapper);
        String htmlPath = "/root/tomcat/jotter/";
        fileUtil.login();
        fileUtil.delectURL(htmlPath+jotter.getName());
        jotterMapper.delete(wrapper);

        return getJotters();
    }
}
