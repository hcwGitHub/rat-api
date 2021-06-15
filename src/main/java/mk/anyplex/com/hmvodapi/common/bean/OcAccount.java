package mk.anyplex.com.hmvodapi.common.bean;

import lombok.Data;

/**
 *   created by andy 2021-04-08
 *   hard Code oc account
 * */
@Data
public class OcAccount {
    private String account ;

    private String pwd;

    public OcAccount(String account, String pwd) {
        this.account = account;
        this.pwd = pwd;
    }

    public OcAccount() {
    }
}
