package com.relay.protection.pojo.converter;


import com.relay.protection.pojo.bo.*;
import com.relay.protection.pojo.input.*;
import com.relay.protection.pojo.output.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

/**
 * @author zhuHx
 * @date 2022/03/14 13:20
 */
@Mapper(componentModel = "spring")
@Component
public interface DtoConverter {

    SysUser userSaveVo2Bo(UserSaveInput from);

    SysUser userUpdateVo2Bo(UserUpdateInput from);

    UserOutput userBo2Vo(SysUser from);

    SysRole roleSaveVo2Bo(RoleSaveInput from);

    SysRole roleUpdateVo2Bo(RoleUpdateInput from);

    RoleOutput roleBo2Vo(SysRole from);

    DeptOutput deptBo2Output(SysDept from);

    SysDept deptSave2Bo(DeptSaveInput from);

    SysDept deptUpdate2Bo(DeptUpdateInput from);

    DirOutput dirBo2Output(BizDir from);

    BizDir dirSave2Bo(DirSaveInput from);

    BizDir dirUpdate2Bo(DirUpdateInput from);

    FileOutput fileBo2Vo(BizFile from);

    BizFile fileSaveVo2Bo(FileSaveInput from);


    BizOrder applySave2Bo(OrderFlowInput from);

    OrderListOutput orderListBo2Output(BizOrder from);

    @Mappings({@Mapping(target = "flowList", source = "flowList")})
    OrderInfoOutput orderInfoBo2Output(BizOrder from);

    OrderTemplateOutput templateBo2Output(BizOrderTemplate from);

    BizOrderTemplate templateSave2Bo(TemplateSaveInput from);

    TemplateSaveInput templateSave2Template(TemplateDirSaveInput from);

    BizOrderTemplate templateUpdate2Bo(TemplateDirUpdateInput from);

}