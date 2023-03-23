package org.example;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;

public class Main {

    private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            }
        }
    }
    public static void main(String[] args) throws IOException {
        String server = "localhost";
        int port = 21;
        String user = "carlostadeu";
        String pass = "326598";
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            showServerReply(ftpClient);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Operation failed. Server reply code: " + replyCode);
                return;
            }
            boolean success = ftpClient.login(user, pass);
            showServerReply(ftpClient);
            if (!success) {
                System.out.println("Could not login to the server");
            } else {
                System.out.println("LOGGED IN SERVER");

                FTPFile[] files = ftpClient.listFiles("");
                for (FTPFile file : files) {
                    System.out.println(file.getName());
                }
            }
        } catch (IOException ex) {
            System.out.println("Oops! Something wrong happened");
            ex.printStackTrace();
        } finally {
            close(ftpClient);
        }
    }

    private static void close(FTPClient ftpClient) throws IOException {
        ftpClient.disconnect();
    }
}