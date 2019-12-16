package com.spring.FileUploadDownload.controller;

import com.jcraft.jsch.*;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;

@RestController
@RequestMapping("/download")
public class FileDownloadController {

    private static final String EXTERNAL_FILE_PATH = "E:/filedownload/";

    private String host = "localhost";
    private int port =22;
    private String username = "user1";
    private String password = "user1";
   //private String localFile = "src/main/resources/";
    //private String remoteFile = "tree.jpg";
    private String localDir = "src/main/resources/downloadedFiles/";
    private String remoteDir = "/";

    @RequestMapping("/file/{fileName:.+}")

    public String downloadResource(/*HttpServletRequest request, HttpServletResponse response,
*/
                                 @PathVariable("fileName") String fileName) throws IOException, JSchException,SftpException {

        //File file = new File(EXTERNAL_FILE_PATH + fileName);

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

        File file = new File(remoteDir + fileName);

        /*if (file.exists()) {
            //get the mimetype

            String mimeType = URLConnection.guessContentTypeFromName(file.getName());

            if (mimeType == null) {

                //unknown mimetype so set the mimetype to application/octet-stream

                mimeType = "application/octet-stream";

            }*/
           /* response.setContentType(mimeType);
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
*/
            sftp.get(file.getName(), localDir );

            sftp.exit();

        /*}*/
        /*File file = new File(remoteDir + fileName);

        if (file.exists()) {
            //get the mimetype

            String mimeType = URLConnection.guessContentTypeFromName(file.getName());

            if (mimeType == null) {

                //unknown mimetype so set the mimetype to application/octet-stream

                mimeType = "application/octet-stream";

            }
            response.setContentType(mimeType);



            *//**

             * In a regular HTTP response, the Content-Disposition response header is a

             * header indicating if the content is expected to be displayed inline in the

             * browser, that is, as a Web page or as part of a Web page, or as an

             * attachment, that is downloaded and saved locally.

             *

             *//*



            *//**

             * Here we have mentioned it to show inline

             *//*

            //response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));



            //Here we have mentioned it to show as attachment

            response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));



            response.setContentLength((int) file.length());



            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));



            FileCopyUtils.copy(inputStream, response.getOutputStream());
            sftp.get(fileName, localDir *//* + "jschFile.txt" *//* );
            sftp.exit();
*/

            return "File has been downloaded successfully";

        }

    }



