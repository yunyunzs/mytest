package cn.zxp.demo.controller;

import cn.zxp.demo.pojo.Rooms;
import cn.zxp.demo.utils.QiniuUploadUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("rooms")
public class RoomsController extends BaseController<Rooms> {
    /**
     *   客房图片上传
     * @param path 文件上传的目标文件夹路径
     * @param myFile 被上传的源文件
     * @return 上传操作的结果
     */
    @RequestMapping("uploadRoomPic")
    public @ResponseBody Map<String,Object> uploadRoomPic(String path, MultipartFile myFile){
        //新建文件上传后的map集合（上传结果）
        Map<String,Object> map = new HashMap<>();
        System.out.println("path = " + path);
        System.out.println("myFile = " + myFile);
        //---------------------文件上传开始---------------------//
//        try{
//            //---修改文件名称
//            //得到文件名称，包括后缀
//            //123.jpg
//            String oldFileName = myFile.getOriginalFilename();
//            System.out.println("oldFileName:"+oldFileName);
//            //得到文件的后缀 jpg
//            //证件照：照片的格式：jpg jpeg png pneg bmp
//            String prefixPath = FilenameUtils.getExtension(oldFileName);
//
//            //得到新文件名
//            //String newFileName = System.currentTimeMillis()+ RandomUtils.nextInt(1000000)+"."+prefixPath;
//            String newFileName=UUID.randomUUID()+"."+prefixPath;
//            //创建路径，上传文件
//            File file = new File(path,newFileName);
//            //判断该路径是否存在
//            if(!file.exists()){
//                //创建路径
//                file.mkdirs();
//            }
//            //文件上传方法
//            myFile.transferTo(file);
//            map.put("code",0); //向map中装入上传的状态，成功
//            map.put("newFileName",newFileName); //向map中装入上传的目标文件名
//        } catch (IOException e) {
//            e.printStackTrace();
//            map.put("code",200); //向map中装入上传的状态，异常
//        }
        try {
            return QiniuUploadUtils.upload(myFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        //---------------------文件上传结束---------------------//
    }
}
