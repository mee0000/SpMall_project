package com.example.spmall.adminController;


import com.example.spmall.adminService.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * UploadController
 *
 * @author toy aa@nfda
 * @version 2023/12/25 3:47 PM
 * @since JDK11
 **/
@Slf4j
@RestController
public class UploadController {
    private static final String FILE_PATH="/meeyeong2/tomcat/webapps/upload";

    @Autowired
    ProductService productService;

    @ResponseBody
    @RequestMapping("/image")
    public byte[] getImage(String fileName) {
        //해당 이미지를 byte[]형태로 반환
        File file=new File(FILE_PATH+fileName);
        InputStream in=null;

        try {
            in=new FileInputStream(file);
            return IOUtils.toByteArray(in);

        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return null;
    }


}
