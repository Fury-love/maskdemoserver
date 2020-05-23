package com.mask.maskdemoserver.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.SocketException;

/**
 * @Author: marico.lv
 * @CreateTime: 2020-04-11 12:57
 * @Description:
 */
public class FtpUtil {
    private static final Logger logger = LoggerFactory.getLogger(FtpUtil.class);
    /**
     * FTP客户端
     */
    private static FTPClient ftpClient;

    /**
     * 获取FTPClient对象
     *
     * @param ftpHost     FTP主机服务器
     * @param ftpPassword FTP 登录密码
     * @param ftpUserName FTP登录用户名
     * @param ftpPort     FTP端口 默认为21
     * @return
     */
    public static FTPClient getFTPClient(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort) {
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
            ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                logger.info("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
            } else {
                logger.info("FTP连接成功。");
            }
        } catch (SocketException e) {
            e.printStackTrace();
            logger.info("FTP的IP地址可能错误，请正确配置。");
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("FTP的端口错误,请正确配置。");
        }
        return ftpClient;
    }

    /**
     * 关闭连接
     */
    public static void closeConnect() {
        try {
            ftpClient.disconnect();
            logger.info("disconnect success");
        } catch (IOException e) {
            logger.error("not disconnect");
            e.printStackTrace();
        }
    }

    /**
     * 从FTP服务器下载文件
     *
     * @param ftpHost     FTP IP地址
     * @param ftpUserName FTP 用户名
     * @param ftpPassword FTP用户名密码
     * @param ftpPort     FTP端口
     * @param ftpPath     FTP服务器中文件所在路径 格式： ftptest/aa
     * @param localPath   下载到本地的位置 格式：H:/download
     * @param fileName    文件名称
     */
    public static void downloadFtpFile(String ftpHost, String ftpUserName,
                                       String ftpPassword, int ftpPort, String ftpPath, String localPath,
                                       String fileName) {
        try {
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpPath);

//            File localFile = new File(localPath);
//            if (!localFile.exists()) {
//                localFile.mkdirs();
//            }
            File file = new File(localPath + File.separator + fileName);
            OutputStream os = new FileOutputStream(file);
            ftpClient.retrieveFile(fileName, os);
            os.close();
            ftpClient.logout();
            closeConnect();
        } catch (FileNotFoundException e) {
            logger.error("没有找到" + ftpPath + "文件");
            e.printStackTrace();
        } catch (SocketException e) {
            logger.error("连接FTP失败.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("文件读取错误。");
            e.printStackTrace();
        }

    }

    /**
     * 上传文件（可供Action/Controller层使用）
     *
     * @param ftpHost     FTP服务器地址
     * @param ftpPort     FTP服务器端口号
     * @param ftpUserName FTP登录帐号
     * @param ftpPassword FTP登录密码
     * @param ftpPath     FTP服务器保存目录
     * @param fileName    上传到FTP服务器后的文件名称
     * @param localPath   输入文件流
     * @return
     */
    public static boolean uploadFile(String ftpHost, String ftpUserName,
                                     String ftpPassword, int ftpPort, String ftpPath, String localPath,
                                     String fileName) {
        boolean flag = false;
        try {
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            ftpClient.setControlEncoding("UTF-8");

//            // 是否成功登录FTP服务器
//            int replyCode = ftpClient.getReplyCode();
//            if (!FTPReply.isPositiveCompletion(replyCode)) {
//                return flag;
//            }
//            //指定上传文件的类型  二进制文件
//            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//            //检查上传路径是否存在 如果不存在返回false
//            boolean pathFlagExist = createDir(ftpClient, ftpPath);
//            if(!pathFlagExist){
//                return false;
//            }
//            //这个方法的意思就是每次数据连接之前，ftp client告诉ftp server开通一个端口来传输数据。为什么要这样做呢，因为ftp server可能每次开启不同的端口来传输数据，但是在linux上或者其他服务器上面，由于安全限制，可能某些端口没有开启，所以就出现阻塞。OK，问题解决。
//            ftpClient.enterLocalPassiveMode();
            //设置上传目录
            ftpClient.changeWorkingDirectory(ftpPath);
            ftpClient.setBufferSize(1024 * 1024);
            File file = new File(localPath);
            InputStream in = new FileInputStream(file);
            BufferedInputStream input = new BufferedInputStream(in);
            ftpClient.storeFile(fileName, input);
            in.close();
            ftpClient.logout();
            closeConnect();
            flag = true;
        } catch (Exception e) {
            logger.info("上传失败" + e);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    logger.info("上传文件-->关闭连接失败" + e);
                }
            }
        }
        return flag;
    }

    /**
     * 创建目录(有则切换目录，没有则创建目录)
     *
     * @param dir
     * @return
     */
    public static boolean createDir(FTPClient ftp, String dir) {
        String d;
        try {
            //目录编码，解决中文路径问题
            d = new String(dir.toString().getBytes("GBK"), "iso-8859-1");
            //尝试切入目录
            if (ftp.changeWorkingDirectory(d))
                return true;
            String[] arr = dir.split("/");
            StringBuffer sbfDir = new StringBuffer();
            //循环生成子目录
            for (String s : arr) {
                sbfDir.append("/");
                sbfDir.append(s);
                //目录编码，解决中文路径问题
                d = new String(sbfDir.toString().getBytes("GBK"), "iso-8859-1");
                //尝试切入目录
                if (ftp.changeWorkingDirectory(d))
                    continue;
                if (!ftp.makeDirectory(d)) {
                    logger.info(("[失败]ftp创建目录：" + sbfDir.toString()));
                    return false;
                }
            }
            //将目录切换至指定路径
            return ftp.changeWorkingDirectory(d);
        } catch (Exception e) {
            logger.info(("[异常]ftp创建目录：" + e));
            return false;
        }
    }
}
