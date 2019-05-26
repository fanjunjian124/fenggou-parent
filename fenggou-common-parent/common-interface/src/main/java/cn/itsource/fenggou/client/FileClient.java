package cn.itsource.fenggou.client;

import cn.itsource.util.AjaxResult;
import org.apache.commons.io.FilenameUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@FeignClient("FENGGOU-COMMON")
public interface FileClient {
    /**
     * 文件的上传
     * @param file
     * @return
     */
    @PostMapping("/file/upload")
     AjaxResult uploadFile(@RequestParam("file") MultipartFile file);

    /**
     * 删除分布式文件系统中的文件
     * @param fileId
     * @return
     */
    @GetMapping("/file/delete")
     AjaxResult deleteFile(@RequestParam("fileId") String fileId);
}
