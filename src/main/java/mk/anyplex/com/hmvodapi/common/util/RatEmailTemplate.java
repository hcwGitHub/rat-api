package mk.anyplex.com.hmvodapi.common.util;

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class RatEmailTemplate {

    /**
     *  entry id , twc hir or hir temp entry id
     * */
    private String id ;

    /**
     * email subject
     * */
    private  String subject;

    /**
     *  email text
     * */
    private String content;

    /**
     *  oc ， mobility
     * */
    private String type;

    /**
     *  type name
     *  0 : twc
     *  1: hir
     *  2: hir_temp
     * */
    private String type_name;

    private String entry;

    /**
     *  project id
     * */
    private String projectId;

    /**
     *  Operation type
     *  1 : created
     *  2: update
     * */
    private String operationType;

    /**
     *  created, 操作者
     * */
    private String created;

    /**
     *  id
     *  type: oc  or mobility
     *  type_name : 0 twc , 1 hir 2 hir temp
     *  projectId
     *  operationType 1 created 2 update
     *  created : creator
     * */
    public RatEmailTemplate(String id, String type ,String type_name, String projectId, String operationType, String created) {
        this.id = id;
        this.type = type;
        this.type_name = type_name;
        this.projectId = projectId;
        this.operationType = operationType;
        this.created = created;
        // auto
        getSyntheticEmailText();
    }

    /**
     *  生成email text 主題 and 内容
     * @return String Email text
     * */
    private RatEmailTemplate  getSyntheticEmailText(){

       String hyperlink = "https://mobility-uat.chunwo.com/projects/nd-2019-04/rat/informative?";
       String link = "http://git.iman.io/#/";
       String twcLink = link+"techdSection2/viewTwcDetail?id={id}&type={type}&email=0";
       String hirOrHirTempLink = link+ "techdSection3/viewHirDetail?id={id}&type={type}&type_name={type_name}&email=0";

        content = "<html>\n" +
                "<body>\n" +
                " <div style='width:100%;'>" +
                "<p>Dear Sir/Madam, \n </p>" +
                "<p>[{created}] {mainText}</p>" +
                "<p>[<a href='{hyperlink}'>{hyperlink} </a>]</p>" +
                "<p>This is a system-generated message, please do not reply this email.</p>" +
                " </div>" +
                "</body>\n" +
                "</html>";
        String  mainText = "";

        if (StringUtils.isEmpty(type_name)||"1".equals(type_name)){
            entry = "twc";
            hyperlink =  hyperlink + "id="+id+"&type="+type+"&type_name="+type_name+"&entry="+entry;

            // 1 twc 類型
            if ("1".equals(this.operationType)){
                // 1.1 twc created
                subject = "[RAT] ("+ projectId+") New TWC" ;
                mainText = "made a new TWC entry. ";

            }else{
                // 1.2 twc update
                subject =  "[RAT] ("+projectId+") Update TWC";
                mainText = "updated the TWC entry.";
            }

        }else if ("2".equals(type_name)){
            entry = "hir";
            hyperlink =  hyperlink + "id="+id+"&type="+type+"&type_name="+type_name+"&entry="+entry;
            // 2 hir 類型
            if ("1".equals(this.operationType)){
                // 2.1 hir created
                subject = "[RAT] ("+ projectId+") New HIR" ;
                mainText = "made a new Hir entry. ";

            }else{
                // 2.2 twc update
                subject =  "[RAT] ("+projectId+") Update HIR";
                mainText = "updated the HIR entry.";
            }

        }else if ("3".equals(type_name)) {
            entry = "hir"; // 也是hir , 不是hir_temp
            hyperlink =  hyperlink + "id="+id+"&type="+type+"&type_name="+type_name+"&entry="+entry;
            //3 hir temp 類型
            if ("1".equals(this.operationType)){
                // 3.1 hir temp  created
                subject = "[RAT] ("+ projectId+")New Temporary Works Submission Schedule" ;
                mainText = "made a new Temporary Works Submission Schedule entry.";

            }else{
                // 3.2  hir temp  update
                subject =  "[RAT] ("+projectId+") Update Temporary Works Submission Schedule";
                mainText = "updated the Temporary Works Submission Schedule entry.";
            }

        }
        // replace

        if ("mobility".equals(type)) {
            this.content =
                    content.replace("{created}", created)
                            .replace("{hyperlink}", hyperlink)
                            .replace("{mainText}", mainText);
        }

        // 新需求, 如果是OC 使用不一样的域名
        if ("oc".equals(type)){
            String linked = "";
            if (StringUtils.isEmpty(type_name) || "1".equals(type_name)){
                // twc
                linked = twcLink.replace("{id}",id).replace("{type}",type);
            }else {
                // hir or hir temp ;
                linked = hirOrHirTempLink.replace("{id}", id).replace("{type}", type).replace("{type_name}", type_name);
            }

           this.content = content.replace("{created}", created)
                   .replace("{hyperlink}", linked)
                   .replace("{mainText}", mainText);
        }

        return this;
    }


}
