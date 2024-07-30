package com.ruoyi.framework.aspectj;

import com.ruoyi.common.annotation.KmsScope;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.SysMenuMapper;
import com.ruoyi.system.service.ISysMenuService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@Component
public class KmsAspect {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private ISysMenuService menuService;

    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_SCOPE = "dataScope";

    //可阅读者表后缀
    private static final String READER_SUFFIX = "_reader";

    @Before("@annotation(controllerDataScope)")
    public void doBefore(JoinPoint point, KmsScope controllerDataScope) throws Throwable
    {
        clearDataScope(point);
        handleDataScope(point, controllerDataScope);
    }

    protected void handleDataScope(final JoinPoint joinPoint, KmsScope controllerDataScope)
    {
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNotNull(loginUser))
        {
            SysUser currentUser = loginUser.getUser();
            SysMenu menu = sysMenuMapper.selectMenuByPerms("kms:main:viewAll");
            List<SysMenu> menus = menuService.selectMenuTreeByUserId(currentUser.getUserId());
            Long[] menuIds = menus.stream().map(SysMenu::getMenuId).collect(Collectors.toList()).toArray(new Long[]{});
            boolean hasViewAll = false;
            for (Long menuId : menuIds) {
                if(menuId==menu.getMenuId()){
                    hasViewAll = true;
                    break;
                }
            }
            // 如果是超级管理员，则不过滤数据
            if ((StringUtils.isNotNull(currentUser) && !currentUser.isAdmin()) || hasViewAll)
            {
                dataScopeFilter(joinPoint, currentUser);
            }
        }
    }

    /**
     * 数据范围过滤
     *
     * @param joinPoint 切点
     * @param user 用户
     */
    public static void dataScopeFilter(JoinPoint joinPoint, SysUser user)
    {
        StringBuilder sqlString = new StringBuilder();

        sqlString.append("(state = '30' OR create_user_id = '"+ user.getUserId()+"')");

        //String tableName = "kms_main";
        //String readerTableName = tableName + READER_SUFFIX;
        //sqlString.append(StringUtils.format("OR (id IN (SELECT know_id FROM {} WHERE user_id = '{}' ))", readerTableName, user.getUserId()));

        if (StringUtils.isNotBlank(sqlString.toString()))
        {
            Object params = joinPoint.getArgs()[0];
            if (StringUtils.isNotNull(params) && params instanceof BaseEntity)
            {
                BaseEntity baseEntity = (BaseEntity) params;
                baseEntity.getParams().put(DATA_SCOPE, " AND (" + sqlString.toString() + ")");
            }
        }
    }

    /**
     * 拼接权限sql前先清空params.dataScope参数防止注入
     */
    private void clearDataScope(final JoinPoint joinPoint)
    {
        Object params = joinPoint.getArgs()[0];
        if (StringUtils.isNotNull(params) && params instanceof BaseEntity)
        {
            BaseEntity baseEntity = (BaseEntity) params;
            if(baseEntity.getParams()==null) {
                Map<String, Object> p = new HashMap<>();
                baseEntity.setParams(p);
            }
            baseEntity.getParams().put(DATA_SCOPE, "");
        }
    }
}
