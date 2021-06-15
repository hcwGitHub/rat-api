package mk.anyplex.com.hmvodapi.common.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * RequsetParams request公共请求参数
 * @author created by hcw
 * 2020-01-09  11：58
 * */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RequsetParamsVO implements Serializable {

    /**
     * 版本号
     * */
    private String appVersion;

    /**
     * 制造商名称
     * */
    private String deviceModel;

    /**
     *设备号序列
     * */
    private String deviceSerial;

    /**
     *  语言
     *  en-英文，zh_HK-繁体中文
     * */
    private String language;

    /**
     *网络环境
     * */
    private String network;

    /**
     *  操作系统版本号
     * */
    private String osVersion;

    /**
     * 平台：
     * ios_pad
     * ios_phone
     * ios
     * aos_pad
     * aos_phone
     * */
    private String platform;

}
