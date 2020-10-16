package org.jeecg.modules.crm.groupfee.util;

import lombok.Data;
import lombok.experimental.Accessors;

import java.lang.reflect.Method;
@Data
@Accessors(chain = true)
public class EntityAttribute {
    private Method getMethod;
    private Method setMethod;
    private String attributeName;

}
