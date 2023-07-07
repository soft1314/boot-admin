package ${service.packageUrl}.impl;

import ${service.entityPackageUrl}.${service.entityName};
import ${service.mapperPackageUrl}.${service.entityName}Mapper;
import ${service.packageUrl}.I${service.entityName}Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* <p>
* 本文件自动生成，严禁人工修改
* ${service.tableComment} 服务实现类
* </p>
*
* @author ${service.author}
* @since ${.now}
*/
@Service
public class ${service.entityName}ServiceImpl extends ServiceImpl<${service.entityName}Mapper, ${service.entityName}> implements I${service.entityName}Service {

}