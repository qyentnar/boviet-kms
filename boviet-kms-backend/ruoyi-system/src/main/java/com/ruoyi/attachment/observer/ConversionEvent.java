package com.ruoyi.attachment.observer;

import org.springframework.context.ApplicationEvent;

/**
 * @author chenzh
 * @Date 1/10/22 11:52 AM
 */
public class ConversionEvent extends ApplicationEvent {

    public ConversionEvent(EventDefinition source) {
        super(source);
    }

}
