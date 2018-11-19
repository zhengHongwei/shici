package com.pamc.api.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * author
 * created by zhenghongwei
 * 2018/9/17
 * description：
 **/
public class Util {
    private static BitSet URI_UNRESERVED_CHARACTERS = new BitSet();
    private static String[] PERCENT_ENCODED_STRINGS = new String[256];

    public Util() {
    }

    public static String mkString(Iterator<String> iter, char seprator) {
        if (!iter.hasNext()) {
            return "";
        } else {
            StringBuilder builder = new StringBuilder();

            while(iter.hasNext()) {
                String item = (String)iter.next();
                builder.append(item);
                builder.append(seprator);
            }

            builder.deleteCharAt(builder.length() - 1);
            return builder.toString();
        }
    }

    public static String uriEncode(String value, boolean encodeSlash) {
        try {
            StringBuilder builder = new StringBuilder();
            byte[] var3 = value.getBytes("UTF8");
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                byte b = var3[var5];
                if (URI_UNRESERVED_CHARACTERS.get(b & 255)) {
                    builder.append((char)b);
                } else {
                    builder.append(PERCENT_ENCODED_STRINGS[b & 255]);
                }
            }

            String encodeString = builder.toString();
            if (!encodeSlash) {
                return encodeString.replace("%2F", "/");
            } else {
                return encodeString;
            }
        } catch (UnsupportedEncodingException var7) {
            throw new RuntimeException(var7);
        }
    }

    public static String getCanonicalTime() {
        SimpleDateFormat utcDayFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat utcHourFormat = new SimpleDateFormat("hh:mm:ss");
        utcDayFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        utcHourFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date now = new Date();
        return String.format("%sT%sZ", utcDayFormat.format(now), utcHourFormat.format(now));
    }

    public static byte[] readFileByBytes(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream((int)file.length());
            BufferedInputStream in = null;

            try {
                in = new BufferedInputStream(new FileInputStream(file));
                int bufSize = 1024;
                byte[] buffer = new byte[bufSize];
                boolean var6 = false;

                int len;
                while(-1 != (len = in.read(buffer, 0, bufSize))) {
                    bos.write(buffer, 0, len);
                }

                byte[] var7 = bos.toByteArray();
                return var7;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException var14) {
                    var14.printStackTrace();
                }

                bos.close();
            }
        }
    }

    public static void writeBytesToFileSystem(byte[] data, String output) throws IOException {
        DataOutputStream out = null;

        try {
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(output)));
            out.write(data);
        } finally {
            if (out != null) {
                out.close();
            }

        }

    }

    public static JSONObject getGeneralError(int errorCode, String errorMsg) {
        JSONObject json = new JSONObject();
        try{
            json.put("error_code", errorCode);
            json.put("error_msg", errorMsg);
        }catch (JSONException e){

        }
        return json;
    }

    public static boolean isLiteral(String input) {
        Pattern pattern = Pattern.compile("[0-9a-zA-Z_]*");
        return pattern.matcher(input).matches();
    }

    static {
        int i;
        for(i = 97; i <= 122; ++i) {
            URI_UNRESERVED_CHARACTERS.set(i);
        }

        for(i = 65; i <= 90; ++i) {
            URI_UNRESERVED_CHARACTERS.set(i);
        }

        for(i = 48; i <= 57; ++i) {
            URI_UNRESERVED_CHARACTERS.set(i);
        }

        URI_UNRESERVED_CHARACTERS.set(45);
        URI_UNRESERVED_CHARACTERS.set(46);
        URI_UNRESERVED_CHARACTERS.set(95);
        URI_UNRESERVED_CHARACTERS.set(126);

        for(i = 0; i < PERCENT_ENCODED_STRINGS.length; ++i) {
            PERCENT_ENCODED_STRINGS[i] = String.format("%%%02X", i);
        }

    }
}
