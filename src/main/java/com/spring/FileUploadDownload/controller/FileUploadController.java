package com.spring.FileUploadDownload.controller;

import com.jcraft.jsch.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileUploadController {

    private String host = "localhost";
    private int port =22;
    private String username = "user1";
    private String password = "user1";
    //private String localFile = "src/main/resources/input.txt";
    private String localFile = "src/main/resources/";
    private String remoteFile = "tree.jpg";
    private String localDir = "src/main/resources/";
    private String remoteDir = "/";


    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException, JSchException, SftpException

    {
        JSch jsch = new JSch();
        Session session = jsch.getSession( username, host, 22 );
        session.setConfig( "PreferredAuthentications", "password" );
        session.setPassword( password );
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");//do not prefer this. demo only
        session.setConfig(config);
        session.connect( 1200 );
        Channel channel = session.openChannel( "sftp" );
        ChannelSftp sftp = ( ChannelSftp ) channel;
        sftp.connect( 600 );
        channel = session.openChannel("sftp");
        channel.connect();
        try {
            File f = new File(localFile + file.getOriginalFilename()/* + this.fileName */);
            sftp.put(new FileInputStream(f), f.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        channel.disconnect();
        sftp.disconnect();

        /*File convertFile = new File("E:/fileupload/" + file.getOriginalFilename());

        convertFile.createNewFile();



        try (FileOutputStream fout = new FileOutputStream(convertFile))

        {

            fout.write(file.getBytes());

        }

        catch (Exception exe)

        {

            exe.printStackTrace();

        }
*/
        return "File has been uploaded successfully";

    }


}
