package com.ruoyi.kms.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.ruoyi.activiti.domain.SysActivitiTemplate;
import com.ruoyi.activiti.mapper.SysActivitiTemplateMapper;
import com.ruoyi.activiti.service.ISysActivitiTemplateService;
import com.ruoyi.attachment.domain.SysAttMain;
import com.ruoyi.attachment.mapper.SysAttMainMapper;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.kms.constant.KmsConstant;
import com.ruoyi.kms.domain.KmsCatalog;
import com.ruoyi.kms.domain.KmsMainToAuthor;
import com.ruoyi.kms.dto.KmsMainDto;
import com.ruoyi.kms.dto.KmsMainUserDto;
import com.ruoyi.kms.dto.KmsMainUserListDto;
import com.ruoyi.kms.dto.KmsSearchDto;
import com.ruoyi.kms.mapper.KmsCatalogMapper;
import com.ruoyi.kms.service.IKmsMainToAuthorService;
import com.ruoyi.kms.vo.KmsMainVo;
import com.ruoyi.system.service.ISysUserService;
import lombok.SneakyThrows;

import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.compress.utils.Lists;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.kms.mapper.KmsMainMapper;
import com.ruoyi.kms.domain.KmsMain;
import com.ruoyi.kms.service.IKmsMainService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static java.util.stream.Collectors.toList;

/**
 * 知识Service业务层处理
 *
 * @author ruoyi
 * @date 2022-05-25
 */
@Service
public class KmsMainServiceImpl  extends ServiceImpl<KmsMainMapper, KmsMain> implements IKmsMainService
{
    @Autowired
    private KmsMainMapper kmsMainMapper;

    @Autowired
    private KmsCatalogMapper kmsCatalogMapper;

    @Autowired
    private SysAttMainMapper sysAttMainMapper;

    @Resource
    private RestHighLevelClient esClient;

    @Autowired
    private IKmsMainToAuthorService kmsMainToAuthorService;

    @Autowired
    private ISysActivitiTemplateService sysActivitiTemplateService;

    @Autowired
    private ISysUserService sysUserService;

    @Resource
    private TaskService taskService;

    public final Integer NEW_VERSION_YES = 1;
    public final Integer NEW_VERSION_NO = 0;

    /**
     * 查询知识
     *
     * @param id 知识主键
     * @return 知识
     */
    @SneakyThrows
    @Override
    public KmsMainVo selectKmsMainById(Long id)
    {

        /*String index = "kms_index";
        GetRequest esRequest = new GetRequest(index, id.toString());
        GetResponse documentFields = esClient.get(esRequest, RequestOptions.DEFAULT);
        if (documentFields.isExists()) {
            String sourceAsString = documentFields.getSourceAsString();
            JSONObject jsonObject = JSONObject.parseObject(sourceAsString);
            System.out.println(jsonObject);
        }*/
        KmsMainVo kmsMain = kmsMainMapper.selectKmsMainById(id);
        KmsMainVo kmsMainVo = new KmsMainVo();
        BeanUtils.copyProperties(kmsMain,kmsMainVo);
        // 作者
        List<KmsMainToAuthor> authorList = kmsMainToAuthorService.selectKmsMainToAuthorByKId(id);
        List<String> authorNameList = authorList.stream().map(KmsMainToAuthor::getAuthorName).collect(toList());
        kmsMainVo.setExtAuthor(authorNameList);
        List<KmsMain> mains = baseMapper.selectList(new LambdaQueryWrapper<KmsMain>()
                .eq(KmsMain::getIsNewVersion, 1)
                .eq(KmsMain::getOriginId, kmsMain.getOriginId()));
        if(CollUtil.isNotEmpty(mains)){
            kmsMainVo.setCurrentVersionId(mains.get(0).getId());
        }
        return kmsMainVo;
    }

