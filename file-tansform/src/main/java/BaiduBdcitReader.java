
/**
 * 功能：解析百度词库文件(bdict)，返回存储词语的list
 */
import java.io.ByteArrayOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.util.*;

/**
 * @author mati
 * @since 2020/1/15 16:16
 */
public class BaiduBdcitReader
{
    /**
     *  读取百度词库文件(bdict)，返回一个包含所以词的list
     * @param bdictFilePath : bdict文件的路径
     * @return: 包含词库文件中所有词的一个List<String>
     * @throws Exception
     */
    public static Set<String> readBdictFile(String bdictFilePath) throws Exception
    {
        // read bdict into byte array
        ByteArrayOutputStream dataOut = new ByteArrayOutputStream();
        FileChannel fChannel = new RandomAccessFile(bdictFilePath, "r").getChannel();
        fChannel.transferTo(0, fChannel.size(), Channels.newChannel(dataOut));
        fChannel.close();
        ByteBuffer dataRawBytes = ByteBuffer.wrap(dataOut.toByteArray());
        dataRawBytes.order(ByteOrder.LITTLE_ENDIAN);

        byte[] buf = new byte[1024];
        byte[] pinyin = new byte[1024];
        // dictionary offset
        dataRawBytes.position(0x350);

        Set<String> wordList = new HashSet<>();
        String word = null;
        while (dataRawBytes.position() < dataRawBytes.capacity())
        {
            int length = dataRawBytes.getShort(); //得到词的字节长度
            dataRawBytes.getShort();
            try
            {
                dataRawBytes.get(pinyin,0,2 * length); //跳过拼音
                dataRawBytes.get(buf, 0, 2 * length);  //得到实际的词
                word = new String(buf, 0, 2 * length, "UTF-16LE");
                wordList.add(word);
            }
            catch (Exception e)
            {
                return wordList;
            }

        }
        return wordList;

    }

}
