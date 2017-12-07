package com.cloud.resources.configs;

import com.cloud.core.configs.BaseCConfig;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/12/5
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class BaseRConfig extends BaseCConfig {

    private static BaseRConfig baseRConfig = new BaseRConfig();

    public static BaseRConfig getInstance() {
        return baseRConfig;
    }

    public void test() {

    }
}