    /**
     * 查询知识列表
     *
     * @param kmsMain 知识
     * @return 知识
     */
    @SneakyThrows
    @Override
    public List<KmsMainVo> selectKmsMainList(KmsMainDto kmsMain)
    {
        PageDomain pageDomain = TableSupport.getPageDomain();
        PageHelper.startPage(pageDomain.getPageNum(), pageDomain.getPageSize(), pageDomain.getOrderBy());
        
        List<KmsMainVo> kmsMains = kmsMainMapper.selectKmsMainList(kmsMain);
        // LoginUser sysUser = SecurityUtils.getLoginUser();
        // List<Task> tasks = taskService.createTaskQuery().taskAssignee(sysUser.getUsername()).list();
        // for (Task task : tasks) {
        //     KmsMainVo main = kmsMainMapper.selectKmsMainByProcessIntanceId(task.getProcessInstanceId());
        //     if(main != null) {
        //         kmsMains.add(main);
        //     }
        //  }
        //List<KmsMainVo> kmsMainVos = new ArrayList<>();
        kmsMains.stream().forEach(item-> {
            // 作者
            List<KmsMainToAuthor> authorList = kmsMainToAuthorService.selectKmsMainToAuthorByKId(item.getId());
            List<String> authorNames = authorList.stream().map(KmsMainToAuthor::getAuthorName).collect(toList());
            //KmsMainVo kmsMainVo = new KmsMainVo();
            //BeanUtils.copyProperties(item, kmsMainVo);
            item.setExtAuthor(authorNames);
            //kmsMainVos.add(kmsMainVo);
        });
        // kmsMains.sort((a,b) -> a.getId().compareTo(b.getId()));
        return kmsMains;
    }

    /**
     * 新增知识
     *
     * @param kmsMainDto 知识
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertKmsMain(KmsMainDto kmsMainDto) throws IOException {
        kmsMainDto.setCreateTime(DateUtils.getNowDate());
        //kmsMainDto.setPublishDate(DateUtils.getNowDate());
        Integer version = 1;
        //新版本暂存或提交
        if (1<kmsMainDto.getOperation()) {
            // 获取当前最新版本知识
            KmsMainVo currentMain = kmsMainMapper.selectKmsMainById(kmsMainDto.getCurrentVersionId());
            Long originId = currentMain.getOriginId()!=null?currentMain.getOriginId():currentMain.getId();
            if(3==kmsMainDto.getOperation()){
                kmsMainDto.setIsNewVersion(1);
                kmsMainDto.setPublishDate(DateUtils.getNowDate());
                //当前版本置为非最新版本
                currentMain.setIsNewVersion(0);
                KmsMain currentMainBean = new KmsMain();
                BeanUtils.copyProperties(currentMain,currentMainBean);
                kmsMainMapper.updateKmsMain(currentMainBean);
            }else if(2==kmsMainDto.getOperation()){
                kmsMainDto.setIsNewVersion(0);
            }
            // 版本+1
            version = kmsMainDto.getVersion()+1;
            // 原始版本id
            kmsMainDto.setOriginId(originId);
        }else{
            if(0==kmsMainDto.getOperation()){
                kmsMainDto.setIsNewVersion(0);
            } else  if(1==kmsMainDto.getOperation()){
                kmsMainDto.setPublishDate(DateUtils.getNowDate());
                kmsMainDto.setIsNewVersion(1);
            }
            // 原始版本id
            kmsMainDto.setOriginId(kmsMainDto.getId());  //id这时还是空的
        }
        kmsMainDto.setVersion(version);
        int row = kmsMainMapper.insertKmsMain(kmsMainDto);
        saveIndex(kmsMainDto);

        //id这时还是空的 ：kmsMainDto.setOriginId(kmsMainDto.getId());
        if (kmsMainDto.getOriginId()==null) {
            kmsMainDto.setOriginId(kmsMainDto.getId());
            KmsMain kmsMain = new KmsMain();
            BeanUtils.copyProperties(kmsMainDto,kmsMain);
            kmsMainMapper.updateKmsMain(kmsMain); //普通暂存、提交 默认原始版本id为当前id
        }

        // 保存作者
        List<String> authors = kmsMainDto.getExtAuthor();
        kmsMainToAuthorService.batchAdd(kmsMainDto.getId(),authors);

        if(!StringUtils.isEmpty(kmsMainDto.getAttMainIds())){
            String[] attIds = kmsMainDto.getAttMainIds().split(",");
            for(String id:attIds){
                if(!StringUtils.isEmpty(id)) {
                    SysAttMain sysAttMain = new SysAttMain();
                    sysAttMain.setModelId(kmsMainDto.getId());
                    sysAttMain.setModelName(KmsMain.class.getName());
                    sysAttMain.setId(Long.parseLong(id));
                    sysAttMainMapper.updateAttMain(sysAttMain);
                }
            }
        }

        //保存es数据 （暂时先注释）
        //saveIndex(kmsMainDto);

        //判断是否启动默认流程

        // KmsMain main = new KmsMain();
        // BeanUtils.copyProperties(kmsMainDto,main);
        // this.submitProcessApply(main);
        return row;
    }

    /**
     * 修改知识
     *
     * @param kmsMainDto 知识
     * @return 结果
     */
    @Override
    public int updateKmsMain(KmsMainDto kmsMainDto)
    {
        kmsMainDto.setUpdateTime(DateUtils.getNowDate());

        KmsMainVo me = kmsMainMapper.selectKmsMainById(kmsMainDto.getId());
        if("30".equals(me.getState())){

        }else {
            //新版本暂存或提交
            if (1 < kmsMainDto.getOperation()) {
                // 获取当前最新版本知识
                KmsMainVo currentMain = kmsMainMapper.selectKmsMainById(kmsMainDto.getCurrentVersionId());
                if (3 == kmsMainDto.getOperation()) {
                    kmsMainDto.setIsNewVersion(1);
                    kmsMainDto.setPublishDate(DateUtils.getNowDate());
                    //当前版本置为非最新版本
                    currentMain.setIsNewVersion(0);
                    KmsMain currentMainBean = new KmsMain();
                    BeanUtils.copyProperties(currentMain,currentMainBean);
                    kmsMainMapper.updateKmsMain(currentMainBean);
                } else if (2 == kmsMainDto.getOperation()) {
                    kmsMainDto.setIsNewVersion(0);
                }
            } else {
                if (0 == kmsMainDto.getOperation()) {
                    kmsMainDto.setIsNewVersion(0);
                } else if (1 == kmsMainDto.getOperation()) {
                    kmsMainDto.setPublishDate(DateUtils.getNowDate());
                    kmsMainDto.setIsNewVersion(1);
                }
            }
        }
        // 保存作者
        List<String> authors = kmsMainDto.getExtAuthor();
        kmsMainToAuthorService.batchAdd(kmsMainDto.getId(),authors);

        KmsMain kmsMain = new KmsMain();
        BeanUtils.copyProperties(kmsMainDto,kmsMain);
        int row = kmsMainMapper.updateKmsMain(kmsMain);

        //更新es（暂时先注释）
        updateIndex(kmsMain);

        return row;
    }

