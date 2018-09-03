package com.xz.lwjk.event.facade.ThreadDemo;

import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @Auther: BUCHU
 * @Date: 2018/8/17 17:25
 * @Description:
 */
public class    ThreadDemo {
    ThreadPoolExecutor executor = new ThreadPoolExecutor(3,
            6, 5, TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>());
    CompletionService completionService = new ExecutorCompletionService<Integer>(executor);

    @Test
    public void demo() {
        Map<Integer, String> maps = Maps.newConcurrentMap();
        maps.put(1, "1");
        maps.put(2, "2");
        maps.put(3, "3");
        maps.put(4, "4");
        maps.put(5, "5");
        maps.put(6, "6");
        maps.put(7, "7");
        maps.put(8, "8");
        maps.put(9, "9");
        maps.put(10, "10");
        for (Integer key : maps.keySet()) {
            System.out.println("Key = " + key);
            completionService.submit(new Task(key,maps));
        }
        // 按照完成顺序,打印结果
        for (int i = 0; i < maps.size(); i++) {
            try {
                System.out.println("线程返回结果"+completionService.take().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

}

class Task implements Callable<Integer> {
    private Integer s;
    Map<Integer, String> maps;
    public Task(Integer s,Map maps) {
        this.s = s;
        this.maps=maps;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("*******:" + Thread.currentThread().getName() + ":" + maps.get(s));
        return s;
    }
}
