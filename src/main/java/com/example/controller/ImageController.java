package com.example.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/uploads")
public class ImageController {

    private final String uploadDir = "uploads";  // 上传目录

    @PostConstruct
    public void init() {
        // 初始化上传目录
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    // 图片上传接口
    @PostMapping("/image")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("image") MultipartFile file,
                                                           @RequestParam("type") String type) {
        try {
            // 验证图片类型（头像和内容）
            if (!type.equals("avatar") && !type.equals("content")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // 生成图片文件名，并拼接路径
            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
            Path directoryPath = Paths.get(uploadDir, type);  // 根据类型选择文件夹
            Files.createDirectories(directoryPath);  // 创建分类目录
            Path path = directoryPath.resolve(fileName).normalize();

            // 保存文件
            file.transferTo(path);

            // 返回图片 URL
            Map<String, String> response = new HashMap<>();
            response.put("imageUrl", "/uploads/" + type + "/" + fileName);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 获取图片接口
    @GetMapping("/images/{type}/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String type, @PathVariable String imageName) {
        try {
            // 获取图片文件夹路径
            Path path = Paths.get(uploadDir, type).resolve(imageName).normalize();

            // 判断文件是否存在
            if (Files.exists(path)) {
                // 根据文件类型选择合适的媒体类型
                MediaType mediaType = getMediaType(imageName);
                Resource resource = new UrlResource(path.toUri());
                return ResponseEntity.ok()
                        .contentType(mediaType)
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 删除图片接口
    @DeleteMapping("/image/{type}/{imageName}")
    public ResponseEntity<String> deleteImage(@PathVariable String type, @PathVariable String imageName) {
        try {
            // 获取图片文件夹路径
            Path path = Paths.get(uploadDir, type).resolve(imageName).normalize();

            // 判断文件是否存在
            File file = path.toFile();
            if (file.exists() && file.isFile()) {
                boolean deleted = file.delete();
                if (deleted) {
                    return ResponseEntity.ok("文件删除成功");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("文件删除失败");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("文件未找到");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除文件时发生错误");
        }
    }

    // 根据文件后缀判断媒体类型
    private MediaType getMediaType(String imageName) {
        if (imageName.endsWith(".jpg") || imageName.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (imageName.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (imageName.endsWith(".gif")) {
            return MediaType.IMAGE_GIF;
        }
        return MediaType.APPLICATION_OCTET_STREAM; // 默认返回二进制流
    }

}
