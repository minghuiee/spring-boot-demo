package com.home.pratice.bootstrap.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.*;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class MacUtil {
    private static String PHYSICAL_ADDR = "Physical Address";
    private static String MAC_ADDR = "MAC Address";

    public static void main(String[] args) throws SocketException, DecoderException {
//        log.info(getMyMAC());
        byte[] bytes = new byte[]{120, -120};
        //120=0111 1000 -> 78
        //-120=0111 1000 -> 1000 0111 + 0000 0001 = 1000 1000 -> 88
        //0111 1000 >> 4 -> 0111 = 7
        String a = String.valueOf(Character.forDigit((bytes[0] >> 4) & 0xF, 16));
        String b = String.valueOf(Character.forDigit((bytes[0]) & 0xF, 16));
        //1000 1000 >> 4 -> 1000 = 8
        String c = String.valueOf(Character.forDigit((bytes[1] >> 4) & 0xF, 16));
        String d = String.valueOf(Character.forDigit((bytes[1]) & 0xF, 16));
        log.info(a+b);
        log.info(c+d);
    }

    /**
     * Get local mac address from NetworkInterface method
     *
     * @return local mac
     * @throws SocketException
     */
    public static String getMyMAC() throws SocketException, DecoderException {
        StringBuilder macs = new StringBuilder();
        Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
        NetworkInterface inter;
        while (networks.hasMoreElements()) {
            inter = networks.nextElement();
            byte[] macBytes = inter.getHardwareAddress();
            if (macBytes == null) continue;
            macs.append("\r\n");
            for (int i = 0; i < macBytes.length; i++) {
                macs.append(Character.forDigit((macBytes[i] >> 4) & 0xF, 16));
                macs.append(Character.forDigit((macBytes[i] & 0xF), 16));
                macs.append(macBytes.length - 1 == i ? "" : "-");
            }
        }
        return macs.toString();
    }

    // Get local mac address from command line
    public static String getMACWithCMD() {
        String mac = null;
        try {
            Process pro = Runtime.getRuntime().exec("cmd.exe /c ipconfig/all");
            InputStream is = pro.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String message = br.readLine();
            int index;
            while (message != null) {
                if ((index = message.indexOf(PHYSICAL_ADDR)) > 0) {
                    mac = message.substring(index + 36).trim();
                    break;
                }
                message = br.readLine();
            }
            br.close();
            pro.destroy();
        } catch (IOException e) {
            //TODO: insert into log table
            log.info("can't find mac address is due to {}", ExceptionUtils.getStackTrace(e));
            return null;
        }
        return mac;
    }

    //使用TCP/IP上的NetBIOS後，獲取遠程主機的NetBIOS信息並顯示協議統計和當前TCP/IP連接
    public static String getMACWithCMD(String ip) {
        String str;
        String macAddress;
        try {
            Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            while (true) {
                str = input.readLine();
                if (str != null) {
                    if (str.indexOf(MAC_ADDR) > 1) {
                        macAddress = str.substring(str.indexOf(MAC_ADDR) + 14);
                        p.destroy();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            //TODO: insert into log table
            log.info("can't find mac address is due to {}", ExceptionUtils.getStackTrace(e));
            return null;
        }
        return macAddress;
    }

    /**
     * in the same LAN
     * arp 位址解析協定 先ping對方ip後再解析對方mac位址 本機系統為windows系統
     * windows需要發送兩次指令
     */
    public static String getMacInWindows(final String ip) {
        String[] cmd = {"cmd", "/c", "ping " + ip};
        String[] another = {"cmd", "/c", "arp -a"};
        String cmdResult = execWindowsCmd(cmd, another);
        return filterMacAddress(ip, cmdResult, "-");
    }

    /**
     * in the same LAN
     * arp 位址解析協定 先ping對方ip後再解析對方mac位址 本機系統為linux系統
     */
    public static String getMacInLinux(final String ip) {
        String[] cmd = {"/bin/sh", "-c", "ping " + ip + " -c 2 && arp -a"};
        String cmdResult = execLinuxCmd(cmd);
        return filterMacAddress(ip, cmdResult, ":");
    }

    private static String filterMacAddress(final String ip, final String sourceString, final String macSeparator) {
        String result = "";
        String regExp = "((([0-9A-Fa-f]{1,2}" + macSeparator + "){1,5})[0-9A-Fa-f]{1,2})";
        Matcher matcher = Pattern.compile(regExp).matcher(sourceString);
        while (matcher.find()) {
            result = matcher.group(1);
            if (sourceString.indexOf(ip) <= sourceString.lastIndexOf(matcher.group(1))) {
                break;
            }
        }

        return result;
    }

    private static String execWindowsCmd(String[] cmd, String[] another) {
        StringBuilder result = new StringBuilder();
        String line;
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(cmd);
            proc.waitFor();
            proc = rt.exec(another);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(is);
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            proc.destroy();
        } catch (Exception e) {
            //TODO: insert into log table
            log.info("execute command error is due to {}", ExceptionUtils.getStackTrace(e));
        }
        return result.toString();
    }

    private static String execLinuxCmd(String[] cmd) {
        StringBuilder result = new StringBuilder();
        String line;
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(is);
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            proc.destroy();
        } catch (Exception e) {
            //TODO: insert into log table
            log.info("execute command error is due to {}", ExceptionUtils.getStackTrace(e));
        }
        return result.toString();
    }
}
