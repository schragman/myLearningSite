package beans;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class UserBean {

    @Resource
    private SessionContext ctx;

    public String getUsername() {
        return ctx.getCallerPrincipal().getName();
    }

}