    /**
     * 修改知识
     *
     * @param kmsMainDto 知识
     * @return 结果
     */
    @Override
    public int updateKmsMain2(KmsMainDto kmsMainDto)
    {
        kmsMainDto.setUpdateTime(DateUtils.getNowDate());

        KmsMainVo me = kmsMainMapper.selectKmsMainById(kmsMainDto.getId());
        if("30".equals(me.getState())){

        }else {
            //新版本暂存或提交
            if (1 < kmsMainDto.getOperation()) {
                // 获取当前最新版本知识
                KmsMainVo currentMain = kmsMainMapper.selectKmsMainById(kmsMainDto.getCurrentVersionId());
                if (3 == kmsMainDto.getOperation()) {
                    kmsMainDto.setIsNewVersion(1);
                    kmsMainDto.setPublishDate(DateUtils.getNowDate());
                    //当前版本置为非最新版本
                    currentMain.setIsNewVersion(0);
                    KmsMain currentMainBean = new KmsMain();
                    BeanUtils.copyProperties(currentMain,currentMainBean);
                    kmsMainMapper.updateKmsMain(currentMainBean);
                } else if (2 == kmsMainDto.getOperation()) {
                    kmsMainDto.setIsNewVersion(0);
                }
            } else {
                if (0 == kmsMainDto.getOperation()) {
                    kmsMainDto.setIsNewVersion(0);
                } else if (1 == kmsMainDto.getOperation()) {
                    kmsMainDto.setPublishDate(DateUtils.getNowDate());
                    kmsMainDto.setIsNewVersion(1);
                }
            }
        }
        // 保存作者
        List<String> authors = kmsMainDto.getExtAuthor();
        kmsMainToAuthorService.batchAdd(kmsMainDto.getId(),authors);

        KmsMain kmsMain = new KmsMain();
        BeanUtils.copyProperties(kmsMainDto,kmsMain);
        int row = kmsMainMapper.updateKmsMain(kmsMain);

        //更新es（暂时先注释）
        updateIndex(kmsMain);

        return row;
    }

    /**
     * 批量删除知识
     *
     * @param ids 需要删除的知识主键
     * @return 结果
     */
    @SneakyThrows
    @Override
    public int deleteKmsMainByIds(Long[] ids)
    {
        String index = "kms_index";
        for (Long id : ids) {
            DeleteRequest deleteRequest = new DeleteRequest(index, id.toString());
            deleteRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
            DeleteResponse delete = esClient.delete(deleteRequest, RequestOptions.DEFAULT);
            System.out.println(("删除结果：" + delete.status().toString()));
        }
        sysAttMainMapper.deleteByIds(ids);
        return kmsMainMapper.deleteKmsMainByIds(ids);
    }

