
package com.flower.mall.controller.admin;

import com.flower.mall.common.IndexConfigTypeEnum;
import com.flower.mall.common.MyException;
import com.flower.mall.common.ServiceResultEnum;
import com.flower.mall.entity.IndexConfig;
import com.flower.mall.service.FlowerMallIndexConfigService;
import com.flower.mall.util.PageQueryUtil;
import com.flower.mall.util.Result;
import com.flower.mall.util.MallResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;


@Controller
@RequestMapping("/admin")
public class NewBeeMallGoodsIndexConfigController {

    @Resource
    private FlowerMallIndexConfigService flowerMallIndexConfigService;

    @GetMapping("/indexConfigs")
    public String indexConfigsPage(HttpServletRequest request, @RequestParam("configType") int configType) {
        IndexConfigTypeEnum indexConfigTypeEnum = IndexConfigTypeEnum.getIndexConfigTypeEnumByType(configType);
        if (indexConfigTypeEnum.equals(IndexConfigTypeEnum.DEFAULT)) {
            MyException.fail("参数异常");
        }

        request.setAttribute("path", indexConfigTypeEnum.getName());
        request.setAttribute("configType", configType);
        return "admin/index_config";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/indexConfigs/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return MallResult.createFailRes("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return MallResult.createSuccessRes(flowerMallIndexConfigService.getConfigsPage(pageUtil));
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/indexConfigs/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody IndexConfig indexConfig) {
        if (Objects.isNull(indexConfig.getConfigType())
                || StringUtils.isEmpty(indexConfig.getConfigName())
                || Objects.isNull(indexConfig.getConfigRank())) {
            return MallResult.createFailRes("参数异常！");
        }
        String result = flowerMallIndexConfigService.saveIndexConfig(indexConfig);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return MallResult.createSuccessRes();
        } else {
            return MallResult.createFailRes(result);
        }
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/indexConfigs/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody IndexConfig indexConfig) {
        if (Objects.isNull(indexConfig.getConfigType())
                || Objects.isNull(indexConfig.getConfigId())
                || StringUtils.isEmpty(indexConfig.getConfigName())
                || Objects.isNull(indexConfig.getConfigRank())) {
            return MallResult.createFailRes("参数异常！");
        }
        String result = flowerMallIndexConfigService.updateIndexConfig(indexConfig);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return MallResult.createSuccessRes();
        } else {
            return MallResult.createFailRes(result);
        }
    }

    /**
     * 详情
     */
    @GetMapping("/indexConfigs/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        IndexConfig config = flowerMallIndexConfigService.getIndexConfigById(id);
        if (config == null) {
            return MallResult.createFailRes("未查询到数据");
        }
        return MallResult.createSuccessRes(config);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/indexConfigs/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Long[] ids) {
        if (ids.length < 1) {
            return MallResult.createFailRes("参数异常！");
        }
        if (flowerMallIndexConfigService.deleteBatch(ids)) {
            return MallResult.createSuccessRes();
        } else {
            return MallResult.createFailRes("删除失败");
        }
    }


}