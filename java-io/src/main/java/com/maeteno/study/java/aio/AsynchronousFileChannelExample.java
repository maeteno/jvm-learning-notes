package com.maeteno.study.java.aio;

import com.maeteno.study.java.nio.FileChannelExample;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Alan.Fu
 */
@Slf4j
public class AsynchronousFileChannelExample {
    /**
     * 异步IO是通过回调的方式返回数据,是推的模式
     */
    @SneakyThrows
    public static void main(String[] args) {
        URL url = FileChannelExample.class.getClassLoader().getResource("demo.txt");
        assert url != null;
        Path path = Paths.get(url.getPath());

        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(path)) {
            ByteBuffer buffer = ByteBuffer.allocate(200);
            // 参数1: 接受的Buffer
            // 参数2: 开始的位置
            // 参数3: 附件
            // 参数4: 回调对象
            channel.read(buffer, 0, null, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    log.info("completed result: {} data: {}", result, attachment.array());
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    log.error("failed: ", exc);
                }
            });
        }

        log.info("Main End...");
        System.in.read();
    }
}
