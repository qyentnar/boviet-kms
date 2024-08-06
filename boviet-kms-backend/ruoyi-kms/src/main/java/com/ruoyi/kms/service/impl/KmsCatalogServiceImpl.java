package com.ruoyi.kms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.kms.mapper.KmsCatalogMapper;
import com.ruoyi.kms.domain.KmsCatalog;
import com.ruoyi.kms.dto.KmsTreeSelect;
import com.ruoyi.kms.service.IKmsCatalogService;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2022-05-30
 */
@Service
public class KmsCatalogServiceImpl implements IKmsCatalogService
{
    @Autowired
    private KmsCatalogMapper kmsCatalogMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public KmsCatalog selectKmsCatalogById(Long id)
    {
        return kmsCatalogMapper.selectKmsCatalogById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param kmsCatalog 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<KmsCatalog> selectKmsCatalogList(KmsCatalog kmsCatalog)
    {
        return kmsCatalogMapper.selectKmsCatalogList(kmsCatalog);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param kmsCatalog 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertKmsCatalog(KmsCatalog kmsCatalog)
    {
        kmsCatalog.setCreateTime(DateUtils.getNowDate());
        return kmsCatalogMapper.insertKmsCatalog(kmsCatalog);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param kmsCatalog 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateKmsCatalog(KmsCatalog kmsCatalog)
    {
        kmsCatalog.setUpdateTime(DateUtils.getNowDate());
        return kmsCatalogMapper.updateKmsCatalog(kmsCatalog);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteKmsCatalogByIds(Long[] ids)
    {
        return kmsCatalogMapper.deleteKmsCatalogByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteKmsCatalogById(Long id)
    {
        return kmsCatalogMapper.deleteKmsCatalogById(id);
    }

     
    @Override
    public List<KmsTreeSelect> buildCatalogTreeSelect(List<KmsCatalog> catalogs){
        List<KmsCatalog> list = buildCatalogTree(catalogs);
        return list.stream().map(KmsTreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public List<KmsCatalog> buildCatalogTree(List<KmsCatalog> catalogs){
        List<KmsCatalog> returnList = new ArrayList<KmsCatalog>();
        List<Long> ids = new ArrayList<Long>();
        for (KmsCatalog catalog : catalogs) {
            ids.add(catalog.getId());
        }
        for (KmsCatalog catalog : catalogs) {
            if (!ids.contains(catalog.getParentId())) {
                List<KmsCatalog> listChildrent = getChildList(catalogs, catalog);
                catalog.setChildren(listChildrent);
                returnList.add(catalog);
            }
        }
        if (returnList.isEmpty()) {
            returnList = catalogs;
        }
        return returnList;
    }

    private List<KmsCatalog> getChildList(List<KmsCatalog> catalogs, KmsCatalog catalog) {
        List<KmsCatalog> childList = new ArrayList<KmsCatalog>();
        for (KmsCatalog kmsCatalog : catalogs) {
            if(StringUtils.isNotNull(kmsCatalog.getParentId())){
                if(kmsCatalog.getParentId().longValue() == catalog.getId().longValue()){
                    if(hashChild(catalogs, kmsCatalog)){
                        List<KmsCatalog> n = getChildList(catalogs, kmsCatalog);
                        kmsCatalog.setChildren(n);
                    }
                    childList.add(kmsCatalog);
                }
            }
        }
        return childList;
    }

    private Map<String, Object> toMapTreeSelect(KmsCatalog catalog){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", catalog.getId());
        map.put("label", catalog.getTitle());
        map.put("children", catalog.getChildren());
        return map;
    }

    private Boolean hashChild(List<KmsCatalog> catalogs, KmsCatalog catalog){
        return getChildList(catalogs, catalog).size() > 0;
    }
}
