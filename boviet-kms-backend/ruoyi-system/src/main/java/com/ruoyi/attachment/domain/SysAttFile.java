package com.ruoyi.attachment.domain;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**
 * @author chenzh
 * @Date 1/5/22 10:59 AM
 */
@Data
public class SysAttFile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    private Long id;

    /** 文件路径*/
    @ApiModelProperty(value = "文件路径(不可预览)")
    private String filePath;

    /** 文件名称 */
    @ApiModelProperty("文件名称")
    private String fileName;

    /** 文件MD5值 */
    private String md5;

    /** 文件内容类型，如：application/msword */
    @ApiModelProperty("文件内容类型")
    private String contentType;

    /** 文件类型，如：doc/xls */
    @ApiModelProperty(value = "文件实际类型;根据文件流的头部信息获得文件类型")
    private String fileType;

    /** 文件大小 */
    @ApiModelProperty(value = "文件大小")
    private long fileSize;

    /** 见下方枚举 */
    @ApiModelProperty(value = "0-转换中;1-转换失败;2-转换成功;3-无需转换的文件", allowableValues = "0,1,2,3")
    private Integer state;

    /** 文档内容 */
    private String fileContent;

    /** 已转换成功的文件地址 */
    @ApiModelProperty(value = "转换后的附件预览地址")
    private String converted;

    @Getter
    public enum FileStateEnum{

        /**
         * 转换中
         */
        converting(0),

        /**
         * 转换失败
         */
        conversion_failed(1),

        /**
         * 转换成功
         */
        conversion_succeeded(2),

        /**
         * 无需转换的文档
         */
        unnecessary(3),

        ;

        private Integer state;


        FileStateEnum(Integer state) {
            this.state = state;
        }

        public Integer getState() {
            return this.state;
        }


    }

}
