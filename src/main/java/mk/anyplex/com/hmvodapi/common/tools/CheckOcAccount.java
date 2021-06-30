package mk.anyplex.com.hmvodapi.common.tools;

import lombok.Data;
import mk.anyplex.com.hmvodapi.common.bean.OcAccount;
import java.util.ArrayList;
import java.util.List;

/**
 * created by andy 2021-04-08
 *   check Oc account
 * */

public class CheckOcAccount {
    // private
    private CheckOcAccount(){}
    //hard code oc account
    private  static List<OcAccount> acList ;
    static {
        acList = new ArrayList<>(16);
        acList.add(new OcAccount("lam.ngarmingdavid@chunwo.com","cw12345678"));
        acList.add(new OcAccount("abby.liu@chunwo.com","cw12345678"));
        acList.add(new OcAccount("sean.wan@chunwo.com","cw12345678"));
        acList.add(new OcAccount("lego.chan@chunwo.com","cw12345678"));
        acList.add(new OcAccount("kester.leung@chunwo.com","cw12345678"));

        // testing
        // acList.add(new OcAccount("972606984@qq.com","cw12345678"));
        // acList.add(new OcAccount(" cclegochan@gmail.com","cw12345678"));

    }
    public static  Boolean check(String account,String pwd){
        boolean flag = false;
        if (account!=null && !account.isEmpty()){
            for (OcAccount ac : acList){
                if (ac.getAccount().equalsIgnoreCase(account) && ac.getPwd().equalsIgnoreCase(pwd)){
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    public static List<OcAccount> getAcList() {
        return acList;
    }

    public static void setAcList(List<OcAccount> acList) {
        CheckOcAccount.acList = acList;
    }
}
