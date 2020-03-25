package cn.moonlord.task;

import cn.moonlord.log.Logger;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class TaskManager {

    private String configFile;

    public TaskManager(String configFile) {
        this.configFile = configFile;
    }

    public void runDailyCycle() throws Exception {
        File file = new File(configFile);
        if(file.exists() == false){
            FileUtils.write(file, "[]","UTF-8");
        }
        String config = FileUtils.readFileToString(file,"UTF-8");
        JSONArray taskList = (JSONArray) JSONArray.parse(config);
        if(taskList == null){
            taskList = new JSONArray();
        }
        Logger.warn("taskList : " + taskList.toJSONString());
    }

}
