package com.example.jeffmusic.lyrics;

import com.example.jeffmusic.utils.PlayerUtils;

import java.io.*;
import java.util.ArrayList;

public class LyricsUtils {

    /**
     * 从字符串中获得时间值
     */
    private static long measureStartTimeMillis(String str) {
        long minute = Long.parseLong(str.substring(1, 3));
        long second = Long.parseLong(str.substring(4, 6));
        long millisecond = Long.parseLong(str.substring(7, 9));
        return millisecond + second * 1000 + minute * 60 * 1000;
    }
    /**
     * 逐行解析歌词
     *
     * @param info 实体类
     * @param line 每行内容
     */
    private static void analyzeLyricByLine(LyricInfo info, String line) {

        int index = line.lastIndexOf("]");

        // 标题
        if (!PlayerUtils.isEmptyString(line) && line.startsWith("[ti:")) {
            info.setTitle(line.substring(4, index).trim());
            return;
        }

        // 歌手
        if (!PlayerUtils.isEmptyString(line) && line.startsWith("[ar:")) {
            info.setArtist(line.substring(4, index).trim());
            return;
        }

        // 专辑
        if (!PlayerUtils.isEmptyString(line) && line.startsWith("[al:")) {
            info.setAlbum(line.substring(4, index).trim());
            return;
        }

        // 制作
        if (!PlayerUtils.isEmptyString(line) && line.startsWith("[by:")) {
            info.setBy(line.substring(4, index).trim());
            return;
        }

        // 偏移量
        if (!PlayerUtils.isEmptyString(line) && line.startsWith("[offset:")) {
            info.setOffset(Long.parseLong(line.substring(8, index).trim()));
            return;
        }

        // 歌词内容
        if (line!=null && index == 9 && line.trim().length() >= 10) {
            LineInfo lineInfo = new LineInfo();
            lineInfo.setStartTime(measureStartTimeMillis(line.substring(0, 10)));
            if(line.length()==10){
                lineInfo.setContent("");
            }else{
                lineInfo.setContent(line.substring(10, line.length()));
            }
            info.getLines().add(lineInfo);// 添加到歌词集合中
            return;
        }

        return;
    }

    /**
     * 解析歌词
     *
     * @param ins
     * @param charsetName
     */
    public static LyricInfo initLyric(InputStream ins, String charsetName) {

        if (ins == null) return null;

        try {
            LyricInfo lyricInfo = new LyricInfo();
            lyricInfo.setLines(new ArrayList<LineInfo>());

            InputStreamReader inputStreamReader = new InputStreamReader(ins, charsetName);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = null;
            // 逐行解析
            while ((line = reader.readLine()) != null) {
                analyzeLyricByLine(lyricInfo, line);
            }
            reader.close();
            ins.close();
            inputStreamReader.close();

            return lyricInfo;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
