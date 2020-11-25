package com.hust.ResultHandler;

import com.hust.entity.User;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import java.util.ArrayList;
import java.util.List;

public class UserResultHandler implements ResultHandler<User> {
    // 存储每批数据的临时容器
    private List<User> resultInfoList = new ArrayList<>();

    public List<User> getResultInfoList() {
        return resultInfoList;
    }

    @Override
    public void handleResult(ResultContext<? extends User> resultContext) {
        User user = resultContext.getResultObject();
        resultInfoList.add(user);
    }
}
