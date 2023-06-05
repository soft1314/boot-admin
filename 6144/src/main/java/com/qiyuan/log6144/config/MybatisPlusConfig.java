package com.qiyuan.log6144.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.qiyuan.bautil.util.StringUtil;
import com.qiyuan.bautil.util.TenantContextHolder;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author miemie
 * @since 2018-08-10
 */
@Configuration
@MapperScan("com.qiyuan.base2048.mapper.mybatis")
public class MybatisPlusConfig {

    /**
     * 新多租户插件配置,一缓和二缓遵循mybatis的规则,
     * 需要设置 MybatisConfiguration#useDeprecatedExecutor = false
     * 避免缓存万一出现问题
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //多租户插件
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                String tenant = TenantContextHolder.getTenantId();
                if (StringUtil.isNotBlank(tenant)) {
                    return new StringValue(tenant);
                }
                return new StringValue("Default");
            }

            @Override
            public String getTenantIdColumn() {
                return "TENANT_ID_";
            }

            // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
            @Override
            public boolean ignoreTable(String tableName) {
                /** 排除 flowable 用表 **/
                if(tableName.toUpperCase().startsWith("ACT_")){
                    return true;
                }
                /** 排除QUARTZ 用表 **/
                if(tableName.toUpperCase().startsWith("QRTZ_")){
                    return true;
                }
                /** 排除部分Oracle字典表 user_tab_columns user_col_comments **/
                if(tableName.toUpperCase().startsWith("USER_")){
                    return true;
                }
                if("V_TABLES_MASTER".equalsIgnoreCase(tableName)){
                    return true;
                }
                return false;//!"user".equalsIgnoreCase(tableName);
            }
        }));
        //分页插件
        // 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
        // 用了分页插件必须设置 MybatisConfiguration#useDeprecatedExecutor = false
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        //防止全表更新插件
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        //乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }
}