    /**
     * 删除知识信息
     *
     * @param id 知识主键
     * @return 结果
     */
    @SneakyThrows
    @Override
    public int deleteKmsMainById(Long id)
    {
        String index = "kms_index";
        DeleteRequest deleteRequest = new DeleteRequest(index, id.toString());
        deleteRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        DeleteResponse delete = esClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(("删除结果：" + delete.status().toString()));

        return kmsMainMapper.deleteKmsMainById(id);
    }

    @Override
    public List<KmsMain> selectForEs(KmsSearchDto kmsSearchDto) {
        // 1.创建并设置SearchSourceBuilder对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 2.创建BoolQueryBuilder对象
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 3.设置boolQueryBuilder条件
        //MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery(kmsSearchDto.getSearchKey(), kmsSearchDto.getSearchValue());
        // 4.添加查询条件到boolQueryBuilder中
        //boolQueryBuilder.must(matchPhraseQueryBuilder);
        if("1".equals(kmsSearchDto.getSearchBy())) {
            boolQueryBuilder.should(QueryBuilders.termsQuery(kmsSearchDto.getSearchKey(), kmsSearchDto.getSearchValue()));
        }
        else if("2".equals(kmsSearchDto.getSearchBy())) {
            boolQueryBuilder.should(QueryBuilders.wildcardQuery(kmsSearchDto.getSearchKey(), "*" + kmsSearchDto.getSearchValue() + "*"));
        }
        // 查询条件--->生成DSL查询语句
        searchSourceBuilder.query(boolQueryBuilder);
        Integer pageNum = 1;
        Integer pageSize = kmsSearchDto.getPageSize();
        // 第几页
        searchSourceBuilder.from((pageNum - 1) * pageSize);
        // 每页多少条数据
        searchSourceBuilder.size(pageSize);
        // 设置排序规则
        String sortBy = kmsSearchDto.getSortBy();
        SortOrder sortType = kmsSearchDto.getSortType().equals("1") ? SortOrder.DESC : SortOrder.ASC;
        searchSourceBuilder.sort(sortBy, sortType);
        SearchRequest searchRequest = new SearchRequest("kms_index");
        searchRequest.source(searchSourceBuilder);
        //返回信息
        List<KmsMain> rsList = Lists.newArrayList();
        try {
            SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
            //解析搜索结果
            for (SearchHit sh : response.getHits()) {
                Map<String, Object> sourceAsMap = sh.getSourceAsMap();
                /*for (Map.Entry<String, Object> stringObjectEntry : sourceAsMap.entrySet()) {
                    System.out.println(sourceAsMap.get(stringObjectEntry.getKey()));
                }*/
                KmsMain main = JSON.parseObject(JSON.toJSONString(sourceAsMap), KmsMain.class);
                rsList.add(main);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rsList;
    }

    public List<KmsMain> listVerson(Long id){
        KmsMain kmsMain = getById(id);
        List<KmsMain> kmsMains = baseMapper.selectList(new LambdaQueryWrapper<KmsMain>()
                .eq(KmsMain::getOriginId, kmsMain.getOriginId())
                .ne(KmsMain::getId,kmsMain.getId())
                .eq(KmsMain::getState,"30")
                .orderByAsc(KmsMain::getVersion));
        return kmsMains;
    }

    private void saveIndex(KmsMainDto kmsMainDto) throws IOException {
        String indexStatus = null;
        Long kId = kmsMainDto.getId();
        String knowStr = JSON.toJSONString(kmsMainDto);
        JSONObject knowObj = JSONObject.parseObject(knowStr);
        knowObj.put("id", String.valueOf(kId));
        IndexRequest esRequest = new IndexRequest("kms_index");
        esRequest.id(String.valueOf(kId));
        esRequest.source(knowObj.toJSONString(), XContentType.JSON);
        System.out.println("========保存数据：" + knowObj.toJSONString());
        esRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        IndexResponse indexResult = esClient.index(esRequest, RequestOptions.DEFAULT);
        indexStatus = indexResult.status().toString();
        System.out.println("========状态：" + indexStatus);
        System.out.println("========保存结果：" + indexResult);
    }
    private void updateIndex(KmsMain kmsMain){
        JSONObject kmsMainObj = (JSONObject) JSONObject.toJSON(kmsMain);
        kmsMainObj.put("createTime",kmsMain.getCreateTime()!=null?kmsMain.getCreateTime().getTime():0L);
        kmsMainObj.put("publishDate",kmsMain.getPublishDate()!=null?kmsMain.getPublishDate().getTime():0L);
        String updateIndex = "kms_index";
        UpdateRequest updateRequest = new UpdateRequest(updateIndex, kmsMain.getId().toString()).doc(kmsMainObj);
        updateRequest.docAsUpsert(true);
        UpdateResponse updateResponse = null;
        try {
            updateResponse = esClient.update(updateRequest, RequestOptions.DEFAULT);
            System.out.println("知识更新：" + updateResponse.status().toString());
        } catch (IOException e) {
            System.err.println(("知识更新异常..."));
            e.printStackTrace();
        }
    }

    /**
     * 提交流程申请
     *
     * @param kmsMain
     */
    private void submitProcessApply(KmsMain kmsMain){
        try{
            KmsCatalog kmsCatalog = kmsCatalogMapper.selectKmsCatalogById(kmsMain.getCatalogId());
            Long processTemplateId = kmsCatalog.getTemplateId();  //默认流程 28
            if(processTemplateId!=null) {
                SysActivitiTemplate sysActivitiTemplate = sysActivitiTemplateService.selectSysActivitiTemplateById(processTemplateId);
                ProcessInstance processInstance = sysActivitiTemplateService.startProcessInstanceByKey(sysActivitiTemplate.getProcessKey());
                kmsMain.setState(KmsConstant.StateEnum.STATE_EXAMINE.getCode()); //待审
                kmsMain.setPublishDate(null);
                kmsMain.setIsNewVersion(NEW_VERSION_NO); //不是最新版本
                kmsMain.setProcessInstanceId(processInstance.getId());
                baseMapper.updateKmsMain(kmsMain);
                getNextApproves2Readers(processInstance.getId(), kmsMain.getId());
            }
        }catch (Exception e){
            log.error("提交流程申请失败：{}", e);
            throw new RuntimeException("提交流程申请失败");
        }
    }



    /**
     * 获取下一个节点处理人并将审批人加入可阅读者中
     *
     * @param
     
    public void getNextApproves2Readers(String processInstanceId, Long kId) {
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        if (CollUtil.isNotEmpty(tasks)) {
            Task task = tasks.get(0);
            String name = task.getAssignee();
            SysUser sysUser = sysUserService.selectUserByUserName(name);
            List<Long> userIds = Lists.newArrayList();
            if(sysUser!=null){
                userIds.add(sysUser.getUserId());
            }
            //将审批人加入可阅读者中
            addMainReaders(kId, userIds);
        }
    }
    */
    public void getNextApproves2Readers(String processInstanceId, Long kId) {
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        if (CollUtil.isNotEmpty(tasks)) {
            for (Task task : tasks) {
                System.out.println("Task ID: " + task.getId());
                System.out.println("Task Name: " + task.getName());
                System.out.println("Task Assignee: " + task.getAssignee());
                //Task task = tasks.get(0);
                String name = task.getAssignee();
                SysUser sysUser = sysUserService.selectUserByUserName(name);
                List<Long> userIds = Lists.newArrayList();
                if(sysUser!=null){
                    userIds.add(sysUser.getUserId());
                }
                //将审批人加入可阅读者中
                addMainReaders(kId, userIds);
            }
        }
    }

    /**
     * 将审批人加入可阅读者表
     *
     * @param mainId
     * @param userIds
     * @throws Exception
     */
    @SuppressWarnings("unlikely-arg-type")
    private void addMainReaders(Long mainId, List<Long> userIds) {
        try {
            KmsMainUserDto readerDto = new KmsMainUserDto();
            List<KmsMainUserListDto> readerDtoList = Lists.newArrayList();
            List<String> readerIds = kmsMainMapper.selectUserIdsByParam(mainId); //已存在的可阅读者，避免重复插入

            userIds.stream().forEach(item -> {
                if (!readerIds.contains(item)) {
                    readerDtoList.add(new KmsMainUserListDto(mainId, item));
                }
            });
            if (CollUtil.isNotEmpty(readerDtoList)) {
                readerDto.setUserList(readerDtoList);
                kmsMainMapper.bachAddMainReaders(readerDto);
            }
        } catch (Exception e) {
            log.error("流程审批人加入可阅读者失败：{}", e);
            throw new RuntimeException("流程审批人加入可阅读者失败");
        }
    }

    public int startProcess(Long id){
        try{
            KmsMain kmsMain = new KmsMain();
            KmsMainVo kmsMainVo = selectKmsMainById(id);
            BeanUtils.copyProperties(kmsMainVo, kmsMain);
            submitProcessApply(kmsMain);
            return 1;
        }catch(Exception e){
            System.out.println("Start Process:\n" +e.getStackTrace());
        }
        return 0;
    }
}
