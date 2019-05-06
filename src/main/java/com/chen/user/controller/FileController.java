package com.chen.user.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.common.ResponseObj;
import com.chen.user.pojo.User;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiOperation;

/**
 * 文件上传下载控制类
 *
 */
@RestController
@RequestMapping("/file")
public class FileController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IService<User> userService;

	@Value("${filepath.avatar}")
	private String avatarpath;

	@Value("${filepath.blog}")
	private String blogFilePath;

	@ApiOperation(value = "上传头像")
	@PostMapping("uploadAvatar")
	public ResponseObj<Object> uploadAvatar(@RequestParam("file") MultipartFile file, @RequestParam(name = "id", required = true) Integer id) {
		Path rootLocation = Paths.get(avatarpath);

		log.info("=====start upload avator=====");
		log.info(rootLocation.toString());
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		// 获取后缀
		String suffix = filename.substring(filename.lastIndexOf(".") + 1);
		try {
			if (file.isEmpty()) {
				return ResponseObj.badRequest("空文件！");
			}
			if (filename.contains("..")) {
				// 文件路径安全校验
				return ResponseObj.badRequest("不能将文件保存到相对目录路径中： ");
			}

			User user = userService.getById(id);

			String avatar = user.getUsername() + "-avatar." + suffix;

			// 将上传的文件保存到指定位置
			File loc = new File(rootLocation.toString());
			if (!loc.exists()) {
				loc.mkdirs();
			}
			Files.copy(file.getInputStream(), rootLocation.resolve(avatar), StandardCopyOption.REPLACE_EXISTING);
			String avatarPath = "avatar" + File.separator + avatar;
			user.setAvatar(avatarPath);
			user.setUpdateTime(new Date());
			boolean updateById = userService.updateById(user);
			if (updateById) {
				return  ResponseObj.success(avatarPath);
			} else {
				return ResponseObj.badRequest("上传头像失败！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseObj.badRequest("参数错误！");
		}
	}

	@ApiOperation(value = "上传博客图片")
	@PostMapping("uploadBlogImg")
	public ResponseObj<Object> uploadBlogImg(@RequestParam("file") MultipartFile file) {
		Path rootLocation = Paths.get(blogFilePath);

		log.info("=====start upload blog img=====");
		log.info(rootLocation.toString());
		try {
			
			String uploadBlogImg = uploadBlogImg(file,rootLocation,String.valueOf(DateUtil.current(true)));
			
			return ResponseObj.success(uploadBlogImg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseObj.badRequest("参数错误！");
		}
	}

	private String uploadBlogImg(MultipartFile file, Path rootLocation, String fileName) throws Exception {
		
		
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		// 0-img.jpg , 1-img.png ...
		log.info("filename:"+filename);
		// 获取后缀
		String suffix = filename.substring(filename.lastIndexOf(".") + 1);
		if (file.isEmpty()) {
			throw new RuntimeException("空文件！");
		}
		if (filename.contains("..")) {
			// 文件路径安全校验
			throw new RuntimeException("不能将文件保存到相对目录路径中！");
		}

		String imgPath = fileName +"."+ suffix;

		// 将上传的文件保存到指定位置
		File loc = new File(rootLocation.toString());
		if (!loc.exists()) {
			loc.mkdirs();
		}
		Files.copy(file.getInputStream(), rootLocation.resolve(imgPath), StandardCopyOption.REPLACE_EXISTING);

		return "blog/" + imgPath;
	}

}
