package ${packageName};
/**
*
* ${comment}
* @author ${author}
* @since ${date}
*/
public class ${repository} {

    <#list operators as op>
    public void ${op}${entity}(){
    }
    </#list>
}